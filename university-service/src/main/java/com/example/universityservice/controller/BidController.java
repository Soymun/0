package com.example.universityservice.controller;


import com.example.universityservice.dto.bid.BidCreateDto;
import com.example.universityservice.dto.bid.BidUpdateDto;
import com.example.universityservice.facade.BidFacade;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class BidController {

    private final BidFacade bidFacade;

    @PostMapping("/bid")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> saveBid(@RequestBody BidCreateDto bidDto){
        return bidFacade.saveBid(bidDto);
    }

    @PatchMapping("/bid")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateBid(@RequestBody BidUpdateDto bidDto) throws JsonProcessingException {
        return bidFacade.updateBid(bidDto);
    }

    @DeleteMapping("/bid/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteBid(@PathVariable Long id){
        return bidFacade.deleteBid(id);
    }

    @GetMapping("/bid/user/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> getBiByUserId(@PathVariable Long id){
        return ResponseEntity.ok(bidFacade.getBidByUserId(id));
    }

    @GetMapping("/bid/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> getBiById(@PathVariable Long id){
        return ResponseEntity.ok(bidFacade.getBidById(id));
    }

    @GetMapping("/bid/university/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getBidByUniversityId(@PathVariable Long id){
        return ResponseEntity.ok(bidFacade.getBidByUniversityId(id));
    }
}
