package com.irrigation.aggregates.irrigationservice.domain.plotconfiguration;

import com.irrigation.aggregates.irrigationservice.domain.plot.PlotKey;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "plot_configuration")
@IdClass(PlotKey.class)
public class PlotConfiguration implements Serializable {

    @Id
    @Column(name = "customer_id", nullable = false)
    private String customerId;

    @Id
    @Column(name = "plot_id", nullable = false)
    private String plotId;

    @Column(name = "plot_name", nullable = false)
    private String name;

    @Column
    private String description;

    @Column(name = "soil_type")
    private String soilType;

    @Type(type = "text")
    @Column(columnDefinition = "text")
    private String crops;

    @Type(type = "text")
    @Column(columnDefinition = "text", nullable = false)
    private String irrigation;

    @Column(name = "average_moisture_level")
    private String averageMoistureLevel;

    @Column(nullable = false)
    private String version;
}
