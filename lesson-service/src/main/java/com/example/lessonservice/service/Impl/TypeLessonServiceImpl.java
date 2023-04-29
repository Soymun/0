package com.example.lessonservice.service.Impl;

import com.example.lessonservice.dto.TypeLesson.TypeLessonDto;
import com.example.lessonservice.entity.TypeOfLesson;
import com.example.lessonservice.exception.NotFoundException;
import com.example.lessonservice.mapper.TypeMapper;
import com.example.lessonservice.repositories.TypeRepository;
import com.example.lessonservice.service.TypeOfLessonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
@Slf4j
@RequiredArgsConstructor
public class TypeLessonServiceImpl implements TypeOfLessonService {

    private final TypeRepository typeRepository;

    private final TypeMapper typeMapper;

    @Override
    public TypeLessonDto getTypeLessonById(Long id) {
        log.info("Выдача типа урока с id {}", id);
        return typeMapper.typeToTypeLessonDto(typeRepository.findById(id).orElseThrow(()-> new NotFoundException("Тип урока не найден")));
    }

    @Override
    public List<TypeLessonDto> getTypeLessons() {
        log.info("Выдача всех типов уроков");
        return typeRepository.findAll().stream().map(typeMapper::typeToTypeLessonDto).toList();
    }

    @Override
    public void saveTypeLesson(TypeLessonDto typeLessonDto) {
        log.info("Сохранение типа урока");
        typeRepository.save(typeMapper.typeLessonDtoToType(typeLessonDto));
    }

    @Override
    public void updateTypeLesson(TypeLessonDto typeLessonDto) {
        log.info("Изменение типа урока с id {}", typeLessonDto.getId());
        TypeOfLesson type = typeRepository.findById(typeLessonDto.getId()).orElseThrow(()-> new NotFoundException("Тип урока не найден"));
        ofNullable(typeLessonDto.getType()).ifPresent(type::setType);
        ofNullable(typeLessonDto.getDescription()).ifPresent(type::setDescription);
        typeRepository.save(type);
    }

    @Override
    public void deleteTypeLessonById(Long id) {
        log.info("Удаление типа урока с id {}", id);
        typeRepository.deleteById(id);
    }
}
