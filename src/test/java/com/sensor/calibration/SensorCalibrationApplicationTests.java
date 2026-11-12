package com.sensor.calibration;

import com.sensor.calibration.model.CalibrationRecord;
import com.sensor.calibration.model.Sensor;
import com.sensor.calibration.service.CalibrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SensorCalibrationApplicationTests {

    private CalibrationService service;

    @BeforeEach
    void setUp() {
        service = new CalibrationService();
    }

    @Test
    @DisplayName("Test 1: Verify Initial Pre-Registered Sensor Registry")
    void testInitialSensorRegistry() {
        List<Sensor> sensors = service.getAllSensors();
        assertNotNull(sensors, "Sensor list should not be null");
        assertTrue(sensors.size() >= 3, "Should have at least 3 initial industrial sensors");
    }

    @Test
    @DisplayName("Test 2: Sensor Calibration within Tolerance - Status PASS")
    void testCalibrationWithinTolerance() {
        // Sensor SN-TEMP-101 has tolerance 2.5%
        // Reference: 100.0 °C, Measured: 101.2 °C -> Drift = 1.2% (<= 2.5%) -> PASS
        CalibrationRecord record = service.calibrateSensor("SN-TEMP-101", 100.0, 101.2, "Kalaivarsan V");

        assertEquals("PASS", record.getCalibrationStatus());
        assertEquals(1.2, record.getDriftPercentage(), 0.05);
        assertTrue(record.getCalibrationFactor() > 0.98 && record.getCalibrationFactor() < 1.0);

        Sensor updated = service.getSensorById("SN-TEMP-101").orElseThrow();
        assertEquals("ACTIVE", updated.getStatus());
    }

    @Test
    @DisplayName("Test 3: Sensor Calibration Exceeding Tolerance - Status FAIL_EXCEEDED_DRIFT")
    void testCalibrationExceedingTolerance() {
        // Sensor SN-TEMP-101 has tolerance 2.5%
        // Reference: 100.0 °C, Measured: 108.5 °C -> Drift = 8.5% (> 2.5%) -> FAIL
        CalibrationRecord record = service.calibrateSensor("SN-TEMP-101", 100.0, 108.5, "Kalaivarsan V");

        assertEquals("FAIL_EXCEEDED_DRIFT", record.getCalibrationStatus());
        assertEquals(8.5, record.getDriftPercentage(), 0.05);

        Sensor updated = service.getSensorById("SN-TEMP-101").orElseThrow();
        assertEquals("OUT_OF_SPEC", updated.getStatus());
    }

    @Test
    @DisplayName("Test 4: Negative or Zero Values Throws Exception")
    void testInvalidMeasurementThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.calibrateSensor("SN-TEMP-101", -10.0, 50.0, "Kalaivarsan V");
        });
    }
}
