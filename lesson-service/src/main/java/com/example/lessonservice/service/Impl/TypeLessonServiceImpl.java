package com.example.lessonservice.service.Impl;

import com.example.lessonservice.Mapper.TypeMapper;
import com.example.lessonservice.dto.TypeLesson.TypeLessonDto;
import com.example.lessonservice.entity.TypeOfLesson;
import com.example.lessonservice.repositories.TypeRepository;
import com.example.lessonservice.service.TypeOfLessonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TypeLessonServiceImpl implements TypeOfLessonService {

    private final TypeRepository typeRepository;

    private final TypeMapper typeMapper;

    @Override
    public TypeLessonDto getTypeLessonById(Long id) {
        log.info("Выдача типа урока с id {}", id);
        return typeMapper.typeToTypeLessonDto(typeRepository.findById(id).orElseThrow(()-> new RuntimeException("Тип урока не найден")));
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
        TypeOfLesson type = typeRepository.findById(typeLessonDto.getId()).orElseThrow(()-> new RuntimeException("Тип урока не найден"));
        if(typeLessonDto.getType() != null){
            type.setType(typeLessonDto.getType());
        }

        if(typeLessonDto.getDescription() != null){
            type.setDescription(typeLessonDto.getDescription());
        }

        typeRepository.save(type);
    }

    @Override
    public void deleteTypeLessonById(Long id) {
        log.info("Удаление типа урока с id {}", id);
        typeRepository.deleteById(id);
    }
}
