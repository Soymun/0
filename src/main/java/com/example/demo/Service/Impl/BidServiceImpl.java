package com.example.demo.Service.Impl;

import com.example.demo.DTO.Bid.BidDto;
import com.example.demo.DTO.Bid.BidUpdateDto;
import com.example.demo.Entity.Bid;
import com.example.demo.Mappers.BidMapper;
import com.example.demo.Repositories.BidRepository;
import com.example.demo.Service.BidService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BidServiceImpl implements BidService {

    private final BidRepository bidRepository;

    private final BidMapper bidMapper;

    @Override
    public void saveBid(BidDto bidDto) {
        log.info("Сохранение заявки");
        bidDto.setCompleted(false);
        bidRepository.save(bidMapper.bidDtoToBid(bidDto));
    }

    @Override
    public BidDto updateBid(BidUpdateDto bidDto) {
        log.info("Изменение заявки с id {}", bidDto.getId());
        Bid bid = bidRepository.findById(bidDto.getId())
                .orElseThrow(() -> {throw new RuntimeException("Заявка не найденна");});
        if (bidDto.isCompleted()){
            bid.setCompleted(true);
        }
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
        return bidRepository.getBidsByUserId(id).stream().map(bidMapper::bidToBidDto).toList();
    }

    @Override
    public BidDto getBidDtoById(Long id) {
        log.info("Выдача заявки c id {}", id);
        return bidMapper.bidToBidDto(bidRepository.findById(id)
                .orElseThrow(() -> {throw new RuntimeException("Заявка не найденна");}));
    }
}
