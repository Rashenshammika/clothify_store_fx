package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.ProductDTO;
import dto.SupplierDTO;
import dto.UserDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import service.ServiceFactory;
import service.custom.ProductService;
import service.custom.SupplierService;
import util.ServiceType;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;

public class ProductManagementFormController implements Initializable {

    ProductService productService = ServiceFactory.getInstance().getServiceType(ServiceType.PRODUCT);
    SupplierService supplierService = ServiceFactory.getInstance().getServiceType(ServiceType.SUPPLIER);

    @FXML
    private JFXButton btnAddProduct;

    @FXML
    private JFXButton btnDeleteProduct;

    @FXML
    private JFXButton btnSearchProduct;

    @FXML
    private JFXButton btnUpdateProduct;

    @FXML
    private JFXComboBox<String> cmbCategory;

    @FXML
    private JFXComboBox<String> cmbSize;

    @FXML
    private JFXComboBox<String> cmbSupplierId;

    @FXML
    private TableColumn<?, ?> colCategory;

    @FXML
    private TableColumn<?, ?> colProductId;

    @FXML
    private TableColumn<?, ?> colProductName;

    @FXML
    private TableColumn<?, ?> colSize;

    @FXML
    private TableColumn<?, ?> colSupplierId;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableView<ProductDTO> tblProduct;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtProductId;

    @FXML
    private JFXTextField txtUnitPrice;

    Integer quantity;

    @FXML
    void btnAddProductOnAction(ActionEvent event) {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/popup/add_product_popup_form.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.show();
    }

    @FXML
    void btnDeleteProductOnAction(ActionEvent event) {
        String productId = txtProductId.getText();

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Delete Confirmation");
        confirmationAlert.setHeaderText("Are you sure you want to delete this product?");
        confirmationAlert.setContentText("Product ID: " + productId);

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean isDeleted = productService.deleteUser(productId);

            if (isDeleted) {
                showAlert(INFORMATION, "Success", "Success!", "User deleted successfully.");
                loadTable();
            } else {
                showAlert(ERROR, "Error", "Error!", "User not deleted.");
            }
        } else {
            // If 'Cancel' is clicked, stop the process
            System.out.println("User deletion cancelled.");
        }
    }

    @FXML
    void btnReloadTableOnAction(ActionEvent event) {
        loadTable();
    }

    @FXML
    void btnSearchProductOnAction(ActionEvent event) {
        ProductDTO searchedProduct = productService.searchProduct(txtProductId.getText());
        if(null== searchedProduct){
            showAlert(ERROR,"Not found","Not found!","Search product not found.");
        }else {
            cmbSupplierId.setValue(searchedProduct.getSupplier_id());
            cmbSize.setValue(searchedProduct.getSize());
            cmbCategory.setValue(searchedProduct.getCategory());
            txtName.setText(searchedProduct.getProduct_name());
            txtUnitPrice.setText(searchedProduct.getUnit_price().toString());
        }
    }

    @FXML
    void btnUpdateProductOnAction(ActionEvent event) {
        ProductDTO productDTO = new ProductDTO(
                txtProductId.getText(),
                cmbSupplierId.getValue(),
                txtName.getText(),
                cmbSize.getValue(),
                cmbCategory.getValue(),
                Double.parseDouble(txtUnitPrice.getText()),
                quantity
        );
        boolean b = productService.updateProduct(productDTO);
        if (b){
            showAlert(INFORMATION,"Success","Success!","Product updated successfully.");
            loadTable();
        }else {
            showAlert(ERROR,"Error","Error!","Product not updated.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<SupplierDTO> supplierObservableList = supplierService.getAll();

        ObservableList<String> supplierIds = FXCollections.observableArrayList();
        supplierObservableList.forEach(supplier -> supplierIds.add(supplier.getId()));
        cmbSupplierId.setItems(supplierIds);

        ObservableList<String> categories = FXCollections.observableArrayList();
        categories.add("T-Shirts & Tops");
        categories.add("Shirts & Blouses");
        categories.add("Jeans & Pants");
        categories.add("Dresses & Skirts");
        categories.add("Sweaters & Hoodies");
        categories.add("Outerwear");
        categories.add("Activewear");
        categories.add("Formal Wear");
        categories.add("Loungewear & Sleepwear");
        categories.add("Swimwear");
        categories.add("Undergarments & Lingerie");
        categories.add("Accessories");

        cmbCategory.setItems(categories);

        ObservableList<String> sizes = FXCollections.observableArrayList();
        sizes.add("XS");
        sizes.add("S");
        sizes.add("M");
        sizes.add("L");
        sizes.add("XL");
        sizes.add("XXL");
        sizes.add("XXXL");

        cmbSize.setItems(sizes);

        colProductId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplier_id"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("product_name"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unit_price"));

        tblProduct.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            setTextToValues(newValue);
        }));

        loadTable();
    }

    private void loadTable(){
        ObservableList<ProductDTO> productObservableList = productService.getAll();
        tblProduct.setItems(productObservableList);
    }

    private void setTextToValues(ProductDTO newValue) {
        cmbSupplierId.setValue(newValue.getSupplier_id());
        cmbCategory.setValue(newValue.getCategory());
        cmbSize.setValue(newValue.getSize());
        txtName.setText(newValue.getProduct_name());
        txtProductId.setText(newValue.getId());
        txtUnitPrice.setText(newValue.getUnit_price().toString());
        quantity = newValue.getQuantity();
    }

    private void showAlert(Alert.AlertType alertType, String title, String header, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
