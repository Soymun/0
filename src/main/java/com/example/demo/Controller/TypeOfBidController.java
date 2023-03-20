package com.example.demo.Controller;

import com.example.demo.DTO.TypeOfBid.TypeOfBidCreateDto;
import com.example.demo.DTO.TypeOfBid.TypeOfBidDto;
import com.example.demo.Response.ResponseDto;
import com.example.demo.Service.Impl.TypeOfBidServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ytsu")
@RequiredArgsConstructor
public class TypeOfBidController {

    private final TypeOfBidServiceImpl  type;

    @PostMapping("/typeOfBid")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> saveTypeOfBid(@RequestBody TypeOfBidCreateDto bidDto){
        return ResponseEntity.ok(ResponseDto.builder().body(type.saveTypeOfBid(bidDto)).build());
    }

    @PatchMapping("/typeOfBid")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateTypeOfBid(@RequestBody TypeOfBidDto bidDto){
        return ResponseEntity.ok(ResponseDto.builder().body(type.updateTypeOfBid(bidDto)).build());
    }

    @DeleteMapping("/typeOfBid/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteTypeOfBid(@PathVariable Long id){
        type.deleteTypeOfBid(id);
        return ResponseEntity.ok(ResponseDto.builder().body("Suggest").build());
    }

    @GetMapping("/typeOfBid")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> getTypeOfBids(){
        return ResponseEntity.ok(ResponseDto.builder().body(type.getListTypeOfBid()).build());
    }

    @GetMapping("/typeOfBid/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> getTypeOfBidById(@PathVariable Long id){
        return ResponseEntity.ok(ResponseDto.builder().body(type.getTypeOfBid(id)).build());
    }
}
