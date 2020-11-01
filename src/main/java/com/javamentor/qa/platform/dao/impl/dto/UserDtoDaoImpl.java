package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.UserDtoDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.models.dto.UserDtoList;
import netscape.security.UserTarget;
import org.hibernate.Session;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.security.PrivateKey;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class UserDtoDaoImpl implements UserDtoDao {

    private final int sizeListTagDto = 3;


    final String QUERY_USER_TAGS_ANSWERS = "select u.id as user_id, tA.id as tag_id,tA.name as tag_name, count(tA.id) as count_Tag" +
            " from User u  left join Answer a  on a.user.id=u.id join a.question.tags tA  where u.id in (:ids) " +
            "group by u.id,tA.id order by count_Tag,u.id";


    final String QUERY_USERDTOLIST_WITHOUT_TAG = "select new com.javamentor.qa.platform.models.dto.UserDtoList" +
            "(u.id,u.fullName, u.imageLink, sum(r.count)) from User u left join Reputation r on r.user.id=u.id" +
            " group by u.id order by sum(r.count) desc NULLS LAST,u.id";


    final String QUERY_USER_TAGS_QUESTIONS =
            "select u.id as user_id, tQ.id as tag_id,tQ.name as tag_name, count(tQ.id) as count_Tag" +
                    " from User u    left join Question q on q.user.id=u.id join q.tags tQ  where u.id in (:ids) " +
                    "group by u.id,tQ.id order by count_Tag,u.id desc";


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
    public List<UserDtoList> getPageUserDtoListByReputationOverPeriod(int page, int size, int quantityOfDay) {

        List<UserDtoList> userDtoLists = entityManager.unwrap(Session.class)
                .createQuery(QUERY_USERDTOLIST_WITHOUT_TAG)
                .unwrap(org.hibernate.query.Query.class)
                .setMaxResults(size)
                .getResultList();

        List<Long> usersIdsPage = userDtoLists.stream().map(i -> i.getId()).collect(Collectors.toList());

        List<TagDtoWithCount> ss = getTagDtoWithCount(usersIdsPage, QUERY_USER_TAGS_ANSWERS);





        return userDtoLists;
    }

    @Override
    public int getTotalResultCountUsers() {
        long totalResultCount = (long) entityManager.createQuery("select count(user) from User user").getSingleResult();
        return (int) totalResultCount;
    }



private List<UserDtoList> collectUserDtoList(List<UserDtoList> lists,List<TagDtoWithCount> listTagDto1,
                                             List<TagDtoWithCount> listTagDto2){

    Map<Long, TagDtoWithCount> r2 = listTagDtoQuestion.stream()
            .collect(Collectors.toMap(TagDtoWithCount::getUserId, tagDtoHelper -> tagDtoHelper));

    Map<Long, TagDtoWithCount> r3 = getTagDtoWithCount(usersIdsPage, QUERY_USER_TAGS_QUESTIONS).stream()
            .collect(Collectors.toMap(TagDtoWithCount::getUserId, tagDtoHelper -> tagDtoHelper));

    r2.forEach((userId, tagDtoHelper) -> {
        r3.get(userId).tagDtoMap.forEach((tagDto, count) -> {
            tagDtoHelper.tagDtoMap.merge(tagDto, count, (v1, v2) -> v1 + v2);
        });
    });


    userDtoLists.stream().forEach(i -> {

                if (r3.containsKey(i.getId())) {
                    i.setTags(
                            r3.get(i.getId()).tagDtoMap.entrySet().stream()
                                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                                    .map(Map.Entry::getKey)
                                    .limit(3)
                                    .collect(Collectors.toList()));
                }
            }
    );


}



    private List<TagDtoWithCount> getTagDtoWithCount(List<Long> usersIds, String query) {
        return entityManager.unwrap(Session.class)
                .createQuery(query)
                .setParameterList("ids", usersIds)
                .unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(new tagDtoWithCountTranformer())
                .getResultList();
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


            if (tagDtoHelper.tagDtoMap.size() < sizeListTagDto) {
                tagDtoHelper.tagDtoMap.put(
                        new TagDto(
                                ((Number) tuple[aliasToIndexMap.get("tag_id")]).longValue(),
                                ((String) tuple[aliasToIndexMap.get("tag_name")])
                        ),
                        ((Number) tuple[aliasToIndexMap.get("count_Tag")]).intValue()
                );
            }
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


