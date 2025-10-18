package com.formmanagement.dto;

import com.formmanagement.model.Response;

import java.time.LocalDateTime;
import java.util.List;

public class ResponseDto {
    
    private Long id;
    private Long formId;
    private Long userId;
    private String respondentEmail;
    private String respondentName;
    private String ipAddress;
    private LocalDateTime submittedAt;
    private Boolean isDuplicate;
    private List<ResponseEntryDto> entries;
    
    // Constructors
    public ResponseDto() {}
    
    public ResponseDto(Response response) {
        this.id = response.getId();
        this.formId = response.getForm().getId();
        this.userId = response.getUser() != null ? response.getUser().getId() : null;
        this.respondentEmail = response.getRespondentEmail();
        this.respondentName = response.getRespondentName();
        this.ipAddress = response.getIpAddress();
        this.submittedAt = response.getSubmittedAt();
        this.isDuplicate = response.getIsDuplicate();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getFormId() {
        return formId;
    }
    
    public void setFormId(Long formId) {
        this.formId = formId;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public String getRespondentEmail() {
        return respondentEmail;
    }
    
    public void setRespondentEmail(String respondentEmail) {
        this.respondentEmail = respondentEmail;
    }
    
    public String getRespondentName() {
        return respondentName;
    }
    
    public void setRespondentName(String respondentName) {
        this.respondentName = respondentName;
    }
    
    public String getIpAddress() {
        return ipAddress;
    }
    
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    
    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }
    
    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }
    
    public Boolean getIsDuplicate() {
        return isDuplicate;
    }
    
    public void setIsDuplicate(Boolean isDuplicate) {
        this.isDuplicate = isDuplicate;
    }
    
    public List<ResponseEntryDto> getEntries() {
        return entries;
    }
    
    public void setEntries(List<ResponseEntryDto> entries) {
        this.entries = entries;
    }
}
