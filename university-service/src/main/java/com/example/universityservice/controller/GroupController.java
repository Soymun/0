package com.example.universityservice.controller;


import com.example.universityservice.dto.group.GroupCreateDto;
import com.example.universityservice.dto.group.GroupUpdateDto;
import com.example.universityservice.service.impl.GroupServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class GroupController {

    private final GroupServiceImpl groupService;

    @GetMapping("/group/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> getGroup(@PathVariable Long id){
        if (id == 0){
            throw new RuntimeException("Группа не найдена");
        }
        return ResponseEntity.ok(groupService.getGroupById(id));
    }

    @PostMapping("/group")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> saveGroup(@RequestBody GroupCreateDto groupDto){
        if(groupDto == null){
            throw new RuntimeException("Создать группу невозможно");
        }
        return ResponseEntity.ok(groupService.saveGroup(groupDto));
    }

    @PutMapping("/group")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateGroup(@RequestBody GroupUpdateDto groupDto){
        if(groupDto == null){
            throw new RuntimeException("Изменить группу невозможно");
        }
        return ResponseEntity.ok(groupService.updateGroup(groupDto));
    }

    @GetMapping("/groups/{page}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> getListGroup(@PathVariable Long page){
        if(page == 0){
            throw new RuntimeException("Страницы 0 не существует в природе");
        }
        return ResponseEntity.ok(groupService.getGroups(page));
    }

    @DeleteMapping("/group/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteGroup(@PathVariable Long id){
        groupService.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }
}
