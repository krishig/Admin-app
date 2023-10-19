package com.krishigadmin.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StarLine {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("start_time")
    @Expose
    public String startTime;
    @SerializedName("market_name")
    @Expose
    public String marketName;
    @SerializedName("end_time")
    @Expose
    public String endTime;
    @SerializedName("numbers")
    @Expose
    public Numbers numbers;
    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("currentDay")
    @Expose
    public String currentDay;
    @SerializedName("color")
    @Expose
    public String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getCurrentDay() {
        return currentDay;
    }

    public void setCurrentDay(String currentDay) {
        this.currentDay = currentDay;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }



    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Numbers getNumbers() {
        return numbers;
    }

    public void setNumbers(Numbers numbers) {
        this.numbers = numbers;
    }

    public class Numbers {
        @SerializedName("number1")
        @Expose
        public String number1;
        @SerializedName("number2")
        @Expose
        public String number2;
        @SerializedName("number3")
        @Expose
        public String number3;
        @SerializedName("number4")
        @Expose
        public String number4;

        public String getNumber1() {
            return number1;
        }

        public void setNumber1(String number1) {
            this.number1 = number1;
        }

        public String getNumber2() {
            return number2;
        }

        public void setNumber2(String number2) {
            this.number2 = number2;
        }

        public String getNumber3() {
            return number3;
        }

        public void setNumber3(String number3) {
            this.number3 = number3;
        }

        public String getNumber4() {
            return number4;
        }

        public void setNumber4(String number4) {
            this.number4 = number4;
        }
    }
}
