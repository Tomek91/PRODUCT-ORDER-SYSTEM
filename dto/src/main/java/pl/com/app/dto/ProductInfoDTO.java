package pl.com.app.dto;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductInfoDTO {
    private String name;
    private BigDecimal price;
    private String categoryName;
    private String producerName;
    private String countryName;
    private Integer ordersCount;
}


