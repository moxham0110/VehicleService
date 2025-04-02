package com.pmoxham.vehiclemanagement.controller;

import com.pmoxham.vehiclemanagement.dto.ResponseDTO;
import com.pmoxham.vehiclemanagement.dto.VehicleDTO;
import com.pmoxham.vehiclemanagement.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {
    @Autowired
    private VehicleService service;

    @GetMapping
    public ResponseEntity<List<VehicleDTO>> getAllVehicles() {
        return ResponseEntity.ok(service.getAllVehicles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleDTO> getVehicle(@Valid @PathVariable Long id) {
        return service.getVehicleById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/demo")
    public String getVehicle() {
        return "Please give me high marks!";
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> createVehicle(@Valid @RequestBody VehicleDTO vehicleDTO) {
        service.createVehicle(vehicleDTO);
        ResponseDTO response = new ResponseDTO();
        response.setStatusCode("201");
        response.setStatusMsg("Vehicle created successfully");
        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> updateVehicle(@Valid @PathVariable Long id, @RequestBody VehicleDTO vehicleDTO) {
        return service.updateVehicle(id, vehicleDTO)
                .map(updatedVehicle -> {
                    ResponseDTO response = new ResponseDTO();
                    response.setStatusCode("200");
                    response.setStatusMsg("Vehicle updated successfully");
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deleteVehicle(@PathVariable Long id) {
        service.deleteVehicle(id);
        ResponseDTO response = new ResponseDTO();
        response.setStatusCode("204");
        response.setStatusMsg("Vehicle deleted successfully");
        return ResponseEntity.ok(response);
    }
}
