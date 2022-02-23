package com.irrigation.services.irrigationservice.core.plotconfiguration;

import com.irrigation.aggregates.irrigationservice.dto.common.EntityWithKeyDto;
import com.irrigation.aggregates.irrigationservice.dto.plot.PlotKeyDto;
import com.irrigation.aggregates.irrigationservice.dto.plotconfiguration.PlotConfigurationDto;
import com.irrigation.aggregates.irrigationservice.dto.user.CustomerKeyDto;
import org.springframework.lang.NonNull;

import java.util.List;

@SuppressWarnings("unused")
public interface PlotConfigurationService {

    EntityWithKeyDto<PlotKeyDto, PlotConfigurationDto> create(@NonNull final PlotKeyDto plotKeyDto,
                                                              @NonNull final PlotConfigurationDto configurationDto);

    List<EntityWithKeyDto<PlotKeyDto, PlotConfigurationDto>> getAll(@NonNull final CustomerKeyDto userKeyDto);

    EntityWithKeyDto<PlotKeyDto, PlotConfigurationDto> get(@NonNull final PlotKeyDto plotKeyDto);

    void update(@NonNull final PlotKeyDto plotKeyDto, @NonNull final PlotConfigurationDto plotDto);

    void delete(@NonNull final PlotKeyDto plotKeyDto);
}
