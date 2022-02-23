package com.irrigation.services.irrigationservice.persistence.jpa.plotconfiguration;

import com.irrigation.aggregates.irrigationservice.domain.plot.PlotKey;
import com.irrigation.aggregates.irrigationservice.domain.plotconfiguration.PlotConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaPlotConfigurationRepository extends JpaRepository<PlotConfiguration, PlotKey> {

    List<PlotConfiguration> findByCustomerId(@NonNull final String customerId);

}
