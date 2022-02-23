package com.irrigation.aggregates.irrigationservice.dto.plot;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.irrigation.aggregates.irrigationservice.dto.common.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AddressDto extends BaseDto {

    private String addressLine1;
    private String addressLine2;
    private String city;
    private String pinCode;
    private String country;

}
