package models;

public class Ticket {
  private String license_plate, expired_date;
  private int status;

  public Ticket() {
  }

  public void setLicense_plate(String license_plate) {
    this.license_plate = license_plate;
  }

  public void setExpired_date(String expired_date) {
    this.expired_date = expired_date;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getLicense_plate() {
    return license_plate;
  }

  public String getExpired_date() {
    return expired_date;
  }

  public int getStatus() {
    return status;
  }

  public Ticket(String license_plate, String expired_date, int status) {
    this.license_plate = license_plate;
    this.expired_date = expired_date;
    this.status = status;
  }
}
