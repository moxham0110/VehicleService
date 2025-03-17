package com.pmoxham.vehiclemanagement.service;

import com.pmoxham.vehiclemanagement.model.Vehicle;
import com.pmoxham.vehiclemanagement.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {
    @Autowired
    private VehicleRepository repository;

    public List<Vehicle> getAllVehicles() {
        return repository.findAll();
    }

    public Vehicle getVehicleById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle with ID " + id + " not found"));
    }

    public Vehicle createVehicle(Vehicle vehicle) {
        validateVehicle(vehicle);

        if (vehicle.getId() != null) {
            throw new IllegalArgumentException("New vehicle cannot have an ID.");
        }

        if (repository.findByVin(vehicle.getVin()).isPresent()) {
            throw new IllegalArgumentException("Vehicle with VIN " + vehicle.getVin() + " already exists.");
        }

        return repository.save(vehicle);
    }

    public Vehicle updateVehicle(Long id, Vehicle vehicle) {
        validateVehicle(vehicle);

        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Cannot update: Vehicle with ID " + id + " does not exist.");
        }

        vehicle.setId(id);
        return repository.save(vehicle);
    }

    public void deleteVehicle(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Cannot delete: Vehicle with ID " + id + " does not exist.");
        }
        repository.deleteById(id);
    }

    private void validateVehicle(Vehicle vehicle) {
        int currentYear = Year.now().getValue();

        if (vehicle.getVehicleYear() > currentYear) {
            throw new IllegalArgumentException("Vehicle year cannot be in the future.");
        }

        if (vehicle.getMileage() < 0) {
            throw new IllegalArgumentException("Mileage cannot be negative.");
        }

        if (vehicle.getVin() == null || vehicle.getVin().trim().isEmpty()) {
            throw new IllegalArgumentException("VIN must not be empty.");
        }
    }
}
