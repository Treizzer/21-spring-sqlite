package com.treizer.spring_sqlite.presentation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.treizer.spring_sqlite.presentation.advice.custom.BadRequestException;
import com.treizer.spring_sqlite.presentation.dto.UserDto;
import com.treizer.spring_sqlite.presentation.dto.UserInsertDto;
import com.treizer.spring_sqlite.presentation.dto.UserUpdateDto;
import com.treizer.spring_sqlite.service.interfaces.ICommonService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private ICommonService<UserDto, UserInsertDto, UserUpdateDto> userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll() {
        return ResponseEntity.ok(this.userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().build();
        }

        var userDto = this.userService.findById(id);

        return userDto == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(userDto);
    }

    @PostMapping
    public ResponseEntity<UserDto> save(@Valid @RequestBody UserInsertDto userInsertDto) {
        var userDto = this.userService.save(userInsertDto);

        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(userDto.getId())
                        .toUri())
                .body(userDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@Valid @RequestBody UserUpdateDto userUpdateDto,
            @PathVariable Long id) {
        if (id <= 0) {
            return ResponseEntity.badRequest().build();
        }

        var userDto = this.userService.update(userUpdateDto, id);

        return userDto == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(userDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> deleteById(@PathVariable Long id) {
        if (id == null || id <= 0) {
            // Testing my exception for first time in method controller
            throw new BadRequestException("No puede ser nulo y no puede ser meor igual a cero un ID:" + id);
            // return ResponseEntity.badRequest().build();
        }

        var userDto = this.userService.deleteById(id);

        return userDto == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(userDto);
    }

}
