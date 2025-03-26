package com.pmoxham.vehiclemanagement;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmoxham.vehiclemanagement.controller.VehicleController;
import com.pmoxham.vehiclemanagement.dto.VehicleDTO;
import com.pmoxham.vehiclemanagement.service.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

@WebMvcTest(VehicleController.class)
class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private VehicleService vehicleService;

    private VehicleDTO vehicleDTO;

    @BeforeEach
    void setUp() {
        vehicleDTO = new VehicleDTO("171WH863", 2022, "Honda", "Civic", 15000);
    }

    @Test
    void getAllVehicles() throws Exception {
        List<VehicleDTO> vehicles = List.of(vehicleDTO);
        given(vehicleService.getAllVehicles()).willReturn(vehicles);

        mockMvc.perform(get("/vehicles")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$[0].vin", is("171WH863")));
    }

    @Test
    void getVehicleByIdFound() throws Exception {
        given(vehicleService.getVehicleById(1L)).willReturn(Optional.of(vehicleDTO));

        mockMvc.perform(get("/vehicles/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vin", is("171WH863")))
                .andExpect(jsonPath("$.make", is("Honda")))
                .andExpect(jsonPath("$.model", is("Civic")));
    }

    @Test
    void getVehicleByIdNotFound() throws Exception {
        given(vehicleService.getVehicleById(99L)).willReturn(Optional.empty());

        mockMvc.perform(get("/vehicles/{id}", 99))
                .andExpect(status().isNotFound());
    }

    @Test
    void createVehicle() throws Exception {
        mockMvc.perform(post("/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vehicleDTO)))
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.statusCode", is("201")))
                .andExpect(jsonPath("$.statusMsg", is("Vehicle created successfully")));
    }

    @Test
    void updateVehicleFound() throws Exception {
        VehicleDTO updatedVehicle = new VehicleDTO("171WH863", 2023, "Honda", "Accord", 12000);
        given(vehicleService.updateVehicle(anyLong(), any(VehicleDTO.class)))
                .willReturn(Optional.of(updatedVehicle));

        mockMvc.perform(put("/vehicles/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedVehicle)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode", is("200")))
                .andExpect(jsonPath("$.statusMsg", is("Vehicle updated successfully")));
    }

    @Test
    void updateVehicleNotFound() throws Exception {
        given(vehicleService.updateVehicle(anyLong(), any(VehicleDTO.class))).willReturn(Optional.empty());

        mockMvc.perform(put("/vehicles/{id}", 10)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vehicleDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteVehicle() throws Exception {
        mockMvc.perform(delete("/vehicles/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode", is("204")))
                .andExpect(jsonPath("$.statusMsg", is("Vehicle deleted successfully")));
    }
}
