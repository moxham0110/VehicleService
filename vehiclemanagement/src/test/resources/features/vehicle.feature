Feature: Vehicle API

  Background:
    * url 'http://localhost:8081/vehicles'
    * def vehicle = { vin: '1HGCM82633A123456', vehicleYear: 2022, make: 'Honda', model: 'Civic', mileage: 15000 }

  Scenario: Get all vehicles
    Given method get
    Then status 200

  Scenario: Get vehicle by ID
    Given path '1'
    When method get
    Then status 200
    And match response.make == 'Honda'
    And match response.model == 'Civic'

  Scenario: Get vehicle by non-existent ID
    Given path '999'
    When method get
    Then status 404

  Scenario: Create a new vehicle
    Given request vehicle
    When method post
    Then status 201
    And match response.statusCode == '200'
    And match response.statusMsg == 'Vehicle created successfully'

  Scenario: Update an existing vehicle
    Given path '1'
    And request { vin: '1HGCM82633A123456', vehicleYear: 2023, make: 'Honda', model: 'Civic', mileage: 16000 }
    When method put
    Then status 200
    And match response.statusCode == '201'
    And match response.statusMsg == 'Vehicle updated successfully'

  Scenario: Delete an existing vehicle
    Given path '1'
    When method delete
    Then status 200
    And match response.statusCode == '204'
    And match response.statusMsg == 'Vehicle deleted successfully'