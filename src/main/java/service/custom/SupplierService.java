package service.custom;

import dto.SupplierDTO;
import javafx.collections.ObservableList;
import service.SuperService;

public interface SupplierService extends SuperService {
    boolean addSupplier(SupplierDTO supplierDTO);

    ObservableList<SupplierDTO> getAll();

    boolean deleteSupplier(String supplierId);

    SupplierDTO searchSupplier(String id);

    boolean updateSupplier(SupplierDTO supplierDTO);

}
