package com.pmoxham.vehiclemanagement.repository;

import com.pmoxham.vehiclemanagement.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
