package com.irrigation.services.irrigationservice.persistence.jpa.plot;

import com.irrigation.aggregates.irrigationservice.domain.plot.Plot;
import com.irrigation.aggregates.irrigationservice.domain.plot.PlotKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaPlotRepository extends JpaRepository<Plot, PlotKey> {

    List<Plot> findByCustomerId(@NonNull final String customerId);

}
