package com.example.gymmanagement.model;

public class UserActivity {
    Integer userId, activityId, userActivityId;
    public Integer getUserActivityId() {
        return userActivityId;
    }

    public void setUserActivityId(Integer userActivityId) {
        this.userActivityId = userActivityId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }


    @Override
    public String toString() {
        return "UserActivity{" +
                "userId=" + userId +
                ", activityId=" + activityId +
                ", userActivityId=" + userActivityId +
                '}';
    }
}
