package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class ProductDTO {
    private String id;
    private String supplier_id;
    private String product_name;
    private String size;
    private String category;
    private Double unit_price;
    private Integer quantity;
}
