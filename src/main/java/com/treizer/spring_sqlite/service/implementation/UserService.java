package com.treizer.spring_sqlite.service.implementation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.treizer.spring_sqlite.persistence.entity.UserEntity;
import com.treizer.spring_sqlite.persistence.repository.IUserRepository;
import com.treizer.spring_sqlite.presentation.dto.UserDto;
import com.treizer.spring_sqlite.presentation.dto.UserInsertDto;
import com.treizer.spring_sqlite.presentation.dto.UserUpdateDto;
import com.treizer.spring_sqlite.service.interfaces.ICommonService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService implements ICommonService<UserDto, UserInsertDto, UserUpdateDto> {

    @Autowired
    private IUserRepository userRepository;

    private static final ModelMapper MAPPER = new ModelMapper();

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        var users = this.userRepository.findAll();

        return StreamSupport
                .stream(users.spliterator(), false)
                .map(user -> MAPPER.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto findById(Long id) {
        var userEntity = this.userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró al usuario con ID: " + id));

        return MAPPER.map(userEntity, UserDto.class);
    }

    @Override
    @Transactional
    public UserDto save(UserInsertDto insertDto) {
        try {
            var userEntity = MAPPER.map(insertDto, UserEntity.class);
            userEntity = this.userRepository.save(userEntity);
            return MAPPER.map(userEntity, UserDto.class);

        } catch (Exception e) {
            throw new UnsupportedOperationException("No fue posible guardar el usuario: " + insertDto +
                    " -> e: " + e.toString());
        }
    }

    @Override
    @Transactional
    public UserDto update(UserUpdateDto updateDto, Long id) {
        var userEntity = id != null
                ? this.userRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(
                                "No se encontró el usuario con ID: " + id))
                : this.userRepository.findById(updateDto.getId())
                        .orElseThrow(() -> new EntityNotFoundException(
                                "No se encontró el usuario con ID: " + updateDto.getId()));

        Optional.ofNullable(updateDto.getName()).ifPresent(userEntity::setName);

        Optional.ofNullable(updateDto.getLastName()).ifPresent(userEntity::setLastName);

        Optional.ofNullable(updateDto.getAge()).ifPresent(userEntity::setAge);

        Optional.ofNullable(updateDto.getEmail()).ifPresent(userEntity::setEmail);

        this.userRepository.save(userEntity);

        return MAPPER.map(userEntity, UserDto.class);
    }

    @Override
    @Transactional
    public UserDto deleteById(Long id) {
        var userEntity = this.userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se encontró el usuario con ID: " + id));

        this.userRepository.deleteById(id);

        return MAPPER.map(userEntity, UserDto.class);
    }

}
