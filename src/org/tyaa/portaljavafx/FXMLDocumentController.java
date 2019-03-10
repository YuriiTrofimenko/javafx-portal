/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tyaa.portaljavafx;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.tools.ValueExtractor;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationMessage;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
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
    
    private ValidationSupport validationSupport;
    private Boolean validationDecorationActivated;
    private Collection<ValidationMessage> validationMessages;
    
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
        validationDecorationActivated = false;
        
        ValueExtractor.addObservableValueExtractor(
                c -> c instanceof CustomTextField,
                c -> ((CustomTextField) c).textProperty());
        validationSupport = new ValidationSupport();
        validationSupport.registerValidator(
                titleCTextField
                , Validator.createEmptyValidator("Title is empty"));
        validationSupport.registerValidator(
                priceCTextField
                , Validator.createRegexValidator(
                        "Format is not #####.00"
                        , "[0-9]{1,5}[\\.][0-9]{2}"
                        , Severity.ERROR)
        );
        
        ChangeListener<Boolean> changeListener =
                new ChangeListener<Boolean>(){
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        if (newValue == false) {
                            //System.out.println("changed");
                            
                            if (!validationDecorationActivated) {
                                validationSupport.initInitialDecoration();
                                validationDecorationActivated = true;
                            }
                            
                            ValidationResult validationResult =
                                validationSupport.getValidationResult();
                            validationMessages =
                                validationResult.getErrors();
                            /*for (ValidationMessage validationMessage : validationMessages) {
                                System.out.println(validationMessage.getText());
                            }*/
                        }
                    }
                };
        
        titleCTextField.focusedProperty().addListener(changeListener);
        priceCTextField.focusedProperty().addListener(changeListener);
    }
    
    @FXML
    private void onAddProduct(){
        
        if (validationMessages.size() > 0) {
            String erorrsSummary = "";
            for (ValidationMessage validationMessage : validationMessages) {
                //System.out.println(validationMessage.getText());
                erorrsSummary +=
                    (" "
                        + ((CustomTextField)validationMessage.getTarget()).getPromptText()
                        + ": "
                        + validationMessage.getText()
                        + ";"
                    );
            }
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Product not added");
            alert.setContentText(erorrsSummary);
            alert.showAndWait();
        } else {
            products.add(
                new Product(
                        titleCTextField.getText()
                        , Double.valueOf(priceCTextField.getText())
                )
            );
        }
    }
}
