/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import DAO.CategoryDAO;
import DAO.RoomDAO;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Admin
 */
public class SpaService {
    private int id;
    private String name;
    private String description;
    private int durationMinutes;
    private BigDecimal price;
    private boolean isActive;
    private int categoryId;
    private String image;
    private Category category;
    
        private List<Room> rooms;

    

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
    
    public List<Room> getRooms() {
        return new RoomDAO().getRoomsBySpaServiceId(id);
    }

    public SpaService() {
    }

    public SpaService(int id, String name, String description, int durationMinutes, BigDecimal price, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.durationMinutes = durationMinutes;
        this.price = price;
        this.image = image;
    }
    public SpaService(int id, String name, String description, int durationMinutes, BigDecimal price, boolean isActive, int categoryId, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.durationMinutes = durationMinutes;
        this.price = price;
        this.isActive = isActive;
        this.categoryId = categoryId;
        this.image = image;
    }

    // Getters and setters

    public Category getCategory() {
        return new CategoryDAO().getCategoriesById(categoryId);
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "SpaService{" + "id=" + id + ", name=" + name + ", description=" + description + ", durationMinutes=" + durationMinutes + ", price=" + price + ", isActive=" + isActive + ", categoryId=" + categoryId + ", image=" + image + ", category=" + category + '}';
    }
    
    
}
