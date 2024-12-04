package entities;

import java.math.BigDecimal;

public class Product {

    private String name;
    private String description;
    private BigDecimal price;
    private int quantity;

    Product(){}

    public Product(String tName, String tDescription,
            BigDecimal tPrice,int tQuantity){

        this.name = tName;
        this.description = tDescription;
        this.price = tPrice;
        this.quantity = tQuantity;

    }

    public String GetName(){
        return this.name;
    }
    public void SetName(String tName){
        this.name = tName;
    }

    public String GetDescription(){
        return this.description;
    }
    public void SetDescription(String tDescription){
        this.description = tDescription;
    }

    public BigDecimal GetPrice(){
        return this.price;
    }
    public void SetPrice(BigDecimal tPrice){
        this.price = tPrice;
    }

    public int GetQuantity() { return this.quantity; }
    public void SetQuantity(int tQuantity){
        this.quantity = tQuantity;
    }
}
