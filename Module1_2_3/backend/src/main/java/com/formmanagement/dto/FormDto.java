package com.formmanagement.dto;

import com.formmanagement.model.Form;
import com.formmanagement.model.FormField;

import java.time.LocalDateTime;
import java.util.List;

public class FormDto {
    
    private Long id;
    private String title;
    private String description;
    private Long creatorId;
    private String creatorUsername;
    private Boolean isActive;
    private Boolean isPublic;
    private Integer submissionLimit;
    private Boolean allowDuplicate;
    private Boolean requireLogin;
    private LocalDateTime expiresAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<FormFieldDto> fields;
    private Long responseCount;
    
    // Constructors
    public FormDto() {}
    
    public FormDto(Form form) {
        this.id = form.getId();
        this.title = form.getTitle();
        this.description = form.getDescription();
        this.creatorId = form.getCreator().getId();
        this.creatorUsername = form.getCreator().getUsername();
        this.isActive = form.getIsActive();
        this.isPublic = form.getIsPublic();
        this.submissionLimit = form.getSubmissionLimit();
        this.allowDuplicate = form.getAllowDuplicate();
        this.requireLogin = form.getRequireLogin();
        this.expiresAt = form.getExpiresAt();
        this.createdAt = form.getCreatedAt();
        this.updatedAt = form.getUpdatedAt();
    }
    
    // Getters and Setters
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
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Long getCreatorId() {
        return creatorId;
    }
    
    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }
    
    public String getCreatorUsername() {
        return creatorUsername;
    }
    
    public void setCreatorUsername(String creatorUsername) {
        this.creatorUsername = creatorUsername;
    }
    
    public Boolean getIsActive() {
        return isActive;
    }
    
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    
    public Boolean getIsPublic() {
        return isPublic;
    }
    
    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }
    
    public Integer getSubmissionLimit() {
        return submissionLimit;
    }
    
    public void setSubmissionLimit(Integer submissionLimit) {
        this.submissionLimit = submissionLimit;
    }
    
    public Boolean getAllowDuplicate() {
        return allowDuplicate;
    }
    
    public void setAllowDuplicate(Boolean allowDuplicate) {
        this.allowDuplicate = allowDuplicate;
    }
    
    public Boolean getRequireLogin() {
        return requireLogin;
    }
    
    public void setRequireLogin(Boolean requireLogin) {
        this.requireLogin = requireLogin;
    }
    
    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }
    
    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public List<FormFieldDto> getFields() {
        return fields;
    }
    
    public void setFields(List<FormFieldDto> fields) {
        this.fields = fields;
    }
    
    public Long getResponseCount() {
        return responseCount;
    }
    
    public void setResponseCount(Long responseCount) {
        this.responseCount = responseCount;
    }
}
