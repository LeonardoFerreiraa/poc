package br.com.leonardoferreira.poc.mapstruct.service;

import br.com.leonardoferreira.poc.mapstruct.domain.dto.UserDTO;
import br.com.leonardoferreira.poc.mapstruct.domain.entity.User;
import br.com.leonardoferreira.poc.mapstruct.mapper.UserMapper;
import br.com.leonardoferreira.poc.mapstruct.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public Page<UserDTO> findAll(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return userMapper.usersToUserDTOS(users);
    }
}
