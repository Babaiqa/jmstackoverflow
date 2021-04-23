package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.BookMarksDao;
import com.javamentor.qa.platform.models.entity.BookMarks;
import com.javamentor.qa.platform.service.abstracts.model.BookMarksService;

public class BookMarksServiceImpl extends ReadWriteServiceImpl<BookMarks, Long> implements BookMarksService {

    public BookMarksServiceImpl(BookMarksDao bookMarksDao) {
        super(bookMarksDao);
    }
}
