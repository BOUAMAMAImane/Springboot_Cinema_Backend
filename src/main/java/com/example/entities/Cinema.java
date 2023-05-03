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
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "ville")
public class Cinema implements Serializable {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nom;
    private double logitude,latitude,altitude;
    private int nombreSalles;
    @ManyToOne()
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonIgnore // Ajout de cette annotation pour éviter la boucle infinie
    private  Ville ville;
    @OneToMany(mappedBy = "cinema")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonIgnore
    private Collection<Salle> salle;
}