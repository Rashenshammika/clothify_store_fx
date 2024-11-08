package service.custom.impl;

import dto.TempUserDTO;
import dto.UserDTO;
import entity.TempUserEntity;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.TempUserDao;
import service.custom.TempUserService;
import util.DaoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TempUserServiceImpl implements TempUserService {

    TempUserDao tempUserDao = DaoFactory.getInstance().getDaoType(DaoType.TEMPUSER);
    @Override
    public boolean addUser(TempUserDTO tempUserDTO) {
        try {
            TempUserEntity entity = new ModelMapper().map(tempUserDTO, TempUserEntity.class);
            tempUserDao.save(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ObservableList<UserDTO> getAll() {
        ObservableList<TempUserEntity> tempUserEntities = tempUserDao.getAll();

        ObservableList<UserDTO> UserDTOList = FXCollections.observableArrayList();

        ModelMapper modelMapper = new ModelMapper();

        for (TempUserEntity entity : tempUserEntities) {
            UserDTO dto = modelMapper.map(entity, UserDTO.class);
            UserDTOList.add(dto);
        }

        return UserDTOList;
    }

    @Override
    public ObservableList<String> getUserIds() {
        return null;
    }
}
