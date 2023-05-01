package com.example.universityservice.repositories;


import com.example.universityservice.entity.TypeOfBid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TypeOfBidRepository extends JpaRepository<TypeOfBid, Long> {

    List<TypeOfBid> getTypeOfBidsByUniversityId(Long id);
}
