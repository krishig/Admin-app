package com.krishigadmin.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PostalPincodeResponse {
    @SerializedName("Message")
    @Expose
    private String message;

    @SerializedName("PostOffice")
    @Expose
    private ArrayList<PostOffice> postOffice;

    // Getters and setters
    // ...

    public class PostOffice {
        @SerializedName("Name")
        private String name;

        @SerializedName("Description")
        @Expose
        public Object description;
        @SerializedName("BranchType")
        @Expose
        public String branchType;
        @SerializedName("DeliveryStatus")
        @Expose
        public String deliveryStatus;
        @SerializedName("Circle")
        @Expose
        public String circle;
        @SerializedName("District")
        @Expose
        public String district;
        @SerializedName("Division")
        @Expose
        public String division;
        @SerializedName("Region")
        @Expose
        public String region;
        @SerializedName("Block")
        @Expose
        public String block;
        @SerializedName("State")
        @Expose
        public String state;
        @SerializedName("Country")
        @Expose
        public String country;
        @SerializedName("Pincode")
        @Expose
        public String pincode;

        // Other fields as required

        // Getters and setters
        // ...


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getDescription() {
            return description;
        }

        public void setDescription(Object description) {
            this.description = description;
        }

        public String getBranchType() {
            return branchType;
        }

        public void setBranchType(String branchType) {
            this.branchType = branchType;
        }

        public String getDeliveryStatus() {
            return deliveryStatus;
        }

        public void setDeliveryStatus(String deliveryStatus) {
            this.deliveryStatus = deliveryStatus;
        }

        public String getCircle() {
            return circle;
        }

        public void setCircle(String circle) {
            this.circle = circle;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getDivision() {
            return division;
        }

        public void setDivision(String division) {
            this.division = division;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getBlock() {
            return block;
        }

        public void setBlock(String block) {
            this.block = block;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getPincode() {
            return pincode;
        }

        public void setPincode(String pincode) {
            this.pincode = pincode;
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<PostOffice> getPostOffice() {
        return postOffice;
    }

    public void setPostOffice(ArrayList<PostOffice> postOffice) {
        this.postOffice = postOffice;
    }
}