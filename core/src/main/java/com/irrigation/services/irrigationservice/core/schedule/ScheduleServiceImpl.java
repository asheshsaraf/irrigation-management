package com.irrigation.services.irrigationservice.core.schedule;

import com.irrigation.aggregates.irrigationservice.domain.schedule.Schedule;
import com.irrigation.aggregates.irrigationservice.dto.common.EntityWithKeyDto;
import com.irrigation.aggregates.irrigationservice.dto.plot.PlotKeyDto;
import com.irrigation.aggregates.irrigationservice.dto.schedule.ScheduleDto;
import com.irrigation.aggregates.irrigationservice.dto.schedule.ScheduleKeyDto;
import com.irrigation.services.irrigationservice.core.plot.PlotService;
import com.irrigation.services.irrigationservice.persistence.jpa.schedule.JpaScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.irrigation.aggregates.irrigationservice.convertor.schedule.ScheduleDtoConvertor.scheduleConvertor;
import static com.irrigation.aggregates.irrigationservice.convertor.schedule.ScheduleKeyDtoConvertor.scheduleKeyConvertor;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final PlotService plotService;
    private final JpaScheduleRepository scheduleRepository;

    @Override
    public ScheduleKeyDto create(@NonNull final PlotKeyDto plotKeyDto, @NonNull final ScheduleDto scheduleDto) {
        validatePlot(plotKeyDto);
        log.info("Creating schedule for plot: {} with details: {}", plotKeyDto, scheduleDto);
        final Schedule schedule = scheduleRepository.saveAndFlush(
                scheduleConvertor().fromDto(toScheduleKey(plotKeyDto), scheduleDto));
        return scheduleConvertor().toDto(schedule).getKey();
    }

    @Override
    public List<EntityWithKeyDto<ScheduleKeyDto, ScheduleDto>> getAll(@NonNull final PlotKeyDto plotKeyDto) {
        log.info("Getting all schedules for a plot with key: {}", plotKeyDto);
        return scheduleConvertor().toDtoList(
                scheduleRepository.findByCustomerIdAndPlotId(plotKeyDto.getCustomerId(), plotKeyDto.getPlotId()));
    }

    @Override
    public List<EntityWithKeyDto<ScheduleKeyDto, ScheduleDto>> getAll(
            @NonNull final List<ScheduleKeyDto> scheduleKeyDtoList) {
        return scheduleConvertor().toDtoList(
                scheduleRepository.findAllById(scheduleKeyConvertor().fromKeyDtoList(scheduleKeyDtoList)));
    }

    @Override
    public EntityWithKeyDto<ScheduleKeyDto, ScheduleDto> get(@NonNull final ScheduleKeyDto scheduleKeyDto) {
        log.info("Getting a schedule for a plot with key: {}", scheduleKeyDto);
        return scheduleRepository.findById(scheduleKeyConvertor().fromKeyDto(scheduleKeyDto))
                .map(scheduleConvertor()::toDto)
                .orElse(null);
    }

    @Override
    public void update(@NonNull final ScheduleKeyDto scheduleKeyDto, @NonNull final ScheduleDto scheduleDto) {
        validatePlot(new PlotKeyDto(scheduleKeyDto.getCustomerId(), scheduleKeyDto.getPlotId()));
        final Optional<Schedule> existingSchedule =
                scheduleRepository.findById(scheduleKeyConvertor().fromKeyDto(scheduleKeyDto));
        if (existingSchedule.isPresent()
                && nonNull(existingSchedule.get().getVersion())
                && existingSchedule.get().getVersion().equalsIgnoreCase(scheduleDto.getVersion())) {
            log.info("Updating schedule: {} with details: {}", scheduleKeyDto, scheduleDto);
            scheduleRepository.saveAndFlush(scheduleConvertor().fromDto(scheduleKeyDto, scheduleDto));
        } else {
            log.error("Version mismatched error while updating the schedule for plot: {} with details: {}",
                    scheduleKeyDto, scheduleDto);
            throw new IllegalArgumentException("Version doesn't match");
        }
    }

    @Override
    public void delete(@NonNull final ScheduleKeyDto scheduleKeyDto) {
        final Optional<Schedule> existingSchedule =
                scheduleRepository.findById(scheduleKeyConvertor().fromKeyDto(scheduleKeyDto));
        if (existingSchedule.isPresent()) {
            log.info("Deleting schedule: {} with details: {}",
                    scheduleKeyDto, scheduleConvertor().toDto(existingSchedule.get()));
            scheduleRepository.deleteById(scheduleKeyConvertor().fromKeyDto(scheduleKeyDto));
        } else {
            log.error("Resource not found while deleting the schedule: {}", scheduleKeyDto);
            throw new IllegalArgumentException("Resource Not found");
        }
    }

    private void validatePlot(@NonNull final PlotKeyDto plotKeyDto) {
        log.info("Validating plot is present with details: {}", plotKeyDto);
        if (isNull(plotService.get(plotKeyDto))) {
            log.error("Plot not found for which schedule is requested for plot: {}", plotKeyDto);
            throw new IllegalArgumentException("Plot not found for which schedule is requested");
        }
    }

    @NonNull
    private static ScheduleKeyDto toScheduleKey(@NonNull final PlotKeyDto plotKeyDto) {
        return new ScheduleKeyDto(plotKeyDto.getCustomerId(),
                plotKeyDto.getPlotId(),
                UUID.randomUUID().toString());
    }

}
