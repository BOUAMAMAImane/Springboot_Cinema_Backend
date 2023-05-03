package com.example.web;

import com.example.dao.FilmRepository;
import com.example.dao.TicketRepository;
import com.example.entities.Film;
import com.example.entities.Ticket;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CinemaRestController {
      @Autowired
      private FilmRepository filmRepository;
      @Autowired
      private TicketRepository ticketRepository;
      @GetMapping(path = "/imagefilm/{id}",produces = MediaType.IMAGE_JPEG_VALUE)
      public byte[] image(@PathVariable(name = "id")Long id) throws Exception {
            Film f=filmRepository.findById(id).get();
            String photoname=f.getPhoto();
            File file=new File(System.getProperty("user.home")+"\\demo\\images\\"+photoname+".png");
            Path path=Paths.get(file.toURI());
            return Files.readAllBytes(path);
      }
      @PostMapping("/payerTickets")
      @Transactional
      public List<Ticket> payerTickets(@RequestBody TicketForm ticketForm){
            List<Ticket> listTickets=new ArrayList<>();
            ticketForm.getTickets().forEach(id->{
                Ticket ticket=ticketRepository.findById(id).get();
                ticket.setNomClient(ticketForm.getNomClient());
                ticket.setReserve(true);
                ticketRepository.save(ticket);
                listTickets.add(ticket);
          });
          return listTickets;
      }

}
@Data
class TicketForm{
    private String nomClient;
    private List<Long> tickets=new ArrayList<>();

}