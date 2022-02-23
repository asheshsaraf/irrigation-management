package com.irrigation.services.irrigationservice.core.plotconfiguration;

import com.irrigation.aggregates.irrigationservice.domain.plotconfiguration.PlotConfiguration;
import com.irrigation.aggregates.irrigationservice.dto.common.EntityWithKeyDto;
import com.irrigation.aggregates.irrigationservice.dto.plot.PlotKeyDto;
import com.irrigation.aggregates.irrigationservice.dto.plotconfiguration.PlotConfigurationDto;
import com.irrigation.aggregates.irrigationservice.dto.user.CustomerKeyDto;
import com.irrigation.services.irrigationservice.core.deviceschedule.DeviceScheduleStateService;
import com.irrigation.services.irrigationservice.core.plot.PlotService;
import com.irrigation.services.irrigationservice.persistence.jpa.plotconfiguration.JpaPlotConfigurationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.irrigation.aggregates.irrigationservice.convertor.plot.PlotKeyDtoConvertor.plotKeyConvertor;
import static com.irrigation.aggregates.irrigationservice.convertor.plotconfiguration.PlotConfigurationDtoConvertor.plotConfigurationConvertor;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlotConfigurationServiceImpl implements PlotConfigurationService {

    private final PlotService plotService;
    private final DeviceScheduleStateService stateService;
    private final JpaPlotConfigurationRepository configurationRepository;

    @Override
    public EntityWithKeyDto<PlotKeyDto, PlotConfigurationDto> create(
            @NonNull final PlotKeyDto plotKeyDto,
            @NonNull final PlotConfigurationDto configurationDto) {
        validatePlot(plotKeyDto);
        log.info("Creating plot configuration for plot: {} with details: {}", plotKeyDto, configurationDto);
        final PlotConfiguration configuration = configurationRepository.saveAndFlush(
                plotConfigurationConvertor().fromDto(plotKeyDto, configurationDto));
        stateService.create(configurationDto);
        return plotConfigurationConvertor().toDto(configuration);
    }

    @Override
    public List<EntityWithKeyDto<PlotKeyDto, PlotConfigurationDto>> getAll(
            @NonNull final CustomerKeyDto customerKeyDto) {
        log.info("Getting all plots configuration for customer with id: {}", customerKeyDto.getCustomerId());
        return plotConfigurationConvertor().toDtoList(
                configurationRepository.findByCustomerId(customerKeyDto.getCustomerId()));
    }

    @Override
    public EntityWithKeyDto<PlotKeyDto, PlotConfigurationDto> get(@NonNull final PlotKeyDto plotKeyDto) {
        log.info("Getting a plot configuration for customer with id: {} having plot id: {}",
                plotKeyDto.getCustomerId(), plotKeyDto.getPlotId());
        return configurationRepository.findById(plotKeyConvertor().fromKeyDto(plotKeyDto))
                .map(plotConfigurationConvertor()::toDto)
                .orElse(null);
    }

    @Override
    public void update(@NonNull final PlotKeyDto plotKeyDto, @NonNull final PlotConfigurationDto configurationDto) {
        validatePlot(plotKeyDto);
        final Optional<PlotConfiguration> existingConfiguration =
                configurationRepository.findById(plotKeyConvertor().fromKeyDto(plotKeyDto));
        if (existingConfiguration.isPresent()
                && nonNull(existingConfiguration.get().getVersion())
                && existingConfiguration.get().getVersion().equalsIgnoreCase(configurationDto.getVersion())) {
            log.info("Updating plot configuration for plot: {} with details: {}", plotKeyDto, configurationDto);
            configurationRepository.saveAndFlush(plotConfigurationConvertor().fromDto(plotKeyDto, configurationDto));
            stateService.create(configurationDto);
        } else {
            log.error("Version mismatched error while updating the configuration for plot: {} with details: {}",
                    plotKeyDto, configurationDto);
            throw new IllegalArgumentException("Version doesn't match");
        }
    }

    @Override
    public void delete(@NonNull final PlotKeyDto plotKeyDto) {
        final Optional<PlotConfiguration> existingConfiguration =
                configurationRepository.findById(plotKeyConvertor().fromKeyDto(plotKeyDto));
        if (existingConfiguration.isPresent()) {
            log.info("Deleting plot configuration for plot: {} with details: {}",
                    plotKeyDto, plotConfigurationConvertor().toDto(existingConfiguration.get()));
            configurationRepository.deleteById(plotKeyConvertor().fromKeyDto(plotKeyDto));
        } else {
            log.error("Resource not found while deleting the configuration for plot: {}", plotKeyDto);
            throw new IllegalArgumentException("Resource Not found");
        }
    }

    private void validatePlot(@NonNull final PlotKeyDto plotKeyDto) {
        log.info("Validating plot is present with details: {}", plotKeyDto);
        if (isNull(plotService.get(plotKeyDto))) {
            log.error("Plot not found for which configuration is requested for plot: {}", plotKeyDto);
            throw new IllegalArgumentException("Plot not found for which configuration is requested");
        }
    }

}
