package br.com.leonardoferreira.poc.mapstruct.service;

import br.com.leonardoferreira.poc.mapstruct.domain.entity.User;
import br.com.leonardoferreira.poc.mapstruct.domain.response.UserResponse;
import br.com.leonardoferreira.poc.mapstruct.exception.ResourceNotFoundException;
import br.com.leonardoferreira.poc.mapstruct.mapper.UserMapper;
import br.com.leonardoferreira.poc.mapstruct.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserService(final UserRepository userRepository,
                       final UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional(readOnly = true)
    public Page<UserResponse> findAll(final Pageable pageable) {
        final Page<User> users = userRepository.findAll(pageable);
        return userMapper.usersToUserResponse(users);
    }

    @Transactional(readOnly = true)
    public UserResponse findById(final Long id) {
        return userRepository.findById(id)
                .map(userMapper::userToUserResponse)
                .orElseThrow(ResourceNotFoundException::new);
    }
}
