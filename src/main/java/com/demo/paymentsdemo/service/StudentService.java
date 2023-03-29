package com.demo.paymentsdemo.service;

import java.util.List;

import com.demo.paymentsdemo.entity.Student;

public interface StudentService {

    Student getStudentByName(String name);
    Student getStudent(Long id);
    List<Student> getStudents();
    List<Student> getStudentsSpecification();
    
}
