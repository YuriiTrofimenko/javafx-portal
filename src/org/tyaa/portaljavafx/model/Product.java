/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tyaa.portaljavafx.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author student
 */
public class Product {
    
    private IntegerProperty id;
    private StringProperty title;
    private DoubleProperty price;

    public Product(String title, Double price) {
        this.title = new SimpleStringProperty(title);
        this.price = new SimpleDoubleProperty(price);
    }

    public StringProperty titleProperty() {
        return title;
    }

    public DoubleProperty priceProperty() {
        return price;
    }
}
