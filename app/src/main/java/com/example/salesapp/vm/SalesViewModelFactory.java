package com.example.salesapp.vm;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.salesapp.repo.SalesRepository;

public class SalesViewModelFactory implements ViewModelProvider.Factory {
    private final SalesRepository repository;

    public SalesViewModelFactory(SalesRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SalesViewModel.class)) {
            return (T) new SalesViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
