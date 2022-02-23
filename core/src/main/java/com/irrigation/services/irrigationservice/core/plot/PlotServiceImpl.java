package com.irrigation.services.irrigationservice.core.plot;

import com.irrigation.aggregates.irrigationservice.domain.plot.Plot;
import com.irrigation.aggregates.irrigationservice.domain.plot.PlotKey;
import com.irrigation.aggregates.irrigationservice.dto.common.EntityWithKeyDto;
import com.irrigation.aggregates.irrigationservice.dto.plot.PlotDto;
import com.irrigation.aggregates.irrigationservice.dto.plot.PlotKeyDto;
import com.irrigation.aggregates.irrigationservice.dto.user.CustomerKeyDto;
import com.irrigation.services.irrigationservice.persistence.jpa.plot.JpaPlotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.irrigation.aggregates.irrigationservice.convertor.plot.PlotDtoConvertor.plotConvertor;
import static com.irrigation.aggregates.irrigationservice.convertor.plot.PlotKeyDtoConvertor.plotKeyConvertor;
import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlotServiceImpl implements PlotService {

    private final JpaPlotRepository plotRepository;

    @Override
    public PlotKeyDto create(@NonNull final CustomerKeyDto customerKeyDto,
                             @NonNull final PlotDto plotDto) {
        log.info("Creating plot with details: {}", plotDto);
        final Plot plot = plotRepository.saveAndFlush(plotConvertor().fromDto(
                new PlotKeyDto(customerKeyDto.getCustomerId(), UUID.randomUUID().toString()),
                plotDto));
        return plotConvertor().toDto(plot).getKey();
    }

    @Override
    public List<EntityWithKeyDto<PlotKeyDto, PlotDto>> getAll(@NonNull final CustomerKeyDto customerKeyDto) {
        log.info("Getting all plots for customer with id: {}", customerKeyDto.getCustomerId());
        return plotConvertor().toDtoList(plotRepository.findByCustomerId(customerKeyDto.getCustomerId()));
    }

    @Override
    public List<EntityWithKeyDto<PlotKeyDto, PlotDto>> getAll() {
        log.info("Getting all plots");
        return plotConvertor().toDtoList(plotRepository.findAll());
    }

    @Override
    public EntityWithKeyDto<PlotKeyDto, PlotDto> get(@NonNull final PlotKeyDto plotKeyDto) {
        log.info("Getting a plot for customer with id: {} having plot id: {}",
                plotKeyDto.getCustomerId(), plotKeyDto.getPlotId());
        return plotRepository.findById(toPlotKey(plotKeyDto))
                .map(plotConvertor()::toDto)
                .orElse(null);
    }

    @Override
    public void update(@NonNull final PlotKeyDto plotKeyDto, @NonNull final PlotDto plotDto) {
        final Optional<Plot> existingPlot = plotRepository.findById(toPlotKey(plotKeyDto));
        if (existingPlot.isPresent()
                && nonNull(existingPlot.get().getVersion())
                && existingPlot.get().getVersion().equalsIgnoreCase(plotDto.getVersion())) {
            plotRepository.saveAndFlush(plotConvertor().fromDto(plotKeyDto, plotDto));
        } else {
            throw new IllegalArgumentException("Version doesn't match");
        }
    }

    @Override
    public void delete(@NonNull final PlotKeyDto plotKeyDto) {
        final Optional<Plot> existingPlot = plotRepository.findById(toPlotKey(plotKeyDto));
        if (existingPlot.isPresent()) {
            plotRepository.deleteById(toPlotKey(plotKeyDto));
        } else {
            throw new IllegalArgumentException("Resource Not found");
        }
    }

    @NonNull
    private PlotKey toPlotKey(@NonNull final PlotKeyDto plotKeyDto) {
        return plotKeyConvertor().fromKeyDto(plotKeyDto);
    }
}
