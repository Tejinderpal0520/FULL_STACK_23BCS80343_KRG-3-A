package com.formmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "forms")
public class Form {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Size(max = 200)
    private String title;
    
    @Size(max = 1000)
    private String description;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @Column(name = "is_public")
    private Boolean isPublic = true;
    
    @Column(name = "submission_limit")
    private Integer submissionLimit;
    
    @Column(name = "allow_duplicate")
    private Boolean allowDuplicate = true;
    
    @Column(name = "require_login")
    private Boolean requireLogin = false;
    
    @Column(name = "expires_at")
    private LocalDateTime expiresAt;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "form", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FormField> fields;
    
    @OneToMany(mappedBy = "form", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Response> responses;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Constructors
    public Form() {}
    
    public Form(String title, String description, User creator) {
        this.title = title;
        this.description = description;
        this.creator = creator;
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
    
    public User getCreator() {
        return creator;
    }
    
    public void setCreator(User creator) {
        this.creator = creator;
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
    
    public List<FormField> getFields() {
        return fields;
    }
    
    public void setFields(List<FormField> fields) {
        this.fields = fields;
    }
    
    public List<Response> getResponses() {
        return responses;
    }
    
    public void setResponses(List<Response> responses) {
        this.responses = responses;
    }
}
