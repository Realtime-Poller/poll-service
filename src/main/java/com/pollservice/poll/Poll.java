package com.pollservice.poll;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.OneToMany;

import java.time.Instant;
import java.util.List;

@Entity
public class Poll extends PanacheEntity {
    private String title;

    private String description;

    private Instant date;

    @OneToMany(cascade = CascadeType.ALL)
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

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

}
