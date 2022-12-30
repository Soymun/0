package com.example.demo.Service;

import com.example.demo.DTO.BidDto;
import com.example.demo.DTO.TypeOfBidDto;

import java.util.List;

public interface BidService {

    BidDto saveBid(BidDto bidDto);

    BidDto updateBid(BidDto bidDto);

    void deleteBid(Long id);

    List<BidDto> getBidByUserId(Long id);

    BidDto getBidDtoById(Long id);

    TypeOfBidDto saveTypeOfBid(TypeOfBidDto type);

    TypeOfBidDto updateTypeOfBid(TypeOfBidDto type);

    void deleteTypeOfBid(Long id);

    TypeOfBidDto getTypeOfBid(Long id);

    List<TypeOfBidDto> getListTypeOfBid();
}
