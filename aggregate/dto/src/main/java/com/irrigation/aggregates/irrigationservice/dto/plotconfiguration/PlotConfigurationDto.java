package com.irrigation.aggregates.irrigationservice.dto.plotconfiguration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.irrigation.aggregates.irrigationservice.dto.common.NamedDto;
import com.irrigation.aggregates.irrigationservice.dto.irrigation.IrrigationDto;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;
import static java.util.Objects.nonNull;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PlotConfigurationDto extends NamedDto {

    private final SoilType soilType;
    private final List<CropType> crops;
    private final List<IrrigationDto> irrigation;
    private final String averageMoistureLevel;

    public PlotConfigurationDto(@NonNull final String name,
                                @Nullable final String description,
                                @Nullable final SoilType soilType,
                                @Nullable final List<CropType> crops,
                                @NonNull final List<IrrigationDto> irrigation,
                                @Nullable final String averageMoistureLevel,
                                @Nullable final String version) {
        super(name, description, version);
        this.soilType = soilType;
        this.crops = nonNull(crops) ? new ArrayList<>(crops) : emptyList();
        this.irrigation = new ArrayList<>(irrigation);
        this.averageMoistureLevel = averageMoistureLevel;
    }

    @Nullable
    public SoilType getSoilType() {
        return soilType;
    }

    @NonNull
    public List<CropType> getCrops() {
        return unmodifiableList(crops);
    }

    @NonNull
    public List<IrrigationDto> getIrrigation() {
        return unmodifiableList(irrigation);
    }

    @Nullable
    public String getAverageMoistureLevel() {
        return averageMoistureLevel;
    }

}
