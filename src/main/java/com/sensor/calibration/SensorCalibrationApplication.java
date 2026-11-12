package com.sensor.calibration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SensorCalibrationApplication {
    public static void main(String[] args) {
        SpringApplication.run(SensorCalibrationApplication.class, args);
        System.out.println(">>> Sensor Calibration Management System started on port 8080 <<<");
    }
}
