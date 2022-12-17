package com.example.demo.Service.Impl;

import com.example.demo.DTO.UserDto;
import com.example.demo.Entity.User;
import com.example.demo.Mappers.UserMapper;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto getUserById(Long id) {
        return userMapper.userDtoToUser(userRepository.getUserById(id).orElse(new User()));
    }

    @Override
    public UserDto getUserByUsername(String username) {
        return userMapper.userDtoToUser(userRepository.getUserByUsername(username).orElse(new User()));

    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        User user = userRepository.getUserById(userDto.getId()).orElseThrow(() -> new RuntimeException("User not found"));
        if(userDto.getUsername() != null){
            user.setUsername(userDto.getUsername());
        }
        if(userDto.getRole() != null){
            user.setRole(userDto.getRole());
        }
        if(userDto.getGroupId() != null){
            user.setGroupId(user.getGroupId());
        }
        return userMapper.userDtoToUser(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDto saveUser(User user) {
        return userMapper.userDtoToUser(userRepository.save(user));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username == null || username.equals("")){
            throw new RuntimeException("User not found");
        }
        User user = userRepository.getUserByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getRole().authority());
    }
}
