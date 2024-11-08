package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import dto.TempUserDTO;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import service.ServiceFactory;
import service.custom.TempUserService;
import util.ServiceType;

import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;

public class SignUpFormController implements Initializable {
    @FXML
    public JFXButton btnRequestToCreateAccount;

    @FXML
    private JFXCheckBox chbAgreement;

    @FXML
    private JFXPasswordField psfPassword;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtFirstName;

    @FXML
    private JFXTextField txtLastName;

    BooleanProperty allFieldsFilled = new SimpleBooleanProperty(false);

    TempUserService tempUserService= ServiceFactory.getInstance().getServiceType(ServiceType.TEMPUSER);

    @FXML
    void btnRequestToCreateAccountOnAction(ActionEvent event) {
        if (!isValidPassword(psfPassword.getText())) {
            showAlert(Alert.AlertType.ERROR,"Error","Error!", "Password is invalid! It must be at least 8 characters long and contain letters, digits, and special characters.");
            psfPassword.setText("");
        } else {
            boolean b = tempUserService.addUser(new TempUserDTO(txtFirstName.getText() + " " + txtLastName.getText(), txtEmail.getText(), psfPassword.getText()));
            if (b){
                showAlert(INFORMATION,"Success","Success!","you request saved successfully.");
            }else {
                showAlert(ERROR,"Error","Error!","you request not saved.");
            }
            txtFirstName.setText("");
            txtLastName.setText("");
            txtEmail.setText("");
            psfPassword.setText("");
            chbAgreement.setSelected(false);
        }
    }

    private void showAlert(Alert.AlertType alertType,String title,String header,String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean isValidPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        // Regex to check for at least one letter, one digit, and one special character
        String regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}$";
        return password.matches(regex);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnRequestToCreateAccount.disableProperty().bind(
                Bindings.createBooleanBinding(() ->
                                txtFirstName.getText().trim().isEmpty() ||
                                        txtLastName.getText().trim().isEmpty() ||
                                        txtEmail.getText().trim().isEmpty() ||
                                        psfPassword.getText().trim().isEmpty() ||
                                        !chbAgreement.isSelected(),
                        txtFirstName.textProperty(),
                        txtLastName.textProperty(),
                        txtEmail.textProperty(),
                        psfPassword.textProperty(),
                        chbAgreement.selectedProperty()
                )
        );
    }
}
