package com.example.lessonservice.service.Impl;

import com.example.lessonservice.dto.ClassRoom;
import com.example.lessonservice.dto.Course;
import com.example.lessonservice.dto.Group;
import com.example.lessonservice.dto.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
//TODO: URL
public class RestServiceTemplate {

    private final ObjectProvider<RestTemplate> restTemplateObjectProvider;


    @Cacheable(value = "teacher", key = "#nameTeacher")
    public Teacher getTeacher(String nameTeacher){
        RestTemplate restTemplate = restTemplateObjectProvider.getObject();
        return restTemplate.getForObject("http://localhost:8072/teacher/v1/teacher/{teacherName}", Teacher.class, nameTeacher);
    }

    @Cacheable(value = "teacher", key = "{#id, #name}")
    public ClassRoom getClassRoomByUniversityIdAndName(Long id, String name){
        RestTemplate restTemplate = restTemplateObjectProvider.getObject();
        return restTemplate.getForObject("http://localhost:8072/university/v1/classRoom/{id}/{name}", ClassRoom.class, id, name);
    }

    @Cacheable(value = "teacher", key = "#id")
    public ClassRoom getClassRoomById(Long id){
        RestTemplate restTemplate = restTemplateObjectProvider.getObject();
        return restTemplate.getForObject("http://localhost:8072/university/v1/classRoom/{id}", ClassRoom.class, id);
    }

    @Cacheable(value = "teacher", key = "#id")
    public Teacher getTeacher(Long id){
        RestTemplate restTemplate = restTemplateObjectProvider.getObject();
        return restTemplate.getForObject("http://localhost:8072/teacher/v1/teacher/{id}", Teacher.class, id);
    }

    @Cacheable(value = "group", key = "{#group, #nameLesson}")
    public Course getCourse(String group, String nameLesson){
        RestTemplate restTemplate = restTemplateObjectProvider.getObject();
        return restTemplate.getForObject("http://localhost:8072/course/v1/course/{groupId}/{nameLesson}", Course.class, group, nameLesson);
    }

    @Cacheable(value = "group", key = "#id")
    public Course getCourse(Long id){
        RestTemplate restTemplate = restTemplateObjectProvider.getObject();
        return restTemplate.getForObject("http://localhost:8072/course/v1/course/{id}", Course.class, id);
    }

    @Cacheable(value = "group", key = "#name")
    public Group getGroupIdByName(String name){
        RestTemplate restTemplate = restTemplateObjectProvider.getObject();
        return restTemplate.getForObject("http://localhost:8072/group/v1/group/{name}", Group.class, name);
    }

    @Cacheable(value = "group", key = "#id")
    public List<Group> getGroupById(Long id){
        RestTemplate restTemplate = restTemplateObjectProvider.getObject();
        return Arrays.stream(Objects.requireNonNull(restTemplate.getForObject("http://localhost:8072/group/v1/group/{id}", Group[].class, id))).toList();
    }
}
