package com.example.demo.Service.Impl;

import com.example.demo.DTO.GroupDto;
import com.example.demo.Entity.Group;
import com.example.demo.Entity.Group_;
import com.example.demo.Mappers.GroupMapper;
import com.example.demo.Repositories.GroupRepository;
import com.example.demo.Service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    private final GroupMapper groupMapper;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository, GroupMapper groupMapper) {
        this.groupRepository = groupRepository;
        this.groupMapper = groupMapper;
    }

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public GroupDto getGroupById(Long id) {
        return groupMapper.groupToGroupDto(groupRepository.getGroupsById(id).orElseThrow(() -> new RuntimeException("Group not found")));
    }

    @Override
    @Cacheable(value = "group", key = "#name")
    public Group getGroupByName(String name) {
        return groupRepository.getGroupsByName(name).orElseThrow(() -> new RuntimeException("Group not found"));

    }

    @Override
    public GroupDto updateGroup(GroupDto groupDto) {
        Group group = groupRepository.getGroupsById(groupDto.getId()).orElseThrow(() -> new RuntimeException("Group not found"));
        if(groupDto.getName() != null){
            group.setName(groupDto.getName());
        }
        return groupMapper.groupToGroupDto(groupRepository.save(group));
    }

    @Override
    public void deleteGroup(Long id) {
        groupRepository.deleteById(id);
    }

    @Override
    public GroupDto saveGroup(GroupDto groupDto) {
        return groupMapper.groupToGroupDto(groupRepository.save(groupMapper.groupDtoToGroup(groupDto)));
    }

    @Override
    public List<GroupDto> getGroups(Long page) {
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
