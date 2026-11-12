package com.sensor.calibration.controller;

import com.sensor.calibration.model.CalibrationRecord;
import com.sensor.calibration.model.Sensor;
import com.sensor.calibration.service.CalibrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class SensorController {

    private final CalibrationService calibrationService;

    public SensorController(CalibrationService calibrationService) {
        this.calibrationService = calibrationService;
    }

    @GetMapping("/sensors")
    public List<Sensor> listSensors() {
        return calibrationService.getAllSensors();
    }

    @PostMapping("/sensors")
    public ResponseEntity<Sensor> registerSensor(@RequestBody Sensor sensor) {
        return ResponseEntity.ok(calibrationService.registerSensor(sensor));
    }

    @GetMapping("/sensors/{id}")
    public ResponseEntity<Sensor> getSensor(@PathVariable String id) {
        return calibrationService.getSensorById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/sensors/{id}/calibrate")
    public ResponseEntity<?> calibrateSensor(@PathVariable String id, @RequestBody Map<String, Object> payload) {
        try {
            double referenceVal = Double.parseDouble(payload.get("referenceValue").toString());
            double measuredVal = Double.parseDouble(payload.get("measuredValue").toString());
            String technician = payload.getOrDefault("technician", "Kalaivarsan V (Lead QA)").toString();

            CalibrationRecord record = calibrationService.calibrateSensor(id, referenceVal, measuredVal, technician);
            return ResponseEntity.ok(record);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/sensors/{id}/records")
    public List<CalibrationRecord> getHistory(@PathVariable String id) {
        return calibrationService.getCalibrationHistory(id);
    }

    @GetMapping("/health")
    public Map<String, String> healthCheck() {
        return Map.of(
                "status", "UP",
                "service", "Sensor Calibration Management System",
                "course", "CCS342 - DevOps",
                "assignedTo", "Kalaivarsan V (Roll 25)"
        );
    }
}
