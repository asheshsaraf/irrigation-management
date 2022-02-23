package com.irrigation.aggregates.irrigationservice.dto.plot;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.irrigation.aggregates.irrigationservice.dto.common.NamedDto;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PlotDto extends NamedDto {

    private final String ownerId;
    private final AddressDto address;
    private final PlotSizeDto size;

    public PlotDto(@NonNull final String name,
                   @Nullable final String description,
                   @NonNull final String ownerId,
                   @Nullable final AddressDto address,
                   @NonNull final PlotSizeDto size,
                   @Nullable final String version) {
        super(name, description, version);
        this.ownerId = ownerId;
        this.address = address;
        this.size = size;
    }

    @NonNull
    public String getOwnerId() {
        return ownerId;
    }

    @Nullable
    public AddressDto getAddress() {
        return address;
    }

    @NonNull
    public PlotSizeDto getSize() {
        return size;
    }
}
