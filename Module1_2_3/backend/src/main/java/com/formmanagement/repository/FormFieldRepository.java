package com.formmanagement.repository;

import com.formmanagement.model.Form;
import com.formmanagement.model.FormField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormFieldRepository extends JpaRepository<FormField, Long> {
    
    List<FormField> findByForm(Form form);
    
    List<FormField> findByFormOrderByFieldOrder(Form form);
    
    @Query("SELECT ff FROM FormField ff WHERE ff.form = :form ORDER BY ff.fieldOrder ASC")
    List<FormField> findFieldsByFormOrdered(@Param("form") Form form);
}
