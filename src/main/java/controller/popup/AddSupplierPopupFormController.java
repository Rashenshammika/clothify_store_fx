package controller.popup;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.SupplierDTO;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import service.ServiceFactory;
import service.custom.SupplierService;
import util.ServiceType;

import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;

public class AddSupplierPopupFormController implements Initializable {
    @FXML
    public JFXButton btnAddSupplier;

    @FXML
    private JFXTextField txtContact;

    @FXML
    private JFXTextField txtName;

    SupplierService supplierService = ServiceFactory.getInstance().getServiceType(ServiceType.SUPPLIER);
    BooleanProperty allFieldsFilled = new SimpleBooleanProperty(false);

    @FXML
    void btnAddSupplierOnAction(ActionEvent event) {
        SupplierDTO supplierDTO = new SupplierDTO(null,txtName.getText(), txtContact.getText());
        boolean b = supplierService.addSupplier(supplierDTO);
        if (b){
            showAlert(INFORMATION,"Success","Success!","Supplier saved successfully.");
        }else {
            showAlert(ERROR,"Error","Error!","Supplier not saved.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String header, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnAddSupplier.disableProperty().bind(
                Bindings.createBooleanBinding(() ->
                                txtContact.getText().trim().isEmpty() ||
                                        txtName.getText().trim().isEmpty(),
                        txtContact.textProperty(),
                        txtName.textProperty()
                )
        );
    }
}
