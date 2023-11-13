package com.example.demo;


public class Reservation {
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

  public String aptId;

  public int hourNumber;

  public int playerCount;


}
