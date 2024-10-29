package com.treizer.spring_sqlite.presentation.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserInsertDto {

    @NotBlank(message = "No debe ser nulo o vacío el nombre")
    @Size(min = 3, max = 20, message = "No debe tener menos de tres caracteres ni más de veinte")
    String name;

    @NotBlank(message = "No debe ser nulo o vació el apellido")
    @Size(min = 3, max = 50, message = "No debe tener menos de tres caracteres ni más de cincuenta")
    String lastName;

    @NotNull(message = "No debe ser nulo la edad")
    @Min(value = 10, message = "No puede tener menos de diez años")
    @Digits(integer = 2, fraction = 0, message = "No debe tener más de dos números enteros y no debe tener fracciones")
    Byte age;

    @NotBlank(message = "No debe ser nulo o vacío el email")
    @Email(message = "No tiene un formato válido el email")
    String email;

}
