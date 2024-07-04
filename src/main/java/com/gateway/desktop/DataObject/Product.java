package com.gateway.desktop.DataObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

  private String name;
  private String description;
  private String category;
  private double price;
  private int stock;
  private Volume volume;
  private String image;
  private String storeId;

}
