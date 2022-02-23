package com.irrigation.services.irrigationservice.api.plot;

import com.irrigation.aggregates.irrigationservice.dto.common.EntityWithKeyDto;
import com.irrigation.aggregates.irrigationservice.dto.plot.PlotDto;
import com.irrigation.aggregates.irrigationservice.dto.plot.PlotKeyDto;
import com.irrigation.services.irrigationservice.core.plot.PlotService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = {"All Plot Management"})
@RequestMapping(value = "plot")
@SuppressWarnings("unused")
public class AllPlotController {

    private final PlotService plotService;

    @GetMapping
    @ApiOperation(value = "This API is used to retrieve all the plots in the system")
    public ResponseEntity<List<EntityWithKeyDto<PlotKeyDto, PlotDto>>> getAllPlots() {
        log.info("Get all plots");
        return ResponseEntity.ok(plotService.getAll());
    }

}
