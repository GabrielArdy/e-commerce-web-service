package com.mobile.mobile.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cart")
public class cart {
    @Id
    private String id;
    private String userId;
    private String status;
    private String timestamp;
    private List<cartItem> productList;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<cartItem> getproductList() {
        return productList;
    }

    public void setproductList(List<cartItem> productList) {
        this.productList = productList;
    }

}
