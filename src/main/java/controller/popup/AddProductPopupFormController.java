package controller.popup;

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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import service.ServiceFactory;
import service.custom.ProductService;
import service.custom.SupplierService;
import util.ServiceType;

import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;

public class AddProductPopupFormController implements Initializable {

    SupplierService supplierService = ServiceFactory.getInstance().getServiceType(ServiceType.SUPPLIER);
    ProductService productService = ServiceFactory.getInstance().getServiceType(ServiceType.PRODUCT);

    @FXML
    private JFXButton btnAddProduct;

    @FXML
    private JFXComboBox<String> cmbCategory;

    @FXML
    private JFXComboBox<String> cmbSize;

    @FXML
    private JFXComboBox<String> cmbSupplierId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtUnitPrice;

    @FXML
    void btnAddProductOnAction(ActionEvent event) {
        ProductDTO productDTO = new ProductDTO(
                null,
                cmbSupplierId.getValue(),
                txtName.getText(),
                cmbSize.getValue(),
                cmbCategory.getValue(),
                Double.parseDouble(txtUnitPrice.getText()),
                0
        );

        boolean b = productService.addProduct(productDTO);
        if (b){
            showAlert(INFORMATION,"Success","Success!","User saved successfully.");
        }else {
            showAlert(ERROR,"Error","Error!","User not saved.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

        ObservableList<SupplierDTO> supplierObservableList = supplierService.getAll();
        ObservableList<String> supplierIds = FXCollections.observableArrayList();

        supplierObservableList.forEach(supplier -> supplierIds.add(supplier.getId()));

        cmbSupplierId.setItems(supplierIds);
    }

    private void showAlert(Alert.AlertType alertType, String title, String header, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

