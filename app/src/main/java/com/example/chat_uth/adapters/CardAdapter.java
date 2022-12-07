package com.example.chat_uth.adapters;

import androidx.cardview.widget.CardView;

// INTERFACE Y DOCUMENTACION DE ANDROID
public interface CardAdapter {

    public final int MAX_ELEVATION_FACTOR = 8;

    float getBaseElevation();

    CardView getCardViewAt(int position);

    int getCount();
}

