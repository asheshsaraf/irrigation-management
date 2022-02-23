package com.irrigation.services.irrigationservice.api.schedule;

import com.irrigation.aggregates.irrigationservice.dto.common.EntityWithKeyDto;
import com.irrigation.aggregates.irrigationservice.dto.plot.PlotKeyDto;
import com.irrigation.aggregates.irrigationservice.dto.schedule.ScheduleDto;
import com.irrigation.aggregates.irrigationservice.dto.schedule.ScheduleKeyDto;
import com.irrigation.services.irrigationservice.core.schedule.ScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

import static com.irrigation.services.irrigationservice.api.common.ResourceUtils.validate;

@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = {"Schedule Management"})
@RequestMapping(value = "customer/{customer_id}/plot/{plot_id}/schedule")
@SuppressWarnings("unused")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    @ApiOperation(value = "This API is used to create a irrigation schedule for a plot of land")
    public ResponseEntity<String> createSchedule(@PathVariable("customer_id") final String customerId,
                                                 @PathVariable("plot_id") final String plotId,
                                                 @RequestBody @Valid final ScheduleDto scheduleDto,
                                                 final UriComponentsBuilder uriComponentsBuilder) {
        validate(customerId, plotId);
        log.info("Creating schedule for a plot: {} for the customer with id: {}", plotId, customerId);
        final ScheduleKeyDto scheduleKeyDto = scheduleService.create(new PlotKeyDto(customerId, plotId), scheduleDto);
        final UriComponents uriComponents = uriComponentsBuilder
                .path("/customer/{customer_id}/plot/{plot_id}/schedule/{schedule_id}")
                .buildAndExpand(customerId, plotId, scheduleKeyDto.getScheduleId());
        return ResponseEntity.created(uriComponents.toUri()).build();
    }

    @GetMapping
    @ApiOperation(value = "This API is used to retrieve all the schedules for a plot of land")
    public ResponseEntity<List<EntityWithKeyDto<ScheduleKeyDto, ScheduleDto>>> getAllSchedule(
            @PathVariable("customer_id") final String customerId,
            @PathVariable("plot_id") final String plotId) {
        validate(customerId, plotId);
        log.info("Get all schedules for a plot: {} for the customer with id: {}", plotId, customerId);
        final List<EntityWithKeyDto<ScheduleKeyDto, ScheduleDto>> schedules =
                scheduleService.getAll(new PlotKeyDto(customerId, plotId));
        return ResponseEntity.ok(schedules);
    }

    @GetMapping("/{schedule_id}")
    @ApiOperation(value = "This API is used to retrieve a schedule for a plot of land")
    public ResponseEntity<EntityWithKeyDto<ScheduleKeyDto, ScheduleDto>> getSchedule(
            @PathVariable("customer_id") final String customerId,
            @PathVariable("plot_id") final String plotId,
            @PathVariable("schedule_id") final String scheduleId) {
        validate(customerId, plotId, scheduleId);
        log.info("Get the schedule: {} for plot with id: {} for the customer with id: {}",
                scheduleId, plotId, customerId);
        final EntityWithKeyDto<ScheduleKeyDto, ScheduleDto> schedule =
                scheduleService.get(new ScheduleKeyDto(customerId, plotId, scheduleId));
        return ResponseEntity.ok(schedule);
    }

    @PutMapping("/{schedule_id}")
    @ApiOperation(value = "This API is used to update a schedule for a plot of land")
    public ResponseEntity<Void> updateSchedule(@PathVariable("customer_id") final String customerId,
                                               @PathVariable("plot_id") final String plotId,
                                               @PathVariable("schedule_id") final String scheduleId,
                                               @RequestBody @Valid final ScheduleDto scheduleDto) {
        validate(customerId, plotId, scheduleId);
        log.info("Update a schedule: {} for a plot with id: {} for the customer with id: {}",
                scheduleId, plotId, customerId);
        scheduleService.update(new ScheduleKeyDto(customerId, plotId, scheduleId), scheduleDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{schedule_id}")
    @ApiOperation(value = "This API is used to delete a schedule for a plot of land")
    public ResponseEntity<Void> deleteSchedule(@PathVariable("customer_id") final String customerId,
                                               @PathVariable("plot_id") final String plotId,
                                               @PathVariable("schedule_id") final String scheduleId) {
        validate(customerId, plotId, scheduleId);
        log.info("Delete a schedule: {} for a plot with id: {} for the customer with id: {}",
                scheduleId, plotId, customerId);
        scheduleService.delete(new ScheduleKeyDto(customerId, plotId, scheduleId));
        return ResponseEntity.noContent().build();
    }

}
