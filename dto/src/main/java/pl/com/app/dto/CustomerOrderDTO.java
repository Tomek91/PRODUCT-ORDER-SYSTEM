package pl.com.app.dto;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import pl.com.app.exceptions.EErrorsMessage;
import pl.com.app.exceptions.MyException;
import pl.com.app.parsers.json.BigDecimalSerializer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class CustomerOrderDTO {
    private Long id;
    private LocalDate date;
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal discount;
    private Integer quantity;
    private CustomerDTO customerDTO;
    private ProductDTO productDTO;
    private PaymentDTO paymentDTO;

    public static CustomerOrderDTO.CustomerOrderDTOBuilder builder() {
        return new CustomerOrderDTO.CustomerOrderDTOBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public BigDecimal getDiscount() {
        return this.discount;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public CustomerDTO getCustomerDTO() {
        return this.customerDTO;
    }

    public ProductDTO getProductDTO() {
        return this.productDTO;
    }

    public PaymentDTO getPaymentDTO() {
        return this.paymentDTO;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDate(LocalDate date) {
        try{
            if (date == null){
                throw new NullPointerException("ORDER DATE IS NULL");
            } else if (!(date.compareTo(LocalDate.now()) >= 0)){
                throw new IllegalArgumentException("ORDER DATE VALUE IS INCORRECT");
            }
            this.date = date;
        } catch (Exception e){
            throw new MyException(String.join(";",
                    this.getClass().getCanonicalName(),
                    EErrorsMessage.VALIDATION_ORDER_DATE_ERROR.toString()));
        }
    }

    public void setDiscount(BigDecimal discount) {
        try{
            if (discount == null){
                throw new NullPointerException("DISCOUNT IS NULL");
            } else if (!(discount.compareTo(new BigDecimal(0)) >= 0
                    && discount.compareTo(new BigDecimal(1)) <= 0)){
                throw new IllegalArgumentException("DISCOUNT VALUE IS INCORRECT");
            }
            this.discount = discount;
        } catch (Exception e){
            throw new MyException(String.join(";",
                    this.getClass().getCanonicalName(),
                    EErrorsMessage.VALIDATION_DISCOUNT_ERROR.toString()));
        }
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setCustomerDTO(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }

    public void setProductDTO(ProductDTO productDTO) {
        this.productDTO = productDTO;
    }

    public void setPaymentDTO(PaymentDTO paymentDTO) {
        this.paymentDTO = paymentDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerOrderDTO that = (CustomerOrderDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(date, that.date) &&
                Objects.equals(discount, that.discount) &&
                Objects.equals(quantity, that.quantity) &&
                Objects.equals(customerDTO, that.customerDTO) &&
                Objects.equals(productDTO, that.productDTO) &&
                Objects.equals(paymentDTO, that.paymentDTO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, discount, quantity, customerDTO, productDTO, paymentDTO);
    }

    protected boolean canEqual(Object other) {
        return other instanceof CustomerOrderDTO;
    }

    public String toString() {
        return "CustomerOrderDTO(id=" + this.getId() + ", date=" + this.getDate() + ", discount=" + this.getDiscount() + ", quantity=" + this.getQuantity() + ", customerDTO=" + this.getCustomerDTO() + ", productDTO=" + this.getProductDTO() + ", paymentDTO=" + this.getPaymentDTO() + ")";
    }

    public CustomerOrderDTO(Long id, LocalDate date, BigDecimal discount, Integer quantity, CustomerDTO customerDTO, ProductDTO productDTO, PaymentDTO paymentDTO) {
        setId(id);
        setDate(date);
        setDiscount(discount);
        setQuantity(quantity);
        setCustomerDTO(customerDTO);
        setProductDTO(productDTO);
        setPaymentDTO(paymentDTO);
    }

    public CustomerOrderDTO() {
    }

    public static class CustomerOrderDTOBuilder {
        private Long id;
        private LocalDate date;
        private BigDecimal discount;
        private Integer quantity;
        private CustomerDTO customerDTO;
        private ProductDTO productDTO;
        private PaymentDTO paymentDTO;

        CustomerOrderDTOBuilder() {
        }

        public CustomerOrderDTO.CustomerOrderDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CustomerOrderDTO.CustomerOrderDTOBuilder date(LocalDate date) {
            this.date = date;
            return this;
        }

        public CustomerOrderDTO.CustomerOrderDTOBuilder discount(BigDecimal discount) {
            this.discount = discount;
            return this;
        }

        public CustomerOrderDTO.CustomerOrderDTOBuilder quantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        public CustomerOrderDTO.CustomerOrderDTOBuilder customerDTO(CustomerDTO customerDTO) {
            this.customerDTO = customerDTO;
            return this;
        }

        public CustomerOrderDTO.CustomerOrderDTOBuilder productDTO(ProductDTO productDTO) {
            this.productDTO = productDTO;
            return this;
        }

        public CustomerOrderDTO.CustomerOrderDTOBuilder paymentDTO(PaymentDTO paymentDTO) {
            this.paymentDTO = paymentDTO;
            return this;
        }

        public CustomerOrderDTO build() {
            return new CustomerOrderDTO(this.id, this.date, this.discount, this.quantity, this.customerDTO, this.productDTO, this.paymentDTO);
        }

        public String toString() {
            return "CustomerOrderDTO.CustomerOrderDTOBuilder(id=" + this.id + ", date=" + this.date + ", discount=" + this.discount + ", quantity=" + this.quantity + ", customerDTO=" + this.customerDTO + ", productDTO=" + this.productDTO + ", paymentDTO=" + this.paymentDTO + ")";
        }
    }
}
