package controller.popup;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.TempUserDTO;
import dto.UserDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.id.IdentifierGenerator;
import service.ServiceFactory;
import service.custom.TempUserService;
import service.custom.UserService;
import util.ServiceType;

import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;

public class AddUserPopupFormController implements Initializable {
    @FXML
    public JFXTextField txtId;

    @FXML
    public JFXTextField txtName;

    @FXML
    private JFXButton btnSearchUserOnAction;

    @FXML
    private JFXComboBox<String> cmbRole;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableView<UserDTO> tblTempUser;

    @FXML
    private JFXTextField txtEmail;

    TempUserService tempUserService = ServiceFactory.getInstance().getServiceType(ServiceType.TEMPUSER);
    UserService userService = ServiceFactory.getInstance().getServiceType(ServiceType.USER);

    private String password;

    @FXML
    void btnAddUserOnAction(ActionEvent event) {
        UserDTO userDTO = new UserDTO(null, txtName.getText(), txtEmail.getText(), password, cmbRole.getValue());
        boolean b = userService.addUser(userDTO);
        if (b){
            showAlert(INFORMATION,"Success","Success!","User saved successfully.");
        }else {
            showAlert(ERROR,"Error","Error!","User not saved.");
        }
    }

    @FXML
    void btnReloadTableOnAction(ActionEvent event) {
        loadTable();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        ObservableList<String> roles = FXCollections.observableArrayList();
        roles.add("Admin");
        roles.add("Employee");
        cmbRole.setItems(roles);

        tblTempUser.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            setTextToValues(newValue);
        }));

        loadTable();
    }

    private void setTextToValues(UserDTO newValue) {
        txtId.setText(newValue.getId());
        txtName.setText(newValue.getName());
        txtEmail.setText(newValue.getEmail());
        password = newValue.getPassword();
    }

    private void loadTable(){
        ObservableList<UserDTO> tempUserObservableList = tempUserService.getAll();
        tblTempUser.setItems(tempUserObservableList);
        System.out.println(tempUserObservableList);
    }

    private void showAlert(Alert.AlertType alertType, String title, String header, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
