package com.allot.nx.health;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Readiness;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

@Readiness
@ApplicationScoped
public class MyReadinessHealthCheck implements HealthCheck {
    @Override
    public HealthCheckResponse call() {
        final String NAME_OF_MY_CHECK = "Readiness: Web Server health check";
        String upMessage = "Readiness: I checked all threads of the web server, and I am now sure you can send HTTP requests!";

        HealthCheckResponseBuilder responseBuilder = HealthCheckResponse.named(NAME_OF_MY_CHECK);

        try {
            simulateWebServerReadyVerification();
            responseBuilder.up().withData("info", upMessage);
        } catch (IllegalStateException e) {
            // cannot access the web server
            responseBuilder.down().withData("errorMessage", e.getMessage());
        }

        return responseBuilder.build();
    }

    private void simulateWebServerReadyVerification() {
        LocalTime now = LocalTime.now(ZoneId.of("Asia/Jerusalem"));
        int minute = now.getMinute();
        int hour = now.getHour();
        if ((minute >= 0) && (minute < 15)) {
            String message = "Readiness: I am on a break until " + hour + ":15";
            RuntimeException ex = new IllegalStateException(message);
            throw ex;
        }
    }
}
