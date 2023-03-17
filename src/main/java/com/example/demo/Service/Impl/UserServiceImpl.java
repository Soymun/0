package com.example.demo.Service.Impl;

import com.example.demo.DTO.User.UserDto;
import com.example.demo.DTO.User.UserUpdateDto;
import com.example.demo.Entity.User;
import com.example.demo.Mappers.UserMapper;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public UserDto getUserById(Long id) {
        log.info("Выдача пользователя с id {}", id);
        return userMapper.userToUserDto(userRepository.getUserById(id).orElse(new User()));
    }

    @Override
    public UserDto getUserByEmail(String email) {
        log.info("Выдача пользователя с email");
        return userMapper.userToUserDto(userRepository.getUserByEmail(email).orElse(new User()));

    }

    @Override
    public UserDto updateUser(UserUpdateDto userUpdateDto) {
        log.info("Изменение пользователя с id {}", userUpdateDto.getId());
        User findUser = userRepository.getUserById(userUpdateDto.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if(userUpdateDto.getEmail() != null){
            findUser.setEmail(userUpdateDto.getEmail());
        }
        if(userUpdateDto.getRole() != null){
            findUser.setRole(userUpdateDto.getRole());
        }
        if(userUpdateDto.getPassword() != null){
            findUser.setPassword(userUpdateDto.getPassword());
        }
        if(userUpdateDto.getName() != null){
            findUser.setName(userUpdateDto.getName());
        }
        if(userUpdateDto.getSurname() != null){
            findUser.setSurname(userUpdateDto.getSurname());
        }
        if(userUpdateDto.getPatronymic() != null){
            findUser.setPatronymic(userUpdateDto.getPatronymic());
        }
        if(userUpdateDto.getBirthday() != null){
            findUser.setBirthday(userUpdateDto.getBirthday());
        }
        if(userUpdateDto.getGroupId() != null){
            findUser.setGroupId(userUpdateDto.getGroupId());
        }
        return userMapper.userToUserDto(userRepository.save(findUser));
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        log.info("Удаление пользователя с id {}", id);
        userRepository.deleteById(id);
    }

    @Override
    public UserDto saveUser(User user) {
        log.info("Сохранение пользователя");
        return userMapper.userToUserDto(userRepository.save(user));
    }

    @Override
    public List<UserDto> getUserByGroupId(Long id) {
        log.info("Выдача пользователей по группе с id {}", id);
        return userRepository.findUserByGroupId(id)
                .stream()
                .filter(Objects::nonNull)
                .map(userMapper::userToUserDto)
                .toList();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (username == null || username.equals("")) {
            throw new RuntimeException("User not found");
        }

        User user = userRepository
                .getUserByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), user.getRole().authority()
        );
    }
}
