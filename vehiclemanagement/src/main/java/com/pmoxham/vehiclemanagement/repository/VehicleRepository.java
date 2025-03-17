package com.pmoxham.vehiclemanagement.repository;

import com.pmoxham.vehiclemanagement.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Optional<Vehicle> findByVin(String vin);
}
