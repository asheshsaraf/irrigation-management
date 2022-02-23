package com.irrigation.aggregates.irrigationservice.dto.irrigationstatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.irrigation.aggregates.irrigationservice.dto.common.BaseDto;
import com.irrigation.aggregates.irrigationservice.dto.schedule.ScheduleKeyDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class IrrigationStatusDto extends BaseDto {

    private final String deviceId;
    private final ScheduleKeyDto scheduleKeyDto;
    private final String state;
    private final Integer retryCount;
    
}
