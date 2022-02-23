package com.irrigation.aggregates.irrigationservice.dto.user;

import com.irrigation.aggregates.irrigationservice.dto.common.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class CustomerKeyDto extends BaseDto {

    private final String customerId;

}
