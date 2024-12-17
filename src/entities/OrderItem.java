package entities;

import java.math.BigDecimal;

public class OrderItem {

    private int id;
    private int orderID;
    private int productID;
    private int quantity;
    private BigDecimal price;

    OrderItem(){}

    public OrderItem(int orderID, int productID,
              int quantity, BigDecimal price){

        this.price = price;
        this.orderID = orderID;
        this.productID = productID;
        this.quantity = quantity;

    }

    public OrderItem(int id,int orderID, int productID,
                     int quantity, BigDecimal price){

        this.id = id;
        this.price = price;
        this.orderID = orderID;
        this.productID = productID;
        this.quantity = quantity;

    }

    public int GetID(){ return id; }
    public void SetID(int tID){ this.id = tID; };

    public BigDecimal GetPrice(){ return price; }
    public void SetPrice(BigDecimal tPrice) { this.price = tPrice; };

    public int GetOrderID(){
        return this.orderID;
    }
    public void SetOrderID(int tOrderID){
        this.orderID = tOrderID;
    }

    public int GetProductID(){
        return this.productID;
    }
    public void SetProductID(int tProductID){
        this.productID = tProductID;
    }

    public int GetQuantity(){
        return quantity;
    }
    public void SetQuantity(int tQuantity){
        this.quantity = tQuantity;
    }
}
