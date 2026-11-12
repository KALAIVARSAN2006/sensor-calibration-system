package com.sensor.calibration.model;

import java.time.LocalDateTime;

public class Sensor {
    private String id;
    private String name;
    private String sensorType; // TEMPERATURE, PRESSURE, HUMIDITY, FLOW
    private String unit;       // °C, PSI, %, L/min
    private String location;
    private double tolerancePercentage;
    private String status;     // ACTIVE, PENDING_CALIBRATION, OUT_OF_SPEC
    private LocalDateTime lastCalibratedAt;

    public Sensor() {}

    public Sensor(String id, String name, String sensorType, String unit, String location, double tolerancePercentage) {
        this.id = id;
        this.name = name;
        this.sensorType = sensorType;
        this.unit = unit;
        this.location = location;
        this.tolerancePercentage = tolerancePercentage;
        this.status = "ACTIVE";
        this.lastCalibratedAt = LocalDateTime.now();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSensorType() { return sensorType; }
    public void setSensorType(String sensorType) { this.sensorType = sensorType; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public double getTolerancePercentage() { return tolerancePercentage; }
    public void setTolerancePercentage(double tolerancePercentage) { this.tolerancePercentage = tolerancePercentage; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getLastCalibratedAt() { return lastCalibratedAt; }
    public void setLastCalibratedAt(LocalDateTime lastCalibratedAt) { this.lastCalibratedAt = lastCalibratedAt; }
}
