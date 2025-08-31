package com.pollservice.poll;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Voter extends PanacheEntityBase {
    @Id
    private UUID uuid;

    @PrePersist
    public void generateUUID() {
        this.uuid = UUID.randomUUID();
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
