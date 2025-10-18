package com.formmanagement.model;

import jakarta.persistence.*;

@Entity
@Table(name = "response_entries")
public class ResponseEntry {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "response_id", nullable = false)
    private Response response;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "form_field_id", nullable = false)
    private FormField formField;
    
    @Column(name = "field_value", columnDefinition = "TEXT")
    private String fieldValue;
    
    // Constructors
    public ResponseEntry() {}
    
    public ResponseEntry(Response response, FormField formField, String fieldValue) {
        this.response = response;
        this.formField = formField;
        this.fieldValue = fieldValue;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Response getResponse() {
        return response;
    }
    
    public void setResponse(Response response) {
        this.response = response;
    }
    
    public FormField getFormField() {
        return formField;
    }
    
    public void setFormField(FormField formField) {
        this.formField = formField;
    }
    
    public String getFieldValue() {
        return fieldValue;
    }
    
    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }
}
