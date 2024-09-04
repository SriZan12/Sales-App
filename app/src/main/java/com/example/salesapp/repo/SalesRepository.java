package com.example.salesapp.repo;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.salesapp.database.remote.model.SalesEntities;
import com.example.salesapp.database.remote.room.SaleDao;
import com.example.salesapp.database.remote.room.SalesDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Utilizes ExecutorService for asynchronous database operations.
 */


public class SalesRepository {
    private final SaleDao saleDao;
    private final ExecutorService executorService;

    public SalesRepository(Context context) {
        SalesDatabase db = SalesDatabase.getInstance(context);
        saleDao = db.saleDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insertSale(SalesEntities sale) {
        executorService.execute(() -> saleDao.insert(sale));
    }

    public LiveData<Double> getMonthlySales(String month) {
        return saleDao.getMonthlySales(month);
    }

    public LiveData<List<SalesEntities>> getSalesBetweenDates() {
        return saleDao.getSalesBetweenDates();
    }

    public void deleteSaleById(int id) {
        executorService.execute(() -> saleDao.deleteSaleById(id));
    }
}


