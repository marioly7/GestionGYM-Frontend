package com.example.gymmanagement.model;

public class User {
    private Integer idUser;
    private String userName;
    private String lastName;
    private String email;
    private String password;
    private Integer userTypeId;
    private Integer registrantId;
    private Integer planId;
    private String status;

    public User() {
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(Integer userTypeId) {
        this.userTypeId = userTypeId;
    }

    public Integer getRegistrantId() {
        return registrantId;
    }

    public void setRegistrantId(Integer registrantId) {
        this.registrantId = registrantId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", userName='" + userName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", userTypeId=" + userTypeId +
                ", registrantId=" + registrantId +
                ", planId=" + planId +
                ", status='" + status + '\'' +
                '}';
    }
}

