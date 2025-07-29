/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Timestamp;

/**
 *
 * @author Admin
 */
public class DiscountCode {
     private int id;
    private String code;
    private String description;
    private String discountType;      // "Percentage" hoặc "FixedAmount"
    private double discountValue;
    private Double minOrderAmount;    // Có thể null
    private Timestamp startDate;
    private Timestamp endDate;
    private Integer quantity;         // Có thể null
    private int used;
    private boolean isActive;
    private Timestamp createdAt;
    
    // Formatted strings for HTML input[type="datetime-local"]
    private String startDateFormatted;
    private String endDateFormatted;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(double discountValue) {
        this.discountValue = discountValue;
    }

    public Double getMinOrderAmount() {
        return minOrderAmount;
    }

    public void setMinOrderAmount(Double minOrderAmount) {
        this.minOrderAmount = minOrderAmount;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public int getUsed() {
        return used;
    }

    public void setUsed(int used) {
        this.used = used;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

   public String getStartDateFormatted() { return startDateFormatted; }
    public void setStartDateFormatted(String startDateFormatted) { this.startDateFormatted = startDateFormatted; }

    public String getEndDateFormatted() { return endDateFormatted; }
    public void setEndDateFormatted(String endDateFormatted) { this.endDateFormatted = endDateFormatted; }
    
    
}
