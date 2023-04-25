package com.example.lessonservice.repositories;



import com.example.lessonservice.entity.ClassRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClassRoomRepository  extends JpaRepository<ClassRoom, Long> {

    Optional<ClassRoom> getClassRoomByClassRoom(String classRoom);
}
