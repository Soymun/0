package com.example.universityservice.dto.bid;

import com.example.universityservice.entity.Status;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BidUpdateDto {

    private Long id;

    private Status status;
}
