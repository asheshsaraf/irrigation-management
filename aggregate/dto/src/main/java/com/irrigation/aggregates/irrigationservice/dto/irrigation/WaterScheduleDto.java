package com.irrigation.aggregates.irrigationservice.dto.irrigation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.irrigation.aggregates.irrigationservice.dto.common.BaseDto;
import com.irrigation.aggregates.irrigationservice.dto.schedule.ScheduleKeyDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class WaterScheduleDto extends BaseDto {

    private ScheduleKeyDto scheduleKeyDto;
    private WaterAmountDto waterAmountDto;
}
