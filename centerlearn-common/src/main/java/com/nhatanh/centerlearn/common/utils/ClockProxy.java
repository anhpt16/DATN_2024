package com.nhatanh.centerlearn.common.utils;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.io.EzyDates;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@EzySingleton
public class ClockProxy {
    private final ZoneId zoneId = ZoneId.systemDefault();
    private final Clock clock = Clock.system(zoneId);

    public LocalDate nowDate() {
        return this.nowDateTime().toLocalDate();
    }

    public LocalDateTime nowDateTime() {
        return EzyDates.millisToDateTime(this.clock.millis(), this.zoneId);
    }

    public long toTimestamp(LocalDateTime dateTime) {
        return EzyDates.toInstant(dateTime, this.zoneId).toEpochMilli();
    }

    public LocalDate toLocalDate(long millis) {
        return this.toLocalDateTime(millis).toLocalDate();
    }

    public LocalDateTime toLocalDateTime(long millis) {
        return EzyDates.millisToDateTime(millis, this.zoneId);
    }
}
