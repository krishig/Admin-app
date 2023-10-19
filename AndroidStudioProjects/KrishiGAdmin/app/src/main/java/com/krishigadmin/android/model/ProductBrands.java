package com.krishigadmin.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProductBrands {


    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("brand_name")
    @Expose
    public String brandName;
    @SerializedName("brand_image_url")
    @Expose
    public String brandImageUrl;
    @SerializedName("total_pages")
    @Expose
    public int total_pages;
    @SerializedName("result")
    @Expose
    public ArrayList<Result> resultArrayList;

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandImageUrl() {
        return brandImageUrl;
    }

    public void setBrandImageUrl(String brandImageUrl) {
        this.brandImageUrl = brandImageUrl;
    }

    public ArrayList<Result> getResultArrayList() {
        return resultArrayList;
    }

    public void setResultArrayList(ArrayList<Result> resultArrayList) {
        this.resultArrayList = resultArrayList;
    }

    public class Result {

        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("brand_name")
        @Expose
        public String brandName;
        @SerializedName("brand_image_url")
        @Expose
        public String brandImageUrl;
        @SerializedName("result")
        @Expose
        public ArrayList<Result> resultArrayList;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public String getBrandImageUrl() {
            return brandImageUrl;
        }

        public void setBrandImageUrl(String brandImageUrl) {
            this.brandImageUrl = brandImageUrl;
        }


    }

}
