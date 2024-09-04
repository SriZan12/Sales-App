package com.example.salesapp.database.remote.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.salesapp.database.converter.DateConverter;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "sales_table")
public class SalesEntities implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int ID;

    @ColumnInfo(name = "product_quantity")
    private String productQuantity = null;

    @ColumnInfo(name = "product_amount")
    private Double productAmount = null;

//    @TypeConverters(DateConverter.class)
//    @ColumnInfo(name = "date")
//    private Date date = null;

    @ColumnInfo(name = "date_string")
    private String dateString = null; // New field

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public Double getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(Double productAmount) {
        this.productAmount = productAmount;
    }

//    public Date getDate() {
//        return date;
//    }
//
//    public void setDate(Date date) {
//        this.date = date;
//    }
}