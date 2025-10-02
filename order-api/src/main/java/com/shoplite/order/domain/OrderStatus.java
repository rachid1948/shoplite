package com.shoplite.order.domain;

public enum OrderStatus {
    NEW,       // créée et stock réservé
    REJECTED   // refusée (ex: stock insuffisant)
}
