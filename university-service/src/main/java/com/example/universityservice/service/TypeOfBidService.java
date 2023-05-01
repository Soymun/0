package com.example.universityservice.service;


import com.example.universityservice.dto.type.TypeOfBidCreateDto;
import com.example.universityservice.dto.type.TypeOfBidDto;

import java.util.List;

public interface TypeOfBidService {
    TypeOfBidDto saveTypeOfBid(TypeOfBidCreateDto type);

    TypeOfBidDto updateTypeOfBid(TypeOfBidDto type);

    void deleteTypeOfBid(Long id);

    TypeOfBidDto getTypeOfBid(Long id);

    List<TypeOfBidDto> getListTypeOfBidByUniversityId(Long universityId);
}
