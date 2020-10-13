package com.javamentor.qa.platform.models.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class QuestionDto implements Serializable {
    private Long id;
    private String title;
    private Long authorId;
    private String authorName;
    private String authorImage;
    private String description;
    private int viewCount;
    private int countAnswer;
    private int countValuable;
    private LocalDateTime persistDateTime;
    private LocalDateTime lastUpdateDateTime;
    private Boolean isHelpful;

    public QuestionDto() {
        super();
    }

    public QuestionDto(Long id, String title, Long authorId, String authorName, String authorImage, String description,
                       int viewCount, int countAnswer, int countValuable, LocalDateTime persistDateTime, LocalDateTime lastUpdateDateTime,
                       Boolean isHelpful) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.authorName = authorName;
        this.authorImage = authorImage;
        this.description = description;
        this.viewCount = viewCount;
        this.countAnswer = countAnswer;
        this.countValuable = countValuable;
        this.persistDateTime = persistDateTime;
        this.lastUpdateDateTime = lastUpdateDateTime;
        this.isHelpful = isHelpful;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorImage() {
        return authorImage;
    }

    public void setAuthorImage(String authorImage) {
        this.authorImage = authorImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getCountAnswer() {
        return countAnswer;
    }

    public void setCountAnswer(int countAnswer) {
        this.countAnswer = countAnswer;
    }

    public int getCountValuable() {
        return countValuable;
    }

    public void setCountValuable(int countValuable) {
        this.countValuable = countValuable;
    }

    public LocalDateTime getPersistDateTime() {
        return persistDateTime;
    }

    public void setPersistDateTime(LocalDateTime persistDateTime) {
        this.persistDateTime = persistDateTime;
    }

    public LocalDateTime getLastUpdateDateTime() {
        return lastUpdateDateTime;
    }

    public void setLastUpdateDateTime(LocalDateTime lastUpdateDateTime) {
        this.lastUpdateDateTime = lastUpdateDateTime;
    }

    public Boolean isHelpful() {
        return isHelpful;
    }

    public void setHelpful(Boolean helpful) {
        isHelpful = helpful;
    }
}

