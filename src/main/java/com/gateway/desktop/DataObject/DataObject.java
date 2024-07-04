package com.gateway.desktop.DataObject;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataObject {

  @JsonProperty("statusCode")
  private int statusCode;

  @JsonProperty("message")
  private String message;

  @JsonProperty("data")
  private Object data;

}
