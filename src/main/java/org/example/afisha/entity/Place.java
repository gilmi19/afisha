package org.example.afisha.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Place {
    //id, name, address, city
    private  Long id;
    private String name;
    private String address;
    private String city;
}
