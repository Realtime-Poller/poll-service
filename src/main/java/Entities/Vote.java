package Entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.ManyToOne;

public class Vote extends PanacheEntity {
    @ManyToOne
    private Voter voter;

    @ManyToOne
    private Choice choice;

    public Voter getVoter() {
        return voter;
    }

    public void setVoter(Voter voter) {
        this.voter = voter;
    }

    public Choice getChoice() {
        return choice;
    }

    public void setChoice(Choice choice) {
        this.choice = choice;
    }
}
