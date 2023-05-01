package com.example.universityservice.service.impl;

import com.example.universityservice.dto.university.UniversityCreateDto;
import com.example.universityservice.dto.university.UniversityDto;
import com.example.universityservice.entity.University;
import com.example.universityservice.exception.NotFoundException;
import com.example.universityservice.mapper.UniversityMapper;
import com.example.universityservice.repositories.UniversityRepository;
import com.example.universityservice.service.UniversityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
@Slf4j
@RequiredArgsConstructor
public class UniversityServiceImpl  implements UniversityService {

    private UniversityRepository universityRepository;

    private UniversityMapper universityMapper;

    @Override
    public UniversityDto getUniversityById(Long id) {
        log.info("Выдача университета с id {}", id);
        return universityMapper.universityToUniversityDto(universityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Университет не найден")));
    }

    @Override
    public void saveUniversity(UniversityCreateDto universityCreateDto) {
        log.info("Сохранение университета");
        universityRepository.save(universityMapper.universityCreateDtoToUniversity(universityCreateDto));
    }

    @Override
    public UniversityDto updateUniversity(UniversityDto universityDto) {
        log.info("Изменение университета с id {}", universityDto.getId());
        University university = universityRepository.findById(universityDto.getId())
                .orElseThrow(() -> new NotFoundException("Университет не найден"));
        ofNullable(universityDto.getName()).ifPresent(university::setName);
        ofNullable(universityDto.getCity()).ifPresent(university::setCity);
        ofNullable(universityDto.getStreet()).ifPresent(university::setStreet);
        ofNullable(universityDto.getHouse()).ifPresent(university::setHouse);
        ofNullable(universityDto.getXCoordinate()).ifPresent(university::setXCoordinate);
        ofNullable(universityDto.getYCoordinate()).ifPresent(university::setYCoordinate);
        return universityMapper.universityToUniversityDto(universityRepository.save(university));
    }

    @Override
    public void deleteUniversityById(Long id) {
        log.info("Удаление университета с id {}", id);
        universityRepository.deleteById(id);
    }

    @Override
    public List<UniversityDto> getAllUniversities() {
        log.info("Выдача всех университетов");
        return universityRepository.findAll().stream().map(universityMapper::universityToUniversityDto).toList();
    }
}
