package com.formmanagement.repository;

import com.formmanagement.model.FormField;
import com.formmanagement.model.Response;
import com.formmanagement.model.ResponseEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResponseEntryRepository extends JpaRepository<ResponseEntry, Long> {
    
    List<ResponseEntry> findByResponse(Response response);
    
    List<ResponseEntry> findByFormField(FormField formField);
    
    List<ResponseEntry> findByResponseAndFormField(Response response, FormField formField);
}
