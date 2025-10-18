package com.formmanagement.dto;

import com.formmanagement.model.ResponseEntry;

public class ResponseEntryDto {
    
    private Long id;
    private Long responseId;
    private Long formFieldId;
    private String fieldValue;
    
    // Constructors
    public ResponseEntryDto() {}
    
    public ResponseEntryDto(ResponseEntry responseEntry) {
        this.id = responseEntry.getId();
        this.responseId = responseEntry.getResponse().getId();
        this.formFieldId = responseEntry.getFormField().getId();
        this.fieldValue = responseEntry.getFieldValue();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getResponseId() {
        return responseId;
    }
    
    public void setResponseId(Long responseId) {
        this.responseId = responseId;
    }
    
    public Long getFormFieldId() {
        return formFieldId;
    }
    
    public void setFormFieldId(Long formFieldId) {
        this.formFieldId = formFieldId;
    }
    
    public String getFieldValue() {
        return fieldValue;
    }
    
    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }
}
