package com.irrigation.services.irrigationservice.core.irrigation;

import com.irrigation.aggregates.irrigationservice.dto.irrigationstatus.IrrigationStatusDto;
import org.springframework.lang.NonNull;

import java.util.List;

public interface IrrigationService {

    void scheduleIrrigation();

    List<IrrigationStatusDto> getAll(@NonNull final String customerId,
                                     @NonNull final String plotId);

}
