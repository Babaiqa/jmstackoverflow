package com.javamentor.qa.platform.models.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class UserProfileDto implements Serializable {
    private Long id;
    private String fullName;
    private String password;
    private LocalDateTime persist_date;
    private Long role_id;
    private LocalDateTime last_redaction_date;
    private String email;
    private String about;
    private String city;
    private String link_site;
    private String link_github;
    private String link_vk;
    private int reputation_count;
    private Boolean is_enable;
    private byte[] image;

    public UserProfileDto() {
        super();
    }

    public UserProfileDto(Long id, String fullName, String password, LocalDateTime persist_date, Long role_id,
                                        LocalDateTime last_redaction_date, String email, String about, String city,
                                        String link_site, String link_github, String link_vk, int reputation_count,
                                        Boolean is_enable, byte[] image) {
        this.id = id;
        this.fullName = fullName;
        this.password = password;
        this.persist_date = persist_date;
        this.role_id = role_id;
        this.last_redaction_date = last_redaction_date;
        this.email = email;
        this.about = about;
        this.city = city;
        this.link_site = link_site;
        this.link_github = link_github;
        this.link_vk = link_vk;
        this.reputation_count = reputation_count;
        this.is_enable = is_enable;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getPersist_date() {
        return persist_date;
    }

    public void setPersist_date(LocalDateTime persist_date) {
        this.persist_date = persist_date;
    }

    public Long getRole_id() {
        return role_id;
    }

    public void setRole_id(Long role_id) {
        this.role_id = role_id;
    }

    public LocalDateTime getLast_redaction_date() {
        return last_redaction_date;
    }

    public void setLast_redaction_date(LocalDateTime last_redaction_date) {
        this.last_redaction_date = last_redaction_date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLink_site() {
        return link_site;
    }

    public void setLink_site(String link_site) {
        this.link_site = link_site;
    }

    public String getLink_github() {
        return link_github;
    }

    public void setLink_github(String link_github) {
        this.link_github = link_github;
    }

    public String getLink_vk() {
        return link_vk;
    }

    public void setLink_vk(String link_vk) {
        this.link_vk = link_vk;
    }

    public int getReputation_count() {
        return reputation_count;
    }

    public void setReputation_count(int reputation_count) {
        this.reputation_count = reputation_count;
    }

    public Boolean getIs_enable() {
        return is_enable;
    }

    public void setIs_enable(Boolean is_enable) {
        this.is_enable = is_enable;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
