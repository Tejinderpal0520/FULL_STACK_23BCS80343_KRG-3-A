package com.formmanagement.controller;

import com.formmanagement.dto.FormDto;
import com.formmanagement.model.User;
import com.formmanagement.service.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/forms")
public class FormController {
    
    @Autowired
    private FormService formService;
    
    @PostMapping
    public ResponseEntity<?> createForm(@RequestBody FormDto formDto, Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            var form = formService.createForm(formDto, user);
            return ResponseEntity.ok(form);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error: " + e.getMessage());
        }
    }
    
    @PutMapping("/{formId}")
    public ResponseEntity<?> updateForm(@PathVariable Long formId, @RequestBody FormDto formDto, 
                                       Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            var form = formService.updateForm(formId, formDto, user);
            return ResponseEntity.ok(form);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{formId}")
    public ResponseEntity<?> deleteForm(@PathVariable Long formId, Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            formService.deleteForm(formId, user);
            return ResponseEntity.ok("Form deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error: " + e.getMessage());
        }
    }
    
    @GetMapping("/my-forms")
    public ResponseEntity<?> getMyForms(Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            List<FormDto> forms = formService.getFormsByCreator(user);
            return ResponseEntity.ok(forms);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error: " + e.getMessage());
        }
    }
    
    @GetMapping("/public")
    public ResponseEntity<?> getPublicForms() {
        try {
            List<FormDto> forms = formService.getPublicForms();
            return ResponseEntity.ok(forms);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error: " + e.getMessage());
        }
    }
    
    @GetMapping("/{formId}")
    public ResponseEntity<?> getFormById(@PathVariable Long formId) {
        try {
            Optional<FormDto> form = formService.getFormById(formId);
            if (form.isPresent()) {
                return ResponseEntity.ok(form.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error: " + e.getMessage());
        }
    }
    
    @GetMapping("/public/{formId}")
    public ResponseEntity<?> getPublicFormById(@PathVariable Long formId) {
        try {
            Optional<FormDto> form = formService.getPublicFormById(formId);
            if (form.isPresent()) {
                return ResponseEntity.ok(form.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error: " + e.getMessage());
        }
    }
}
