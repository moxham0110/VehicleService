package com.pmoxham.vehiclemanagement;

import com.pmoxham.vehiclemanagement.dto.VehicleDTO;
import com.pmoxham.vehiclemanagement.mapper.VehicleMapper;
import com.pmoxham.vehiclemanagement.model.Vehicle;
import com.pmoxham.vehiclemanagement.repository.VehicleRepository;
import com.pmoxham.vehiclemanagement.service.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Year;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private VehicleService vehicleService;

    private Vehicle validVehicle;
    private VehicleDTO validVehicleDTO;
    private static final int CURRENT_YEAR = Year.now().getValue();

    @BeforeEach
    void setUp() {
        validVehicle = new Vehicle();
        validVehicle.setId(1L);
        validVehicle.setVin("171WH863");
        validVehicle.setVehicleYear(2020);
        validVehicle.setMake("Skoda");
        validVehicle.setModel("Fabia");
        validVehicle.setMileage(50000);

        validVehicleDTO = VehicleMapper.mapToVehicleDTO(validVehicle);
    }

    @Test
    void testGetAllVehicles() {
        when(vehicleRepository.findAll()).thenReturn(List.of(validVehicle));
        List<VehicleDTO> vehicles = vehicleService.getAllVehicles();
        assertEquals(1, vehicles.size());
        assertEquals("171WH863", vehicles.get(0).getVin());
        verify(vehicleRepository, times(1)).findAll();
    }

    @Test
    void testGetVehicleByIdExists() {
        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(validVehicle));
        Optional<VehicleDTO> vehicleDTO = vehicleService.getVehicleById(1L);
        assertTrue(vehicleDTO.isPresent());
        assertEquals("Skoda", vehicleDTO.get().getMake());
        verify(vehicleRepository, times(1)).findById(1L);
    }

    @Test
    void testGetVehicleByIdNotExistsShouldReturnEmpty() {
        when(vehicleRepository.findById(5L)).thenReturn(Optional.empty());
        Optional<VehicleDTO> vehicleDTO = vehicleService.getVehicleById(5L);
        assertFalse(vehicleDTO.isPresent());
        verify(vehicleRepository, times(1)).findById(5L);
    }

    @Test
    void testCreateVehicleSuccess() {
        when(vehicleRepository.findByVin(validVehicle.getVin())).thenReturn(Optional.empty());
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(validVehicle);
        VehicleDTO createdVehicleDTO = vehicleService.createVehicle(validVehicleDTO);
        assertNotNull(createdVehicleDTO);
        assertEquals("171WH863", createdVehicleDTO.getVin());
        verify(vehicleRepository, times(1)).save(any(Vehicle.class));
    }

    @Test
    void testUpdateVehicleExistsShouldUpdateSuccessfully() {
        when(vehicleRepository.existsById(1L)).thenReturn(true);
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(validVehicle);
        Optional<VehicleDTO> updatedVehicleDTO = vehicleService.updateVehicle(1L, validVehicleDTO);
        assertTrue(updatedVehicleDTO.isPresent());
        assertEquals("Skoda", updatedVehicleDTO.get().getMake());
        verify(vehicleRepository, times(1)).save(any(Vehicle.class));
    }

    @Test
    void testUpdateVehicleNotExistsShouldReturnEmpty() {
        when(vehicleRepository.existsById(99L)).thenReturn(false);
        Optional<VehicleDTO> updatedVehicleDTO = vehicleService.updateVehicle(99L, validVehicleDTO);
        assertFalse(updatedVehicleDTO.isPresent());
        verify(vehicleRepository, never()).save(any(Vehicle.class));
    }
}
