package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.UserDtoDao;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.models.entity.user.User;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UserDtoDaoImpl implements UserDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    //Запрос возвращает List USERDTO
    final String QUERY_USERDTO ="select user_entity.id as user_entity_id, " +
            " user_entity.full_name as user_entity_full_name," +
            " user_entity.email as user_entity_email," +
            " user_entity.image_link as user_entity_image_link," +
            " user_entity.reputation_count as user_entity_reputation_count";


//            " user_entity.about as user_entity_about," +
//            " user_entity.city as user_entity_city," +
//            " user_entity.is_enabled as user_entity_is_enabled," +
//            " user_entity.last_redaction_date as user_entity_last_redaction_date," +
//            " user_entity.link_github as user_entity_link_github," +
//            " user_entity.link_site as user_entity_link_site," +
//            " user_entity.link_vk as user_entity_link_vk," +
//            " user_entity.password as user_entity_password," +
//            " user_entity.persist_date as user_entity_persist_date," +
//            " user_entity.reputation_count as user_entity_reputation_count," +
//            "(select count(a.user_entity.id) from Role a where a.user_entity.id=:id) as user_countRole,"


    @Override
    public Optional<List<UserDto>> getUserDtoByName(String name) {
       // entityManager.createQuery("FROM User AS i WHERE i.fullName LIKE '%" + name + "%'", User.class).getResultList(); select i.fullName as fullName
      //  System.out.println(entityManager.createQuery("select user.fullName as fullname FROM User user WHERE user.fullName LIKE '%" + name + "%'"));  " from Question question " +
        List<UserDto> userDtoList = entityManager.createQuery("select user.fullName as fullName, user.id as id, user.email as email," +
                " user.imageLink as imageLink, user.reputationCount as reputationCount," +
                " tag.id as tag_id, tag.name as tag_name " +
                        " FROM User user, Question question " + " INNER JOIN  question.user" +
                                " join question.tags tag"  + " WHERE user.fullName LIKE '%" + name + "%' "
                        )
                .unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(Transformers.aliasToBean(UserDto.class))
                .getResultList();

        return Optional.ofNullable(userDtoList);
    }

}
