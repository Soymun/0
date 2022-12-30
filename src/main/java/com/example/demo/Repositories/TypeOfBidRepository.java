package com.example.demo.Repositories;

import com.example.demo.Entity.TypeOfBid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TypeOfBidRepository extends JpaRepository<TypeOfBid, Long> {

    TypeOfBid getTypeOfBidById(Long id);

}
