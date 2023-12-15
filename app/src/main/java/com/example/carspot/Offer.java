package com.example.carspot;

public class Offer {
    String id;
    String offeredCarName;
    String personName;

    public Offer(String id, String offeredCarName, String personName) {
        this.id = id;
        this.offeredCarName = offeredCarName;
        this.personName = personName;
    }

    public Offer() {
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOfferedCarName() {
        return offeredCarName;
    }

    public void setOfferedCarName(String offeredCarName) {
        this.offeredCarName = offeredCarName;
    }
}
