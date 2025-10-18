package com.formmanagement.controller;

import com.formmanagement.dto.ResponseDto;
import com.formmanagement.model.User;
import com.formmanagement.service.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/responses")
public class ResponseController {
    
    @Autowired
    private ResponseService responseService;
    
    @PostMapping("/submit/{formId}")
    public ResponseEntity<?> submitResponse(@PathVariable Long formId, 
                                         @RequestBody Map<String, Object> responseData,
                                         @RequestParam(required = false) String respondentEmail,
                                         @RequestParam(required = false) String respondentName,
                                         HttpServletRequest request,
                                         Authentication authentication) {
        try {
            User user = authentication != null ? (User) authentication.getPrincipal() : null;
            String ipAddress = getClientIpAddress(request);
            String userAgent = request.getHeader("User-Agent");
            
            var response = responseService.submitResponse(formId, responseData, respondentEmail, 
                                                        respondentName, ipAddress, userAgent, user);
            return ResponseEntity.ok("Response submitted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error: " + e.getMessage());
        }
    }
    
    @GetMapping("/form/{formId}")
    public ResponseEntity<?> getResponsesByForm(@PathVariable Long formId, Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            List<ResponseDto> responses = responseService.getResponsesByForm(formId, user);
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error: " + e.getMessage());
        }
    }
    
    @GetMapping("/form/{formId}/date-range")
    public ResponseEntity<?> getResponsesByFormAndDateRange(@PathVariable Long formId,
                                                           @RequestParam String startDate,
                                                           @RequestParam String endDate,
                                                           Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            LocalDateTime start = LocalDateTime.parse(startDate);
            LocalDateTime end = LocalDateTime.parse(endDate);
            
            List<ResponseDto> responses = responseService.getResponsesByFormAndDateRange(formId, start, end, user);
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error: " + e.getMessage());
        }
    }
    
    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedForHeader = request.getHeader("X-Forwarded-For");
        if (xForwardedForHeader == null) {
            return request.getRemoteAddr();
        } else {
            return xForwardedForHeader.split(",")[0];
        }
    }
}
