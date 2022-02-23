package com.irrigation.services.irrigationservice.api.plot;

import com.irrigation.aggregates.irrigationservice.dto.common.EntityWithKeyDto;
import com.irrigation.aggregates.irrigationservice.dto.plot.PlotDto;
import com.irrigation.aggregates.irrigationservice.dto.plot.PlotKeyDto;
import com.irrigation.aggregates.irrigationservice.dto.user.CustomerKeyDto;
import com.irrigation.services.irrigationservice.core.plot.PlotService;
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
@Api(tags = {"Plot Management"})
@RequestMapping(value = "customer/{customer_id}/plot")
@SuppressWarnings("unused")
public class PlotController {

    private final PlotService plotService;

    @PostMapping
    @ApiOperation(value = "This API is used to create a plot of land")
    public ResponseEntity<String> createPlot(@PathVariable("customer_id") final String customerId,
                                             @RequestBody @Valid final PlotDto plotDto,
                                             final UriComponentsBuilder uriComponentsBuilder) {
        validate(customerId);
        log.info("Creating plot for the customer with id: {}", customerId);
        final PlotKeyDto plotKeyDto = plotService.create(new CustomerKeyDto(customerId), plotDto);
        final UriComponents uriComponents = uriComponentsBuilder
                .path("/customer/{customer_id}/plot/{plot_id}")
                .buildAndExpand(plotDto.getOwnerId(), plotKeyDto.getPlotId());
        return ResponseEntity.created(uriComponents.toUri()).build();
    }

    @GetMapping
    @ApiOperation(value = "This API is used to retrieve all the plots for a customer")
    public ResponseEntity<List<EntityWithKeyDto<PlotKeyDto, PlotDto>>> getAllPlots(
            @PathVariable("customer_id") final String customerId) {
        validate(customerId);
        log.info("Get all plots for the customer with id: {}", customerId);
        final List<EntityWithKeyDto<PlotKeyDto, PlotDto>> plots =
                plotService.getAll(new CustomerKeyDto(customerId));
        return ResponseEntity.ok(plots);
    }

    @GetMapping("/{plot_id}")
    @ApiOperation(value = "This API is used to retrieve a plot for a customer")
    public ResponseEntity<EntityWithKeyDto<PlotKeyDto, PlotDto>> getPlot(
            @PathVariable("customer_id") final String customerId,
            @PathVariable("plot_id") final String plotId) {
        validate(customerId, plotId);
        log.info("Get a plot with id: {} for the customer with id: {}", plotId, customerId);
        final EntityWithKeyDto<PlotKeyDto, PlotDto> plot =
                plotService.get(new PlotKeyDto(customerId, plotId));
        return ResponseEntity.ok(plot);
    }

    @PutMapping("/{plot_id}")
    @ApiOperation(value = "This API is used to update a plot for a customer")
    public ResponseEntity<Void> updatePlot(@PathVariable("customer_id") final String customerId,
                                           @PathVariable("plot_id") final String plotId,
                                           @RequestBody @Valid final PlotDto plotDto) {
        validate(customerId, plotId);
        log.info("Update a plot with id: {} for the customer with id: {}", plotId, customerId);
        plotService.update(new PlotKeyDto(customerId, plotId), plotDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{plot_id}")
    @ApiOperation(value = "This API is used to delete a plot for a customer")
    public ResponseEntity<Void> deletePlot(@PathVariable("customer_id") final String customerId,
                                           @PathVariable("plot_id") final String plotId) {
        validate(customerId, plotId);
        log.info("Delete a plot with id: {} for the customer with id: {}", plotId, customerId);
        plotService.delete(new PlotKeyDto(customerId, plotId));
        return ResponseEntity.noContent().build();
    }

}
