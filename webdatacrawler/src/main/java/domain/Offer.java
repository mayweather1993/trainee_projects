package domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Offer {
    private String articleId;
    private String productName;
    private String brand;
    private String color;
    private String price;
    private String initialPrice;
    private String description;
    private String shippingPrice;
}
