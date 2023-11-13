package com.example.gspannerdemo;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.google.cloud.Date;


@Service
public class ReservationService {

    // @Autowired
    private ReservationRepo reservationRepository;

    public ReservationService(ReservationRepo repository){
        this.reservationRepository = repository;
    }

    public Reservation getReservationById(String id){
        // return reservationRepository.findById(id);
        Optional<Reservation> res = reservationRepository.findById(id);
        return res.isPresent()? res.get(): null;
    }

    public boolean isReservationPresent(String id) {
        String[] splitId = id.split("_");
        String dateId = splitId[0];
        String hourId = splitId[1];
        Iterator<Reservation> itr = reservationRepository.findAll().iterator();
        String date = "";
        String hour = "";
        while(itr.hasNext()){
            Reservation res = itr.next();
            date = res.getId().toString().split("_")[1];
            hour = Integer.toString(res.getHourNumber());
            if(date.equals(dateId) && hour.equals(hourId))
            return true;
        }
        return false;
    }

    public String createReservation(Reservation reservation){
        java.util.Date dt = new java.util.Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        dt = c.getTime();
        Date d = Date.fromJavaUtilDate(dt);
        reservation.setId(reservation.getAptId() + "_" + d);
        Reservation saved = reservationRepository.save(reservation);
        return saved.getId();
    }

    public Reservation updatReservation(Reservation reservation, String id){
        Optional<Reservation> existingReservation = reservationRepository.findById(id);
        if(!existingReservation.isPresent()){
            return null;
        }
        existingReservation.get().setAptId(reservation.getAptId());
        existingReservation.get().setHourNumber(reservation.getHourNumber());
        existingReservation.get().setPlayerCount(reservation.getPlayerCount());
        reservationRepository.save(existingReservation.get());
        return existingReservation.get();
    }

    public Reservation deleteReseervation(String id) {
        Reservation exReservation = reservationRepository.findById(id).orElse(null);
        if(exReservation == null) return null;
        reservationRepository.delete(exReservation);
        return exReservation;
    }
    
}
