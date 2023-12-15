package com.example.carspot;

public class Car {

    String name, year, reach, power, description;


    public Car(String name, String year, String reach, String power, String description) {
        this.name = name;

        this.year = year;
        this.reach = reach;
        this.power = power;
        this.description = description;
    }

    public Car(){}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getReach() {
        return reach;
    }

    public void setReach(String reach) {
        this.reach = reach;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }
}
