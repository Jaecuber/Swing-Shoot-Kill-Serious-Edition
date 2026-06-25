package com.github.Jaecuber.swingShootKill.data;

public class UpgradeEntry {
    private String name;
    private String rarity;
    private String description;
    private int price;
    
    public UpgradeEntry(){};
    public UpgradeEntry(String name, String rarity, String description, int price) {
        this.name = name;
        this.rarity = rarity;
        this.description = description;
        this.price = price;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getRarity() {
        return rarity;
    }
    public void setRarity(String rarity) {
        this.rarity = rarity;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
}
