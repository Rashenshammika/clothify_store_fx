package service.custom.impl;

import dto.LoginInfoDTO;
import javafx.util.Pair;
import service.custom.LoginInfoService;

public class LoginInfoServiceImpl implements LoginInfoService {
    public Pair<Boolean, String> isValidate(LoginInfoDTO loginInfoDTO) {
        boolean status = true;
        String message = "Operation successful";
        return new Pair<>(status, message);
    }
}
