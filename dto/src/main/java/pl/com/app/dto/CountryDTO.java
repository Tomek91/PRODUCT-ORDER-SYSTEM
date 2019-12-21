package pl.com.app.dto;


import pl.com.app.exceptions.EErrorsMessage;
import pl.com.app.exceptions.MyException;

import java.util.Objects;

public class CountryDTO {
    private Long id;
    private String name;

    public static CountryDTO.CountryDTOBuilder builder() {
        return new CountryDTO.CountryDTOBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CountryDTO that = (CountryDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    protected boolean canEqual(Object other) {
        return other instanceof CountryDTO;
    }

    public String toString() {
        return "CountryDTO(id=" + this.getId() + ", name=" + this.getName() + ")";
    }

    public CountryDTO(Long id, String name) {
        setId(id);
        setName(name);
    }

    public CountryDTO() {
    }

    public static class CountryDTOBuilder {
        private Long id;
        private String name;

        CountryDTOBuilder() {
        }

        public CountryDTO.CountryDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CountryDTO.CountryDTOBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CountryDTO build() {
            return new CountryDTO(this.id, this.name);
        }

        public String toString() {
            return "CountryDTO.CountryDTOBuilder(id=" + this.id + ", name=" + this.name + ")";
        }
    }
}
