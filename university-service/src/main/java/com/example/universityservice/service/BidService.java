package com.example.universityservice.service;


import com.example.universityservice.dto.bid.BidCreateDto;
import com.example.universityservice.dto.bid.BidDto;
import com.example.universityservice.dto.bid.BidUpdateDto;

import java.util.List;
import java.util.UUID;

public interface BidService {

    void saveBid(BidCreateDto bidDto);

    BidDto updateBid(BidUpdateDto bidDto);

    void deleteBid(Long id);

    List<BidDto> getBidByUserId(Long id);

    BidDto getBidDtoById(Long id);

    List<BidDto> getBidByUniversityId(Long universityId);
}
