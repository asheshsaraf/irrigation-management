package com.irrigation.services.irrigationservice.core;

import com.irrigation.aggregates.irrigationservice.dto.common.EntityWithKeyDto;
import com.irrigation.aggregates.irrigationservice.dto.irrigation.IrrigationDto;
import com.irrigation.aggregates.irrigationservice.dto.irrigation.WaterAmountDto;
import com.irrigation.aggregates.irrigationservice.dto.irrigation.WaterScheduleDto;
import com.irrigation.aggregates.irrigationservice.dto.irrigation.WaterUnit;
import com.irrigation.aggregates.irrigationservice.dto.plot.PlotDto;
import com.irrigation.aggregates.irrigationservice.dto.plot.PlotKeyDto;
import com.irrigation.aggregates.irrigationservice.dto.plot.PlotSizeDto;
import com.irrigation.aggregates.irrigationservice.dto.plot.PlotUnit;
import com.irrigation.aggregates.irrigationservice.dto.plotconfiguration.CropType;
import com.irrigation.aggregates.irrigationservice.dto.plotconfiguration.PlotConfigurationDto;
import com.irrigation.aggregates.irrigationservice.dto.plotconfiguration.SoilType;
import com.irrigation.aggregates.irrigationservice.dto.schedule.ScheduleDto;
import com.irrigation.aggregates.irrigationservice.dto.schedule.ScheduleKeyDto;
import com.irrigation.aggregates.irrigationservice.dto.user.CustomerKeyDto;
import com.irrigation.services.irrigationservice.core.plot.PlotService;
import com.irrigation.services.irrigationservice.core.plotconfiguration.PlotConfigurationService;
import com.irrigation.services.irrigationservice.core.schedule.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

import static com.irrigation.aggregates.irrigationservice.convertor.common.DateTimeConvertor.dateTimeConvertor;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeedDataService {

    @Value("${configuration.prepare-seed-data.run}")
    private boolean prepareData;
    @Value("${configuration.prepare-seed-data.customer-number}")
    private Integer numberOfCustomers;
    @Value("${configuration.prepare-seed-data.plot-per-customer}")
    private Integer numberOfPlotsPerCustomer;
    @Value("${configuration.prepare-seed-data.schedule-per-plot}")
    private Integer numberOfSchedulesPerPlot;
    @Value("${configuration.prepare-seed-data.schedule-per-device}")
    private Integer numberOfSchedulesPerDevice;
    @Value("${configuration.prepare-seed-data.irrigation-duration-in-minutes}")
    private Integer durationOfIrrigationInMinutes;

    private final PlotService plotService;
    private final ScheduleService scheduleService;
    private final PlotConfigurationService plotConfigurationService;

    public void prepareSeedData() {
        if (prepareData) {
            final List<EntityWithKeyDto<PlotKeyDto, PlotDto>> plots =
                    preparePlots();
            final List<EntityWithKeyDto<ScheduleKeyDto, ScheduleDto>> schedules =
                    prepareSchedules(plots);
            preparePlotConfigurations(plots, schedules);
        }
    }

    private List<EntityWithKeyDto<PlotKeyDto, PlotDto>> preparePlots() {
        final List<EntityWithKeyDto<CustomerKeyDto, PlotDto>> plots =
                new ArrayList<>(numberOfCustomers * numberOfPlotsPerCustomer);
        for (int i = 0; i < numberOfCustomers; i++) {
            final String customerId = UUID.randomUUID().toString();
            for (int j = 0; j < numberOfPlotsPerCustomer; j++) {
                plots.add(new EntityWithKeyDto<>(new CustomerKeyDto(customerId),
                        new PlotDto("Plot " + i + 1 + "_" + j + 1,
                                "Customer " + i + 1 + " Plot " + j + 1,
                                customerId,
                                null,
                                new PlotSizeDto(PlotUnit.METER, randomNumeric(10), randomNumeric(10)),
                                null)));
            }
        }
        return plots.stream().map(plot -> {
            final PlotKeyDto plotKeyDto = plotService.create(plot.getKey(), plot.getAttributes());
            return new EntityWithKeyDto<>(plotKeyDto, plot.getAttributes());
        }).toList();
    }

    private List<EntityWithKeyDto<ScheduleKeyDto, ScheduleDto>> prepareSchedules(
            List<EntityWithKeyDto<PlotKeyDto, PlotDto>> plots) {
        final List<EntityWithKeyDto<PlotKeyDto, ScheduleDto>> schedules =
                new ArrayList<>(plots.size() * numberOfSchedulesPerPlot);
        plots.forEach(plot -> {
            for (int i = 1; i <= numberOfSchedulesPerPlot; i++) {
                schedules.add(new EntityWithKeyDto<>(plot.getKey(),
                        new ScheduleDto(plot.getAttributes().getName() + " Schedule " + i,
                                plot.getAttributes().getDescription() + " Schedule " + i,
                                dateTimeConvertor().fromLocalDateTime(LocalDateTime.now(ZoneOffset.UTC).plusMinutes(i)),
                                dateTimeConvertor().fromLocalDateTime(
                                        LocalDateTime.now(ZoneOffset.UTC).plusMinutes(i + durationOfIrrigationInMinutes)),
                                null)));
            }
        });
        sleep();
        return schedules.stream().map(schedule -> {
            final ScheduleKeyDto scheduleKeyDto = scheduleService.create(schedule.getKey(), schedule.getAttributes());
            return new EntityWithKeyDto<>(scheduleKeyDto, schedule.getAttributes());
        }).toList();
    }

    private void preparePlotConfigurations(List<EntityWithKeyDto<PlotKeyDto, PlotDto>> plots,
                                           List<EntityWithKeyDto<ScheduleKeyDto, ScheduleDto>> schedules) {
        final Map<PlotKeyDto, List<EntityWithKeyDto<ScheduleKeyDto, ScheduleDto>>> mapping =
                schedules.stream()
                        .collect(Collectors.groupingBy(schedule -> toPlotKeyDto(schedule.getKey())));
        sleep();
        plots.forEach(plot -> {
            final PlotConfigurationDto configurationDto = new PlotConfigurationDto(plot.getAttributes().getName(),
                    plot.getAttributes().getDescription(),
                    SoilType.A,
                    Arrays.asList(CropType.A, CropType.B),
                    getIrrigationDtoList(plot, mapping.get(plot.getKey())),
                    "1",
                    null);
            plotConfigurationService.create(plot.getKey(), configurationDto);
        });
    }

    private List<IrrigationDto> getIrrigationDtoList(
            EntityWithKeyDto<PlotKeyDto, PlotDto> plot,
            List<EntityWithKeyDto<ScheduleKeyDto, ScheduleDto>> entityWithKeyDtos) {
        final List<IrrigationDto> irrigationDtos = new ArrayList<>();
        for (int i = 1; i <= numberOfSchedulesPerPlot / numberOfSchedulesPerDevice; i++) {
            final String deviceId = UUID.randomUUID().toString();
            final List<WaterScheduleDto> waterScheduleDtoList = new ArrayList<>();
            for (int j = 1; j <= numberOfSchedulesPerDevice; j++) {
                if (entityWithKeyDtos.size() > 0) {
                    waterScheduleDtoList.add(new WaterScheduleDto(entityWithKeyDtos.remove(0).getKey(),
                            new WaterAmountDto(WaterUnit.LITRE_CUBE, "50")));
                }
            }
            irrigationDtos.add(new IrrigationDto(deviceId, waterScheduleDtoList));
        }
        return irrigationDtos;
    }

    private static PlotKeyDto toPlotKeyDto(final ScheduleKeyDto scheduleKeyDto) {
        return new PlotKeyDto(scheduleKeyDto.getCustomerId(), scheduleKeyDto.getPlotId());
    }

    private void sleep() {
        try {
            log.error("Waiting for sometime to load seed more data");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.error("Interrupted exception: {}", e.getMessage());
        }
    }
}
