package com.irrigation.aggregates.irrigationservice.convertor.schedule;

import com.irrigation.aggregates.irrigationservice.domain.schedule.ScheduleKey;
import com.irrigation.aggregates.irrigationservice.dto.schedule.ScheduleKeyDto;
import org.springframework.lang.NonNull;

import java.util.List;

public final class ScheduleKeyDtoConvertor {

    private static final ScheduleKeyDtoConvertor convertor = new ScheduleKeyDtoConvertor();

    private ScheduleKeyDtoConvertor() {
    }

    public static ScheduleKeyDtoConvertor scheduleKeyConvertor() {
        return convertor;
    }

    @NonNull
    public List<ScheduleKey> fromKeyDtoList(@NonNull final List<ScheduleKeyDto> keyDtoList) {
        return keyDtoList.stream().map(this::fromKeyDto).toList();
    }

    @NonNull
    public ScheduleKey fromKeyDto(@NonNull final ScheduleKeyDto keyDto) {
        return new ScheduleKey(keyDto.getCustomerId(), keyDto.getPlotId(), keyDto.getScheduleId());
    }

    @NonNull
    public ScheduleKeyDto toKeyDto(@NonNull final ScheduleKey key) {
        return new ScheduleKeyDto(key.getCustomerId(), key.getPlotId(), key.getScheduleId());
    }

}
