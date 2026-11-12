package com.sensor.calibration.model;

import java.time.LocalDateTime;

public class CalibrationRecord {
    private String recordId;
    private String sensorId;
    private double referenceStandardValue;
    private double measuredSensorValue;
    private double calibrationFactor;
    private double driftPercentage;
    private String calibrationStatus; // PASS, FAIL_EXCEEDED_DRIFT
    private String technicianName;
    private LocalDateTime calibrationTimestamp;

    public CalibrationRecord() {}

    public CalibrationRecord(String recordId, String sensorId, double referenceStandardValue,
                             double measuredSensorValue, double calibrationFactor, double driftPercentage,
                             String calibrationStatus, String technicianName) {
        this.recordId = recordId;
        this.sensorId = sensorId;
        this.referenceStandardValue = referenceStandardValue;
        this.measuredSensorValue = measuredSensorValue;
        this.calibrationFactor = calibrationFactor;
        this.driftPercentage = driftPercentage;
        this.calibrationStatus = calibrationStatus;
        this.technicianName = technicianName;
        this.calibrationTimestamp = LocalDateTime.now();
    }

    public String getRecordId() { return recordId; }
    public void setRecordId(String recordId) { this.recordId = recordId; }

    public String getSensorId() { return sensorId; }
    public void setSensorId(String sensorId) { this.sensorId = sensorId; }

    public double getReferenceStandardValue() { return referenceStandardValue; }
    public void setReferenceStandardValue(double referenceStandardValue) { this.referenceStandardValue = referenceStandardValue; }

    public double getMeasuredSensorValue() { return measuredSensorValue; }
    public void setMeasuredSensorValue(double measuredSensorValue) { this.measuredSensorValue = measuredSensorValue; }

    public double getCalibrationFactor() { return calibrationFactor; }
    public void setCalibrationFactor(double calibrationFactor) { this.calibrationFactor = calibrationFactor; }

    public double getDriftPercentage() { return driftPercentage; }
    public void setDriftPercentage(double driftPercentage) { this.driftPercentage = driftPercentage; }

    public String getCalibrationStatus() { return calibrationStatus; }
    public void setCalibrationStatus(String calibrationStatus) { this.calibrationStatus = calibrationStatus; }

    public String getTechnicianName() { return technicianName; }
    public void setTechnicianName(String technicianName) { this.technicianName = technicianName; }

    public LocalDateTime getCalibrationTimestamp() { return calibrationTimestamp; }
    public void setCalibrationTimestamp(LocalDateTime calibrationTimestamp) { this.calibrationTimestamp = calibrationTimestamp; }
}
