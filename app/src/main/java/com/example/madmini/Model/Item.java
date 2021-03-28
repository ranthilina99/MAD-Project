package com.example.madmini.Model;

public class Item {

    private String pid;
    private String ItemName;
    private String petCategory;
    private String ItemType;
    private String Brand;
    private  String Price;
    private String status;
    private String description;


    private String Date;
    private String  Time;

    private  String Image;

    public Item() {
    }


    public Item(String pid, String itemName, String petCategory, String itemType, String brand, String price, String status, String description, String date, String time, String image) {
        this.pid = pid;
        ItemName = itemName;
        this.petCategory = petCategory;
        ItemType = itemType;
        Brand = brand;
        Price = price;
        this.status = status;
        this.description = description;
        Date = date;
        Time = time;
        Image = image;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }


    public String getPetCategory() {
        return petCategory;
    }

    public void setPetCategory(String petCategory) {
        this.petCategory = petCategory;
    }

    public String getItemType() {
        return ItemType;
    }

    public void setItemType(String itemType) {
        ItemType = itemType;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
