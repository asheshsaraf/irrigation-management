package com.irrigation.aggregates.irrigationservice.domain.plot;

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
@Table(name = "plot")
@IdClass(PlotKey.class)
public class Plot implements Serializable {

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

    @Column(name = "owner_id")
    private String ownerId;

    @Type(type = "text")
    @Column(columnDefinition = "text")
    private String address;

    @Type(type = "text")
    @Column(name = "plot_size", columnDefinition = "text", nullable = false)
    private String plotSize;

    @Column(nullable = false)
    private String version;
}
