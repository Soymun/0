package com.example.demo.Service.Impl;

import com.example.demo.DTO.GroupDto;
import com.example.demo.Entity.Group;
import com.example.demo.Mappers.GroupMapper;
import com.example.demo.Repositories.GroupRepository;
import com.example.demo.Service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    private final GroupMapper groupMapper;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository, GroupMapper groupMapper) {
        this.groupRepository = groupRepository;
        this.groupMapper = groupMapper;
    }

    @Override
    public GroupDto getGroupById(Long id) {
        return groupMapper.groupToGroupDto(groupRepository.getGroupsById(id).orElseThrow(() -> new RuntimeException("Group not found")));
    }

    @Override
    public GroupDto getGroupByName(String name) {
        return groupMapper.groupToGroupDto(groupRepository.getGroupsByName(name).orElseThrow(() -> new RuntimeException("Group not found")));

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
}
