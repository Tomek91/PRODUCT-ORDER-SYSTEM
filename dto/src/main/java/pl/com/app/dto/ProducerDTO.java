package pl.com.app.dto;



import pl.com.app.exceptions.EErrorsMessage;
import pl.com.app.exceptions.MyException;

import java.util.Objects;

public class ProducerDTO {
    private Long id;
    private String name;
    private CountryDTO countryDTO;
    private TradeDTO tradeDTO;

    public static ProducerDTO.ProducerDTOBuilder builder() {
        return new ProducerDTO.ProducerDTOBuilder();
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

    public TradeDTO getTradeDTO() {
        return this.tradeDTO;
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

    public void setTradeDTO(TradeDTO tradeDTO) {
        this.tradeDTO = tradeDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProducerDTO that = (ProducerDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(countryDTO, that.countryDTO) &&
                Objects.equals(tradeDTO, that.tradeDTO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, countryDTO, tradeDTO);
    }

    protected boolean canEqual(Object other) {
        return other instanceof ProducerDTO;
    }

    public String toString() {
        return "ProducerDTO(id=" + this.getId() + ", name=" + this.getName() + ", countryDTO=" + this.getCountryDTO() + ", tradeDTO=" + this.getTradeDTO() + ")";
    }

    public ProducerDTO(Long id, String name, CountryDTO countryDTO, TradeDTO tradeDTO) {
        setId(id);
        setName(name);
        setCountryDTO(countryDTO);
        setTradeDTO(tradeDTO);
    }

    public ProducerDTO() {
    }

    public static class ProducerDTOBuilder {
        private Long id;
        private String name;
        private CountryDTO countryDTO;
        private TradeDTO tradeDTO;

        ProducerDTOBuilder() {
        }

        public ProducerDTO.ProducerDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ProducerDTO.ProducerDTOBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ProducerDTO.ProducerDTOBuilder countryDTO(CountryDTO countryDTO) {
            this.countryDTO = countryDTO;
            return this;
        }

        public ProducerDTO.ProducerDTOBuilder tradeDTO(TradeDTO tradeDTO) {
            this.tradeDTO = tradeDTO;
            return this;
        }

        public ProducerDTO build() {
            return new ProducerDTO(this.id, this.name, this.countryDTO, this.tradeDTO);
        }

        public String toString() {
            return "ProducerDTO.ProducerDTOBuilder(id=" + this.id + ", name=" + this.name + ", countryDTO=" + this.countryDTO + ", tradeDTO=" + this.tradeDTO + ")";
        }
    }
}
