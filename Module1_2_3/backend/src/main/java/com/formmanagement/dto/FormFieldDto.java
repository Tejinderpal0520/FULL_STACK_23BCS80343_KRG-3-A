package com.formmanagement.dto;

import com.formmanagement.model.FormField;

public class FormFieldDto {
    
    private Long id;
    private String label;
    private String fieldType;
    private Boolean isRequired;
    private Integer fieldOrder;
    private String placeholder;
    private String helpText;
    private String options;
    private String validationRules;
    
    // Constructors
    public FormFieldDto() {}
    
    public FormFieldDto(FormField formField) {
        this.id = formField.getId();
        this.label = formField.getLabel();
        this.fieldType = formField.getFieldType().name();
        this.isRequired = formField.getIsRequired();
        this.fieldOrder = formField.getFieldOrder();
        this.placeholder = formField.getPlaceholder();
        this.helpText = formField.getHelpText();
        this.options = formField.getOptions();
        this.validationRules = formField.getValidationRules();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getLabel() {
        return label;
    }
    
    public void setLabel(String label) {
        this.label = label;
    }
    
    public String getFieldType() {
        return fieldType;
    }
    
    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }
    
    public Boolean getIsRequired() {
        return isRequired;
    }
    
    public void setIsRequired(Boolean isRequired) {
        this.isRequired = isRequired;
    }
    
    public Integer getFieldOrder() {
        return fieldOrder;
    }
    
    public void setFieldOrder(Integer fieldOrder) {
        this.fieldOrder = fieldOrder;
    }
    
    public String getPlaceholder() {
        return placeholder;
    }
    
    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }
    
    public String getHelpText() {
        return helpText;
    }
    
    public void setHelpText(String helpText) {
        this.helpText = helpText;
    }
    
    public String getOptions() {
        return options;
    }
    
    public void setOptions(String options) {
        this.options = options;
    }
    
    public String getValidationRules() {
        return validationRules;
    }
    
    public void setValidationRules(String validationRules) {
        this.validationRules = validationRules;
    }
}
