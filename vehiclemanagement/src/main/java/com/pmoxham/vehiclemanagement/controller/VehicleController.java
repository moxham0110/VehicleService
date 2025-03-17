package com.pmoxham.vehiclemanagement.controller;

import com.pmoxham.vehiclemanagement.model.Vehicle;
import com.pmoxham.vehiclemanagement.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {
    @Autowired
    private VehicleService service;

    @GetMapping
    public List<Vehicle> getAllVehicles() { return service.getAllVehicles(); }

    @GetMapping("/{id}")
    public Vehicle getVehicle(@PathVariable Long id) { return service.getVehicleById(id); }

    @PostMapping
    public Vehicle createVehicle(@RequestBody Vehicle vehicle) { return service.createVehicle(vehicle); }

    @PutMapping("/{id}")
    public Vehicle updateVehicle(@PathVariable Long id, @RequestBody Vehicle vehicle) {
        return service.updateVehicle(id, vehicle);
    }

    @DeleteMapping("/{id}")
    public void deleteVehicle(@PathVariable Long id) { service.deleteVehicle(id); }
}
