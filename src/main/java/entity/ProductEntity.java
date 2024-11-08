package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity

public class ProductEntity {
    @Id
    @GeneratedValue(generator = "custom-id")
    @GenericGenerator(
            name = "custom-id",
            strategy = "generator.ProductIdGenerator"
    )
    private String id;
    private String supplier_id;
    private String product_name;
    private String size;
    private String category;
    private Double unit_price;
    private Integer quantity;
}
