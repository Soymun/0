package com.example.universityservice.repositories;

import com.example.universityservice.entity.ClassRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClassRoomRepository extends JpaRepository<ClassRoom, Long> {

    Optional<ClassRoom> getClassRoomByUniversityIdAndClassRoom(Long universityId, String classRoom);

    List<ClassRoom> getClassRoomsByUniversityId(Long universityId);
}
