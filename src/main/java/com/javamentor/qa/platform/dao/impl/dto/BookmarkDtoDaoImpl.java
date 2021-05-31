package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.BookmarkDtoDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.dto.BookmarkDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class BookmarkDtoDaoImpl implements BookmarkDtoDao {

    @PersistenceContext
    private final EntityManager entityManager;

    public BookmarkDtoDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<BookmarkDto> getBookmarkByUserId(Long id) {
        TypedQuery<BookmarkDto> query = entityManager.createQuery(
                "SELECT new com.javamentor.qa.platform.models.dto.BookmarkDto(b.id, b.user.id, b.question.title, b.question.voteQuestions.size) " +
                        "FROM BookMarks as b " +
                        "INNER JOIN User u ON u.id = b.user.id " +
                        "INNER JOIN Question q ON q.title = b.question.title " +
                        "WHERE u.id =: userId", BookmarkDto.class)
                .setParameter("userId", id);
        return query.getResultList();
    }
}
