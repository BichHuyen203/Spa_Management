/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Date;

public class User {

    private int id;
    private String email;
    private String password;
    private String fullname;
    private String gender;
    private String address;
    private String phone;
    private boolean isDeleted;
    private Date createdAt;
    private int createdBy;
    private String avatar;
    private String changeHistory;
    private int roleId;
    private String roleString;
    private int loyaltyPoints;

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }
    
    public String getRoleString() {
        return roleString;
    }

    public void setRoleString(String roleString) {
        this.roleString = roleString;
    }
    
    

    public User(int id, String email, String password, String fullname, String gender, String address, String phone, boolean isDeleted, Date createdAt, int createdBy, String avatar, String changeHistory, int roleId) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.avatar = avatar;
        this.changeHistory = changeHistory;
        this.roleId = roleId;
    }

    public User() {
    }
    
    

    public String getChangeHistory() {
        return changeHistory;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public void setChangeHistory(String changeHistory) {
        this.changeHistory = changeHistory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", email=" + email + ", password=" + password + ", fullname=" + fullname + ", gender=" + gender + ", address=" + address + ", phone=" + phone + ", isDeleted=" + isDeleted + ", createdAt=" + createdAt + ", createdBy=" + createdBy + ", avatar=" + avatar + ", changeHistory=" + changeHistory + ", roleId=" + roleId + ", roleString=" + roleString + '}';
    }
    
    

}
