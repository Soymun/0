package com.example.universityservice.repositories;

import com.example.universityservice.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BidRepository extends JpaRepository<Bid, Long> {

    List<Bid> getBidsByStudentId(Long studentId);

    List<Bid> getBidsByUniversityIdOrderByStatus(Long universityId);
}
