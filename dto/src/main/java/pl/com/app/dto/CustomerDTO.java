package pl.com.app.dto;



import pl.com.app.exceptions.EErrorsMessage;
import pl.com.app.exceptions.MyException;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public class CustomerDTO {
    private Long id;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private CountryDTO countryDTO;

    public static CustomerDTO.CustomerDTOBuilder builder() {
        return new CustomerDTO.CustomerDTOBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public LocalDate getBirthDate() {
        return this.birthDate;
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

    public void setSurname(String surname) {
        try{
            if (surname == null){
                throw new NullPointerException("SURNAME IS NULL");
            } else if (!surname.matches("[A-Z ]+")){
                throw new IllegalArgumentException("SURNAME VALUE IS INCORRECT");
            }
            this.surname = surname;
        } catch (Exception e){
            throw new MyException(String.join(";",
                    this.getClass().getCanonicalName(),
                    EErrorsMessage.VALIDATION_SURNAME_ERROR.toString()));
        }
    }

    public void setBirthDate(LocalDate birthDate) {
        try{
            if (birthDate == null){
                throw new NullPointerException("BIRTHDAY IS NULL");
            } else if (!(Period.between(birthDate, LocalDate.now()).getYears() >= 18)){
                throw new IllegalArgumentException("BIRTHDAY VALUE IS INCORRECT");
            }
            this.birthDate = birthDate;
        } catch (Exception e){
            throw new MyException(String.join(";",
                    this.getClass().getCanonicalName(),
                    EErrorsMessage.VALIDATION_BIRTHDATE_ERROR.toString()));
        }
    }

    public void setCountryDTO(CountryDTO countryDTO) {
        this.countryDTO = countryDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerDTO that = (CustomerDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(surname, that.surname) &&
                Objects.equals(birthDate, that.birthDate) &&
                Objects.equals(countryDTO, that.countryDTO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, birthDate, countryDTO);
    }

    protected boolean canEqual(Object other) {
        return other instanceof CustomerDTO;
    }

    public String toString() {
        return "CustomerDTO(id=" + this.getId() + ", name=" + this.getName() + ", surname=" + this.getSurname() + ", birthDate=" + this.getBirthDate() + ", countryDTO=" + this.getCountryDTO() + ")";
    }

    public CustomerDTO(Long id, String name, String surname, LocalDate birthDate, CountryDTO countryDTO) {
        setId(id);
        setName(name);
        setSurname(surname);
        setBirthDate(birthDate);
        setCountryDTO(countryDTO);
    }

    public CustomerDTO() {
    }

    public static class CustomerDTOBuilder {
        private Long id;
        private String name;
        private String surname;
        private LocalDate birthDate;
        private CountryDTO countryDTO;

        CustomerDTOBuilder() {
        }

        public CustomerDTO.CustomerDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CustomerDTO.CustomerDTOBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CustomerDTO.CustomerDTOBuilder surname(String surname) {
            this.surname = surname;
            return this;
        }

        public CustomerDTO.CustomerDTOBuilder birthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public CustomerDTO.CustomerDTOBuilder countryDTO(CountryDTO countryDTO) {
            this.countryDTO = countryDTO;
            return this;
        }

        public CustomerDTO build() {
            return new CustomerDTO(this.id, this.name, this.surname, this.birthDate, this.countryDTO);
        }

        public String toString() {
            return "CustomerDTO.CustomerDTOBuilder(id=" + this.id + ", name=" + this.name + ", surname=" + this.surname + ", birthDate=" + this.birthDate + ", countryDTO=" + this.countryDTO + ")";
        }
    }
}
