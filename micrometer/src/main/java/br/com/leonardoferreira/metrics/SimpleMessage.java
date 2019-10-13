package br.com.leonardoferreira.metrics;

import java.util.StringJoiner;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SimpleMessage {

    @Id
    private String id;

    public SimpleMessage(final String id) {
        this.id = id;
    }

    public SimpleMessage() {
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", "SimpleMessage(", ")")
                .add("id=" + id)
                .toString();
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }
}
