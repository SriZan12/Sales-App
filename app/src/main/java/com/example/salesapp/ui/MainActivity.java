package com.example.salesapp.ui;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.salesapp.R;
import com.example.salesapp.adapters.SalesAdapter;
import com.example.salesapp.callbacks.OnClickListener;
import com.example.salesapp.callbacks.OnSaleActionListener;
import com.example.salesapp.database.remote.model.SalesEntities;
import com.example.salesapp.databinding.ActivityMainBinding;
import com.example.salesapp.databinding.DialogAddSaleBinding;
import com.example.salesapp.databinding.DialogSalesSummaryBinding;
import com.example.salesapp.repo.SalesRepository;
import com.example.salesapp.utils.DateUtils;
import com.example.salesapp.utils.DialogUtils;
import com.example.salesapp.vm.SalesViewModel;
import com.example.salesapp.vm.SalesViewModelFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * The app is built on MVVM Architecture with JAVA.
 * It contains the single activity 'MainActivity'.
 * Room Database is used for CRUD operations on Sales.
 * */

public class MainActivity extends AppCompatActivity implements OnSaleActionListener {

    private ActivityMainBinding binding;
    private SalesAdapter adapter;
    private SalesViewModel salesViewModel;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    DialogSalesSummaryBinding dialogBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        initialize();
        setupRecyclerView();
        observeSalesList();
        setupListeners();
    }

    private void initialize() {
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("yyyy-MM", Locale.getDefault());

        SalesRepository repository = new SalesRepository(this);
        SalesViewModelFactory factory = new SalesViewModelFactory(repository);
        salesViewModel = new ViewModelProvider(this, factory).get(SalesViewModel.class);

        dialogBinding = DialogSalesSummaryBinding.inflate(getLayoutInflater());

        fetchAndDisplaySalesData(DateUtils.getCurrentMonth());
    }

    private void setupRecyclerView() {
        adapter = new SalesAdapter(onClickListener);
        binding.recyclerViewSales.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewSales.setAdapter(adapter);
    }

    private void observeSalesList() {
        salesViewModel.getSalesBetweenDates().observe(this, sales -> {
            Log.d("SALES LIST", "Sales size: " + sales.size());
            adapter.setSalesList(sales);
        });
    }

    private void setupListeners() {
        binding.infoIcon.setOnClickListener(v -> showSalesSummaryDialog());
        binding.fabAddSale.setOnClickListener(v -> showSaleDialog(false, null));
    }

    private void showSaleDialog(boolean isUpdate, SalesEntities existingSale) {
        DialogAddSaleBinding dialogBinding = DialogAddSaleBinding.inflate(getLayoutInflater());
        DialogUtils.showAddSaleDialog(isUpdate, existingSale, this, dialogBinding, this);
    }

    private void showSalesSummaryDialog() {

        if (dialogBinding.getRoot().getParent() != null) {
            ((ViewGroup) dialogBinding.getRoot().getParent()).removeView(dialogBinding.getRoot());
        }

        dialogBinding.buttonFilterDate.setOnClickListener(v -> {
            showDatePickerDialog();
        });

        new AlertDialog.Builder(this)
                .setTitle(R.string.sales_form)
                .setView(dialogBinding.getRoot())
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    dialog.dismiss();
                    fetchAndDisplaySalesData(DateUtils.getCurrentMonth());
                })
                .create()
                .show();
    }

    private void showDatePickerDialog() {
        new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    calendar.set(year, month, dayOfMonth);
                    String selectedMonth = dateFormat.format(calendar.getTime());

                    Log.d("SELECTED MONTH", selectedMonth);
                    fetchAndDisplaySalesData(selectedMonth);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    private void fetchAndDisplaySalesData(String currentMonth) {
        salesViewModel.getMonthlySales(currentMonth).observe(this, monthlySales -> {
            dialogBinding.textViewTotalAmount.setText("Total Amount: Rs." + monthlySales);
        });

    }

    @Override
    public void onSaleAdd(SalesEntities sale) {
        salesViewModel.insertSale(sale);
    }

    @Override
    public void onSaleUpdate(SalesEntities sale) {
        salesViewModel.insertSale(sale);
    }

    private final OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClickEdit(SalesEntities sale) {
            showSaleDialog(true, sale);
        }

        @Override
        public void onClickDelete(int id) {
            salesViewModel.deleteSaleById(id);
        }
    };
}
