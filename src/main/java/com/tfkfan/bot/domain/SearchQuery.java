package com.tfkfan.bot.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

/**
 * A SearchQuery.
 */
@Entity
@Table(name = "search_query")
@RegisterForReflection
public class SearchQuery extends PanacheEntityBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    public Long id;

    @NotNull
    @Column(name = "value", nullable = false, unique = true)
    public String value;

    @NotNull
    @Column(name = "active", nullable = false)
    public Boolean active;

    @Column(name = "min_price")
    public Long minPrice;

    @Column(name = "max_price")
    public Long maxPrice;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SearchQuery)) {
            return false;
        }
        return id != null && id.equals(((SearchQuery) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return (
            "SearchQuery{" +
            "id=" +
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

    public SearchQuery update() {
        return update(this);
    }

    public SearchQuery persistOrUpdate() {
        return persistOrUpdate(this);
    }

    public static SearchQuery update(SearchQuery searchQuery) {
        if (searchQuery == null) {
            throw new IllegalArgumentException("searchQuery can't be null");
        }
        var entity = SearchQuery.<SearchQuery>findById(searchQuery.id);
        if (entity != null) {
            entity.value = searchQuery.value;
            entity.active = searchQuery.active;
            entity.minPrice = searchQuery.minPrice;
            entity.maxPrice = searchQuery.maxPrice;
        }
        return entity;
    }

    public static SearchQuery persistOrUpdate(SearchQuery searchQuery) {
        if (searchQuery == null) {
            throw new IllegalArgumentException("searchQuery can't be null");
        }
        if (searchQuery.id == null) {
            persist(searchQuery);
            return searchQuery;
        } else {
            return update(searchQuery);
        }
    }

    public static List<SearchQuery> findActive() {
        return find("active", true).list();
    }
}
