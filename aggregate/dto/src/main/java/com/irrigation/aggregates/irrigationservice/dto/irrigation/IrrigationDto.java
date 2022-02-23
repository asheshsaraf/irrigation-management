package com.irrigation.aggregates.irrigationservice.dto.irrigation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.irrigation.aggregates.irrigationservice.dto.common.BaseDto;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class IrrigationDto extends BaseDto {

    private String deviceId;
    private List<WaterScheduleDto> waterSchedules;

}
