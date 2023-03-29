package com.demo.paymentsdemo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="student")
public class Student {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name="name",unique = false)
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="address_id",referencedColumnName = "id")
    private Address address;

    @JsonManagedReference
    @OneToMany(mappedBy = "studentId", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    private Set<Subject> subjects;

}
