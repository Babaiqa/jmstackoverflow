package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.UserDtoDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.models.dto.UserDtoList;
import org.hibernate.Session;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class UserDtoDaoImpl implements UserDtoDao {


   private static final  String QUERY_USER_TAGS_ANSWERS = "select u.id as user_id, t.id as tag_id,t.name as tag_name, count(t.id) as count_Tag" +
            " from User u  left join Answer a  on a.user.id=u.id join a.question.tags t  where u.id in (:ids) ";


    private static final  String QUERY_USERDTOLIST_WITHOUT_TAG = "select new com.javamentor.qa.platform.models.dto.UserDtoList" +
            "(u.id,u.fullName, u.imageLink, sum(r.count)) from User u left join Reputation r on r.user.id=u.id";


    private static final  String QUERY_USER_TAGS_QUESTIONS =
            "select u.id as user_id, t.id as tag_id,t.name as tag_name, count(t.id) as count_Tag" +
                    " from User u    left join Question q on q.user.id=u.id join q.tags t  where u.id in (:ids)";


    @PersistenceContext
    private final EntityManager entityManager;

    public UserDtoDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public Optional<UserDto> getUserById(long id) {
        TypedQuery<UserDto> q = entityManager.createQuery(
                "select new com.javamentor.qa.platform.models.dto.UserDto(u.id,  u.email, u.fullName, u.imageLink, u.reputationCount) from " +
                        " User u where u.id = :userId", UserDto.class)
                .setParameter("userId", id);
        return SingleResultUtil.getSingleResultOrNull(q);
    }

    @Override
    public int getTotalResultCountUsers() {
        return ((Number) entityManager.createQuery("select count(user) from User user").getSingleResult()).intValue();
    }


    @Override
    public List<UserDtoList> getPageUserDtoListByReputationOverPeriod(int page, int size, int quantityOfDay) {

        List<UserDtoList> userDtoLists = entityManager.unwrap(Session.class)
                .createQuery(QUERY_USERDTOLIST_WITHOUT_TAG + " and current_date-(:quantityOfDays)<date(r.persistDate) " +
                        "group by u.id order by sum(r.count) desc NULLS LAST,u.id")
                .setParameter("quantityOfDays", quantityOfDay)
                .unwrap(org.hibernate.query.Query.class)
                .setFirstResult(page * size - size)
                .setMaxResults(size)
                .getResultList();

        List<Long> usersIdsPage = userDtoLists.stream().map(UserDtoList::getId).collect(Collectors.toList());

        return collectUserDtoListWithTagDto(userDtoLists,
                getListTagDtoWithCountOverPeriod(usersIdsPage,QUERY_USER_TAGS_ANSWERS, quantityOfDay),
                getListTagDtoWithCountOverPeriod(usersIdsPage,QUERY_USER_TAGS_QUESTIONS, quantityOfDay)
              );
    }


    private List<TagDtoWithCount>  getListTagDtoWithCountOverPeriod(List<Long> usersIds, String query, int quantityOfDay ){
        return entityManager.unwrap(Session.class)
                .createQuery(query + " and current_date-(:quantityOfDays)<date(q.persistDateTime)" +
                        " group by u.id,t.id order by count_Tag desc,t.id")
                .setParameterList("ids", usersIds)
                .setParameter("quantityOfDays", quantityOfDay)
                .unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(new tagDtoWithCountTranformer())
                .getResultList();
    }


    //Объединение тэгов вопросов и ответов и доавление к UserDtoList трех TagDto, которые наиболее часто встречаются у юзера
    private List<UserDtoList> collectUserDtoListWithTagDto(List<UserDtoList> userDtoLists, List<TagDtoWithCount> listTagDtoAnswer,
                                                           List<TagDtoWithCount> listTagDtoQuestion) {

        Map<Long, TagDtoWithCount> mapTagDto = listTagDtoQuestion.stream()
                .collect(Collectors.toMap(TagDtoWithCount::getUserId, tagDtoHelper -> tagDtoHelper));


        listTagDtoAnswer.forEach(tagDtoWithCount -> {
                    if (mapTagDto.containsKey(tagDtoWithCount.getUserId())) {
                        tagDtoWithCount.tagDtoMap.forEach((tagDto, count) -> {
                            mapTagDto.get(tagDtoWithCount.userId).tagDtoMap.merge(tagDto, count, Integer::sum);
                        });
                    } else {
                        mapTagDto.put(tagDtoWithCount.userId, tagDtoWithCount);
                    }
                }
        );



        userDtoLists.forEach(i -> {
                    if (mapTagDto.containsKey(i.getId())) {
                        i.setTags(
                                mapTagDto.get(i.getId()).tagDtoMap.entrySet().stream()
                                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                                        .map(Map.Entry::getKey)
                                        .limit(3)
                                        .collect(Collectors.toList()));
                    } else {
                        i.setTags(new ArrayList<>());
                    }
                }
        );

        return userDtoLists;
    }


    class tagDtoWithCountTranformer implements ResultTransformer {

        private Map<Long, TagDtoWithCount> tagDtoHelperDtoMap = new LinkedHashMap<>();

        @Override
        public Object transformTuple(Object[] tuple, String[] aliases) {

            Map<String, Integer> aliasToIndexMap = aliasToIndexMap(aliases);
            Long userId = ((Number) tuple[0]).longValue();

            TagDtoWithCount tagDtoHelper = tagDtoHelperDtoMap.computeIfAbsent(
                    userId,
                    id1 -> {
                        TagDtoWithCount tagDtoHelperTemp = new TagDtoWithCount();
                        tagDtoHelperTemp.setUserId(((Number) tuple[aliasToIndexMap.get("user_id")]).longValue());
                        tagDtoHelperTemp.setTagDto(new LinkedHashMap<>());
                        return tagDtoHelperTemp;
                    }
            );

                tagDtoHelper.tagDtoMap.put(
                        new TagDto(
                                ((Number) tuple[aliasToIndexMap.get("tag_id")]).longValue(),
                                ((String) tuple[aliasToIndexMap.get("tag_name")])
                        ),
                        ((Number) tuple[aliasToIndexMap.get("count_Tag")]).intValue()
                );
            return tagDtoHelper;
        }

        @Override
        public List transformList(List list) {
            return new ArrayList<>(tagDtoHelperDtoMap.values());
        }

        public Map<String, Integer> aliasToIndexMap(
                String[] aliases) {

            Map<String, Integer> aliasToIndexMap = new LinkedHashMap<>();

            for (int i = 0; i < aliases.length; i++) {
                aliasToIndexMap.put(aliases[i], i);
            }
            return aliasToIndexMap;
        }
    }


    
    class TagDtoWithCount {
        Long userId;
        Map<TagDto, Integer> tagDtoMap;

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public Map<TagDto, Integer> getTagDto() {
            return tagDtoMap;
        }

        public void setTagDto(Map<TagDto, Integer> tagDto) {
            this.tagDtoMap = tagDto;
        }
    }
}


