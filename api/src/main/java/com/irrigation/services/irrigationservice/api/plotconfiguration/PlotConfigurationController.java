package com.irrigation.services.irrigationservice.api.plotconfiguration;

import com.irrigation.aggregates.irrigationservice.dto.common.EntityWithKeyDto;
import com.irrigation.aggregates.irrigationservice.dto.plot.PlotKeyDto;
import com.irrigation.aggregates.irrigationservice.dto.plotconfiguration.PlotConfigurationDto;
import com.irrigation.services.irrigationservice.core.plotconfiguration.PlotConfigurationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

import static com.irrigation.services.irrigationservice.api.common.ResourceUtils.validate;

@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = {"Plot Configuration Management"})
@RequestMapping(value = "customer/{customer_id}/plot/{plot_id}/configuration")
@SuppressWarnings("unused")
public class PlotConfigurationController {

    private final PlotConfigurationService configurationService;

    @PostMapping
    @ApiOperation(value = "This API is used to create configuration for a plot of land")
    public ResponseEntity<String> createConfiguration(@PathVariable("customer_id") final String customerId,
                                                      @PathVariable("plot_id") final String plotId,
                                                      @RequestBody @Valid final PlotConfigurationDto configurationDto,
                                                      final UriComponentsBuilder uriComponentsBuilder) {
        validate(customerId, plotId);
        log.info("Creating plot configuration for the customer with id: {} for plot: {}", customerId, plotId);
        final PlotKeyDto plotKeyDto =
                configurationService.create(new PlotKeyDto(customerId, plotId), configurationDto).getKey();
        final UriComponents uriComponents = uriComponentsBuilder
                .path("/customer/{customer_id}/plot/{plot_id}/configuration")
                .buildAndExpand(customerId, plotId);
        return ResponseEntity.created(uriComponents.toUri()).build();
    }

    @GetMapping
    @ApiOperation(value = "This API is used to retrieve a plot configuration for a plot of land")
    public ResponseEntity<EntityWithKeyDto<PlotKeyDto, PlotConfigurationDto>> getConfiguration(
            @PathVariable("customer_id") final String customerId,
            @PathVariable("plot_id") final String plotId) {
        validate(customerId, plotId);
        log.info("Get configuration for a plot with id: {} for the customer with id: {}", plotId, customerId);
        final EntityWithKeyDto<PlotKeyDto, PlotConfigurationDto> configuration =
                configurationService.get(new PlotKeyDto(customerId, plotId));
        return ResponseEntity.ok(configuration);
    }

    @PutMapping
    @ApiOperation(value = "This API is used to update the configuration for a plot of land")
    public ResponseEntity<Void> updateConfiguration(@PathVariable("customer_id") final String customerId,
                                                    @PathVariable("plot_id") final String plotId,
                                                    @RequestBody @Valid final PlotConfigurationDto configurationDto) {
        validate(customerId, plotId);
        log.info("Update configuration for a plot with id: {} for the customer with id: {}", plotId, customerId);
        configurationService.update(new PlotKeyDto(customerId, plotId), configurationDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    @ApiOperation(value = "This API is used to delete the configuration for a plot of land")
    public ResponseEntity<Void> deletePlot(@PathVariable("customer_id") final String customerId,
                                           @PathVariable("plot_id") final String plotId) {
        validate(customerId, plotId);
        log.info("Delete the configuration for a plot with id: {} for the customer with id: {}", plotId, customerId);
        configurationService.delete(new PlotKeyDto(customerId, plotId));
        return ResponseEntity.noContent().build();
    }

}
