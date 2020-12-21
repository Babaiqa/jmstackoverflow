package com.javamentor.qa.platform.dao.abstracts.pagination;

import java.util.List;
import java.util.Map;

public interface PaginationDao<T> {

    List<T> getItems(Map<String, Object> parameters);
    int getCount(Map<String, Object> parameters);
}
