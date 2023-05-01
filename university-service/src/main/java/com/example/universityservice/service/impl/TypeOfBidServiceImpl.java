package com.example.universityservice.service.impl;

import com.example.universityservice.dto.type.TypeOfBidCreateDto;
import com.example.universityservice.dto.type.TypeOfBidDto;
import com.example.universityservice.entity.TypeOfBid;
import com.example.universityservice.exception.NotFoundException;
import com.example.universityservice.mapper.BidMapper;
import com.example.universityservice.repositories.TypeOfBidRepository;
import com.example.universityservice.service.TypeOfBidService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TypeOfBidServiceImpl implements TypeOfBidService {

    private final TypeOfBidRepository typeOfBidRepository;

    private final BidMapper bidMapper;

    @Override
    public TypeOfBidDto saveTypeOfBid(TypeOfBidCreateDto type) {
        log.info("Сохранение типа заявки");
        return bidMapper.typeOfBidToTypeOfBidDto(typeOfBidRepository.save(bidMapper.typeOfBidCreateDtoToTypeOfBid(type)));
    }

    @Override
    public TypeOfBidDto updateTypeOfBid(TypeOfBidDto type) {
        log.info("Изменение типа заявки с id {}", type.getId());
        TypeOfBid typeOfBid = typeOfBidRepository.findById(type.getId())
                .orElseThrow(() -> {throw new NotFoundException("Тип заявки не найден");});
        if(type.getName() != null){
            typeOfBid.setName(type.getName());
        }
        return bidMapper.typeOfBidToTypeOfBidDto(typeOfBidRepository.save(typeOfBid));
    }

    @Override
    @Transactional
    public void deleteTypeOfBid(Long id) {
        log.info("Удаление типа заявки с id {}", id);
        typeOfBidRepository.deleteById(id);
    }

    @Override
    public TypeOfBidDto getTypeOfBid(Long id) {
        log.info("Выдача типа заявки с id {}", id);
        return bidMapper.typeOfBidToTypeOfBidDto(typeOfBidRepository.findById(id)
                .orElseThrow(() -> {throw new NotFoundException("Тип заявки не найден");}));
    }

    @Override
    public List<TypeOfBidDto> getListTypeOfBidByUniversityId(Long universityId) {
        log.info("Выдача типов заявок по университету с id {}", universityId);
        return typeOfBidRepository.getTypeOfBidsByUniversityId(universityId).stream().map(bidMapper::typeOfBidToTypeOfBidDto).toList();
    }
}
