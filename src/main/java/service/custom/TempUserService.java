package service.custom;

import dto.TempUserDTO;
import dto.UserDTO;
import javafx.collections.ObservableList;
import service.SuperService;

public interface TempUserService extends SuperService {
    boolean addUser(TempUserDTO tempUserDTO);
    ObservableList<UserDTO> getAll();
    ObservableList<String> getUserIds();
}
