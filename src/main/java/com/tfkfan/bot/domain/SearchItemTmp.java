package com.tfkfan.bot.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A SearchItem.
 */
@Entity
@Table(name = "search_item_tmp")
@RegisterForReflection
public class SearchItemTmp extends PanacheEntityBase implements Serializable {

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
        if (!(o instanceof SearchItemTmp)) {
            return false;
        }
        return href != null && href.equals(((SearchItemTmp) o).href);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return ("SearchItem{" + "href=" + href + ", title='" + title + "'" + ", img='" + img + "'" + ", price=" + price + "}");
    }

    public SearchItemTmp update() {
        return update(this);
    }

    public SearchItemTmp persistOrUpdate() {
        return persistOrUpdate(this);
    }

    public static SearchItemTmp update(SearchItemTmp searchItem) {
        if (searchItem == null) {
            throw new IllegalArgumentException("searchItem can't be null");
        }
        var entity = SearchItemTmp.<SearchItemTmp>findById(searchItem.href);
        if (entity != null) {
            entity.title = searchItem.title;
            entity.img = searchItem.img;
            entity.price = searchItem.price;
        }
        return entity;
    }

    public static SearchItemTmp persistOrUpdate(SearchItemTmp searchItem) {
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
