package com.example.courseservice.service.impl;


import com.example.courseservice.dto.marks.MarkCreateDto;
import com.example.courseservice.dto.marks.MarksDto;
import com.example.courseservice.dto.marks.MarksUpdateDto;
import com.example.courseservice.entity.Courses;
import com.example.courseservice.entity.Courses_;
import com.example.courseservice.entity.Marks;
import com.example.courseservice.entity.Marks_;
import com.example.courseservice.mappers.MarksMapper;
import com.example.courseservice.repositories.MarksRepository;
import com.example.courseservice.service.MarksService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
@Slf4j
@RequiredArgsConstructor
public class MarksServiceImpl implements MarksService {

    private final MarksRepository marksRepository;
    private final MarksMapper marksMapper;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void saveMark(MarkCreateDto marksDto) {
        log.info("Сохранение оценки");
        marksMapper.marksToMarksDto(marksRepository.save(marksMapper.marksDtoToMarks(marksDto)));
    }

    @Override
    public MarksDto updateMarks(MarksUpdateDto marksDto) {
        log.info("Изменение оценки с id {}", marksDto.getId());
        Marks foundMark = marksRepository.findById(marksDto.getId())
                .orElseThrow(() -> {
                    throw new RuntimeException("Оценка не найдена");
                });
        ofNullable(marksDto.getMark()).ifPresent(foundMark::setMark);
        return marksMapper.marksToMarksDto(marksRepository.save(foundMark));
    }

    @Override
    public MarksDto getMarksById(Long id) {
        log.info("Выдача оценки с id {}", id);
        return marksMapper.marksToMarksDto(marksRepository.findById(id)
                .orElseThrow(() -> {
                    throw new RuntimeException("Оценка не найдена");
                }));
    }

    @Override
    public List<MarksDto> getMarksByUserId(Long id) {
        log.info("Выдача оценок по пользователю с id {}", id);
        return marksRepository.getMarksByStudentId(id).stream().map(marksMapper::marksToMarksDto).toList();
    }

    @Override
    @Transactional
    public void deleteMarks(Long id) {
        log.info("Удаление оценки с id {}", id);
        marksRepository.deleteById(id);
    }

    @Override
    public List<MarksDto> getMarksByCoursesAndGroup(Long coursesId, List<Long> studentIds) {
        log.info("Выдача оценок по курсу с id {}", coursesId);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<MarksDto> cq = cb.createQuery(MarksDto.class);
        Root<Marks> root = cq.from(Marks.class);

        Subquery<Courses> subquery = cq.subquery(Courses.class);
        Root<Marks> subRoot = subquery.from(Marks.class);
        Join<Marks, Courses> join = subRoot.join(Marks_.COURSES);

        subquery.where(cb.equal(root.get(Marks_.courses_id), join.get(Courses_.id)));

        subquery.select(join);

        cq.where(cb.and(cb.in(root.get(Marks_.studentId)).in(studentIds), cb.equal(root.get(Marks_.courses_id), coursesId)));

        cq.multiselect(
                root.get(Marks_.id),
                root.get(Marks_.studentId),
                subquery,
                root.get(Marks_.mark)
        );
        return entityManager.createQuery(cq).getResultList();
    }
}
