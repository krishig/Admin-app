package com.krishigadmin.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MainModule {

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @SerializedName("number")
    @Expose
    String number;
    @SerializedName("page")
    @Expose
    String page;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public MainModule(String number) {
        this.number = number;
    }

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("market_type")
    @Expose
    public String marketType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMarketType() {
        return marketType;
    }

    public void setMarketType(String marketType) {
        this.marketType = marketType;
    }

    @SerializedName("ink")
    @Expose
    public ArrayList<Ink> ink;
    @SerializedName("pana")
    @Expose
    public ArrayList<Pana> pana;

    public ArrayList<Ink> getInk() {
        return ink;
    }

    public void setInk(ArrayList<Ink> ink) {
        this.ink = ink;
    }

    public ArrayList<Pana> getPana() {
        return pana;
    }

    public void setPana(ArrayList<Pana> pana) {
        this.pana = pana;
    }

    public class Pana {

        @SerializedName("number")
        @Expose
        public String number;

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }
    }

    public class Ink {

        @SerializedName("number")
        @Expose
        public String number;

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }
    }

    @SerializedName("open_pana")
    @Expose
    public ArrayList<Ink> openPana;
    @SerializedName("close_pana")
    @Expose
    public ArrayList<Ink> closePana;

    public ArrayList<Ink> getOpenPana() {
        return openPana;
    }

    public void setOpenPana(ArrayList<Ink> openPana) {
        this.openPana = openPana;
    }

    public ArrayList<Ink> getClosePana() {
        return closePana;
    }

    public void setClosePana(ArrayList<Ink> closePana) {
        this.closePana = closePana;
    }
}
