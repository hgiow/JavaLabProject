package entities;

public class OrderItem {

    private int orderID;
    private int productID;
    private int quantity;

    OrderItem(){}

    OrderItem(int tOrderID, int tProductID,
              int tQuantity){

        this.orderID = tOrderID;
        this.productID = tProductID;
        this.quantity = tQuantity;

    }

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
