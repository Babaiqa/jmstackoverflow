package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.BookmarkDtoDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.dto.BookmarkDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class BookmarkDtoDaoImpl implements BookmarkDtoDao {

    @PersistenceContext
    private final EntityManager entityManager;

    public BookmarkDtoDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<BookmarkDto> getBookmarkByUserId(Long id) {
        TypedQuery<BookmarkDto> query = entityManager.createQuery(
                "SELECT new com.javamentor.qa.platform.models.dto.BookmarkDto(b.id, b.user, b.question)" +
                        "FROM BookMarks b WHERE b.user.id =: userId", BookmarkDto.class)
                .setParameter("userId", id);
        return SingleResultUtil.getSingleResultOrNull(query);
    }
}
