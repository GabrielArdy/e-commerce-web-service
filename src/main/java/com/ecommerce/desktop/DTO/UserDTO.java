package com.ecommerce.desktop.DTO;

public class UserDTO {
  private String email;
  private String name;
  private String telepon;
  private String address;

  public UserDTO(String email, String name, String telepon, String address) {
    this.email = email;
    this.name = name;
    this.address = address;
    this.telepon = telepon;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getTelepon() {
    return telepon;
  }

  public void setTelepon(String telepon) {
    this.telepon = telepon;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
