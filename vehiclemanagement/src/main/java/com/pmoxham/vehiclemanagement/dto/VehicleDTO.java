package com.pmoxham.vehiclemanagement.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.Year;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VehicleDTO {
    @NotBlank(message = "VIN must not be blank")
    private String vin;

    @Min(value = 1900, message = "Vehicle year must be no earlier than 1900")
    private int vehicleYear;

    @NotBlank(message = "Make must not be blank")
    @Size(max = 20, message = "Make must not exceed 20 characters")
    private String make;

    @NotBlank(message = "Model must not be blank")
    @Size(max = 20, message = "Model must not exceed 20 characters")
    private String model;

    @PositiveOrZero(message = "Mileage must be zero or positive")
    private double mileage;
}
