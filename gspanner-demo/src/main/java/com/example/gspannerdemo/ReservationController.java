package com.example.gspannerdemo;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ReservationController {

    @Autowired
    private ReservationService reservationService;
    
    //read reservation by id
    @GetMapping("/reservations/{id}")
    public ResponseEntity<Reservation> getReservation(@PathVariable("id") String id){
        Reservation reservation =  reservationService.getReservationById(id);
        if(reservation != null){
            return new ResponseEntity<>(reservation, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //read reservation with parameterized checks
    @GetMapping("/reservations/status/{id}")
    public String isReservationPresent(@PathVariable("id") String id){
        return reservationService.isReservationPresent(id)==true?"PRESENT":"ABSENT";
    }

    //insertReservation
    @PostMapping("/reservations")
    public ResponseEntity<String> createReservation(@RequestBody Reservation reservation){
        String savedId = reservationService.createReservation(reservation);
        return new ResponseEntity<>(savedId, HttpStatus.CREATED);
    }

    //update booking
    @PutMapping("/reservations/{id}")
    public ResponseEntity<Reservation> updatReservation(@RequestBody Reservation reservation, @PathVariable("id") String id){
        Reservation updatedReservation = reservationService.updatReservation(reservation, id);
        return updatedReservation != null 
                            ? new ResponseEntity<>(updatedReservation, HttpStatus.ACCEPTED) :
                            new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    //delete reservation
    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Reservation> deleteReseervation(@PathVariable("id") String id){
        Reservation delReservation = reservationService.deleteReseervation(id);
        return delReservation != null ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @GetMapping("/")
    public String saySomething() {
        return "Spring Boot App on Cloud Run, containerized by Jib!";
    }

}
