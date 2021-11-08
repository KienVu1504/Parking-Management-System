package models;

public class History {
  private int id;
  private String license_plate, type, seat, time_in, time_out, parking_time, fee;
  private int ticket;
  private int status;

  public History() {
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setLicense_plate(String license_plate) {
    this.license_plate = license_plate;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setSeat(String seat) {
    this.seat = seat;
  }

  public void setTicket(int ticket) {
    this.ticket = ticket;
  }

  public void setTime_in(String time_in) {
    this.time_in = time_in;
  }

  public void setTime_out(String time_out) {
    this.time_out = time_out;
  }

  public void setParking_time(String parking_time) {
    this.parking_time = parking_time;
  }

  public void setFee(String fee) {
    this.fee = fee;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public int getId() {
    return id;
  }

  public String getLicense_plate() {
    return license_plate;
  }

  public String getType() {
    return type;
  }

  public String getSeat() {
    return seat;
  }

  public int getTicket() {
    return ticket;
  }

  public String getTime_in() {
    return time_in;
  }

  public String getTime_out() {
    return time_out;
  }

  public String getParking_time() {
    return parking_time;
  }

  public String getFee() {
    return fee;
  }

  public int getStatus() {
    return status;
  }

  public History(int id, String license_plate, String type, String seat, int ticket, String time_in, String time_out, String parking_time, String fee, int status) {
    this.id = id;
    this.license_plate = license_plate;
    this.type = type;
    this.seat = seat;
    this.ticket = ticket;
    this.time_in = time_in;
    this.time_out = time_out;
    this.parking_time = parking_time;
    this.fee = fee;
    this.status = status;
  }
}
