package service.custom;

import dto.ProductDTO;
import javafx.collections.ObservableList;
import service.SuperService;

public interface ProductService extends SuperService {
    boolean addProduct(ProductDTO productDTO);

    ObservableList<ProductDTO> getAll();

    boolean deleteUser(String id);

    ProductDTO searchProduct(String id);

    boolean updateProduct(ProductDTO productDTO);
}
