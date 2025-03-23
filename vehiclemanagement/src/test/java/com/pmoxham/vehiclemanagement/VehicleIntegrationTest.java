package com.pmoxham.vehiclemanagement.integration;

import com.pmoxham.vehiclemanagement.dto.ResponseDTO;
import com.pmoxham.vehiclemanagement.dto.VehicleDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
@AutoConfigureMockMvc
public class VehicleIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private VehicleDTO testVehicle;

    @BeforeEach
    void setUp() {
        testVehicle = new VehicleDTO();
        testVehicle.setVin("171WH863");
        testVehicle.setVehicleYear(2022);
        testVehicle.setMake("Skoda");
        testVehicle.setModel("Fabia");
        testVehicle.setMileage(20000.0);
    }

    @Test
    void testCreateVehicle() {
        ResponseEntity<ResponseDTO> response = restTemplate.postForEntity("/vehicles", testVehicle, ResponseDTO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatusMsg()).isEqualTo("Vehicle created successfully");
    }

    @Test
    void testGetAllVehicles() {
        ResponseEntity<VehicleDTO[]> response = restTemplate.getForEntity("/vehicles", VehicleDTO[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().length).isEqualTo(4);
    }

    @Test
    void testGetVehicleById() {
        Long vehicleId = 1L;
        ResponseEntity<VehicleDTO> getResponse = restTemplate.getForEntity("/vehicles/" + vehicleId, VehicleDTO.class);

        // Validate response
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
    }


    @Test
    void testUpdateVehicle() {
        testVehicle.setModel("Camry");
        testVehicle.setMileage(20000.0);
        HttpEntity<VehicleDTO> requestEntity = new HttpEntity<>(testVehicle);
        ResponseEntity<ResponseDTO> response = restTemplate.exchange("/vehicles/1", HttpMethod.PUT, requestEntity, ResponseDTO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatusMsg()).isEqualTo("Vehicle updated successfully");
    }

    @Test
    void testDeleteVehicle() {
        ResponseEntity<ResponseDTO> response = restTemplate.exchange("/vehicles/1", HttpMethod.DELETE, null, ResponseDTO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatusMsg()).isEqualTo("Vehicle deleted successfully");
    }
}
