package cn.fuelteam.watt.longpolling.common;

import org.fluttercode.datafactory.impl.DataFactory;

public class Payload {

    private final DataFactory DATAFACTORY = new DataFactory();

    private String address;
    private String city;
    private String email;
    private String businessName;
    private String name;

    public Payload() {
        this.address = DATAFACTORY.getAddress();
        this.city = DATAFACTORY.getCity();
        this.email = DATAFACTORY.getEmailAddress();
        this.businessName = DATAFACTORY.getBusinessName();
        this.name = DATAFACTORY.getName();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}