package com.example.demo.Repositories;

import com.example.demo.Entity.ClassRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClassRoomRepository  extends JpaRepository<ClassRoom, Long> {

    Optional<ClassRoom> getClassRoomByClassRoom(String classRoom);
}
