package com.example.dao;


import com.example.entities.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
//toutes les methodes héritées de Jparepository sont accessible via API REST
public interface PlaceRepository extends JpaRepository<Place,Long> {
    
}
