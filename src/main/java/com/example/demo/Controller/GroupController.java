package com.example.demo.Controller;


import com.example.demo.DTO.GroupDto;
import com.example.demo.Response.ResponseDto;
import com.example.demo.Service.Impl.GroupServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ytsu")
public class GroupController {

    private final GroupServiceImpl groupService;

    public GroupController(GroupServiceImpl groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/group/{id}")
    public ResponseEntity<?> getGroup(@PathVariable Long id){
        if (id == 0){
            throw new RuntimeException("Группа не найдена");
        }
        return ResponseEntity.ok(ResponseDto.builder().body(groupService.getGroupById(id)).build());
    }

    @PostMapping("/group")
    public ResponseEntity<?> saveGroup(@RequestBody GroupDto groupDto){
        if(groupDto == null){
            throw new RuntimeException("Создать группу невозможно");
        }
        return ResponseEntity.ok(ResponseDto.builder().body(groupService.saveGroup(groupDto)).build());
    }

    @PutMapping("/group")
    public ResponseEntity<?> updateGroup(@RequestBody GroupDto groupDto){
        if(groupDto == null){
            throw new RuntimeException("Изменить группу невозможно");
        }
        return ResponseEntity.ok(ResponseDto.builder().body(groupService.updateGroup(groupDto)).build());
    }
}
