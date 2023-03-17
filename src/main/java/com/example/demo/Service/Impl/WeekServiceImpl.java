package com.example.demo.Service.Impl;

import com.example.demo.DTO.Week.WeekCreateDto;
import com.example.demo.DTO.Week.WeekDto;
import com.example.demo.Entity.Week;
import com.example.demo.Entity.Week_;
import com.example.demo.Mappers.WeekMapper;
import com.example.demo.Repositories.WeekRepository;
import com.example.demo.Service.WeekService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;

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
    public Long findWeekByDay(LocalDateTime day) {
        log.info("Выдача недели по вхождению дня {}", day);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Week> root = cq.from(Week.class);

        cq.where(cb.and(cb.lessThanOrEqualTo(root.get(Week_.FROM_WEEK), day),
                cb.greaterThanOrEqualTo(root.get(Week_.TO_WEEK), day)));

        cq.multiselect(
                root.get(Week_.ID)
        );
        return entityManager.createQuery(cq).getSingleResult();
    }

    @Override
    public WeekDto updateWeek(WeekDto weekDto) {
        log.info("Изменение недели с id {}", weekDto.getId());
        Week week = weekRepository.findById(weekDto.getId())
                .orElseThrow(()->{throw new RuntimeException("Недля не найденна");});
        if(weekDto.getToWeek() != null){
            week.setToWeek(weekDto.getToWeek());
        }
        if(weekDto.getFromWeek() != null){
            week.setFromWeek(weekDto.getFromWeek());
        }
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
