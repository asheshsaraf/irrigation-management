package com.irrigation.aggregates.irrigationservice.convertor.plot;

import com.irrigation.aggregates.irrigationservice.convertor.common.JsonUtils;
import com.irrigation.aggregates.irrigationservice.convertor.common.VersionUtils;
import com.irrigation.aggregates.irrigationservice.domain.plot.Plot;
import com.irrigation.aggregates.irrigationservice.dto.common.EntityWithKeyDto;
import com.irrigation.aggregates.irrigationservice.dto.plot.AddressDto;
import com.irrigation.aggregates.irrigationservice.dto.plot.PlotDto;
import com.irrigation.aggregates.irrigationservice.dto.plot.PlotKeyDto;
import com.irrigation.aggregates.irrigationservice.dto.plot.PlotSizeDto;
import org.springframework.lang.NonNull;

import java.util.List;

import static com.irrigation.aggregates.irrigationservice.convertor.common.JsonUtils.toJson;
import static java.util.stream.Collectors.toList;

public final class PlotDtoConvertor {

    private static final PlotDtoConvertor convertor = new PlotDtoConvertor();

    private PlotDtoConvertor() {
    }

    public static PlotDtoConvertor plotConvertor() {
        return convertor;
    }

    @NonNull
    public Plot fromDto(@NonNull final PlotKeyDto plotKeyDto,
                        @NonNull final PlotDto plotDto) {
        final Plot plot = new Plot();
        plot.setCustomerId(plotKeyDto.getCustomerId());
        plot.setPlotId(plotKeyDto.getPlotId());
        plot.setName(plotDto.getName());
        plot.setDescription(plotDto.getDescription());
        plot.setOwnerId(plotDto.getOwnerId());
        plot.setAddress(toJson(plotDto.getAddress()));
        plot.setPlotSize(toJson(plotDto.getSize()));
        plot.setVersion(VersionUtils.create());
        return plot;
    }

    @NonNull
    public List<EntityWithKeyDto<PlotKeyDto, PlotDto>> toDtoList(@NonNull final List<Plot> plots) {
        return plots.stream()
                .map(this::toDto)
                .collect(toList());
    }

    @NonNull
    public EntityWithKeyDto<PlotKeyDto, PlotDto> toDto(@NonNull final Plot plot) {
        final PlotKeyDto plotKeyDto = new PlotKeyDto(plot.getCustomerId(), plot.getPlotId());
        final PlotDto plotDto = new PlotDto(plot.getName(),
                plot.getDescription(),
                plot.getOwnerId(),
                JsonUtils.toObject(plot.getAddress(), AddressDto.class),
                JsonUtils.toObject(plot.getPlotSize(), PlotSizeDto.class),
                plot.getVersion());
        return new EntityWithKeyDto<>(plotKeyDto, plotDto);
    }

}
