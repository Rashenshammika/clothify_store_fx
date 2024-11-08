package controller;

import com.jfoenix.controls.JFXTextField;
import dto.SupplierDTO;
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
import service.custom.SupplierService;
import util.ServiceType;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;

public class SupplierManagementFormController implements Initializable {
    SupplierService supplierService = ServiceFactory.getInstance().getServiceType(ServiceType.SUPPLIER);

    @FXML
    public TableView<SupplierDTO> tblSupplier;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    public JFXTextField txtId;

    @FXML
    public JFXTextField txtName;

    @FXML
    private JFXTextField txtContact;


    @FXML
    void btnAddSupplierOnAction(ActionEvent event) {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/popup/add_supplier_popup_form.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.show();
    }

    @FXML
    void btnDeleteSupplierOnAction(ActionEvent event) {
        String supplierId = txtId.getText();

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Delete Confirmation");
        confirmationAlert.setHeaderText("Are you sure you want to delete this user?");
        confirmationAlert.setContentText("User ID: " + supplierId);

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean isDeleted = supplierService.deleteSupplier(supplierId);

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
    void btnSearchSupplierOnAction(ActionEvent event) {
        SupplierDTO supplierDTO = supplierService.searchSupplier(txtId.getText());
        if(null==supplierDTO){
            showAlert(ERROR,"Not found","Not found!","Search supplier not found.");
        }else {
            txtName.setText(supplierDTO.getName());
            txtContact.setText(supplierDTO.getContact());
        }
    }

    @FXML
    void btnUpdateSupplierOnAction(ActionEvent event) {
        SupplierDTO supplierDTO = new SupplierDTO(txtId.getText(), txtName.getText(), txtContact.getText());
        boolean b = supplierService.updateSupplier(supplierDTO);
        if (b){
            showAlert(INFORMATION,"Success","Success!","Supplier updated successfully.");
            loadTable();
        }else {
            showAlert(ERROR,"Error","Error!","Supplier not updated.");
        }
    }


    public void btnReloadTableOnAction(ActionEvent actionEvent) {
        loadTable();
    }

    private void loadTable(){
        ObservableList<SupplierDTO> supplierObservableList = supplierService.getAll();
        tblSupplier.setItems(supplierObservableList);
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
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));

        tblSupplier.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            setTextToValues(newValue);
        }));

        loadTable();
    }

    private void setTextToValues(SupplierDTO newValue) {
        txtId.setText(newValue.getId());
        txtName.setText(newValue.getName());
        txtContact.setText(newValue.getContact());
    }
}
