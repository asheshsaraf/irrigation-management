package com.irrigation.aggregates.irrigationservice.convertor.plot;

import com.irrigation.aggregates.irrigationservice.domain.plot.PlotKey;
import com.irrigation.aggregates.irrigationservice.dto.plot.PlotKeyDto;
import org.springframework.lang.NonNull;

public final class PlotKeyDtoConvertor {

    private static final PlotKeyDtoConvertor convertor = new PlotKeyDtoConvertor();

    private PlotKeyDtoConvertor() {
    }

    public static PlotKeyDtoConvertor plotKeyConvertor() {
        return convertor;
    }

    @NonNull
    public PlotKey fromKeyDto(@NonNull final PlotKeyDto plotKeyDto) {
        return new PlotKey(plotKeyDto.getCustomerId(), plotKeyDto.getPlotId());
    }

    @NonNull
    public PlotKeyDto toKeyDto(@NonNull final PlotKey plotKey) {
        return new PlotKeyDto(plotKey.getCustomerId(), plotKey.getPlotId());
    }

}
