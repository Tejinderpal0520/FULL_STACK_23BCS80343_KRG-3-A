package com.formmanagement.repository;

import com.formmanagement.model.Form;
import com.formmanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FormRepository extends JpaRepository<Form, Long> {
    
    List<Form> findByCreator(User creator);
    
    List<Form> findByCreatorAndIsActive(User creator, Boolean isActive);
    
    List<Form> findByIsPublicAndIsActive(Boolean isPublic, Boolean isActive);
    
    Optional<Form> findByIdAndIsActive(Long id, Boolean isActive);
    
    @Query("SELECT f FROM Form f WHERE f.creator = :creator AND f.isActive = true ORDER BY f.createdAt DESC")
    List<Form> findActiveFormsByCreator(@Param("creator") User creator);
    
    @Query("SELECT f FROM Form f WHERE f.isPublic = true AND f.isActive = true AND (f.expiresAt IS NULL OR f.expiresAt > CURRENT_TIMESTAMP) ORDER BY f.createdAt DESC")
    List<Form> findPublicActiveForms();
    
    @Query("SELECT COUNT(r) FROM Response r WHERE r.form = :form")
    Long countResponsesByForm(@Param("form") Form form);
}
