package com.example.gymmanagement.model;

public class PaymentResponse {
    private Integer paymentId;
    private Integer userId;
    private Integer status;
    private String name;
    private String lastName;

    public PaymentResponse(Integer paymentId, Integer userId, Integer status, String name, String lastName) {
        this.paymentId = paymentId;
        this.userId = userId;
        this.status = status;
        this.name = name;
        this.lastName = lastName;
    }

    public PaymentResponse() {

    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PaymentResponse{" +
                "paymentId=" + paymentId +
                ", userId=" + userId +
                ", status=" + status +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
