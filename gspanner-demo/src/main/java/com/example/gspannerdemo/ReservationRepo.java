package com.example.gspannerdemo;


import org.springframework.stereotype.Repository;

import com.google.cloud.spring.data.spanner.repository.SpannerRepository;

@Repository
public interface ReservationRepo extends SpannerRepository<Reservation,String>{
    
}
