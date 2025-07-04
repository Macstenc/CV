package pl.kul.Sklep.Entity;

import lombok.Data;

@Data
class Address {
    private String street;
    private String city;
    private String postalCode;
    private String country;
}