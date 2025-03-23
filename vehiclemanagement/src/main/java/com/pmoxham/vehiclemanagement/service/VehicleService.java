package com.pmoxham.vehiclemanagement.service;

import com.pmoxham.vehiclemanagement.dto.VehicleDTO;
import com.pmoxham.vehiclemanagement.mapper.VehicleMapper;
import com.pmoxham.vehiclemanagement.model.Vehicle;
import com.pmoxham.vehiclemanagement.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehicleService {
    @Autowired
    private VehicleRepository repository;

    public List<VehicleDTO> getAllVehicles() {
        return repository.findAll().stream()
                .map(VehicleMapper::mapToVehicleDTO)
                .collect(Collectors.toList());
    }

    public Optional<VehicleDTO> getVehicleById(Long id) {
        return repository.findById(id).map(VehicleMapper::mapToVehicleDTO);
    }

    public VehicleDTO createVehicle(VehicleDTO vehicleDTO) {
        Vehicle vehicle = VehicleMapper.mapToVehicle(vehicleDTO);
        validateVehicle(vehicle);

        if (repository.findByVin(vehicle.getVin()).isPresent()) {
            throw new IllegalArgumentException("Vehicle with VIN " + vehicle.getVin() + " already exists.");
        }

        return VehicleMapper.mapToVehicleDTO(repository.save(vehicle));
    }

    public Optional<VehicleDTO> updateVehicle(Long id, VehicleDTO vehicleDTO) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }

        Vehicle vehicle = VehicleMapper.mapToVehicle(vehicleDTO);
        validateVehicle(vehicle);
        vehicle.setId(id);
        return Optional.of(VehicleMapper.mapToVehicleDTO(repository.save(vehicle)));
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
