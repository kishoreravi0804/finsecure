package com.project.finsecure.entity;

import java.time.LocalDateTime;

import com.project.finsecure.audit.AuditAction;
import com.project.finsecure.audit.EntityType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuditLog {
    
@Id
@GeneratedValue(strategy =  GenerationType.IDENTITY)
 private Long id;

 @Column(nullable = false,name  = "user_email")
 private String userEmail;

@Enumerated(EnumType.STRING)
@Column(nullable = false)
private AuditAction action;

@Enumerated(EnumType.STRING)
@Column(name = "entity_type", nullable = false)
private EntityType entityType;
  @Column(name= "entity_id")
  private Long entityId;
   
  @Column(name = "created_at")
 private LocalDateTime createdAt = LocalDateTime.now();

}
