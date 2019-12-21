package pl.com.app.dto;



import pl.com.app.exceptions.EErrorsMessage;
import pl.com.app.exceptions.MyException;

import java.util.Objects;

public class StockDTO {
    private Long id;
    private Integer quantity;
    private ProductDTO productDTO;
    private ShopDTO shopDTO;

    public static StockDTO.StockDTOBuilder builder() {
        return new StockDTO.StockDTOBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public ProductDTO getProductDTO() {
        return this.productDTO;
    }

    public ShopDTO getShopDTO() {
        return this.shopDTO;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuantity(Integer quantity) {
        try{
            if (quantity == null){
                throw new NullPointerException("QUANTITY IS NULL");
            } else if (!(quantity.toString().matches("\\d+")
                    && quantity >= 0)){
                throw new IllegalArgumentException("QUANTITY VALUE IS INCORRECT");
            }
            this.quantity = quantity;
        } catch (Exception e){
            throw new MyException(String.join(";",
                    this.getClass().getCanonicalName(),
                    EErrorsMessage.VALIDATION_QUANTITY_ERROR.toString()));
        }
    }

    public void setProductDTO(ProductDTO productDTO) {
        this.productDTO = productDTO;
    }

    public void setShopDTO(ShopDTO shopDTO) {
        this.shopDTO = shopDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockDTO stockDTO = (StockDTO) o;
        return Objects.equals(id, stockDTO.id) &&
                Objects.equals(quantity, stockDTO.quantity) &&
                Objects.equals(productDTO, stockDTO.productDTO) &&
                Objects.equals(shopDTO, stockDTO.shopDTO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantity, productDTO, shopDTO);
    }

    protected boolean canEqual(Object other) {
        return other instanceof StockDTO;
    }

    public String toString() {
        return "StockDTO(id=" + this.getId() + ", quantity=" + this.getQuantity() + ", productDTO=" + this.getProductDTO() + ", shopDTO=" + this.getShopDTO() + ")";
    }

    public StockDTO(Long id, Integer quantity, ProductDTO productDTO, ShopDTO shopDTO) {
        setId(id);
        setQuantity(quantity);
        setProductDTO(productDTO);
        setShopDTO(shopDTO);
    }

    public StockDTO() {
    }

    public static class StockDTOBuilder {
        private Long id;
        private Integer quantity;
        private ProductDTO productDTO;
        private ShopDTO shopDTO;

        StockDTOBuilder() {
        }

        public StockDTO.StockDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public StockDTO.StockDTOBuilder quantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        public StockDTO.StockDTOBuilder productDTO(ProductDTO productDTO) {
            this.productDTO = productDTO;
            return this;
        }

        public StockDTO.StockDTOBuilder shopDTO(ShopDTO shopDTO) {
            this.shopDTO = shopDTO;
            return this;
        }

        public StockDTO build() {
            return new StockDTO(this.id, this.quantity, this.productDTO, this.shopDTO);
        }

        public String toString() {
            return "StockDTO.StockDTOBuilder(id=" + this.id + ", quantity=" + this.quantity + ", productDTO=" + this.productDTO + ", shopDTO=" + this.shopDTO + ")";
        }
    }
}
