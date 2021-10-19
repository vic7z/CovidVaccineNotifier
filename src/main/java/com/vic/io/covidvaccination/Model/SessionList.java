package com.vic.io.covidvaccination.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class SessionList {

  private String session_id;
  private String date;
  private int available_capacity;
  private int min_age_limit;
  private String vaccine;
  private List<String> slots = new ArrayList<>();
  private int available_capacity_dose1;
  private int available_capacity_dose2;
}
