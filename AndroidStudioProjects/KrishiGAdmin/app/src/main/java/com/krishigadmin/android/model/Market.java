package com.krishigadmin.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Market {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("market_name")
    @Expose
    public String marketName;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("color")
    @Expose
    public String color;
    @SerializedName("is_open")
    @Expose
    public Boolean isOpen;
    @SerializedName("is_holiday")
    @Expose
    public String is_holiday;
    @SerializedName("open_time")
    @Expose
    public String openTime;
    @SerializedName("close_time")
    @Expose
    public String closeTime;
    @SerializedName("number")
    @Expose
    public String number;

    @SerializedName("open")
    @Expose
    public String opens;
    @SerializedName("close")
    @Expose
    public String close;
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("on_wallet")
    @Expose
    public String on_wallet;
    @SerializedName("total_point")
    @Expose
    public String total_point;

    public String getTotal_point() {
        return total_point;
    }

    public void setTotal_point(String total_point) {
        this.total_point = total_point;
    }

    public String getOn_wallet() {
        return on_wallet;
    }

    public void setOn_wallet(String on_wallet) {
        this.on_wallet = on_wallet;
    }

    public String getIs_holiday() {
        return is_holiday;
    }

    public void setIs_holiday(String is_holiday) {
        this.is_holiday = is_holiday;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOpens() {
        return opens;
    }

    public void setOpens(String opens) {
        this.opens = opens;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
