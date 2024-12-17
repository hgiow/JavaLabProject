package entities;

import java.sql.Date;

public class Order {

    private int id;
    private int customerID;
    private Date createdDate;
    private Date closedDate;
    private String status;

    public Order(){}

    public Order(int customerID, Date createdDate,
          Date closedDate, String status){

        this.customerID = customerID;
        this.createdDate = createdDate;
        this.closedDate = closedDate;
        this.status = status;
    }

    public Order(int id, int customerID, Date createdDate,
                 Date closedDate, String status){

        this.customerID = customerID;
        this.createdDate = createdDate;
        this.closedDate = closedDate;
        this.status = status;
    }

    public int GetID() { return id; }
    public void SetID(int tID) { this.id = tID; }

    public int GetCustomerID() { return this.customerID; }
    public void SetCustomerID(int tCustomerID){
        this.customerID = tCustomerID;
    }

    public Date GetCreatedDate(){
        return this.createdDate;
    }
    public void SetCreatedDate(Date tCreatedDate){
        this.createdDate = tCreatedDate;
    }

    public Date GetClosedDate(){
        return this.closedDate;
    }
    public void SetClosedDate(Date tClosedDate){
        this.closedDate = tClosedDate;
    }

    public String GetStatus(){
        return this.status;
    }
    public void SetStatus(String tStatus){
        this.status = tStatus;
    }
}
