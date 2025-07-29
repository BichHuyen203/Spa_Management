/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author PCASUS
 */
public class CustomerBehavior {
    private String customerName;
    private int feedbackCount;
    private int supportRequestCount;

    // Getters và Setters
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public int getFeedbackCount() { return feedbackCount; }
    public void setFeedbackCount(int feedbackCount) { this.feedbackCount = feedbackCount; }
    public int getSupportRequestCount() { return supportRequestCount; }
    public void setSupportRequestCount(int supportRequestCount) { this.supportRequestCount = supportRequestCount; }
}