package com.irrigation.services.irrigationservice.main;

import com.irrigation.services.irrigationservice.core.SeedDataService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaRepositories(basePackages = "com.irrigation.services.irrigationservice.persistence.jpa")
@EntityScan(basePackages = "com.irrigation.aggregates.irrigationservice.domain")
@SpringBootApplication(scanBasePackages = {"com.irrigation.services.irrigationservice",
        "com.irrigation.aggregates.irrigationservice"})
@EnableScheduling
public class IrrigationServiceApplication {

    public static void main(String[] args) {
        final ConfigurableApplicationContext context = SpringApplication.run(IrrigationServiceApplication.class, args);
		context.getBean("seedDataService", SeedDataService.class).prepareSeedData();
    }

}
