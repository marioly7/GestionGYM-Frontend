package com.example.gymmanagement.model;

public class Plan {
    private Integer planId;
    private String plan;
    private float price;
    private Integer status;

    public Plan(){

    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "Plan{" +
                "planId=" + planId +
                ", plan='" + plan + '\'' +
                ", price=" + price +
                ", status=" + status +
                '}';
    }
}
