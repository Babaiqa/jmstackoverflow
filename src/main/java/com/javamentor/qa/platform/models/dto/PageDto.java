package com.javamentor.qa.platform.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class PageDto<T, V>  implements Serializable {
        private int currentPageNumber;
        private  int totalPageCount;
        private int totalResultCount;
        private List<T> items;
        private List<V> meta;
        private static final int itemsOnPage = 12;
}
