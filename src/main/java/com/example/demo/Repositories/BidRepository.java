package com.example.demo.Repositories;

import com.example.demo.Entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BidRepository extends JpaRepository<Bid, Long> {

    Bid getBidById(Long id);

    List<Bid> getBidsByUserId(Long userId);
}
