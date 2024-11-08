package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
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
import service.custom.UserService;
import util.ServiceType;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;

public class UserManagementFormForAdminController implements Initializable {

    UserService userService = ServiceFactory.getInstance().getServiceType(ServiceType.USER);
    @FXML
    public JFXTextField txtId;

    @FXML
    public JFXTextField txtName;

    @FXML
    private JFXButton btnDeleteUserOnAction;

    @FXML
    private JFXButton btnSearchUserOnAction;

    @FXML
    private JFXButton btnUpdateUserOnAction;

    @FXML
    private JFXComboBox<String> cmbRole;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colRole;

    @FXML
    private TableView<UserDTO> tblUser;

    @FXML
    private JFXTextField txtEmail;

    private String password;
    private String user_id;

    @FXML
    void btnAddUserOnAction(ActionEvent event) {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/popup/add_user_popup_form.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.show();
    }

    @FXML
    void btnDeleteSupplierOnAction(ActionEvent event) {
        String userId = txtId.getText();

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Delete Confirmation");
        confirmationAlert.setHeaderText("Are you sure you want to delete this user?");
        confirmationAlert.setContentText("User ID: " + userId);

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean isDeleted = userService.deleteUser(userId);

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
    void btnSearchUserOnAction(ActionEvent event) {
        UserDTO searchedUser = userService.searchUser(txtId.getText());
        if(null==searchedUser){
            showAlert(ERROR,"Not found","Not found!","Search user not found.");
        }else {
            txtName.setText(searchedUser.getName());
            txtEmail.setText(searchedUser.getEmail());
            cmbRole.setValue(searchedUser.getRole());
        }
    }

    @FXML
    void btnUpdateUserOnAction(ActionEvent event) {
        UserDTO userDTO = new UserDTO(user_id, txtName.getText(), txtEmail.getText(), password, cmbRole.getValue());
        boolean b = userService.updateUser(userDTO);
        if (b){
            showAlert(INFORMATION,"Success","Success!","User updated successfully.");
            loadTable();
        }else {
            showAlert(ERROR,"Error","Error!","User not updated.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));

        ObservableList<String> roles = FXCollections.observableArrayList();
        roles.add("Admin");
        roles.add("Employee");
        cmbRole.setItems(roles);

        tblUser.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            setTextToValues(newValue);
        }));

        loadTable();
    }

    private void setTextToValues(UserDTO newValue) {
        txtId.setText(newValue.getId());
        txtName.setText(newValue.getName());
        txtEmail.setText(newValue.getEmail());
        cmbRole.setValue(newValue.getRole());
        password = newValue.getPassword();
        user_id = newValue.getId();
    }

    private void loadTable(){
        ObservableList<UserDTO> userObservableList = userService.getAll();
        tblUser.setItems(userObservableList);
        System.out.println(userObservableList);
    }

    private void showAlert(Alert.AlertType alertType, String title, String header, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
