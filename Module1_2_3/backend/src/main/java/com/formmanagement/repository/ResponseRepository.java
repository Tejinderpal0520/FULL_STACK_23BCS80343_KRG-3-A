package com.formmanagement.repository;

import com.formmanagement.model.Form;
import com.formmanagement.model.Response;
import com.formmanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ResponseRepository extends JpaRepository<Response, Long> {
    
    List<Response> findByForm(Form form);
    
    List<Response> findByFormOrderBySubmittedAtDesc(Form form);
    
    List<Response> findByUser(User user);
    
    @Query("SELECT r FROM Response r WHERE r.form = :form AND r.submittedAt BETWEEN :startDate AND :endDate ORDER BY r.submittedAt DESC")
    List<Response> findByFormAndDateRange(@Param("form") Form form, 
                                        @Param("startDate") LocalDateTime startDate, 
                                        @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT r FROM Response r WHERE r.form = :form AND r.respondentEmail = :email")
    List<Response> findByFormAndRespondentEmail(@Param("form") Form form, @Param("email") String email);
    
    @Query("SELECT COUNT(r) FROM Response r WHERE r.form = :form")
    Long countByForm(@Param("form") Form form);
    
    @Query("SELECT COUNT(r) FROM Response r WHERE r.form = :form AND r.respondentEmail = :email")
    Long countByFormAndEmail(@Param("form") Form form, @Param("email") String email);
}
