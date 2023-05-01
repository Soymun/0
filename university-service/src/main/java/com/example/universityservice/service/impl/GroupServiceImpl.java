package com.example.universityservice.service.impl;

import com.example.universityservice.dto.group.GroupCreateDto;
import com.example.universityservice.dto.group.GroupDto;
import com.example.universityservice.dto.group.GroupUpdateDto;
import com.example.universityservice.entity.Group;
import com.example.universityservice.entity.Group_;
import com.example.universityservice.exception.NotFoundException;
import com.example.universityservice.mapper.GroupMapper;
import com.example.universityservice.repositories.GroupRepository;
import com.example.universityservice.service.GroupService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
@Slf4j
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    private final GroupMapper groupMapper;


    @PersistenceContext
    EntityManager entityManager;

    @Override
    public GroupDto getGroupById(Long id) {
        log.info("Выдача группы с id {}", id);
        return groupMapper.groupToGroupDto(groupRepository.getGroupsById(id).orElseThrow(() -> new NotFoundException("Группа не найдена")));
    }

    @Override
    public Group getGroupByName(String name) {
        log.info("Выдача группы с названием {}", name);
        return groupRepository.getGroupsByName(name).orElseThrow(() -> new NotFoundException("Группа не найдена"));

    }

    @Override
    public GroupDto updateGroup(GroupUpdateDto groupDto) {
        log.info("Изменение группы с id {}", groupDto.getId());
        Group group = groupRepository.getGroupsById(groupDto.getId()).orElseThrow(() -> new NotFoundException("Группа не найдена"));
        ofNullable(groupDto.getName()).ifPresent(group::setName);
        ofNullable(groupDto.getNumberCourse()).ifPresent(group::setNumberCourse);
        return groupMapper.groupToGroupDto(groupRepository.save(group));
    }

    @Override
    public void deleteGroup(Long id) {
        log.info("Удаление группы с id {}", id);
        groupRepository.deleteById(id);
    }

    @Override
    public GroupDto saveGroup(GroupCreateDto groupDto) {
        log.info("Сохранение группы с названием {}", groupDto.getName());
        return groupMapper.groupToGroupDto(groupRepository.save(groupMapper.groupDtoToGroup(groupDto)));
    }

    @Override
    public List<GroupDto> getGroups(Long page) {
        log.info("Выдача групп со страницы {}", page);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<GroupDto> cq = cb.createQuery(GroupDto.class);
        Root<Group> root = cq.from(Group.class);
        cq.orderBy(cb.asc(root.get(Group_.NAME)));
        cq.multiselect(
                root.get(Group_.ID),
                root.get(Group_.NAME)
        );
        return entityManager.createQuery(cq).setFirstResult((page.intValue()-1)*20).setMaxResults(page.intValue()*20).getResultList();
    }
}
