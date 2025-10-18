package com.formmanagement.service;

import com.formmanagement.dto.ResponseDto;
import com.formmanagement.dto.ResponseEntryDto;
import com.formmanagement.model.*;
import com.formmanagement.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class ResponseService {
    
    @Autowired
    private ResponseRepository responseRepository;
    
    @Autowired
    private ResponseEntryRepository responseEntryRepository;
    
    @Autowired
    private FormRepository formRepository;
    
    @Autowired
    private FormFieldRepository formFieldRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public Response submitResponse(Long formId, Map<String, Object> responseData, String respondentEmail, 
                                 String respondentName, String ipAddress, String userAgent, User user) {
        
        Form form = formRepository.findByIdAndIsActive(formId, true)
                .orElseThrow(() -> new RuntimeException("Form not found or inactive"));
        
        // Check if form is public or user has access
        if (!form.getIsPublic() && user == null) {
            throw new RuntimeException("Form requires authentication");
        }
        
        // Check submission limits
        if (form.getSubmissionLimit() != null) {
            Long currentCount = responseRepository.countByForm(form);
            if (currentCount >= form.getSubmissionLimit()) {
                throw new RuntimeException("Form submission limit reached");
            }
        }
        
        // Check for duplicate submissions
        if (!form.getAllowDuplicate() && respondentEmail != null) {
            Long duplicateCount = responseRepository.countByFormAndEmail(form, respondentEmail);
            if (duplicateCount > 0) {
                throw new RuntimeException("Duplicate submission not allowed");
            }
        }
        
        // Create response
        Response response = new Response();
        response.setForm(form);
        response.setUser(user);
        response.setRespondentEmail(respondentEmail);
        response.setRespondentName(respondentName);
        response.setIpAddress(ipAddress);
        response.setUserAgent(userAgent);
        
        Response savedResponse = responseRepository.save(response);
        
        // Save response entries
        for (Map.Entry<String, Object> entry : responseData.entrySet()) {
            try {
                Long fieldId = Long.parseLong(entry.getKey());
                FormField field = formFieldRepository.findById(fieldId)
                        .orElseThrow(() -> new RuntimeException("Field not found: " + fieldId));
                
                if (!field.getForm().getId().equals(formId)) {
                    throw new RuntimeException("Field does not belong to this form");
                }
                
                String fieldValue = entry.getValue() != null ? entry.getValue().toString() : "";
                
                ResponseEntry responseEntry = new ResponseEntry();
                responseEntry.setResponse(savedResponse);
                responseEntry.setFormField(field);
                responseEntry.setFieldValue(fieldValue);
                
                responseEntryRepository.save(responseEntry);
            } catch (NumberFormatException e) {
                // Skip invalid field IDs
                continue;
            }
        }
        
        return savedResponse;
    }
    
    public List<ResponseDto> getResponsesByForm(Long formId, User creator) {
        Form form = formRepository.findById(formId)
                .orElseThrow(() -> new RuntimeException("Form not found"));
        
        if (!form.getCreator().getId().equals(creator.getId())) {
            throw new RuntimeException("You don't have permission to view responses for this form");
        }
        
        List<Response> responses = responseRepository.findByFormOrderBySubmittedAtDesc(form);
        return responses.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public List<ResponseDto> getResponsesByFormAndDateRange(Long formId, LocalDateTime startDate, 
                                                           LocalDateTime endDate, User creator) {
        Form form = formRepository.findById(formId)
                .orElseThrow(() -> new RuntimeException("Form not found"));
        
        if (!form.getCreator().getId().equals(creator.getId())) {
            throw new RuntimeException("You don't have permission to view responses for this form");
        }
        
        List<Response> responses = responseRepository.findByFormAndDateRange(form, startDate, endDate);
        return responses.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    private ResponseDto convertToDto(Response response) {
        ResponseDto dto = new ResponseDto(response);
        
        // Get response entries
        List<ResponseEntry> entries = responseEntryRepository.findByResponse(response);
        List<ResponseEntryDto> entryDtos = entries.stream()
                .map(ResponseEntryDto::new)
                .collect(Collectors.toList());
        dto.setEntries(entryDtos);
        
        return dto;
    }
}
