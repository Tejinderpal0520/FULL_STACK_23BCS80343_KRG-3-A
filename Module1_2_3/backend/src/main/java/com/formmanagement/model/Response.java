package com.formmanagement.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "responses")
public class Response {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "form_id", nullable = false)
    private Form form;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    
    @Column(name = "respondent_email")
    private String respondentEmail;
    
    @Column(name = "respondent_name")
    private String respondentName;
    
    @Column(name = "ip_address")
    private String ipAddress;
    
    @Column(name = "user_agent")
    private String userAgent;
    
    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;
    
    @Column(name = "is_duplicate")
    private Boolean isDuplicate = false;
    
    @OneToMany(mappedBy = "response", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ResponseEntry> entries;
    
    @PrePersist
    protected void onCreate() {
        submittedAt = LocalDateTime.now();
    }
    
    // Constructors
    public Response() {}
    
    public Response(Form form, User user, String respondentEmail, String respondentName) {
        this.form = form;
        this.user = user;
        this.respondentEmail = respondentEmail;
        this.respondentName = respondentName;
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
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
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
    
    public String getUserAgent() {
        return userAgent;
    }
    
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
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
    
    public List<ResponseEntry> getEntries() {
        return entries;
    }
    
    public void setEntries(List<ResponseEntry> entries) {
        this.entries = entries;
    }
}
