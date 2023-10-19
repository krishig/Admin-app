package com.krishigadmin.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Customer {
    @SerializedName("content")
    @Expose
    public ArrayList<Content> content;
    @SerializedName("pageNumber")
    @Expose
    public String pageNumber;
    @SerializedName("pageSize")
    @Expose
    public String pageSize;
    @SerializedName("totalElements")
    @Expose
    public String totalElements;
    @SerializedName("totalPages")
    @Expose
    public int totalPages;
    @SerializedName("lastPage")
    @Expose
    public Boolean lastPage;

    public class Content {

        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("fullName")
        @Expose
        public String fullName;
        @SerializedName("mobileNumber")
        @Expose
        public String mobileNumber;
        @SerializedName("gender")
        @Expose
        public String gender;
        @SerializedName("address")
        @Expose
        public ArrayList<Address> address;
        @SerializedName("customerCartResponseDto")
        @Expose
        public CustomerCartResponseDto customerCartResponseDto;
        @SerializedName("createdBy")
        @Expose
        public String createdBy;
        @SerializedName("createdDate")
        @Expose
        public String createdDate;
        @SerializedName("modifiedBy")
        @Expose
        public Object modifiedBy;
        @SerializedName("modifiedDate")
        @Expose
        public String modifiedDate;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
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

        public ArrayList<Address> getAddress() {
            return address;
        }

        public void setAddress(ArrayList<Address> address) {
            this.address = address;
        }

        public CustomerCartResponseDto getCustomerCartResponseDto() {
            return customerCartResponseDto;
        }

        public void setCustomerCartResponseDto(CustomerCartResponseDto customerCartResponseDto) {
            this.customerCartResponseDto = customerCartResponseDto;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public Object getModifiedBy() {
            return modifiedBy;
        }

        public void setModifiedBy(Object modifiedBy) {
            this.modifiedBy = modifiedBy;
        }

        public String getModifiedDate() {
            return modifiedDate;
        }

        public void setModifiedDate(String modifiedDate) {
            this.modifiedDate = modifiedDate;
        }
    }

    public class Address {

        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("customer")
        @Expose
        public String customer;
        @SerializedName("houseNumber")
        @Expose
        public String houseNumber;
        @SerializedName("streetName")
        @Expose
        public String streetName;
        @SerializedName("villageName")
        @Expose
        public String villageName;
        @SerializedName("district")
        @Expose
        public String district;
        @SerializedName("state")
        @Expose
        public String state;
        @SerializedName("block")
        @Expose
        public String block;
        @SerializedName("postalCode")
        @Expose
        public String postalCode;
        @SerializedName("createdBy")
        @Expose
        public String createdBy;
        @SerializedName("createdDate")
        @Expose
        public String createdDate;
        @SerializedName("modifiedBy")
        @Expose
        public Object modifiedBy;
        @SerializedName("modifiedDate")
        @Expose
        public String modifiedDate;
        @SerializedName("customerCartResponseDto")
        @Expose
        public Object customerCartResponseDto;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCustomer() {
            return customer;
        }

        public void setCustomer(String customer) {
            this.customer = customer;
        }

        public String getHouseNumber() {
            return houseNumber;
        }

        public void setHouseNumber(String houseNumber) {
            this.houseNumber = houseNumber;
        }

        public String getStreetName() {
            return streetName;
        }

        public void setStreetName(String streetName) {
            this.streetName = streetName;
        }

        public String getVillageName() {
            return villageName;
        }

        public void setVillageName(String villageName) {
            this.villageName = villageName;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
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

        public String getPostalCode() {
            return postalCode;
        }

        public void setPostalCode(String postalCode) {
            this.postalCode = postalCode;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public Object getModifiedBy() {
            return modifiedBy;
        }

        public void setModifiedBy(Object modifiedBy) {
            this.modifiedBy = modifiedBy;
        }

        public String getModifiedDate() {
            return modifiedDate;
        }

        public void setModifiedDate(String modifiedDate) {
            this.modifiedDate = modifiedDate;
        }

        public Object getCustomerCartResponseDto() {
            return customerCartResponseDto;
        }

        public void setCustomerCartResponseDto(Object customerCartResponseDto) {
            this.customerCartResponseDto = customerCartResponseDto;
        }
    }

    public class CustomerCartResponseDto {

        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("customerId")
        @Expose
        public String customerId;
        @SerializedName("createdBy")
        @Expose
        public String createdBy;
        @SerializedName("createdDate")
        @Expose
        public String createdDate;
        @SerializedName("modifiedBy")
        @Expose
        public Object modifiedBy;
        @SerializedName("modifiedDate")
        @Expose
        public String modifiedDate;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public Object getModifiedBy() {
            return modifiedBy;
        }

        public void setModifiedBy(Object modifiedBy) {
            this.modifiedBy = modifiedBy;
        }

        public String getModifiedDate() {
            return modifiedDate;
        }

        public void setModifiedDate(String modifiedDate) {
            this.modifiedDate = modifiedDate;
        }
    }


    public ArrayList<Content> getContent() {
        return content;
    }

    public void setContent(ArrayList<Content> content) {
        this.content = content;
    }

    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(String totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public Boolean getLastPage() {
        return lastPage;
    }

    public void setLastPage(Boolean lastPage) {
        this.lastPage = lastPage;
    }

}
