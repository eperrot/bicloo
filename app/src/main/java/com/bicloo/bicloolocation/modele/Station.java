package com.bicloo.bicloolocation.modele;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Station {

    @SerializedName("number")
    @Expose
    private int number;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("position")
    @Expose
    private Position position;
    @SerializedName("banking")
    @Expose
    private boolean banking;
    @SerializedName("bonus")
    @Expose
    private boolean bonus;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("contract_name")
    @Expose
    private String contractName;
    @SerializedName("bike_stands")
    @Expose
    private int bikeStands;
    @SerializedName("available_bike_stands")
    @Expose
    private int availableBikeStands;
    @SerializedName("available_bikes")
    @Expose
    private int availableBikes;
    @SerializedName("last_update")
    @Expose
    private long lastUpdate;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean getBanking() {
        return banking;
    }

    public void setBanking(boolean banking) {
        this.banking = banking;
    }

    public boolean getBonus() {
        return bonus;
    }

    public void setBonus(boolean bonus) {
        this.bonus = bonus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public int getBikeStands() {
        return bikeStands;
    }

    public void setBikeStands(int bikeStands) {
        this.bikeStands = bikeStands;
    }

    public int getAvailableBikeStands() {
        return availableBikeStands;
    }

    public void setAvailableBikeStands(int availableBikeStands) {
        this.availableBikeStands = availableBikeStands;
    }

    public int getAvailableBikes() {
        return availableBikes;
    }

    public void setAvailableBikes(int availableBikes) {
        this.availableBikes = availableBikes;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

}