package com.tfkfan.bot.service.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.tfkfan.bot.domain.SearchQuery} entity.
 */
@RegisterForReflection
public class SearchQueryDTO implements Serializable {

    public Long id;

    @NotNull
    public String value;

    @NotNull
    public Boolean active;

    public Long minPrice;

    public Long maxPrice;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SearchQueryDTO)) {
            return false;
        }

        return id != null && id.equals(((SearchQueryDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return (
            "SearchQueryDTO{" +
            ", id=" +
            id +
            ", value='" +
            value +
            "'" +
            ", active='" +
            active +
            "'" +
            ", minPrice=" +
            minPrice +
            ", maxPrice=" +
            maxPrice +
            "}"
        );
    }
}
