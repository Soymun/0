package com.example.universityservice.repositories;


import com.example.universityservice.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {

    Optional<Group> getGroupsById(Long id);

    Optional<Group> getGroupsByName(String name);
}
