package com.example.salesapp.callbacks;

import com.example.salesapp.database.remote.model.SalesEntities;

public interface OnClickListener {
    void onClickEdit(SalesEntities sale);
    void onClickDelete(int id);
}
