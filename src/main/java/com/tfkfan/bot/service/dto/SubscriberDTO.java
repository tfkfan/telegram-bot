package com.tfkfan.bot.service.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.tfkfan.bot.domain.Subscriber} entity.
 */
@RegisterForReflection
public class SubscriberDTO implements Serializable {

    public Long id;

    @NotNull
    public Boolean active;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubscriberDTO)) {
            return false;
        }

        return id != null && id.equals(((SubscriberDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SubscriberDTO{" + ", id=" + id + ", active='" + active + "'" + "}";
    }
}
