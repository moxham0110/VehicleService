package com.pmoxham.vehiclemanagement;

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
    private static final int CURRENTYEAR = Year.now().getValue();

    @BeforeEach
    void setUp() {
        validVehicle = new Vehicle();
        validVehicle.setId(1L);
        validVehicle.setVin("171WH863");
        validVehicle.setVehicleYear(2020);
        validVehicle.setMake("Skoda");
        validVehicle.setModel("Fabia");
        validVehicle.setMileage(50000);
    }

    @Test
    void testGetAllVehicles() {
        when(vehicleRepository.findAll()).thenReturn(List.of(validVehicle));

        List<Vehicle> vehicles = vehicleService.getAllVehicles();

        assertEquals(1, vehicles.size());
        assertEquals("171WH863", vehicles.get(0).getVin());
        verify(vehicleRepository, times(1)).findAll();
    }

    @Test
    void testGetVehicleByIdExists() {
        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(validVehicle));

        Vehicle vehicle = vehicleService.getVehicleById(1L);

        assertNotNull(vehicle);
        assertEquals("Skoda", vehicle.getMake());
        verify(vehicleRepository, times(1)).findById(1L);
    }

    @Test
    void testGetVehicleByIdNotExistsShouldThrowException() {
        when(vehicleRepository.findById(99L)).thenReturn(Optional.empty());

        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> vehicleService.getVehicleById(99L),
                "Expected getVehicleById() to throw exception"
        );

        assertEquals("Vehicle with ID 99 not found", thrown.getMessage());
        verify(vehicleRepository, times(1)).findById(99L);
    }

    @Test
    void testCreateVehicleSuccess() {
        when(vehicleRepository.findByVin(validVehicle.getVin())).thenReturn(Optional.empty());
        when(vehicleRepository.save(validVehicle)).thenReturn(validVehicle);
        validVehicle.setId(null);

        Vehicle createdVehicle = vehicleService.createVehicle(validVehicle);

        assertNotNull(createdVehicle);
        assertEquals("171WH863", createdVehicle.getVin());
        verify(vehicleRepository, times(1)).save(validVehicle);
    }

    @Test
    void testCreateVehicleWithInvalidDataShouldThrowException() {
        validVehicle.setVehicleYear(CURRENTYEAR + 1);
        validVehicle.setMileage(-100);

        IllegalArgumentException futureYearException = assertThrows(
                IllegalArgumentException.class,
                () -> vehicleService.createVehicle(validVehicle)
        );
        assertEquals("Vehicle year cannot be in the future.", futureYearException.getMessage());

        validVehicle.setVehicleYear(2020);
        IllegalArgumentException negativeMileageException = assertThrows(
                IllegalArgumentException.class,
                () -> vehicleService.createVehicle(validVehicle)
        );
        assertEquals("Mileage cannot be negative.", negativeMileageException.getMessage());

        verify(vehicleRepository, never()).save(any(Vehicle.class));
    }

    @Test
    void testCreateVehicleWithExistingVinShouldThrowException() {
        when(vehicleRepository.findByVin(validVehicle.getVin())).thenReturn(Optional.of(validVehicle));
        validVehicle.setId(null);

        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> vehicleService.createVehicle(validVehicle)
        );

        assertEquals("Vehicle with VIN 171WH863 already exists.", thrown.getMessage());
        verify(vehicleRepository, never()).save(any(Vehicle.class));
    }

    @Test
    void testUpdateVehicleWhenExistsShouldUpdateSuccessfully() {
        when(vehicleRepository.existsById(1L)).thenReturn(true);
        when(vehicleRepository.save(validVehicle)).thenReturn(validVehicle);

        Vehicle updatedVehicle = vehicleService.updateVehicle(1L, validVehicle);

        assertNotNull(updatedVehicle);
        assertEquals("Skoda", updatedVehicle.getMake());
        verify(vehicleRepository, times(1)).save(validVehicle);
    }

    @Test
    void testUpdateVehicleWhenNotExistsShouldThrowException() {
        when(vehicleRepository.existsById(99L)).thenReturn(false);

        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> vehicleService.updateVehicle(99L, validVehicle)
        );

        assertEquals("Cannot update: Vehicle with ID 99 does not exist.", thrown.getMessage());
        verify(vehicleRepository, never()).save(any(Vehicle.class));
    }
}
