package com.example.demo.DTO.Bid;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BidDto {

    private Long id;

    private Long userId;

    private Long typeOfBid;

    private boolean completed;
}
