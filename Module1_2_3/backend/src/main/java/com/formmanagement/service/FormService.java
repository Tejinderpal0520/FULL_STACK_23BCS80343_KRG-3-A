package com.formmanagement.service;

import com.formmanagement.dto.FormDto;
import com.formmanagement.dto.FormFieldDto;
import com.formmanagement.model.Form;
import com.formmanagement.model.FormField;
import com.formmanagement.model.User;
import com.formmanagement.repository.FormRepository;
import com.formmanagement.repository.FormFieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class FormService {
    
    @Autowired
    private FormRepository formRepository;
    
    @Autowired
    private FormFieldRepository formFieldRepository;
    
    public Form createForm(FormDto formDto, User creator) {
        Form form = new Form();
        form.setTitle(formDto.getTitle());
        form.setDescription(formDto.getDescription());
        form.setCreator(creator);
        form.setIsActive(formDto.getIsActive() != null ? formDto.getIsActive() : true);
        form.setIsPublic(formDto.getIsPublic() != null ? formDto.getIsPublic() : true);
        form.setSubmissionLimit(formDto.getSubmissionLimit());
        form.setAllowDuplicate(formDto.getAllowDuplicate() != null ? formDto.getAllowDuplicate() : true);
        form.setRequireLogin(formDto.getRequireLogin() != null ? formDto.getRequireLogin() : false);
        form.setExpiresAt(formDto.getExpiresAt());
        
        Form savedForm = formRepository.save(form);
        
        // Save form fields
        if (formDto.getFields() != null) {
            for (FormFieldDto fieldDto : formDto.getFields()) {
                FormField field = new FormField();
                field.setForm(savedForm);
                field.setLabel(fieldDto.getLabel());
                field.setFieldType(FormField.FieldType.valueOf(fieldDto.getFieldType()));
                field.setIsRequired(fieldDto.getIsRequired() != null ? fieldDto.getIsRequired() : false);
                field.setFieldOrder(fieldDto.getFieldOrder());
                field.setPlaceholder(fieldDto.getPlaceholder());
                field.setHelpText(fieldDto.getHelpText());
                field.setOptions(fieldDto.getOptions());
                field.setValidationRules(fieldDto.getValidationRules());
                
                formFieldRepository.save(field);
            }
        }
        
        return savedForm;
    }
    
    public Form updateForm(Long formId, FormDto formDto, User creator) {
        Form form = formRepository.findById(formId)
                .orElseThrow(() -> new RuntimeException("Form not found"));
        
        if (!form.getCreator().getId().equals(creator.getId())) {
            throw new RuntimeException("You don't have permission to update this form");
        }
        
        form.setTitle(formDto.getTitle());
        form.setDescription(formDto.getDescription());
        form.setIsActive(formDto.getIsActive());
        form.setIsPublic(formDto.getIsPublic());
        form.setSubmissionLimit(formDto.getSubmissionLimit());
        form.setAllowDuplicate(formDto.getAllowDuplicate());
        form.setRequireLogin(formDto.getRequireLogin());
        form.setExpiresAt(formDto.getExpiresAt());
        
        return formRepository.save(form);
    }
    
    public void deleteForm(Long formId, User creator) {
        Form form = formRepository.findById(formId)
                .orElseThrow(() -> new RuntimeException("Form not found"));
        
        if (!form.getCreator().getId().equals(creator.getId())) {
            throw new RuntimeException("You don't have permission to delete this form");
        }
        
        formRepository.delete(form);
    }
    
    public List<FormDto> getFormsByCreator(User creator) {
        List<Form> forms = formRepository.findActiveFormsByCreator(creator);
        return forms.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public List<FormDto> getPublicForms() {
        List<Form> forms = formRepository.findPublicActiveForms();
        return forms.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public Optional<FormDto> getFormById(Long formId) {
        return formRepository.findByIdAndIsActive(formId, true)
                .map(this::convertToDto);
    }
    
    public Optional<FormDto> getPublicFormById(Long formId) {
        return formRepository.findByIdAndIsActive(formId, true)
                .filter(form -> form.getIsPublic())
                .map(this::convertToDto);
    }
    
    private FormDto convertToDto(Form form) {
        FormDto dto = new FormDto(form);
        
        // Get form fields
        List<FormField> fields = formFieldRepository.findFieldsByFormOrdered(form);
        List<FormFieldDto> fieldDtos = fields.stream()
                .map(FormFieldDto::new)
                .collect(Collectors.toList());
        dto.setFields(fieldDtos);
        
        return dto;
    }
}
