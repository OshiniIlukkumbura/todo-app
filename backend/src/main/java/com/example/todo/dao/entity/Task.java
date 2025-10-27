package com.example.todo.dao.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "task")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(nullable = false)
    private Short completed ;

    @CreationTimestamp
    @Column(name = "created_at",nullable = false)
    private Date createdAt ;

    @UpdateTimestamp
    @Column(name = "updated_at",nullable = false)
    private Date updatedAt ;

}
