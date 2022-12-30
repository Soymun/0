package com.example.demo.Service.Impl;

import com.example.demo.DTO.BidDto;
import com.example.demo.DTO.TypeOfBidDto;
import com.example.demo.Entity.Bid;
import com.example.demo.Entity.TypeOfBid;
import com.example.demo.Mappers.BidMapper;
import com.example.demo.Repositories.BidRepository;
import com.example.demo.Repositories.TypeOfBidRepository;
import com.example.demo.Service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidServiceImpl implements BidService {

    private final BidRepository bidRepository;

    private final TypeOfBidRepository typeOfBidRepository;

    private final BidMapper bidMapper;

    @Autowired
    public BidServiceImpl(BidRepository bidRepository, TypeOfBidRepository type, BidMapper bidMapper) {
        this.bidRepository = bidRepository;
        this.typeOfBidRepository = type;
        this.bidMapper = bidMapper;
    }

    @Override
    public BidDto saveBid(BidDto bidDto) {
        bidDto.setCompleted(false);
        return bidMapper.bidToBidDto(bidRepository.save(bidMapper.bidDtoToBid(bidDto)));
    }

    @Override
    public BidDto updateBid(BidDto bidDto) {
        Bid bid = bidRepository.getBidById(bidDto.getId());
        if(bidDto.getTypeOfBid() != null){
            bid.setTypeOfBidId(bid.getTypeOfBidId());
        }
        if (bidDto.isCompleted()){
            bid.setCompleted(true);
        }
        return bidMapper.bidToBidDto(bidRepository.save(bid));

    }

    @Override
    public void deleteBid(Long id) {
        bidRepository.deleteById(id);
    }

    @Override
    public List<BidDto> getBidByUserId(Long id) {
        return bidRepository.getBidsByUserId(id).stream().map(bidMapper::bidToBidDto).toList();
    }

    @Override
    public BidDto getBidDtoById(Long id) {
        return bidMapper.bidToBidDto(bidRepository.getBidById(id));
    }

    @Override
    public TypeOfBidDto saveTypeOfBid(TypeOfBidDto type) {
        return bidMapper.typeOfBidToTypeOfBidDto(typeOfBidRepository.save(bidMapper.typeOfBidDtoToTypeOfBid(type)));
    }

    @Override
    public TypeOfBidDto updateTypeOfBid(TypeOfBidDto type) {
        TypeOfBid typeOfBid = typeOfBidRepository.getTypeOfBidById(type.getId());
        if(type.getName() != null){
            typeOfBid.setName(type.getName());
        }
        return bidMapper.typeOfBidToTypeOfBidDto(typeOfBidRepository.save(typeOfBid));
    }

    @Override
    public void deleteTypeOfBid(Long id) {
        typeOfBidRepository.deleteById(id);
    }

    @Override
    public TypeOfBidDto getTypeOfBid(Long id) {
        return bidMapper.typeOfBidToTypeOfBidDto(typeOfBidRepository.getTypeOfBidById(id));
    }

    @Override
    public List<TypeOfBidDto> getListTypeOfBid() {
        return typeOfBidRepository.findAll().stream().map(bidMapper::typeOfBidToTypeOfBidDto).toList();
    }
}
