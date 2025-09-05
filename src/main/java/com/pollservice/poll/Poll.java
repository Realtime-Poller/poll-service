package com.pollservice.poll;

import jakarta.persistence.*;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;

@Entity
public class Poll extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotBlank(message = "Title must not be blank at the entity level.")
    @Column(length = 200, nullable = false)
    private String title;

    @Column(length = 5000)
    private String description;

    //@CreationTimestamp
    private Instant createdTimestamp;

    //@UpdateTimestamp
    private Instant lastUpdatedTimestamp;


    @PrePersist
    public void prePersist() {
        this.createdTimestamp = Instant.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.lastUpdatedTimestamp = Instant.now();
    }

    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL)
    private List<Choice> choices;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Instant date) {
        this.createdTimestamp = date;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public Instant getLastUpdatedTimestamp() {
        return lastUpdatedTimestamp;
    }

    public void setLastUpdatedTimestamp(Instant lastUpdatedTimestamp) {
        this.lastUpdatedTimestamp = lastUpdatedTimestamp;
    }
}
