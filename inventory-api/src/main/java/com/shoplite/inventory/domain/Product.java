package com.shoplite.inventory.domain;

/**
 * Modèle métier minimal côté stock.
 * Pour l’instant : en mémoire, sans JPA.
 */
public class Product {

    private Long id;       // identifiant technique
    private String name;   // nom produit
    private int stock;     // quantité disponible

    public Product() { }

    public Product(Long id, String name, int stock) {
        this.id = id;
        this.name = name;
        this.stock = stock;
    }

    // -- getters / setters --
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
}
