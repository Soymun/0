package com.example.demo.Service;

import com.example.demo.DTO.Bid.BidDto;
import com.example.demo.DTO.Bid.BidUpdateDto;

import java.util.List;

public interface BidService {

    void saveBid(BidDto bidDto);

    BidDto updateBid(BidUpdateDto bidDto);

    void deleteBid(Long id);

    List<BidDto> getBidByUserId(Long id);

    BidDto getBidDtoById(Long id);
}
