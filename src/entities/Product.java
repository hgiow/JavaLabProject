package entities;

import java.math.BigDecimal;

public class Product {

    private int id;
    private String name;
    private String description;
    private BigDecimal price;
    private int quantity;

    public Product() {};

    public Product(int id, String name, String description, BigDecimal price, int quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public Product(String name, String description, BigDecimal price, int quantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public int GetID() { return id; }
    public void SetID(int id) { this.id = id; }

    public String GetName() { return name; }
    public void SetName(String name) { this.name = name; }

    public String GetDescription() { return description; }
    public void SetDescription(String description) { this.description = description; }

    public BigDecimal GetPrice() { return price; }
    public void SetPrice(BigDecimal price) { this.price = price; }

    public int GetQuantity() { return quantity; }
    public void SetQuantity(int quantity) { this.quantity = quantity; }
}