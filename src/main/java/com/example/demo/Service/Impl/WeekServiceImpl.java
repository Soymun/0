package com.example.demo.Service.Impl;

import com.example.demo.Entity.Week;
import com.example.demo.Entity.Week_;
import com.example.demo.Repositories.WeekRepository;
import com.example.demo.Service.WeekService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;

@Service
public class WeekServiceImpl implements WeekService {

    private final WeekRepository weekRepository;

    @PersistenceContext
    EntityManager entityManager;

    public WeekServiceImpl(WeekRepository weekRepository) {
        this.weekRepository = weekRepository;
    }

    @Override
    public Week findWeekById(Long id) {
        return weekRepository.findWeekById(id);
    }

    @Override
    public Long findWeekId(LocalDateTime dateTime) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Week> root = cq.from(Week.class);

        cq.where(cb.and(cb.lessThanOrEqualTo(root.get(Week_.FROM_WEEK), dateTime), cb.greaterThanOrEqualTo(root.get(Week_.TO_WEEK), dateTime)));
        cq.multiselect(
                root.get(Week_.ID)
        );
        return entityManager.createQuery(cq).getSingleResult();
    }
}
