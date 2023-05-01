package com.example.universityservice.service.impl;

import com.example.universityservice.dto.bid.BidCreateDto;
import com.example.universityservice.dto.bid.BidDto;
import com.example.universityservice.dto.bid.BidUpdateDto;
import com.example.universityservice.entity.Bid;
import com.example.universityservice.entity.Status;
import com.example.universityservice.exception.NotFoundException;
import com.example.universityservice.mapper.BidMapper;
import com.example.universityservice.repositories.BidRepository;
import com.example.universityservice.service.BidService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
@Slf4j
@RequiredArgsConstructor
public class BidServiceImpl implements BidService {

    private final BidRepository bidRepository;

    private final BidMapper bidMapper;

    @Override
    public void saveBid(BidCreateDto bidDto) {
        log.info("Сохранение заявки");
        Bid bid = bidMapper.bidDtoToBid(bidDto);
        bid.setStatus(Status.CREATED);
        bidRepository.save(bidMapper.bidDtoToBid(bidDto));
    }

    @Override
    public BidDto updateBid(BidUpdateDto bidDto) {
        log.info("Изменение заявки с id {}", bidDto.getId());
        Bid bid = bidRepository.findById(bidDto.getId())
                .orElseThrow(() -> {throw new NotFoundException("Заявка не найдена");});
        ofNullable(bidDto.getStatus()).ifPresent(bid::setStatus);
        return bidMapper.bidToBidDto(bidRepository.save(bid));

    }

    @Override
    public void deleteBid(Long id) {
        log.info("Удаление заявки с id {}", id);
        bidRepository.deleteById(id);
    }

    @Override
    public List<BidDto> getBidByUserId(Long id) {
        log.info("Выдача заявки пользователя с id {}", id);
        return bidRepository.getBidsByStudentId(id).stream().map(bidMapper::bidToBidDto).toList();
    }

    @Override
    public BidDto getBidDtoById(Long id) {
        log.info("Выдача заявки c id {}", id);
        return bidMapper.bidToBidDto(bidRepository.findById(id)
                .orElseThrow(() -> {throw new NotFoundException("Заявка не найдена");}));
    }

    @Override
    public List<BidDto> getBidByUniversityId(Long universityId) {
        log.info("Выдача запросов у университета с id {}", universityId);
        return bidRepository.getBidsByUniversityIdOrderByStatus(universityId)
                .stream().map(bidMapper::bidToBidDto).toList();
    }
}
