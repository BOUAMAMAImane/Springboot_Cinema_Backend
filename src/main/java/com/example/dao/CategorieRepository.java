package com.example.dao;

import com.example.entities.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
//toutes les methodes héritées de Jparepository sont accessible via API REST
public interface CategorieRepository extends JpaRepository<Categorie,Long> {
    
}
