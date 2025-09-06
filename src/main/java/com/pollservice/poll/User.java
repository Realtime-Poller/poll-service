package com.pollservice.poll;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends PanacheEntityBase{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(unique = true, length = 254)
    @NotBlank(message = "Email must not be blank at the entity level.")
    private String email;

    @Column(length = 60)
    @NotBlank(message = "Password must not be blank at the entity level.")
    private String password;

    private Instant createdTimestamp;

    private Instant lastUpdatedTimestamp;

    @OneToMany(mappedBy = "owner")
    private List<Poll> ownedPolls;

    @PrePersist
    public void prePersist() {
        this.createdTimestamp = Instant.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.lastUpdatedTimestamp = Instant.now();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Instant getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Instant createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public Instant getLastUpdatedTimestamp() {
        return lastUpdatedTimestamp;
    }

    public void setLastUpdatedTimestamp(Instant lastUpdatedTimestamp) {
        this.lastUpdatedTimestamp = lastUpdatedTimestamp;
    }

    public List<Poll> getOwnedPolls() {
        return ownedPolls;
    }

    public void setOwnedPolls(List<Poll> ownedPolls) {
        this.ownedPolls = ownedPolls;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + email + '\'' +
                ", password='" + "dummy password" + '\'' +
                ", createdTimestamp=" + createdTimestamp +
                ", lastUpdatedTimestamp=" + lastUpdatedTimestamp +
                '}';
    }
}