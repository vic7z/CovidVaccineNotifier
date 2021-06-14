package com.vic.io.covidvaccination.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Centers {
    private int center_id;
    private String name;
    private String address;
    private String block_name;
    private int pincode;
    private String from;
    private String to;
    private String fee_type;
    private List<SessionList> sessions=new ArrayList<>();
    private List<VaccineFee> vaccine_fees=new ArrayList<>();

}
