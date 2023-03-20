package com.example.demo.Controller;


import com.example.demo.DTO.Group.GroupDto;
import com.example.demo.Response.ResponseDto;
import com.example.demo.Service.Impl.GroupServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ytsu")
public class GroupController {

    private final GroupServiceImpl groupService;

    public GroupController(GroupServiceImpl groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/group/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> getGroup(@PathVariable Long id){
        if (id == 0){
            throw new RuntimeException("Группа не найдена");
        }
        return ResponseEntity.ok(ResponseDto.builder().body(groupService.getGroupById(id)).build());
    }

    @PostMapping("/group")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> saveGroup(@RequestBody GroupDto groupDto){
        if(groupDto == null){
            throw new RuntimeException("Создать группу невозможно");
        }
        return ResponseEntity.ok(ResponseDto.builder().body(groupService.saveGroup(groupDto)).build());
    }

    @PutMapping("/group")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateGroup(@RequestBody GroupDto groupDto){
        if(groupDto == null){
            throw new RuntimeException("Изменить группу невозможно");
        }
        return ResponseEntity.ok(ResponseDto.builder().body(groupService.updateGroup(groupDto)).build());
    }

    @GetMapping("/groups/{page}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> getListGroup(@PathVariable Long page){
        if(page == 0){
            throw new RuntimeException("Страницы 0 не существует в природе");
        }
        return ResponseEntity.ok(ResponseDto.builder().body(groupService.getGroups(page)).build());
    }

    @DeleteMapping("/group/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteGroup(@PathVariable Long id){
        groupService.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }
}
