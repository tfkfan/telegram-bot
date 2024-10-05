package com.tfkfan.bot.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

/**
 * A Subscriber.
 */
@Entity
@Table(name = "subscriber")
@RegisterForReflection
public class Subscriber extends PanacheEntityBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    public Long id;

    @NotNull
    @Column(name = "active", nullable = false)
    public Boolean active = false;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    public Subscriber() {}

    public Subscriber(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Subscriber)) {
            return false;
        }
        return id != null && id.equals(((Subscriber) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Subscriber{" + "id=" + id + ", active='" + active + "'" + "}";
    }

    public Subscriber update() {
        return update(this);
    }

    public Subscriber persistOrUpdate() {
        return persistOrUpdate(this);
    }

    public static Subscriber update(Subscriber subscriber) {
        if (subscriber == null) {
            throw new IllegalArgumentException("subscriber can't be null");
        }
        var entity = Subscriber.<Subscriber>findById(subscriber.id);
        if (entity != null) {
            entity.active = subscriber.active;
        }
        return entity;
    }

    public static Subscriber persistOrUpdate(Subscriber subscriber) {
        if (subscriber == null) {
            throw new IllegalArgumentException("subscriber can't be null");
        }
        if (subscriber.id == null) {
            persist(subscriber);
            return subscriber;
        } else {
            return update(subscriber);
        }
    }

    public static List<Subscriber> findActive() {
        return find("active", true).list();
    }
}
