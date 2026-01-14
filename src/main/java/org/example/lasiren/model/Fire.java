package org.example.lasiren.model;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Fire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double latitude;
    private double longitude;

    private LocalDateTime startTime;

    private boolean closed;


    // Siren ---> Fire : Siren er owner-siden og har Foreign Key fire_id.

    // Dette er derfor Inverse side
    @OneToMany(mappedBy = "fire")
    private List<Siren> sirens = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime starTime) {
        this.startTime = starTime;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public List<Siren> getSirens() {
        return sirens;
    }

    public void setSirens(List<Siren> sirens) {
        this.sirens = sirens;
    }
}
