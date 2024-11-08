package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.ProductDTO;
import dto.SupplierDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import service.ServiceFactory;
import service.custom.ProductService;
import util.ServiceType;

import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;

public class StockManagementFormController implements Initializable {

    ProductService productService = ServiceFactory.getInstance().getServiceType(ServiceType.PRODUCT);

    @FXML
    public JFXButton btnSearch;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnRemove;

    @FXML
    private JFXComboBox<String> cmbProductId;

    @FXML
    private Label lblCategory;

    @FXML
    private Label lblCurrentQty;

    @FXML
    private Label lblProductName;

    @FXML
    private Label lblSize;

    @FXML
    private Label lblSupplierId;

    @FXML
    private Label lblSupplierId11;

    @FXML
    private Label lblSupplierId111;

    @FXML
    private Label lblUnitPrise;

    @FXML
    private JFXTextField txtUpdateQty;

    ProductDTO searchedProduct;

    @FXML
    void btnAddQtyOnAction(ActionEvent event) {
        Integer currentQty = searchedProduct.getQuantity();
        Integer updateQty = Integer.parseInt(txtUpdateQty.getText());
        Integer newQty = currentQty+updateQty;
        if (newQty<0){
            showAlert(ERROR,"Error","Error!","Invalid stock value.");
        }else{
            ProductDTO productDTO = new ProductDTO(
                    searchedProduct.getId(),
                    searchedProduct.getSupplier_id(),
                    searchedProduct.getProduct_name(),
                    searchedProduct.getSize(),
                    searchedProduct.getCategory(),
                    searchedProduct.getUnit_price(),
                    newQty
            );
            boolean b = productService.updateProduct(productDTO);
            if (b){
                showAlert(INFORMATION,"Success","Success!","Stock updated successfully.");
                clearFields();
            }else {
                showAlert(ERROR,"Error","Error!","Stock not updated.");
            }
        }
    }

    @FXML
    void btnRemoveQtyOnAction(ActionEvent event) {
        Integer currentQty = searchedProduct.getQuantity();
        Integer updateQty = Integer.parseInt(txtUpdateQty.getText());
        Integer newQty = currentQty-updateQty;
        if (updateQty>currentQty){
            showAlert(ERROR,"Error","Error!","Invalid stock value.");
        }else{
            ProductDTO productDTO = new ProductDTO(
                searchedProduct.getId(),
                searchedProduct.getSupplier_id(),
                searchedProduct.getProduct_name(),
                searchedProduct.getSize(),
                searchedProduct.getCategory(),
                searchedProduct.getUnit_price(),
                newQty
        );
            boolean b = productService.updateProduct(productDTO);
            if (b){
                showAlert(INFORMATION,"Success","Success!","Stock updated successfully.");
                clearFields();
            }else {
                showAlert(ERROR,"Error","Error!","Stock not updated.");
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<ProductDTO> productObservableList = productService.getAll();
        ObservableList<String> productIds = FXCollections.observableArrayList();
        productObservableList.forEach(products -> productIds.add(products.getId()));
        cmbProductId.setItems(productIds);
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        searchedProduct = productService.searchProduct(cmbProductId.getValue());
        if(null== searchedProduct){
            showAlert(ERROR,"Not found","Not found!","Search product not found.");
        }else {
            lblProductName.setText(searchedProduct.getProduct_name());
            lblSupplierId.setText(searchedProduct.getSupplier_id());
            lblCategory.setText(searchedProduct.getCategory());
            lblSize.setText(searchedProduct.getSize());
            lblUnitPrise.setText(searchedProduct.getUnit_price().toString());
            lblCurrentQty.setText(searchedProduct.getQuantity().toString());
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String header, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields(){
        lblProductName.setText("");
        lblSupplierId.setText("");
        lblCategory.setText("");
        lblSize.setText("");
        lblUnitPrise.setText("");
        lblCurrentQty.setText("");
        cmbProductId.setValue("");
        txtUpdateQty.setText("");
    }
}
