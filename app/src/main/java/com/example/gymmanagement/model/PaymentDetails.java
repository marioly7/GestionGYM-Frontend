package com.example.gymmanagement.model;

public class PaymentDetails {
    Integer paymentId;
    Integer userId;
    Integer planId;
    Integer status;
    String userName;
    String plan;
    float price;
    String fecha;

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "PaymentDetails{" +
                "paymentId=" + paymentId +
                ", userId=" + userId +
                ", planId=" + planId +
                ", status=" + status +
                ", userName='" + userName + '\'' +
                ", plan='" + plan + '\'' +
                ", price='" + price + '\'' +
                ", fecha='" + fecha + '\'' +
                '}';
    }
}
