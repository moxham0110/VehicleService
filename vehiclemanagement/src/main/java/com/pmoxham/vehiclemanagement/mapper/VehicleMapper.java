package com.pmoxham.vehiclemanagement.mapper;

import com.pmoxham.vehiclemanagement.dto.VehicleDTO;
import com.pmoxham.vehiclemanagement.model.Vehicle;

public class VehicleMapper {

    public static VehicleDTO mapToVehicleDTO(Vehicle vehicle) {
        VehicleDTO vehicleDTO = new VehicleDTO();
        vehicleDTO.setVin(vehicle.getVin());
        vehicleDTO.setVehicleYear(vehicle.getVehicleYear());
        vehicleDTO.setMake(vehicle.getMake());
        vehicleDTO.setModel(vehicle.getModel());
        vehicleDTO.setMileage(vehicle.getMileage());
        return vehicleDTO;
    }

    public static Vehicle mapToVehicle(VehicleDTO vehicleDTO) {
        Vehicle vehicle = new Vehicle();
        vehicle.setVin(vehicleDTO.getVin());
        vehicle.setVehicleYear(vehicleDTO.getVehicleYear());
        vehicle.setMake(vehicleDTO.getMake());
        vehicle.setModel(vehicleDTO.getModel());
        vehicle.setMileage(vehicleDTO.getMileage());
        return vehicle;
    }
}
