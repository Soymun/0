package com.example.lessonservice.service.Impl;


import com.example.lessonservice.dto.Week.WeekCreateDto;
import com.example.lessonservice.dto.Week.WeekDto;
import com.example.lessonservice.entity.Week;
import com.example.lessonservice.entity.Week_;
import com.example.lessonservice.exception.NotFoundException;
import com.example.lessonservice.mapper.WeekMapper;
import com.example.lessonservice.repositories.WeekRepository;
import com.example.lessonservice.service.WeekService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static java.util.Optional.ofNullable;

@Service
@Slf4j
@RequiredArgsConstructor
public class WeekServiceImpl implements WeekService {

    private final WeekRepository weekRepository;

    private final WeekMapper weekMapper;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public WeekDto findWeekById(Long id) {
        log.info("Выдача недели с id {}", id);
        return weekMapper.weekToWeekDto(weekRepository.findWeekById(id));
    }

    @Override
    public WeekDto findWeekByDay(LocalDateTime day) {
        log.info("Выдача недели по вхождению дня {}", day);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<WeekDto> cq = cb.createQuery(WeekDto.class);
        Root<Week> root = cq.from(Week.class);

        cq.where(cb.and(cb.lessThanOrEqualTo(root.get(Week_.FROM_WEEK), day),
                cb.greaterThanOrEqualTo(root.get(Week_.TO_WEEK), day)));

        cq.multiselect(
                root.get(Week_.ID),
                root.get(Week_.NUMBER_WEEK),
                root.get(Week_.FROM_WEEK),
                root.get(Week_.TO_WEEK)
        );
        return entityManager.createQuery(cq).getSingleResult();
    }

    @Override
    public WeekDto updateWeek(WeekDto weekDto) {
        log.info("Изменение недели с id {}", weekDto.getId());
        Week week = weekRepository.findById(weekDto.getId())
                .orElseThrow(()->{throw new NotFoundException("Неделя не найдена");});
        ofNullable(weekDto.getNumberWeek()).ifPresent(week::setNumberWeek);
        ofNullable(weekDto.getToWeek()).ifPresent(week::setToWeek);
        ofNullable(weekDto.getFromWeek()).ifPresent(week::setFromWeek);
        return weekMapper.weekToWeekDto(weekRepository.save(week));
    }

    @Override
    @Transactional
    public void deleteWeek(Long id) {
        log.info("Удаление недели с id {}", id);
        weekRepository.deleteById(id);
    }

    @Override
    public void saveWeek(WeekCreateDto weekCreateDto) {
        log.info("Сохранение недели");
        weekRepository.save(weekMapper.weekCreateDtoToWeek(weekCreateDto));
    }
}
