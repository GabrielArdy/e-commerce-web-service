package com.ecommerce.desktop.DTO;

import com.ecommerce.desktop.Model.Store;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreDTO {

  private int statusCode;
  private String message;
  private Store data;
}