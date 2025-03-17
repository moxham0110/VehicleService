package com.pmoxham.vehiclemanagement.service;

import com.pmoxham.vehiclemanagement.model.Vehicle;
import com.pmoxham.vehiclemanagement.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {
    @Autowired
    private VehicleRepository repository;

    public List<Vehicle> getAllVehicles() { return repository.findAll(); }
    public Vehicle getVehicleById(Long id) { return repository.findById(id).orElse(null); }
    public Vehicle createVehicle(Vehicle vehicle) { return repository.save(vehicle); }
    public Vehicle updateVehicle(Long id, Vehicle vehicle) {
        vehicle.setId(id);
        return repository.save(vehicle);
    }
    public void deleteVehicle(Long id) { repository.deleteById(id); }
}
