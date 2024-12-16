package com.tfkfan.bot.service.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.tfkfan.bot.domain.SearchItem} entity.
 */
@RegisterForReflection
public class SearchItemDTO implements Serializable {

    @NotNull
    public String href;

    @NotNull
    public String title;

    public String img;

    @NotNull
    public Long price;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SearchItemDTO)) {
            return false;
        }

        return href != null && href.equals(((SearchItemDTO) o).href);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return ("SearchItemDTO{" + ", href=" + href + ", title='" + title + "'" + ", img='" + img + "'" + ", price=" + price + "}");
    }
}
