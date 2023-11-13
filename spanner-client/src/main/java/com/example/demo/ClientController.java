package com.example.demo;

import java.util.Arrays;
import java.util.Calendar;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.http.RequestEntity;
import org.springframework.http.HttpMethod;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import com.google.cloud.Timestamp;
import com.google.cloud.Date;
import org.springframework.core.ParameterizedTypeReference;
import java.net.URI;
import com.example.demo.ApisConstants;

@Controller
public class ClientController {

    
    private RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<String> callReservationsAPI(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<String> result = restTemplate.exchange(ApisConstants.get_reservations, HttpMethod.GET, entity, String.class);
        return result;
    }

    public boolean validateId(Reservation newReservation){
        java.util.Date dt = new java.util.Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        dt = c.getTime();
        String idParam = newReservation.getAptId() +"_"+ Date.fromJavaUtilDate(dt);
        Map<String,String> param  = new HashMap<>();
        param.put("id", idParam);
        try{
            Reservation oldReservation = restTemplate.getForObject(ApisConstants.get_reservation_by_id, Reservation.class, param);
            if(oldReservation.getId().equals(idParam)){
                return true;
            } else return false;
        }catch(Exception e){
            return false;
        }
    }

    public boolean validateSlot(Reservation newReservation){
        java.util.Date dt = new java.util.Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        dt = c.getTime();
        String idParamDt = Date.fromJavaUtilDate(dt).toString();
        int hour = newReservation.getHourNumber();
        String idParam = ApisConstants.get_reservations+"/"+idParamDt+"_"+hour;
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<String> result = restTemplate.exchange(idParam, HttpMethod.GET, entity, String.class);
        if(result.toString().equals("present")){
            return true;
        }else return false;
    }

    @GetMapping("/showreservations")
    public String callReservationsByIdAPI(Reservation reservation){
        try{
            java.util.Date dt = new java.util.Date();
            Calendar c = Calendar.getInstance();
            c.setTime(dt);
            dt = c.getTime();
            String idParam = reservation.getAptId() +"_"+ Date.fromJavaUtilDate(dt);
            Map<String,String> param  = new HashMap<>();
            param.put("id", idParam);
            Reservation res = restTemplate.getForObject(ApisConstants.get_reservation_by_id, Reservation.class, param);
            reservation.setAptId(res.getAptId());
            reservation.setHourNumber(res.getHourNumber());
            reservation.setPlayerCount(res.getPlayerCount());
            return "showMessage";
            
        }catch(Exception e){
            return "searchReservation";
        }
    }

    @GetMapping("/showreservation")
    public String showForm(Reservation reservation){
        String res = callReservationsAPI().toString();
        reservation.setAptId(res);
        return "showMessage";
    }

    @GetMapping("/search")
    public String searchForm(Reservation reservation){
        return "searchReservation";
    }

    @GetMapping("/home")
    public String homeForm(Reservation reservation){
        return "HomePage";
    }

    @GetMapping("/addreservation")
    public String sendForm(Reservation reservation){
        return "addReservation";
    }

    @PostMapping("/addreservation")
    public String processForm(Reservation reservation){
        if(validateId(reservation)) return "errMessage";
        ResponseEntity<String> res = restTemplate.postForEntity(ApisConstants.create_reservations, reservation, String.class);
        reservation.setId(res.toString());
        return "showMessage";
    }

    @PostMapping("/editreservation")
    public String editForm(Reservation reservation){
            Map<String, String> param = new HashMap<>();
            java.util.Date dt = new java.util.Date();
            java.util.Calendar c = java.util.Calendar.getInstance(); 
            c.setTime(dt); 
            dt = c.getTime();
            String idParam = reservation.getAptId() +"_"+  Date.fromJavaUtilDate(dt);
            param.put("id", idParam);
            restTemplate.put(ApisConstants.edit_reservation, reservation, param);
            return "showMessage";
    }

    @PostMapping("/deletereservation")
    public String deleteForm(Reservation reservation){
        Map<String, String> param = new HashMap<>();
            java.util.Date dt = new java.util.Date();
            java.util.Calendar c = java.util.Calendar.getInstance(); 
            c.setTime(dt); 
            dt = c.getTime();
            String idParam = reservation.getAptId() +"_"+  Date.fromJavaUtilDate(dt);
            param.put("id", idParam);
            restTemplate.delete(ApisConstants.delete_reservation, param);
            return "searchReservation";
        }
}
