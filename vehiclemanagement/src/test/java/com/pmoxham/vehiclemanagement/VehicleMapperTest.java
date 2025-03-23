package com.pmoxham.vehiclemanagement;

import com.pmoxham.vehiclemanagement.dto.VehicleDTO;
import com.pmoxham.vehiclemanagement.mapper.VehicleMapper;
import com.pmoxham.vehiclemanagement.model.Vehicle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VehicleMapperTest {

    @Test
    void testMapToVehicleDTO() {
        Vehicle vehicle = new Vehicle();
        vehicle.setVin("171WH863");
        vehicle.setVehicleYear(2020);
        vehicle.setMake("Skoda");
        vehicle.setModel("Fabia");
        vehicle.setMileage(50000);

        VehicleDTO vehicleDTO = VehicleMapper.mapToVehicleDTO(vehicle);

        assertNotNull(vehicleDTO);
        assertEquals(vehicle.getVin(), vehicleDTO.getVin());
        assertEquals(vehicle.getVehicleYear(), vehicleDTO.getVehicleYear());
        assertEquals(vehicle.getMake(), vehicleDTO.getMake());
        assertEquals(vehicle.getModel(), vehicleDTO.getModel());
        assertEquals(vehicle.getMileage(), vehicleDTO.getMileage());
    }

    @Test
    void testMapToVehicle() {
        VehicleDTO vehicleDTO = new VehicleDTO();
        vehicleDTO.setVin("171WH863");
        vehicleDTO.setVehicleYear(2020);
        vehicleDTO.setMake("Skoda");
        vehicleDTO.setModel("Fabia");
        vehicleDTO.setMileage(50000);

        Vehicle vehicle = VehicleMapper.mapToVehicle(vehicleDTO);

        assertNotNull(vehicle);
        assertEquals(vehicleDTO.getVin(), vehicle.getVin());
        assertEquals(vehicleDTO.getVehicleYear(), vehicle.getVehicleYear());
        assertEquals(vehicleDTO.getMake(), vehicle.getMake());
        assertEquals(vehicleDTO.getModel(), vehicle.getModel());
        assertEquals(vehicleDTO.getMileage(), vehicle.getMileage());
    }
}
