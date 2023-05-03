package com.example.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Collection;
@Data@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "cinema")
public class Salle implements Serializable {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private long id;
     private String nom;
     private int nombrePlace;
     @ManyToOne
     @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
     private Cinema cinema;
     @OneToMany(mappedBy = "salle")
     @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
     @JsonIgnore
     private Collection<ProjectionFilm> projectionFilm;
     @OneToMany(mappedBy = "salle")
     @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
     private Collection<Place> place;

}
