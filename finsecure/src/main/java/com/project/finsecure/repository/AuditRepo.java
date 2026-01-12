package com.project.finsecure.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.project.finsecure.entity.AuditLog;


public interface AuditRepo extends  JpaRepository<AuditLog, Long>{
       
      

}
