package pl.com.app.dto;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import pl.com.app.exceptions.EErrorsMessage;
import pl.com.app.exceptions.MyException;
import pl.com.app.parsers.json.BigDecimalSerializer;
import pl.com.app.repository.model.enums.EGuarantee;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

public class ProductDTO {
    private Long id;
    private String name;
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal price;
    private ProducerDTO producerDTO;
    private CategoryDTO categoryDTO;
    private Set<EGuarantee> eGuarantees;

    public static ProductDTO.ProductDTOBuilder builder() {
        return new ProductDTO.ProductDTOBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public ProducerDTO getProducerDTO() {
        return this.producerDTO;
    }

    public CategoryDTO getCategoryDTO() {
        return this.categoryDTO;
    }

    public Set<EGuarantee> getEGuarantees() {
        return this.eGuarantees;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        try{
            if (name == null){
                throw new NullPointerException("NAME IS NULL");
            } else if (!name.matches("[A-Z ]+")){
                throw new IllegalArgumentException("NAME VALUE IS INCORRECT");
            }
            this.name = name;
        } catch (Exception e){
            throw new MyException(String.join(";",
                    this.getClass().getCanonicalName(),
                    EErrorsMessage.VALIDATION_NAME_ERROR.toString()));
        }
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setProducerDTO(ProducerDTO producerDTO) {
        this.producerDTO = producerDTO;
    }

    public void setCategoryDTO(CategoryDTO categoryDTO) {
        this.categoryDTO = categoryDTO;
    }

    public void setEGuarantees(Set<EGuarantee> eGuarantees) {
        this.eGuarantees = eGuarantees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDTO that = (ProductDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(price, that.price) &&
                Objects.equals(producerDTO, that.producerDTO) &&
                Objects.equals(categoryDTO, that.categoryDTO) &&
                Objects.equals(eGuarantees, that.eGuarantees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, producerDTO, categoryDTO, eGuarantees);
    }

    protected boolean canEqual(Object other) {
        return other instanceof ProductDTO;
    }

    public String toString() {
        return "ProductDTO(id=" + this.getId() + ", name=" + this.getName() + ", price=" + this.getPrice() + ", producerDTO=" + this.getProducerDTO() + ", categoryDTO=" + this.getCategoryDTO() + ", eGuarantees=" + this.getEGuarantees() + ")";
    }

    public ProductDTO(Long id, String name, BigDecimal price, ProducerDTO producerDTO, CategoryDTO categoryDTO, Set<EGuarantee> eGuarantees) {
        setId(id);
        setName(name);
        setPrice(price);
        setProducerDTO(producerDTO);
        setCategoryDTO(categoryDTO);
        setEGuarantees(eGuarantees);
    }

    public ProductDTO() {
    }

    public static class ProductDTOBuilder {
        private Long id;
        private String name;
        private BigDecimal price;
        private ProducerDTO producerDTO;
        private CategoryDTO categoryDTO;
        private Set<EGuarantee> eGuarantees;

        ProductDTOBuilder() {
        }

        public ProductDTO.ProductDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ProductDTO.ProductDTOBuilder name(String name) {
            try{
                if (name == null){
                    throw new NullPointerException("NAME IS NULL");
                } else if (!name.matches("[A-Z ]+")){
                    throw new IllegalArgumentException("NAME VALUE IS INCORRECT");
                }
                this.name = name;
            } catch (Exception e){
                throw new MyException(String.join(";",
                        this.getClass().getCanonicalName(),
                        EErrorsMessage.VALIDATION_NAME_ERROR.toString()));
            }
            return this;
        }

        public ProductDTO.ProductDTOBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public ProductDTO.ProductDTOBuilder producerDTO(ProducerDTO producerDTO) {
            this.producerDTO = producerDTO;
            return this;
        }

        public ProductDTO.ProductDTOBuilder categoryDTO(CategoryDTO categoryDTO) {
            this.categoryDTO = categoryDTO;
            return this;
        }

        public ProductDTO.ProductDTOBuilder eGuarantees(Set<EGuarantee> eGuarantees) {
            this.eGuarantees = eGuarantees;
            return this;
        }

        public ProductDTO build() {
            return new ProductDTO(this.id, this.name, this.price, this.producerDTO, this.categoryDTO, this.eGuarantees);
        }

        public String toString() {
            return "ProductDTO.ProductDTOBuilder(id=" + this.id + ", name=" + this.name + ", price=" + this.price + ", producerDTO=" + this.producerDTO + ", categoryDTO=" + this.categoryDTO + ", eGuarantees=" + this.eGuarantees + ")";
        }
    }
}
