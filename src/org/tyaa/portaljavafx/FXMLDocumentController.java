/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tyaa.portaljavafx;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.textfield.CustomTextField;
import org.tyaa.portaljavafx.model.Product;

/**
 *
 * @author student
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private TableView productsTableView;
    @FXML
    private TableColumn titleTableColumn;
    @FXML
    private TableColumn priceTableColumn;
    
    @FXML
    private CustomTextField titleCTextField;
    @FXML
    private CustomTextField priceCTextField;
    
    private ObservableList<Product> products;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        products = FXCollections.observableArrayList();
        titleTableColumn.setCellValueFactory(
                new PropertyValueFactory<Product, String>("title")
        );
        priceTableColumn.setCellValueFactory(
                new PropertyValueFactory<Product, String>("price")
        );
        productsTableView.setItems(products);
        
        //products.add(new Product("t1", 99.99));
        //products.add(new Product("t2", 59.99));
    }
    
    @FXML
    private void onAddProduct(){
        products.add(
                new Product(
                        titleCTextField.getText()
                        , Double.valueOf(priceCTextField.getText())
                )
        );
    }
    
}
