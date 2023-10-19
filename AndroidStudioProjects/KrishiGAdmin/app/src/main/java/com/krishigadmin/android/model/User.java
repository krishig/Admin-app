package com.krishigadmin.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class User {
    @SerializedName("total_pages")
    @Expose
    public Integer total_pages;
    @SerializedName("result")
    @Expose
    ArrayList<result> arrayList;

    public Integer getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(Integer total_pages) {
        this.total_pages = total_pages;
    }

    public class result {
        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("username")
        @Expose
        public String username;
        @SerializedName("fullname")
        @Expose
        public String fullname;
        @SerializedName("email")
        @Expose
        public String email;
        @SerializedName("mobile_number")
        @Expose
        public String mobileNumber;
        @SerializedName("gender")
        @Expose
        public String gender;
        @SerializedName("adhaar_number")
        @Expose
        public String adhaarNumber;
        @SerializedName("password")
        @Expose
        public String password;
        @SerializedName("House_number")
        @Expose
        public String houseNumber;
        @SerializedName("landmark")
        @Expose
        public String landmark;
        @SerializedName("pincode")
        @Expose
        public String pincode;
        @SerializedName("city")
        @Expose
        public String city;
        @SerializedName("district")
        @Expose
        public String district;
        @SerializedName("state")
        @Expose
        public String state;
        @SerializedName("Role")
        @Expose
        public String role;
        @SerializedName("Role_name")
        @Expose
        public String roleName;
        @SerializedName("created_by")
        @Expose
        public String createdBy;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("modified_by")
        @Expose
        public String modifiedBy;
        @SerializedName("modified_at")
        @Expose
        public String modifiedAt;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobileNumber() {
            return mobileNumber;
        }

        public void setMobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getAdhaarNumber() {
            return adhaarNumber;
        }

        public void setAdhaarNumber(String adhaarNumber) {
            this.adhaarNumber = adhaarNumber;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getHouseNumber() {
            return houseNumber;
        }

        public void setHouseNumber(String houseNumber) {
            this.houseNumber = houseNumber;
        }

        public String getLandmark() {
            return landmark;
        }

        public void setLandmark(String landmark) {
            this.landmark = landmark;
        }

        public void setPincode(String pincode) {
            this.pincode = pincode;
        }

        public String getPincode() {
            return pincode;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getModifiedBy() {
            return modifiedBy;
        }

        public void setModifiedBy(String modifiedBy) {
            this.modifiedBy = modifiedBy;
        }

        public String getModifiedAt() {
            return modifiedAt;
        }

        public void setModifiedAt(String modifiedAt) {
            this.modifiedAt = modifiedAt;
        }

        @SerializedName("token")
        @Expose
        String token;

        @SerializedName("Name")
        @Expose
        public String name;
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
        @SerializedName("Division")
        @Expose
        public String division;
        @SerializedName("Region")
        @Expose
        public String region;
        @SerializedName("Block")
        @Expose
        public String block;
        @SerializedName("Country")
        @Expose
        public String country;


        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

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

    }

    public ArrayList<result> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<result> arrayList) {
        this.arrayList = arrayList;
    }

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("username")
    @Expose
    public String username;
    @SerializedName("fullname")
    @Expose
    public String fullname;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("mobile_number")
    @Expose
    public String mobileNumber;
    @SerializedName("gender")
    @Expose
    public String gender;
    @SerializedName("adhaar_number")
    @Expose
    public String adhaarNumber;
    @SerializedName("password")
    @Expose
    public String password;
    @SerializedName("House_number")
    @Expose
    public String houseNumber;
    @SerializedName("landmark")
    @Expose
    public String landmark;
    @SerializedName("pincode")
    @Expose
    public String pincode;
    @SerializedName("city")
    @Expose
    public String city;
    @SerializedName("district")
    @Expose
    public String district;
    @SerializedName("state")
    @Expose
    public String state;
    @SerializedName("Role")
    @Expose
    public String role;
    @SerializedName("Role_name")
    @Expose
    public String roleName;
    @SerializedName("created_by")
    @Expose
    public String createdBy;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("modified_by")
    @Expose
    public String modifiedBy;
    @SerializedName("modified_at")
    @Expose
    public String modifiedAt;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAdhaarNumber() {
        return adhaarNumber;
    }

    public void setAdhaarNumber(String adhaarNumber) {
        this.adhaarNumber = adhaarNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getPincode() {
        return pincode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(String modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    @SerializedName("token")
    @Expose
    String token;

    @SerializedName("Name")
    @Expose
    public String name;
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
    @SerializedName("Division")
    @Expose
    public String division;
    @SerializedName("Region")
    @Expose
    public String region;
    @SerializedName("Block")
    @Expose
    public String block;
    @SerializedName("Country")
    @Expose
    public String country;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

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


}
