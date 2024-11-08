package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import dto.LoginInfoDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;
import service.ServiceFactory;
import service.custom.LoginInfoService;
import util.ServiceType;

import java.io.IOException;

public class LoginFormController {

    @FXML
    private JFXButton btnLogin;

    @FXML
    private JFXPasswordField psfPassword;

    @FXML
    private JFXTextField txtEmail;

    LoginInfoService loginInfoService = ServiceFactory.getInstance().getServiceType(ServiceType.LOGININFO);

    @FXML
    void userLogOnAction(ActionEvent event) {
        LoginInfoDTO loginInfoDTO = new LoginInfoDTO(
                txtEmail.getText(),
                psfPassword.getText()
        );
        Pair<Boolean, String> validate = loginInfoService.isValidate(loginInfoDTO);
        System.out.println(validate);

        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/login_dash_form_for_admin.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.show();
    }

}
