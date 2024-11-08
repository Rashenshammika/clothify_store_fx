package service.custom.impl;

import dto.SupplierDTO;
import dto.UserDTO;
import entity.SupplierEntity;
import entity.TempUserEntity;
import entity.UserEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.SupplierDao;
import repository.custom.UserDao;
import service.custom.SupplierService;
import util.DaoType;

public class SupplierServiceImpl implements SupplierService {
    SupplierDao supplierDao = DaoFactory.getInstance().getDaoType(DaoType.SUPPLIER);
    public boolean addSupplier(SupplierDTO supplierDTO){
        try {
            SupplierEntity entity = new ModelMapper().map(supplierDTO, SupplierEntity.class);
            supplierDao.save(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ObservableList<SupplierDTO> getAll() {
        ObservableList<SupplierEntity> supplierEntities = supplierDao.getAll();

        ObservableList<SupplierDTO> supplierDTOList= FXCollections.observableArrayList();

        ModelMapper modelMapper = new ModelMapper();

        for (SupplierEntity entity : supplierEntities) {
            SupplierDTO dto = modelMapper.map(entity, SupplierDTO.class);
            supplierDTOList.add(dto);
        }

        return supplierDTOList;
    }

    @Override
    public boolean deleteSupplier(String supplierId) {
        return supplierDao.delete(supplierId);
    }

    @Override
    public SupplierDTO searchSupplier(String id) {
        SupplierEntity supplierEntity = supplierDao.search(id);
        if (supplierEntity == null) {
            return null;
        }
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(supplierEntity, SupplierDTO.class);
    }

    @Override
    public boolean updateSupplier(SupplierDTO supplierDTO) {
        try {
            SupplierEntity supplierEntity = new ModelMapper().map(supplierDTO, SupplierEntity.class);
            supplierDao.update(supplierEntity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
