package com.krishigadmin.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProductId {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("product_name")
    @Expose
    public String productName;
    @SerializedName("whole_sale_price")
    @Expose
    public String wholeSalePrice;
    @SerializedName("price")
    @Expose
    public String price;
    @SerializedName("quantity")
    @Expose
    public String quantity;
    @SerializedName("base_unit")
    @Expose
    public String baseUnit;
    @SerializedName("discount")
    @Expose
    public String discount;
    @SerializedName("sub_category_id")
    @Expose
    public String subCategoryId;
    @SerializedName("sub_category_name")
    @Expose
    public String subCategoryName;
    @SerializedName("brand_id")
    @Expose
    public String brandId;
    @SerializedName("brand_name")
    @Expose
    public String brandName;
    @SerializedName("product_description")
    @Expose
    public String productDescription;
    @SerializedName("product_image")
    @Expose
    public ArrayList<ProductImage> productImage;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getWholeSalePrice() {
        return wholeSalePrice;
    }

    public void setWholeSalePrice(String wholeSalePrice) {
        this.wholeSalePrice = wholeSalePrice;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getBaseUnit() {
        return baseUnit;
    }

    public void setBaseUnit(String baseUnit) {
        this.baseUnit = baseUnit;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public ArrayList<ProductImage> getProductImage() {
        return productImage;
    }

    public void setProductImage(ArrayList<ProductImage> productImage) {
        this.productImage = productImage;
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

    @SerializedName("total_pages")
    @Expose
    public String totalPages;
    @SerializedName("next_page")
    @Expose
    public String nextPage;
    @SerializedName("result")
    @Expose
    public Result result;

    public String getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(String totalPages) {
        this.totalPages = totalPages;
    }

    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public class Result {

        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("product_name")
        @Expose
        public String productName;
        @SerializedName("whole_sale_price")
        @Expose
        public String wholeSalePrice;
        @SerializedName("price")
        @Expose
        public int price;
        @SerializedName("quantity")
        @Expose
        public String quantity;
        @SerializedName("base_unit")
        @Expose
        public String baseUnit;
        @SerializedName("discount")
        @Expose
        public int discount;
        @SerializedName("sub_category_id")
        @Expose
        public String subCategoryId;
        @SerializedName("sub_category_name")
        @Expose
        public String subCategoryName;
        @SerializedName("brand_id")
        @Expose
        public String brandId;
        @SerializedName("brand_name")
        @Expose
        public String brandName;
        @SerializedName("product_description")
        @Expose
        public String productDescription;
        @SerializedName("product_image")
        @Expose
        public ArrayList<ProductImage> productImage;
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

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getWholeSalePrice() {
            return wholeSalePrice;
        }

        public void setWholeSalePrice(String wholeSalePrice) {
            this.wholeSalePrice = wholeSalePrice;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getBaseUnit() {
            return baseUnit;
        }

        public void setBaseUnit(String baseUnit) {
            this.baseUnit = baseUnit;
        }

        public int getDiscount() {
            return discount;
        }

        public void setDiscount(int discount) {
            this.discount = discount;
        }

        public String getSubCategoryId() {
            return subCategoryId;
        }

        public void setSubCategoryId(String subCategoryId) {
            this.subCategoryId = subCategoryId;
        }

        public String getSubCategoryName() {
            return subCategoryName;
        }

        public void setSubCategoryName(String subCategoryName) {
            this.subCategoryName = subCategoryName;
        }

        public String getBrandId() {
            return brandId;
        }

        public void setBrandId(String brandId) {
            this.brandId = brandId;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public String getProductDescription() {
            return productDescription;
        }

        public void setProductDescription(String productDescription) {
            this.productDescription = productDescription;
        }

        public ArrayList<ProductImage> getProductImage() {
            return productImage;
        }

        public void setProductImage(ArrayList<ProductImage> productImage) {
            this.productImage = productImage;
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

        public class ProductImage {

            @SerializedName("id")
            @Expose
            public String id;
            @SerializedName("image_url")
            @Expose
            public String imageUrl;
            @SerializedName("image_name")
            @Expose
            public String imageName;
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

            public String getImageUrl() {
                return imageUrl;
            }

            public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
            }

            public String getImageName() {
                return imageName;
            }

            public void setImageName(String imageName) {
                this.imageName = imageName;
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
        }



    }



    public class ProductImage {

        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("image_url")
        @Expose
        public String imageUrl;
        @SerializedName("image_name")
        @Expose
        public String imageName;
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

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getImageName() {
            return imageName;
        }

        public void setImageName(String imageName) {
            this.imageName = imageName;
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
    }
}
