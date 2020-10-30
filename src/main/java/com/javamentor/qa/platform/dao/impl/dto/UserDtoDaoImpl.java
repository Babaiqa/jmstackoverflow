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
import javax.persistence.TypedQuery;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class UserDtoDaoImpl implements UserDtoDao {


    private final String QUERY_USERDTOLIST_BY_REPUTATION =
            " select u.id as user_id," +
                    "sum(r.count) as user_reputation," +
                    "t.id as tag_id," +
                    "t.name as tag_name," +

                    "(select count(tA.id) from Answer ae join ae.question.tags tA where ae.user.id=u.id and " +
                    " tA.id=t.id  and  current_date()-:quantityOfDay<date(ae.persistDateTime)) as tags_User_Answers," +
                    "(select count(tQ.id) from Question qe join qe.tags tQ where qe.user.id=u.id and tQ.id=t.id and " +
                    " current_date()-:quantityOfDay<date(qe.persistDateTime)) as tags_User_Questions " +

                    "from User u,Tag t left join Reputation r on u.id=r.user.id " +
                    "where " +
                    "(exists(select a from Answer a where a.user.id=u.id and t in elements(a.question.tags) " +
                    "and  current_date()-:quantityOfDay<date(a.persistDateTime)) " +
                    "or " +
                    "exists(select q from Question q where q.user.id=u.id and t in elements(q.tags) and " +
                    " current_date()-:quantityOfDay<date(q.persistDateTime) )) " +
                    "and u.id in(:ids)  " +
                    "group by u.id,t.id order by user_reputation  desc NULLS LAST,u.id";


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
        List<Long> usersIdsPage = getUsersIdsPage(page, size);
        return entityManager.unwrap(Session.class)
                .createQuery(QUERY_USERDTOLIST_BY_REPUTATION)
                .setParameter("quantityOfDay",quantityOfDay)
                .setParameterList("ids", usersIdsPage)
                .unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(new userDtoListTranformer())
                .getResultList();
    }

    @Override
    public int getTotalResultCountUsers() {
        long totalResultCount = (long) entityManager.createQuery("select count(user) from User user").getSingleResult();
        return (int) totalResultCount;
    }

    private List<Long> getUsersIdsPage(int page, int size) {
        return entityManager.unwrap(Session.class)
                .createQuery(
                        "select u.id  from User u left join Reputation r on r.user.id=u.id" +
                                " group by u.id order by sum(r.count) desc NULLS LAST,u.id"
                )
                .unwrap(org.hibernate.query.Query.class)
                .setMaxResults(12)
                .getResultList();
    }


    class userDtoListTranformer implements ResultTransformer {
        Long prevUserId = -1L;
        boolean flagFirstUser = true;
        private Map<Long, UserDtoList> questionDtoMap = new LinkedHashMap<>();
        Map<TagDto, Integer> mapTags = new HashMap<>();

        @Override
        public Object transformTuple(Object[] tuple, String[] aliases) {


            Map<String, Integer> aliasToIndexMap = aliasToIndexMap(aliases);
            Long userId = ((Number) tuple[0]).longValue();

            UserDtoList userDtoList = questionDtoMap.computeIfAbsent(
                    userId,
                    id1 -> {
                        UserDtoList userDtoListTemp = new UserDtoList();
                        userDtoListTemp.setId(((Number) tuple[aliasToIndexMap.get("user_id")]).longValue());
                        userDtoListTemp.setReputation(((Number) tuple[aliasToIndexMap.get("user_reputation")]).longValue());
                        userDtoListTemp.setTags(new LinkedList<>());
                        return userDtoListTemp;
                    }
            );


            if (!prevUserId.equals(userId) && !flagFirstUser) {
                mapTagsDtoToSortedListTagsDto(prevUserId);
            }


            mapTags.put(
                    new TagDto(
                            ((Number) tuple[aliasToIndexMap.get("tag_id")]).longValue(),
                            ((String) tuple[aliasToIndexMap.get("tag_name")])
                    ),
                    ((Number) tuple[aliasToIndexMap.get("tags_User_Answers")]).intValue() +
                            ((Number) tuple[aliasToIndexMap.get("tags_User_Questions")]).intValue()
            );

            prevUserId = userId;
            flagFirstUser = false;
            return userDtoList;
        }


        @Override
        public List<UserDtoList> transformList(List list) {
            mapTagsDtoToSortedListTagsDto(prevUserId);
            return new ArrayList<>(questionDtoMap.values());
        }


        private void mapTagsDtoToSortedListTagsDto(Long prevUserId) {
            questionDtoMap.get(prevUserId).setTags(
                    mapTags.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                            .map(Map.Entry::getKey)
                            .limit(3)
                            .collect(Collectors.toList()));
            mapTags.clear();

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


}


