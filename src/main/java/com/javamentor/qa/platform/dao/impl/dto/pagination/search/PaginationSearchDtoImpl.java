package com.javamentor.qa.platform.dao.impl.dto.pagination.search;

import com.javamentor.qa.platform.dao.abstracts.dto.pagination.PaginationDao;
import com.javamentor.qa.platform.dao.search.predicates.PredicateFactoryBean;
import com.javamentor.qa.platform.models.dto.FoundEntryDto;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.util.FoundEntryType;
import org.hibernate.search.engine.search.query.SearchFetchable;
import org.hibernate.search.engine.search.sort.SearchSort;
import org.hibernate.search.engine.search.sort.dsl.SearchSortFactory;
import org.hibernate.search.engine.search.sort.dsl.SortOrder;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.scope.SearchScope;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository(value = "paginationSearch")
public class PaginationSearchDtoImpl implements PaginationDao<FoundEntryDto> {

    @PersistenceContext
    private EntityManager entityManager;

    private PredicateFactoryBean predicateFactoryBean;

    @Autowired
    public void setPredicateFactoryBean(PredicateFactoryBean predicateFactoryBean) {
        this.predicateFactoryBean = predicateFactoryBean;
    }

    @Override
    public List<FoundEntryDto> getItems(Map<String, Object> parameters) {
        int page = (int) parameters.get("page");
        int size = (int) parameters.get("size");
        String sort = (String) parameters.get("sort");
        String order = (String) parameters.get("order");
        String query = (String) parameters.get("query");

        return getFoundEntryDtos(query, page * size - size, size, sort, order);
    }

    @Override
    public int getCount(Map<String, Object> parameters) {
        int page = (int) parameters.get("page");
        int size = (int) parameters.get("size");
        String sort = (String) parameters.get("sort");
        String order = (String) parameters.get("order");
        String query = (String) parameters.get("query");

        return fetchFoundEntriesTotal(query, page * size - size, size, sort, order);
    }

    private SearchFetchable<List<?>> search(String query, String field, String order) {

        SearchSession searchSession = Search.session(entityManager);

        SearchScope<Serializable> scope = searchSession.scope(Arrays.asList(Question.class, Answer.class));

        SearchFetchable<List<?>> result = searchSession
                .search(scope)
                .select(f -> f.composite(
                        f.field("id", Long.class),
                        f.field("title", String.class),
                        f.field("description", String.class),
                        f.field("answersCount", Long.class),
                        f.field("tags.id", Long.class).multi(),
                        f.field("tags.name", String.class).multi(),
                        f.field("tags.description", String.class).multi(),

                        f.field("answerId", Long.class),
                        f.field("question.id", Long.class),
                        f.field("question.title", String.class),
                        f.field("htmlBody", String.class),
                        f.field("question.tags.id", Long.class).multi(),
                        f.field("question.tags.name", String.class).multi(),
                        f.field("question.tags.description", String.class).multi(),

                        f.field("user.id", Long.class),
                        f.field("user.fullName", String.class),
                        f.field("persistDateTime", LocalDateTime.class),
                        f.field("votesCount", Long.class),

                        f.field("viewCount", Integer.class)

                ))
                .where(predicateFactoryBean.getPredicate(scope, query))
                .sort(sortBy(scope, field, order)
                );

        return result;
    }

    private SearchSort sortBy(SearchScope scope, String field, String order) {
        SearchSortFactory sort = scope.sort();
        SortOrder sortOrder = (order.equals("asc")) ? SortOrder.ASC : SortOrder.DESC;

        if (!isSortable(field)) {
            return sort.score().toSort();
        }

        return scope.sort().field(field).order(sortOrder).toSort();
    }

    private boolean isSortable(String field) {
        return Stream.of("persistDateTime",
                "votesCount",
                "answersCount")
                .collect(Collectors.toSet())
                .contains(field);
    }

    private List<List<?>> fetchFoundEntries(String query, int offset, int limit, String field, String order) {
        return search(query, field, order).fetchHits(offset, limit);
    }

    private Integer fetchFoundEntriesTotal(String query, int offset, int limit, String field, String order) {
        return Math.toIntExact(search(query, field, order).fetchTotalHitCount());
    }

    private List<FoundEntryDto> getFoundEntryDtos(String query, int offset, int limit, String field, String order) {
        List<List<?>> hits = fetchFoundEntries(query, offset, limit, field, order);

        List<FoundEntryDto> foundEntryDtos = hits.stream().map(hit -> {
            if (hit.get(0) != null) {
                return getQuestionEntry(hit);
            }

            return getAnswerEntry(hit);
        }).collect(Collectors.toList());

        return foundEntryDtos;
    }

    private FoundEntryDto getQuestionEntry(List<?> hit) {
        return FoundEntryDto.builder()
                .questionId((Long) hit.get(0))
                .title((String) hit.get(1))
                .description((String) hit.get(2))
                .answersCount((Long) hit.get(3))
                .tags(getTagDtos(
                        (List<Long>) hit.get(4),
                        (List<String>) hit.get(5),
                        (List<String>) hit.get(6)))
                .authorId((Long) hit.get(14))
                .authorName((String) hit.get(15))
                .persistDate((LocalDateTime) hit.get(16))
                .votesCount((Long) hit.get(17))
                .type(FoundEntryType.QUESTION)
                .build();
    }

    private FoundEntryDto getAnswerEntry(List<?> hit) {
        return FoundEntryDto.builder()
                .answerId((Long) hit.get(7))
                .questionId((Long) hit.get(8))
                .title((String) hit.get(9))
                .description((String) hit.get(10))
                .tags(getTagDtos(
                        (List<Long>) hit.get(11),
                        (List<String>) hit.get(12),
                        (List<String>) hit.get(13)))
                .authorId((Long) hit.get(14))
                .authorName((String) hit.get(15))
                .persistDate((LocalDateTime) hit.get(16))
                .votesCount((Long) hit.get(17))
                .type(FoundEntryType.ANSWER)
                .build();
    }

    private List<TagDto> getTagDtos(List<Long> tagIds, List<String> tagNames, List<String> tagDescriptions) {
        List<TagDto> tagDtoList = new ArrayList<>();
        for (int i = 0; i < tagIds.size(); i++) {
            tagDtoList.add(new TagDto(
                    tagIds.get(i),
                    tagNames.get(i),
                    tagDescriptions.get(i)));
        }

        return tagDtoList;
    }

}
