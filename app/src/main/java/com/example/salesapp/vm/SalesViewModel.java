package com.example.salesapp.vm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.salesapp.database.remote.model.SalesEntities;
import com.example.salesapp.repo.SalesRepository;

import java.util.List;

public class SalesViewModel extends ViewModel {
    private final SalesRepository repository;

    public SalesViewModel(SalesRepository repository) {
        this.repository = repository;
    }

    public void insertSale(SalesEntities sale) {
        repository.insertSale(sale);
    }

    public LiveData<Double> getMonthlySales(String month) {
        return repository.getMonthlySales(month);
    }


    public LiveData<List<SalesEntities>> getSalesBetweenDates() {
        return repository.getSalesBetweenDates();
    }

    public void deleteSaleById(int id) {
        repository.deleteSaleById(id);
    }
}
