package com.irrigation.aggregates.irrigationservice.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BaseDto implements Serializable {
}
