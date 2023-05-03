package com.example.service;

import com.example.dao.*;
import com.example.entities.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;
@Service
@Transactional
public class CinemaInitServiceImp implements ICinemaInitService{
    @Autowired
    private VilleRepository villeRepository;
    @Autowired
    private CategorieRepository categorieRepository;
    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private ProjectionFilmRepository projectionFilmRepository;
    @Autowired
    private SalleRepository salleRepository;
    @Autowired
    private SeanceRepository seanceRepository;
    @Autowired
    private  TicketRepository ticketRepository;
    @Override
    public void initVilles() {
        Stream.of("Fes","Tanger","Casablanca","Rabat","Marrakech").forEach(v->{
            Ville ville=new Ville();
            ville.setNom(v);
            villeRepository.save(ville);
        });
    }
    @Override
    public void initCinemas() {
         villeRepository.findAll().forEach(ville -> {
             Stream.of("REX","Megarama","Founoun").forEach(c->{
                 Cinema cinema=new Cinema();
                 cinema.setNom(c);
                 cinema.setVille(ville);
                 cinema.setNombreSalles(10+(int)(Math.random()*20));
                 cinemaRepository.save(cinema);
             });
         });
    }
    @Override
    public void initSalles() {
        cinemaRepository.findAll().forEach(cinema -> {
        for (int i=0;i<cinema.getNombreSalles();i++){
            Salle salle=new Salle();
            salle.setNombrePlace(15+(int)(Math.random()*20));
            salle.setNom("Salle"+(i+1));
            salle.setCinema(cinema);
            salleRepository.save(salle);
        }
        });
    }
    @Override
    public void initPlaces() {
        salleRepository.findAll().forEach(salle -> {
            for (int i=0;i<salle.getNombrePlace();i++){
                Place place=new Place();
                place.setNumero(i+1);
                place.setSalle(salle);
                placeRepository.save(place);
            }
        });
    }
    @Override
    public void initSeances() {
        DateFormat df=new SimpleDateFormat("HH:mm");
        Stream.of("11:00","13:00","16:00","18:00","20:00").forEach(s -> {
            Seance seance=new Seance();
            try {
                seance.setHeureDebut(df.parse(s));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            seanceRepository.save(seance);
        });
    }
    @Override
    public void initCategories() {
        Stream.of("Action","Histoire","Fiction","Comedie","Aventure","Horreur").forEach(C->{
           Categorie categorie=new Categorie();
           categorie.setNom(C);
           categorieRepository.save(categorie);
        });

    }
    @Override
    public void initFilms() {
        double[]  duree=new double[]{1,1.5,2,2.5};
        List<Categorie> categories=categorieRepository.findAll();
        Stream.of("King kong","After","Titanic","Me Befor you","The in between").forEach(f->{
            Film film=new Film();
            film.setTitre(f);
            film.setDuree(duree[new Random().nextInt(duree.length)]);
            film.setPhoto(f.replaceAll(" ",""));
            film.setCategorie(categories.get(new Random().nextInt(categories.size())));
            filmRepository.save(film);
        });
    }

    @Override
    public void initProjections() {
        double[] prices = new double[] {30,50,60,70,90,100};
        villeRepository.findAll().forEach(ville -> {
            ville.getCinema().forEach(cinema -> {
                cinema.getSalle().forEach(salle -> {
                        filmRepository.findAll().forEach(film -> {
                            seanceRepository.findAll().forEach(seance -> {
                                ProjectionFilm projection = new ProjectionFilm();
                                projection.setDateProjection(new Date());
                                projection.setFilm(film);
                                projection.setPrix(prices[new Random().nextInt(prices.length)]);
                                projection.setSalle(salle);
                                projection.setSeance(seance);
                                projectionFilmRepository.save(projection);
                            });
                    });
                });
            });
        });
    }
    @Override
    public void initTickets() {
        projectionFilmRepository.findAll().forEach(p->{
            if(!p.getSalle().getPlace().isEmpty()) { // Vérifier s'il y a des places associées à la projection
                p.getSalle().getPlace().forEach(place->{
                    Ticket ticket = new Ticket();
                    ticket.setPlace(place);

                    ticket.setPrix(p.getPrix());
                    ticket.setProjectionFilm(p);
                    ticket.setReserve(true);
                    ticketRepository.save(ticket);

                });
            }
        });
    }


}
