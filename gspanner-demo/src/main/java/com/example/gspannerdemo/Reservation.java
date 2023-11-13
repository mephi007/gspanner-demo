package com.example.gspannerdemo;


import lombok.Data;
import com.google.cloud.Timestamp;
import com.google.cloud.spring.data.spanner.core.mapping.*;
import com.google.cloud.Date;

@Table(name="RESERVATION")
@Data
public class Reservation {
  @PrimaryKey(keyOrder = 1)
  @Column(name="ID")
  private String id;

  public String getId() {
    return id;
}
public void setId(String id) {
    this.id = id;
}
public String getAptId() {
    return aptId;
}
public void setAptId(String aptId) {
    this.aptId = aptId;
}
public int getHourNumber() {
    return hourNumber;
}
public void setHourNumber(int hourNumber) {
    this.hourNumber = hourNumber;
}
public int getPlayerCount() {
    return playerCount;
}
public void setPlayerCount(int playerCount) {
    this.playerCount = playerCount;
}
  @Column(name="APT_ID")
  private String aptId;

  @Column(name="HOUR_NUMBER")
  private int hourNumber;

  @Column(name="PLAYER_COUNT")
  private int playerCount;


}
