package com.example.demo.Service.Impl;


import com.example.demo.DTO.Marks.MarkCreateDto;
import com.example.demo.DTO.Marks.MarksDto;
import com.example.demo.DTO.Marks.MarksUpdateDto;
import com.example.demo.Entity.Marks;
import com.example.demo.Entity.Marks_;
import com.example.demo.Entity.User;
import com.example.demo.Entity.User_;
import com.example.demo.Mappers.MarksMapper;
import com.example.demo.Repositories.MarksRepository;
import com.example.demo.Service.MarksService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

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
                    throw new RuntimeException("Оценка не найденна");
                });
        if (marksDto.getMark() != null) {
            foundMark.setMark(marksDto.getMark());
        }
        return marksMapper.marksToMarksDto(marksRepository.save(foundMark));
    }

    @Override
    public MarksDto getMarksById(Long id) {
        log.info("Выдача оценки с id {}", id);
        return marksMapper.marksToMarksDto(marksRepository.findById(id)
                .orElseThrow(() -> {
                    throw new RuntimeException("Оценка не найденна");
                }));
    }

    @Override
    public List<MarksDto> getMarksByUserId(Long id) {
        log.info("Выдача оценок по пользователю с id {}", id);
        return marksRepository.getMarksByUserId(id).stream().map(marksMapper::marksToMarksDto).toList();
    }

    @Override
    @Transactional
    public void deleteMarks(Long id) {
        log.info("Удаление оценки с id {}", id);
        marksRepository.deleteById(id);
    }

    @Override
    public List<MarksDto> getMarksByCoursesAndGroup(Long coursesId, Long groupId) {
        log.info("Выдача оценок по курсу с id {} и группе с id {}", coursesId, groupId);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<MarksDto> cq = cb.createQuery(MarksDto.class);
        Root<Marks> root = cq.from(Marks.class);
        Join<Marks, User> join = root.join(Marks_.USER);

        cq.where(cb.and(cb.equal(join.get(User_.groupId), groupId), cb.equal(root.get(Marks_.courses_id), coursesId)));

        cq.orderBy(cb.asc(join.get(User_.name)));

        cq.multiselect(
                root.get(Marks_.id),
                root.get(Marks_.userId),
                root.get(Marks_.courses_id),
                root.get(Marks_.mark)
        );
        return entityManager.createQuery(cq).getResultList();
    }
}
