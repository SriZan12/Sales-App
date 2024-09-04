package com.example.salesapp.database.remote.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Upsert;

import com.example.salesapp.database.remote.model.SalesEntities;

import java.util.List;

@Dao
public interface SaleDao {
    @Upsert
    void insert(SalesEntities sale);

    @Query("SELECT * FROM sales_table")
    LiveData<List<SalesEntities>> getSalesBetweenDates();

    @Query("SELECT COALESCE(SUM(product_amount), 0.0) FROM sales_table WHERE strftime('%Y-%m', date_string) = :month")
    LiveData<Double> getMonthlySales(String month);

    @Query("DELETE FROM sales_table WHERE id = :saleId")
    void deleteSaleById(int saleId);

}
