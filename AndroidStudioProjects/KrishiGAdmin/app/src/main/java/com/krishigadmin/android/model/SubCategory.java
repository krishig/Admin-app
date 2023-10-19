package com.krishigadmin.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SubCategory {





    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("sub_category_name")
    @Expose
    public String subCategoryName;
    @SerializedName("total_pages")
    @Expose
    public Integer total_pages;
    @SerializedName("sub_category_image")
    @Expose
    public String subCategoryImage;
    @SerializedName("category_name")
    @Expose
    public String categoryName;
    @SerializedName("image_url")
    @Expose
    public String image_url;
    @SerializedName("result")
    @Expose
    public ArrayList<Result> resultArrayList;

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public ArrayList<Result> getResultArrayList() {
        return resultArrayList;
    }

    public void setResultArrayList(ArrayList<Result> resultArrayList) {
        this.resultArrayList = resultArrayList;
    }

    public Integer getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(Integer total_pages) {
        this.total_pages = total_pages;
    }

    public class Result{
        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("sub_category_name")
        @Expose
        public String subCategoryName;
        @SerializedName("sub_category_image")
        @Expose
        public String subCategoryImage;
        @SerializedName("category_name")
        @Expose
        public String categoryName;
        @SerializedName("image_url")
        @Expose
        public String image_url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSubCategoryName() {
            return subCategoryName;
        }

        public void setSubCategoryName(String subCategoryName) {
            this.subCategoryName = subCategoryName;
        }

        public String getSubCategoryImage() {
            return subCategoryImage;
        }

        public void setSubCategoryImage(String subCategoryImage) {
            this.subCategoryImage = subCategoryImage;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public String getSubCategoryImage() {
        return subCategoryImage;
    }

    public void setSubCategoryImage(String subCategoryImage) {
        this.subCategoryImage = subCategoryImage;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }






    //Brands
    @SerializedName("brand_name")
    @Expose
    public String brandName;
    @SerializedName("brand_image_url")
    @Expose
    public String brandImageUrl;

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
