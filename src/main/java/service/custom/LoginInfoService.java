package service.custom;

import dto.LoginInfoDTO;
import javafx.util.Pair;
import service.SuperService;

public interface LoginInfoService extends SuperService {
    Pair<Boolean, String> isValidate(LoginInfoDTO loginInfoDTO);
}
