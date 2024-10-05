package com.tfkfan.bot.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A SearchItem.
 */
@Entity
@Table(name = "search_item")
@RegisterForReflection
public class SearchItem extends PanacheEntityBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "href", nullable = false, unique = true)
    public String href;

    @NotNull
    @Column(name = "title", nullable = false)
    public String title;

    @Column(name = "img")
    public String img;

    @NotNull
    @Column(name = "price", nullable = false)
    public Long price;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SearchItem)) {
            return false;
        }
        return href != null && href.equals(((SearchItem) o).href);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return ("SearchItem{" + "href=" + href + ", title='" + title + "'" + ", img='" + img + "'" + ", price=" + price + "}");
    }

    public SearchItem update() {
        return update(this);
    }

    public SearchItem persistOrUpdate() {
        return persistOrUpdate(this);
    }

    public static SearchItem update(SearchItem searchItem) {
        if (searchItem == null) {
            throw new IllegalArgumentException("searchItem can't be null");
        }
        var entity = SearchItem.<SearchItem>findById(searchItem.href);
        if (entity != null) {
            entity.title = searchItem.title;
            entity.img = searchItem.img;
            entity.price = searchItem.price;
        }
        return entity;
    }

    public static SearchItem persistOrUpdate(SearchItem searchItem) {
        if (searchItem == null) {
            throw new IllegalArgumentException("searchItem can't be null");
        }
        // if (searchItem.href == null) {
        persist(searchItem);
        return searchItem;
        //  } else {
        //       return update(searchItem);
        //   }
    }
}
