package pl.com.app.dto;



import pl.com.app.exceptions.EErrorsMessage;
import pl.com.app.exceptions.MyException;

import java.util.Objects;

public class ShopDTO {
    private Long id;
    private String name;
    private CountryDTO countryDTO;

    public static ShopDTO.ShopDTOBuilder builder() {
        return new ShopDTO.ShopDTOBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public CountryDTO getCountryDTO() {
        return this.countryDTO;
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

    public void setCountryDTO(CountryDTO countryDTO) {
        this.countryDTO = countryDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShopDTO shopDTO = (ShopDTO) o;
        return Objects.equals(id, shopDTO.id) &&
                Objects.equals(name, shopDTO.name) &&
                Objects.equals(countryDTO, shopDTO.countryDTO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, countryDTO);
    }

    protected boolean canEqual(Object other) {
        return other instanceof ShopDTO;
    }

    public String toString() {
        return "ShopDTO(id=" + this.getId() + ", name=" + this.getName() + ", countryDTO=" + this.getCountryDTO() + ")";
    }

    public ShopDTO(Long id, String name, CountryDTO countryDTO) {
        setId(id);
        setName(name);
        setCountryDTO(countryDTO);
    }

    public ShopDTO() {
    }

    public static class ShopDTOBuilder {
        private Long id;
        private String name;
        private CountryDTO countryDTO;

        ShopDTOBuilder() {
        }

        public ShopDTO.ShopDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ShopDTO.ShopDTOBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ShopDTO.ShopDTOBuilder countryDTO(CountryDTO countryDTO) {
            this.countryDTO = countryDTO;
            return this;
        }

        public ShopDTO build() {
            return new ShopDTO(this.id, this.name, this.countryDTO);
        }

        public String toString() {
            return "ShopDTO.ShopDTOBuilder(id=" + this.id + ", name=" + this.name + ", countryDTO=" + this.countryDTO + ")";
        }
    }
}
