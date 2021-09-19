package com.example.gymmanagement.model;

public class UserResponse {
    private Integer idUser;
    private String userName;
    private String lastName;
    private String email;
    private String password;
    private String userType;
    private String plan;
//    private String status;

    public UserResponse(Integer idUser, String userName, String lastName, String email, String password, String userType, String plan) {
        this.idUser = idUser;
        this.userName = userName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.plan = plan;
    }

    public UserResponse() {
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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "idUser=" + idUser +
                ", userName='" + userName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", userType='" + userType + '\'' +
                ", plan='" + plan + '\'' +
                '}';
    }
}
