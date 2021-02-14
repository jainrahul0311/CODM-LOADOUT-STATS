package com.rahul.codmloadoutstats.user;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity(name = "Users")
public class UserDAO {
    @Id
    private long userId;
    private String userName;
    private String userIgn;
    private Date createDate;
    private boolean addAccess;
    private boolean viewAccess;
    private boolean suAccess;

    public UserDAO() {
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserIgn() {
        return userIgn;
    }

    public void setUserIgn(String userIgn) {
        this.userIgn = userIgn;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public boolean isAddAccess() {
        return addAccess;
    }

    public void setAddAccess(boolean addAccess) {
        this.addAccess = addAccess;
    }

    public boolean isViewAccess() {
        return viewAccess;
    }

    public void setViewAccess(boolean viewAccess) {
        this.viewAccess = viewAccess;
    }

    public boolean isSuAccess() {
        return suAccess;
    }

    public void setSuAccess(boolean suAccess) {
        this.suAccess = suAccess;
    }

    @Override
    public String toString() {
        return "UserDAO{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userIgn='" + userIgn + '\'' +
                ", createDate=" + createDate +
                ", addAccess=" + addAccess +
                ", viewAccess=" + viewAccess +
                ", suAccess=" + suAccess +
                '}';
    }
}
