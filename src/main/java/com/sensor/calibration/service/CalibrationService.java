package com.sensor.calibration.service;

import com.sensor.calibration.model.CalibrationRecord;
import com.sensor.calibration.model.Sensor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CalibrationService {

    private final Map<String, Sensor> sensorRegistry = new ConcurrentHashMap<>();
    private final Map<String, List<CalibrationRecord>> calibrationHistory = new ConcurrentHashMap<>();

    public CalibrationService() {
        // Pre-populate with typical industrial sensors
        registerSensor(new Sensor("SN-TEMP-101", "Furnace Core Thermocouple", "TEMPERATURE", "°C", "Bay-A Plant 1", 2.5));
        registerSensor(new Sensor("SN-PRES-204", "Hydraulic Line Pressure Gauge", "PRESSURE", "PSI", "Line 4 Assembly", 3.0));
        registerSensor(new Sensor("SN-HUM-305", "Cleanroom Humidity Sensor", "HUMIDITY", "%", "Zone-3 Cleanroom", 4.0));
    }

    public Sensor registerSensor(Sensor sensor) {
        if (sensor.getId() == null || sensor.getId().trim().isEmpty()) {
            sensor.setId("SN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        }
        sensorRegistry.put(sensor.getId(), sensor);
        calibrationHistory.putIfAbsent(sensor.getId(), new ArrayList<>());
        return sensor;
    }

    public List<Sensor> getAllSensors() {
        return new ArrayList<>(sensorRegistry.values());
    }

    public Optional<Sensor> getSensorById(String id) {
        return Optional.ofNullable(sensorRegistry.get(id));
    }

    public CalibrationRecord calibrateSensor(String sensorId, double referenceValue, double measuredValue, String technicianName) {
        Sensor sensor = sensorRegistry.get(sensorId);
        if (sensor == null) {
            throw new IllegalArgumentException("Sensor not found with ID: " + sensorId);
        }

        if (referenceValue <= 0 || measuredValue <= 0) {
            throw new IllegalArgumentException("Reference and measured values must be strictly positive");
        }

        // Calibration Factor: Standard / Measured
        double calibrationFactor = Math.round((referenceValue / measuredValue) * 10000.0) / 10000.0;

        // Drift Percentage: |(Measured - Reference) / Reference| * 100
        double driftPercent = Math.round((Math.abs(measuredValue - referenceValue) / referenceValue * 100.0) * 100.0) / 100.0;

        // Check against sensor tolerance threshold
        String status = (driftPercent <= sensor.getTolerancePercentage()) ? "PASS" : "FAIL_EXCEEDED_DRIFT";

        CalibrationRecord record = new CalibrationRecord(
                "CAL-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(),
                sensorId,
                referenceValue,
                measuredValue,
                calibrationFactor,
                driftPercent,
                status,
                technicianName
        );

        // Update sensor state
        sensor.setStatus("PASS".equals(status) ? "ACTIVE" : "OUT_OF_SPEC");
        sensor.setLastCalibratedAt(LocalDateTime.now());

        calibrationHistory.get(sensorId).add(record);
        return record;
    }

    public List<CalibrationRecord> getCalibrationHistory(String sensorId) {
        return calibrationHistory.getOrDefault(sensorId, Collections.emptyList());
    }
}
