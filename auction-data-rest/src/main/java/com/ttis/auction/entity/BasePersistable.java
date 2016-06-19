package com.ttis.auction.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.proxy.HibernateProxy;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.PojomaticPolicy;
import org.pojomatic.annotations.Property;
import org.springframework.data.domain.Persistable;

/**
 * Created by tap
 */
@MappedSuperclass
public abstract class BasePersistable implements Serializable, Persistable<String> {
    @Id
    @GeneratedValue(
            generator = "uuid-generator"
    )
    @GenericGenerator(
            name = "uuid-generator",
            strategy = "uuid2"
    )
    @Column(
            name = "ID"
    )
    @Property(
            policy = PojomaticPolicy.TO_STRING
    )
    private String id;

    public BasePersistable() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isNew() {
        return null == this.getId();
    }

    public final String toString() {
        return Pojomatic.toString(this);
    }

    public final boolean equals(Object obj) {
        return Pojomatic.equals(this, obj);
    }

    public final int hashCode() {
        return Pojomatic.hashCode(this);
    }
}
