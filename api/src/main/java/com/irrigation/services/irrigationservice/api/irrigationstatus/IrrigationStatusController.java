package com.irrigation.services.irrigationservice.api.irrigationstatus;

import com.irrigation.aggregates.irrigationservice.dto.irrigationstatus.IrrigationStatusDto;
import com.irrigation.services.irrigationservice.core.irrigation.IrrigationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.irrigation.services.irrigationservice.api.common.ResourceUtils.validate;

@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = {"Irrigation Status Management"})
@RequestMapping(value = "customer/{customer_id}/plot/{plot_id}/irrigation")
@SuppressWarnings("unused")
public class IrrigationStatusController {

    private final IrrigationService irrigationService;

    @GetMapping
    @ApiOperation(value = "This API is used to retrieve all the irrigation status for all schedules for a plot of land")
    public ResponseEntity<List<IrrigationStatusDto>> getAllIrrigationStatus(
            @PathVariable("customer_id") final String customerId,
            @PathVariable("plot_id") final String plotId) {
        validate(customerId, plotId);
        log.info("Get all irrigation status for all schedules for a plot: {} for the customer with id: {}",
                plotId, customerId);
        return ResponseEntity.ok(irrigationService.getAll(customerId, plotId));
    }

}
