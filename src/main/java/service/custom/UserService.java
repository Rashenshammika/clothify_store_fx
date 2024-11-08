package service.custom;

import dto.TempUserDTO;
import dto.UserDTO;
import javafx.collections.ObservableList;
import service.SuperService;

public interface UserService extends SuperService {
    boolean addUser(UserDTO userDTO);
    boolean deleteUser(String id);
    ObservableList<UserDTO> getAll();
    boolean updateUser(UserDTO UserDTO);
    UserDTO searchUser(String id);
    ObservableList<String> getUserIds();
}
