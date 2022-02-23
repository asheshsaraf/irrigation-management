package com.irrigation.services.irrigationservice.core.plot;

import com.irrigation.aggregates.irrigationservice.dto.common.EntityWithKeyDto;
import com.irrigation.aggregates.irrigationservice.dto.plot.PlotDto;
import com.irrigation.aggregates.irrigationservice.dto.plot.PlotKeyDto;
import com.irrigation.aggregates.irrigationservice.dto.user.CustomerKeyDto;
import org.springframework.lang.NonNull;

import java.util.List;

public interface PlotService {

    PlotKeyDto create(@NonNull final CustomerKeyDto customerKeyDto, @NonNull final PlotDto plotDto);

    List<EntityWithKeyDto<PlotKeyDto, PlotDto>> getAll(@NonNull final CustomerKeyDto customerKeyDto);

    List<EntityWithKeyDto<PlotKeyDto, PlotDto>> getAll();

    EntityWithKeyDto<PlotKeyDto, PlotDto> get(@NonNull final PlotKeyDto plotKeyDto);

    void update(@NonNull final PlotKeyDto plotKeyDto, @NonNull final PlotDto plotDto);

    void delete(@NonNull final PlotKeyDto plotKeyDto);
}
