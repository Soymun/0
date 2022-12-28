package com.example.demo.Service.Impl;


import com.example.demo.DTO.MarksDto;
import com.example.demo.Mappers.MarksMapper;
import com.example.demo.Repositories.LessonNameRepository;
import com.example.demo.Repositories.MarksRepository;
import com.example.demo.Service.MarksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarksServiceImpl implements MarksService {

    private final MarksRepository marksRepository;

    private final MarksMapper marksMapper;

    private final LessonNameRepository lessonNameRepository;

    @Autowired
    public MarksServiceImpl(MarksRepository marksRepository, MarksMapper marksMapper, LessonNameRepository lessonNameRepository) {
        this.marksRepository = marksRepository;
        this.marksMapper = marksMapper;
        this.lessonNameRepository = lessonNameRepository;
    }

    @Override
    public MarksDto saveMark(MarksDto marksDto) {
        return marksMapper.marksToMarksDto(marksRepository.save(marksMapper.marksDtoToMarks(marksDto)));
    }

    @Override
    public MarksDto updateMarks(MarksDto marksDto) {
        MarksDto marksDto1 = marksMapper.marksToMarksDto(marksRepository.getMarksById(marksDto.getId()));
        if(marksDto1 == null){
            throw new RuntimeException("Оценка не найдена");
        }
        if (marksDto.getMark() != 0){
            marksDto1.setMark(marksDto.getMark());
        }
        if(marksDto.getLessonId() != null){
            marksDto1.setLessonId(marksDto.getLessonId());
        }
        return marksMapper.marksToMarksDto(marksRepository.save(marksMapper.marksDtoToMarks(marksDto1)));
    }

    @Override
    public MarksDto getMarksById(Long id) {
        return marksMapper.marksToMarksDto(marksRepository.getMarksById(id));
    }

    @Override
    public List<MarksDto> getMarksByUserId(Long id) {
        return marksRepository.getMarksByUserId(id).stream().map(marksMapper::marksToMarksDto).toList();
    }

    @Override
    public void deleteMarks(Long id) {
        marksRepository.deleteById(id);
    }
}
