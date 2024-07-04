package com.gateway.desktop.DataObject;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Store {
  private String storeName;
  private String description;
  private String location;
  private String telpNumber;

  @Builder.Default
  List<Product> productsData = new ArrayList<>();

}
