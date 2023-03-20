package com.example.demo.Mappers;


import com.example.demo.DTO.Bid.BidDto;
import com.example.demo.DTO.TypeOfBid.TypeOfBidCreateDto;
import com.example.demo.DTO.TypeOfBid.TypeOfBidDto;
import com.example.demo.Entity.Bid;
import com.example.demo.Entity.TypeOfBid;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BidMapper {

    BidDto bidToBidDto(Bid bid);

    Bid bidDtoToBid(BidDto bidDto);

    TypeOfBid typeOfBidCreateDtoToTypeOfBid(TypeOfBidCreateDto type);

    TypeOfBidDto typeOfBidToTypeOfBidDto(TypeOfBid type);
}
