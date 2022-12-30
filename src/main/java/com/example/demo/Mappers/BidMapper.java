package com.example.demo.Mappers;


import com.example.demo.DTO.BidDto;
import com.example.demo.DTO.TypeOfBidDto;
import com.example.demo.Entity.Bid;
import com.example.demo.Entity.TypeOfBid;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BidMapper {

    BidDto bidToBidDto(Bid bid);

    Bid bidDtoToBid(BidDto bidDto);

    TypeOfBid typeOfBidDtoToTypeOfBid(TypeOfBidDto type);

    TypeOfBidDto typeOfBidToTypeOfBidDto(TypeOfBid type);
}
