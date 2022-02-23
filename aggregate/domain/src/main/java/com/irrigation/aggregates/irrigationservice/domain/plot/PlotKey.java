package com.irrigation.aggregates.irrigationservice.domain.plot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlotKey implements Serializable {

    private String customerId;
    private String plotId;

}
