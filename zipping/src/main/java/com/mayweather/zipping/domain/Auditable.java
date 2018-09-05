package com.mayweather.zipping.domain;

import java.time.Instant;

public interface Auditable {
    void setCreatedDate(final Instant createdDate);
    void setLastModifiedDate(final Instant lastModifiedDate);
}
