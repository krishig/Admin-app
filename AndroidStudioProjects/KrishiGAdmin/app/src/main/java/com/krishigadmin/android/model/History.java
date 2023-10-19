package com.krishigadmin.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class History {

    String id;
    String partName;
    String partCode;
    String category;
    String uploadDocument;
    String action;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getPartCode() {
        return partCode;
    }

    public void setPartCode(String partCode) {
        this.partCode = partCode;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUploadDocument() {
        return uploadDocument;
    }

    public void setUploadDocument(String uploadDocument) {
        this.uploadDocument = uploadDocument;
    }

    public History(String id, String partName, String partCode, String category, String uploadDocument, String action) {
        this.id = id;
        this.partName = partName;
        this.partCode = partCode;
        this.category = category;
        this.uploadDocument = uploadDocument;
        this.action = action;
    }
}
