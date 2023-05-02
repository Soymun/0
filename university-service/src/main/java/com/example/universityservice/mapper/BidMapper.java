package com.example.universityservice.mapper;



import com.example.universityservice.dto.Student;
import com.example.universityservice.dto.bid.BidCreateDto;
import com.example.universityservice.dto.bid.BidDto;
import com.example.universityservice.dto.type.TypeOfBidCreateDto;
import com.example.universityservice.dto.type.TypeOfBidDto;
import com.example.universityservice.entity.Bid;
import com.example.universityservice.entity.TypeOfBid;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BidMapper {

    BidDto bidToBidDto(Bid bid);

    Bid bidDtoToBid(BidCreateDto bidDto);

    TypeOfBid typeOfBidCreateDtoToTypeOfBid(TypeOfBidCreateDto type);

    TypeOfBidDto typeOfBidToTypeOfBidDto(TypeOfBid type);

    default Student map(Long value){
        Student student = new Student();
        student.setId(value);
        return student;
    }
}
