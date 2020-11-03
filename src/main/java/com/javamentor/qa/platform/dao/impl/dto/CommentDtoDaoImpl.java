package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.CommentDtoDao;
import com.javamentor.qa.platform.models.dto.CommentDto;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class CommentDtoDaoImpl implements CommentDtoDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Optional<CommentDto> getCommentDtoById(Long commentId) {

        return entityManager.unwrap(Session.class).createQuery("select " +
                "new com.javamentor.qa.platform.models.dto.CommentDto(" +
                " c.id, c.text," +
                "c.persistDateTime, c.lastUpdateDateTime, c.commentType," +
                "u.id , u.fullName , sum(r.count)) " +
                "from Comment c " +
                "join User u on u.id= c.user.id " +
                "left join Reputation r on r.user.id=u.id " +
                "where c.id=(:ids) group by c,u,r ")
                .setParameter("ids", commentId)
                .unwrap(org.hibernate.query.Query.class)
                .uniqueResultOptional();
    }
}
