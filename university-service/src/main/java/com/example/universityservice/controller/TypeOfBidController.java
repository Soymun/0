package com.example.universityservice.controller;

import com.example.universityservice.dto.type.TypeOfBidCreateDto;
import com.example.universityservice.dto.type.TypeOfBidDto;
import com.example.universityservice.service.impl.TypeOfBidServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class TypeOfBidController {

    private final TypeOfBidServiceImpl type;

    @PostMapping("/typeOfBid")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> saveTypeOfBid(@RequestBody TypeOfBidCreateDto bidDto){
        return ResponseEntity.ok(type.saveTypeOfBid(bidDto));
    }

    @PatchMapping("/typeOfBid")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateTypeOfBid(@RequestBody TypeOfBidDto bidDto){
        return ResponseEntity.ok(type.updateTypeOfBid(bidDto));
    }

    @DeleteMapping("/typeOfBid/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteTypeOfBid(@PathVariable Long id){
        type.deleteTypeOfBid(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/typeOfBid/university/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> getTypeOfBids(@PathVariable Long id){
        return ResponseEntity.ok(type.getListTypeOfBidByUniversityId(id));
    }

    @GetMapping("/typeOfBid/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> getTypeOfBidById(@PathVariable Long id){
        return ResponseEntity.ok(type.getTypeOfBid(id));
    }
}
