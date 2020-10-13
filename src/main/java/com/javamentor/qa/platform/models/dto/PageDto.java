package com.javamentor.qa.platform.models.dto;

import java.io.Serializable;
import java.util.List;

public class PageDto<T, V>  implements Serializable {
        private int currentPageNumber;
        private  int totalPageCount;
        private int totalResultCount;
        private List<T> items;
        private List<V> meta;
        private static final int itemsOnPage = 12;

        public PageDto() {
                super();
        }

        public PageDto(int currentPageNumber, int totalPageCount, int totalResultCount, List<T> items, List<V> meta) {
                this.currentPageNumber = currentPageNumber;
                this.totalPageCount = totalPageCount;
                this.totalResultCount = totalResultCount;
                this.items = items;
                this.meta = meta;
        }

        public int getCurrentPageNumber() {
                return currentPageNumber;
        }

        public void setCurrentPageNumber(int currentPageNumber) {
                this.currentPageNumber = currentPageNumber;
        }

        public int getTotalPageCount() {
                return totalPageCount;
        }

        public void setTotalPageCount(int totalPageCount) {
                this.totalPageCount = totalPageCount;
        }

        public int getTotalResultCount() {
                return totalResultCount;
        }

        public void setTotalResultCount(int totalResultCount) {
                this.totalResultCount = totalResultCount;
        }

        public List<T> getItems() {
                return items;
        }

        public void setItems(List<T> items) {
                this.items = items;
        }

        public List<V> getMeta() {
                return meta;
        }

        public void setMeta(List<V> meta) {
                this.meta = meta;
        }

        public int getItemsOnPage() {
                return itemsOnPage;
        }
}
