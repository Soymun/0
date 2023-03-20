package com.example.demo.Service;

import com.example.demo.DTO.TypeOfBid.TypeOfBidCreateDto;
import com.example.demo.DTO.TypeOfBid.TypeOfBidDto;

import java.util.List;

public interface TypeOfBidService {
    TypeOfBidDto saveTypeOfBid(TypeOfBidCreateDto type);

    TypeOfBidDto updateTypeOfBid(TypeOfBidDto type);

    void deleteTypeOfBid(Long id);

    TypeOfBidDto getTypeOfBid(Long id);

    List<TypeOfBidDto> getListTypeOfBid();
}
