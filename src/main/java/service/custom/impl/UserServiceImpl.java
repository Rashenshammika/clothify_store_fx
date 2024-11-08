package service.custom.impl;

import dto.TempUserDTO;
import dto.UserDTO;
import entity.TempUserEntity;
import entity.UserEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.UserDao;
import service.custom.UserService;
import util.DaoType;

public class UserServiceImpl implements UserService {

    UserDao userDao = DaoFactory.getInstance().getDaoType(DaoType.USER);
    @Override
    public boolean addUser(UserDTO userDTO) {
        try {
            UserEntity entity = new ModelMapper().map(userDTO, UserEntity.class);
            userDao.save(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteUser(String id) {
        return userDao.delete(id);
    }

    @Override
    public ObservableList<UserDTO> getAll() {
        ObservableList<UserEntity> userEntities = userDao.getAll();

        ObservableList<UserDTO> UserDTOList = FXCollections.observableArrayList();

        ModelMapper modelMapper = new ModelMapper();

        for (UserEntity entity : userEntities) {
            UserDTO dto = modelMapper.map(entity, UserDTO.class);
            UserDTOList.add(dto);
        }

        return UserDTOList;
    }

    @Override
    public boolean updateUser(UserDTO userDTO) {
        try {
            UserEntity entity = new ModelMapper().map(userDTO, UserEntity.class);
            userDao.update(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public UserDTO searchUser(String id) {
        UserEntity userEntity = userDao.search(id);
        if (userEntity == null) {
            return null;
        }
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(userEntity, UserDTO.class);
    }

    @Override
    public ObservableList<String> getUserIds() {
        return null;
    }
}
