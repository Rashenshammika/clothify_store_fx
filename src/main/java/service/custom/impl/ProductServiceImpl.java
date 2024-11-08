package service.custom.impl;

import dto.ProductDTO;
import entity.ProductEntity;
import entity.SupplierEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.ProductDao;
import repository.custom.SupplierDao;
import service.custom.ProductService;
import util.DaoType;

public class ProductServiceImpl implements ProductService {

    ProductDao productDao = DaoFactory.getInstance().getDaoType(DaoType.PRODUCT);
    SupplierDao supplierDao = DaoFactory.getInstance().getDaoType(DaoType.SUPPLIER);
    @Override
    public boolean addProduct(ProductDTO productDTO) {
        try {
            ProductEntity entity = new ModelMapper().map(productDTO, ProductEntity.class);
            productDao.save(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ObservableList<ProductDTO> getAll() {
        ObservableList<ProductEntity> productEntities = productDao.getAll();

        ObservableList<ProductDTO> productDTOObservableList = FXCollections.observableArrayList();

        ModelMapper modelMapper = new ModelMapper();

        for (ProductEntity entity : productEntities) {
            ProductDTO dto = modelMapper.map(entity, ProductDTO.class);
            productDTOObservableList.add(dto);
        }

        return productDTOObservableList;
    }

    @Override
    public boolean deleteUser(String id) {
        return productDao.delete(id);
    }

    @Override
    public ProductDTO searchProduct(String id) {
        ProductEntity productEntity = productDao.search(id);
        if (productEntity == null) {
            return null;
        }
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(productEntity, ProductDTO.class);
    }

    @Override
    public boolean updateProduct(ProductDTO productDTO) {
        try {
            ProductEntity productEntity = new ModelMapper().map(productDTO, ProductEntity.class);
            productDao.update(productEntity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
