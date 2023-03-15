package com.example.demo.Entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Column(name = "type_of_bid_id")
    private Long typeOfBidId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_of_bid_id", insertable = false, updatable = false)
    private TypeOfBid type;

    private boolean completed;

}
