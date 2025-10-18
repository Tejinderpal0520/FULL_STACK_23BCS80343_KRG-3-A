package com.formmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "form_fields")
public class FormField {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "form_id", nullable = false)
    private Form form;
    
    @NotBlank
    private String label;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private FieldType fieldType;
    
    @Column(name = "is_required")
    private Boolean isRequired = false;
    
    @Column(name = "field_order")
    private Integer fieldOrder;
    
    @Column(name = "placeholder")
    private String placeholder;
    
    @Column(name = "help_text")
    private String helpText;
    
    @Column(name = "options", columnDefinition = "TEXT")
    private String options; // JSON string for field options (for radio, checkbox, dropdown)
    
    @Column(name = "validation_rules", columnDefinition = "TEXT")
    private String validationRules; // JSON string for validation rules
    
    @OneToMany(mappedBy = "formField", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ResponseEntry> responseEntries;
    
    // Constructors
    public FormField() {}
    
    public FormField(Form form, String label, FieldType fieldType, Integer fieldOrder) {
        this.form = form;
        this.label = label;
        this.fieldType = fieldType;
        this.fieldOrder = fieldOrder;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Form getForm() {
        return form;
    }
    
    public void setForm(Form form) {
        this.form = form;
    }
    
    public String getLabel() {
        return label;
    }
    
    public void setLabel(String label) {
        this.label = label;
    }
    
    public FieldType getFieldType() {
        return fieldType;
    }
    
    public void setFieldType(FieldType fieldType) {
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
    
    public List<ResponseEntry> getResponseEntries() {
        return responseEntries;
    }
    
    public void setResponseEntries(List<ResponseEntry> responseEntries) {
        this.responseEntries = responseEntries;
    }
    
    public enum FieldType {
        TEXT,
        TEXTAREA,
        EMAIL,
        NUMBER,
        DATE,
        RADIO,
        CHECKBOX,
        DROPDOWN,
        FILE
    }
}
