package com.example.salesapp.callbacks;

import com.example.salesapp.database.remote.model.SalesEntities;

public interface OnSaleActionListener {
    void onSaleAdd(SalesEntities sale);
    void onSaleUpdate(SalesEntities sale);
}
