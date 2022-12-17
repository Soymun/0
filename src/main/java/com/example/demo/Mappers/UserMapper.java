package com.example.demo.Mappers;

import com.example.demo.DTO.UserDto;
import com.example.demo.Entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User userDtoToUser(UserDto userDto);

    UserDto userDtoToUser(User user);
}
