package com.example.universityservice.service.impl;

import com.example.universityservice.dto.classroom.ClassRoomCreateDto;
import com.example.universityservice.dto.classroom.ClassRoomDto;
import com.example.universityservice.dto.classroom.ClassRoomUpdateDto;
import com.example.universityservice.entity.ClassRoom;
import com.example.universityservice.exception.NotFoundException;
import com.example.universityservice.mapper.ClassRoomMapper;
import com.example.universityservice.repositories.ClassRoomRepository;
import com.example.universityservice.service.ClassRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClassRoomServiceImpl implements ClassRoomService {

    private final ClassRoomRepository classRoomRepository;

    private final ClassRoomMapper classRoomMapper;

    @Override
    public ClassRoomDto getClassRoomById(Long id) {
        log.info("Выдача аудитории с id {}", id);
        return classRoomMapper.classRoomToClassRoomCreateDto(classRoomRepository.findById(id)
                .orElseThrow(() ->  new NotFoundException("Аудитория не найдена")));
    }

    @Override
    public ClassRoomDto getClassRoomByNameAndUniversityId(String name, Long universityId) {
        log.info("Выдача аудитории с названием {} и университету с id {}", name, universityId);
        return classRoomMapper.classRoomToClassRoomCreateDto(
                classRoomRepository.getClassRoomByUniversityIdAndClassRoom(universityId, name)
                        .orElseThrow(() ->  new NotFoundException("Аудитория не найдена")));
    }

    @Override
    public void saveClassRoom(ClassRoomCreateDto classRoomCreateDto) {
        log.info("Сохранение аудитории");
        classRoomRepository.save(classRoomMapper.classRoomCreateDtoToClassRoom(classRoomCreateDto));
    }

    @Override
    public void deleteClassRoom(Long id) {
        log.info("Удаление аудитории с id {}", id);
    }

    @Override
    public ClassRoomDto updateClassRoom(ClassRoomUpdateDto classRoomUpdateDto) {
        log.info("Изменение аудитории с id {}", classRoomUpdateDto.getId());
        ClassRoom classRoom = classRoomRepository.findById(classRoomUpdateDto.getId())
                .orElseThrow(() ->  new NotFoundException("Аудитория не найдена"));
        ofNullable(classRoomUpdateDto.getClassRoom()).ifPresent(classRoom::setClassRoom);
        return classRoomMapper.classRoomToClassRoomCreateDto(classRoomRepository.save(classRoom));
    }

    @Override
    public List<ClassRoomDto> getClassRoomByUniversityId(Long universityId) {
        log.info("Выдача всех аудитории в университете с id {}", universityId);
        return classRoomRepository.getClassRoomsByUniversityId(universityId)
                .stream().map(classRoomMapper::classRoomToClassRoomCreateDto)
                .toList();
    }
}
