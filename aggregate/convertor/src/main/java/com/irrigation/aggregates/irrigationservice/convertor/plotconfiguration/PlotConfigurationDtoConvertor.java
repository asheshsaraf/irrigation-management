package com.irrigation.aggregates.irrigationservice.convertor.plotconfiguration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.irrigation.aggregates.irrigationservice.convertor.common.JsonUtils;
import com.irrigation.aggregates.irrigationservice.convertor.common.VersionUtils;
import com.irrigation.aggregates.irrigationservice.domain.plotconfiguration.PlotConfiguration;
import com.irrigation.aggregates.irrigationservice.dto.common.EntityWithKeyDto;
import com.irrigation.aggregates.irrigationservice.dto.irrigation.IrrigationDto;
import com.irrigation.aggregates.irrigationservice.dto.plot.PlotKeyDto;
import com.irrigation.aggregates.irrigationservice.dto.plotconfiguration.CropType;
import com.irrigation.aggregates.irrigationservice.dto.plotconfiguration.PlotConfigurationDto;
import com.irrigation.aggregates.irrigationservice.dto.plotconfiguration.SoilType;
import org.springframework.lang.NonNull;

import java.util.List;

import static com.irrigation.aggregates.irrigationservice.convertor.common.JsonUtils.toJson;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

public final class PlotConfigurationDtoConvertor {

    private static final PlotConfigurationDtoConvertor convertor = new PlotConfigurationDtoConvertor();

    private PlotConfigurationDtoConvertor() {
    }

    public static PlotConfigurationDtoConvertor plotConfigurationConvertor() {
        return convertor;
    }

    @NonNull
    public PlotConfiguration fromDto(@NonNull final PlotKeyDto keyDto,
                                     @NonNull final PlotConfigurationDto dto) {
        final PlotConfiguration configuration = new PlotConfiguration();
        configuration.setCustomerId(keyDto.getCustomerId());
        configuration.setPlotId(keyDto.getPlotId());
        configuration.setName(dto.getName());
        configuration.setDescription(dto.getDescription());
        configuration.setSoilType(nonNull(dto.getSoilType()) ? dto.getSoilType().name() : null);
        configuration.setCrops(toJson(dto.getCrops()));
        configuration.setIrrigation(toJson(dto.getIrrigation()));
        configuration.setAverageMoistureLevel(dto.getAverageMoistureLevel());
        configuration.setVersion(VersionUtils.create());
        return configuration;
    }

    @NonNull
    public List<EntityWithKeyDto<PlotKeyDto, PlotConfigurationDto>> toDtoList(
            @NonNull final List<PlotConfiguration> configurations) {
        return configurations.stream()
                .map(this::toDto)
                .collect(toList());
    }

    @NonNull
    public EntityWithKeyDto<PlotKeyDto, PlotConfigurationDto> toDto(@NonNull final PlotConfiguration configuration) {
        final PlotKeyDto plotKeyDto = new PlotKeyDto(configuration.getCustomerId(), configuration.getPlotId());
        final PlotConfigurationDto configurationDto = new PlotConfigurationDto(configuration.getName(),
                configuration.getDescription(),
                SoilType.valueOf(configuration.getSoilType()),
                toCropDto(configuration.getCrops()),
                JsonUtils.toObjectList(configuration.getIrrigation(), new TypeReference<List<IrrigationDto>>() {
                }),
                configuration.getAverageMoistureLevel(),
                configuration.getVersion());
        return new EntityWithKeyDto<>(plotKeyDto, configurationDto);
    }

    @NonNull
    private static List<CropType> toCropDto(@NonNull final String cropTypes) {
        return JsonUtils.toObjectList(cropTypes, new TypeReference<List<String>>() {
                }).stream()
                .map(CropType::valueOf)
                .collect(toList());
    }

}
