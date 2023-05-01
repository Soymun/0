package com.example.universityservice.repositories;

import com.example.universityservice.entity.University;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UniversityRepository extends JpaRepository<University, Long>{}
