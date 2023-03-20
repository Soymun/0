package com.example.demo.Controller;


import com.example.demo.DTO.Bid.BidDto;
import com.example.demo.DTO.Bid.BidUpdateDto;
import com.example.demo.Response.ResponseDto;
import com.example.demo.Service.Impl.BidServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ytsu")
public class BidController {

    private final BidServiceImpl bidService;

    public BidController(BidServiceImpl bidService) {
        this.bidService = bidService;
    }

    @PostMapping("/bid")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> saveBid(@RequestBody BidDto bidDto){
        bidService.saveBid(bidDto);
        return ResponseEntity.status(201).build();
    }

    @PatchMapping("/bid")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateBid(@RequestBody BidUpdateDto bidDto){
        return ResponseEntity.ok(ResponseDto.builder().body(bidService.updateBid(bidDto)).build());
    }

    @DeleteMapping("/bid/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteBid(@PathVariable Long id){
        bidService.deleteBid(id);
        return ResponseEntity.ok(ResponseDto.builder().body("Suggest").build());
    }

    @GetMapping("/bid/user/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> getBiByUserId(@PathVariable Long id){
        return ResponseEntity.ok(ResponseDto.builder().body(bidService.getBidByUserId(id)).build());
    }

    @GetMapping("/bid/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> getBiById(@PathVariable Long id){
        return ResponseEntity.ok(ResponseDto.builder().body(bidService.getBidDtoById(id)).build());
    }
}
