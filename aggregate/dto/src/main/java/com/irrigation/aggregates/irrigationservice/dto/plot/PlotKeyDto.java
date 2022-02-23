package com.irrigation.aggregates.irrigationservice.dto.plot;

import com.irrigation.aggregates.irrigationservice.dto.user.CustomerKeyDto;
import org.springframework.lang.NonNull;

public class PlotKeyDto extends CustomerKeyDto {

    private final String plotId;

    public PlotKeyDto(@NonNull final String customerId,
                      @NonNull final String plotId) {
        super(customerId);
        this.plotId = plotId;
    }

    @NonNull
    public String getPlotId() {
        return plotId;
    }

}
