package com.example.universityservice.dto.bid;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BidCreateDto {

    private Long studentId;

    private Long typeOfBidId;

    private Long universityId;
}
