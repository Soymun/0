package com.example.universityservice.facade;

import com.example.universityservice.dto.ResponseEmail;
import com.example.universityservice.dto.Student;
import com.example.universityservice.dto.bid.BidCreateDto;
import com.example.universityservice.dto.bid.BidDto;
import com.example.universityservice.dto.bid.BidUpdateDto;
import com.example.universityservice.entity.Status;
import com.example.universityservice.service.impl.BidServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class BidFacade {

    private final ObjectProvider<RestTemplate> restTemplateObjectProvider;

    private final BidServiceImpl bidService;

    public ResponseEntity<?> saveBid(BidCreateDto bidCreateDto){
        bidService.saveBid(bidCreateDto);
        return ResponseEntity.status(201).build();
    }

    public ResponseEntity<?> updateBid(BidUpdateDto bidUpdateDto) throws JsonProcessingException {
        BidDto bidDto = bidService.updateBid(bidUpdateDto);
        bidDto.setStudent(getStudent(bidDto.getStudent().getId()));
        if(bidDto.getStatus().equals(Status.FINISH)){
            getEmail(bidDto.getStudent().getEmail(), "Ваша заявка " + bidDto.getId() + ", для получения: " + bidDto.getType().getName() + "- готова.");
        }
        return ResponseEntity.ok(bidDto);
    }

    public ResponseEntity<?> deleteBid(Long id){
        bidService.deleteBid(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<?> getBidByUserId(Long userId){
        return ResponseEntity.ok(bidService.getBidByUserId(userId).stream().peek(bid -> bid.setStudent(getStudent(bid.getStudent().getId()))).toList());
    }

    public ResponseEntity<?> getBidById(Long id){
        BidDto bidDto = bidService.getBidDtoById(id);
        bidDto.setStudent(getStudent(bidDto.getStudent().getId()));
        return ResponseEntity.ok(bidDto);
    }

    public ResponseEntity<?> getBidByUniversityId(Long universityId){
        return ResponseEntity.ok(bidService.getBidByUniversityId(universityId).stream().peek(bid -> bid.setStudent(getStudent(bid.getStudent().getId()))).toList());
    }

    private Student getStudent(Long id){
        return restTemplateObjectProvider.getObject().getForObject("http://localhost:8072/profile/v1/student/{id}", Student.class, id);
    }

    private void getEmail(String email, String message) throws JsonProcessingException {
        ResponseEmail responseEmail = new ResponseEmail(email, message);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();

        restTemplateObjectProvider.getObject().postForEntity("http://localhost:8072/mail/v1/send",new HttpEntity<>(mapper.writeValueAsString(responseEmail), headers), Void.class);
    }
}
