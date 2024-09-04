package com.example.salesapp.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;

import com.example.salesapp.callbacks.OnSaleActionListener;
import com.example.salesapp.database.remote.model.SalesEntities;
import com.example.salesapp.databinding.DialogAddSaleBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DialogUtils {

    @SuppressLint("SetTextI18n")
    public static void showAddSaleDialog(boolean isUpdate, SalesEntities existingSale, Context context, DialogAddSaleBinding dialogBinding, OnSaleActionListener callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        if (dialogBinding.getRoot().getParent() != null) {
            ((ViewGroup) dialogBinding.getRoot().getParent()).removeView(dialogBinding.getRoot());
        }

        builder.setView(dialogBinding.getRoot());

        AlertDialog dialog = builder.create();

        if (isUpdate && existingSale != null) {
            dialogBinding.editTextQuantity.setText(String.valueOf(existingSale.getProductQuantity()));
            dialogBinding.editTextAmount.setText(String.valueOf(existingSale.getProductAmount()));
            dialogBinding.buttonAddSale.setText("Update");
        }

        dialogBinding.buttonAddSale.setOnClickListener(v -> {
            String quantityStr = dialogBinding.editTextQuantity.getText().toString().trim();
            String amountStr = dialogBinding.editTextAmount.getText().toString().trim();

            if (validateSaleForm(dialogBinding)) {
                int quantity = Integer.parseInt(quantityStr);
                double amount = Double.parseDouble(amountStr);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String formattedDate = sdf.format(new Date());

                SalesEntities sale = new SalesEntities();
                sale.setProductAmount(amount);
                sale.setProductQuantity(String.valueOf(quantity));
                sale.setDateString(formattedDate);

                if (isUpdate && existingSale != null) {
                    sale.setID(existingSale.getID());
                    callback.onSaleUpdate(sale);
                } else {
                    callback.onSaleAdd(sale);
                }

                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private static boolean validateSaleForm(DialogAddSaleBinding dialogBinding) {
        String quantityStr = dialogBinding.editTextQuantity.getText().toString().trim();
        String amountStr = dialogBinding.editTextAmount.getText().toString().trim();

        if (quantityStr.isEmpty()) {
            dialogBinding.editTextQuantity.setError("Quantity is required");
            dialogBinding.editTextQuantity.requestFocus();
            return false;
        }

        if (amountStr.isEmpty()) {
            dialogBinding.editTextAmount.setError("Amount is required");
            dialogBinding.editTextAmount.requestFocus();
            return false;
        }

        try {
            int quantity = Integer.parseInt(quantityStr);
            if (quantity <= 0) {
                dialogBinding.editTextQuantity.setError("Quantity must be greater than zero");
                dialogBinding.editTextQuantity.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            dialogBinding.editTextQuantity.setError("Invalid quantity");
            dialogBinding.editTextQuantity.requestFocus();
            return false;
        }

        try {
            double amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                dialogBinding.editTextAmount.setError("Amount must be greater than zero");
                dialogBinding.editTextAmount.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            dialogBinding.editTextAmount.setError("Invalid amount");
            dialogBinding.editTextAmount.requestFocus();
            return false;
        }

        return true;
    }


}
