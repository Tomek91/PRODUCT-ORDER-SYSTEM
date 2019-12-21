package pl.com.app.dto;



import pl.com.app.exceptions.EErrorsMessage;
import pl.com.app.exceptions.MyException;

import java.util.Objects;

public class TradeDTO {
    private Long id;
    private String name;

    public static TradeDTO.TradeDTOBuilder builder() {
        return new TradeDTO.TradeDTOBuilder();
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
        TradeDTO tradeDTO = (TradeDTO) o;
        return Objects.equals(id, tradeDTO.id) &&
                Objects.equals(name, tradeDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    protected boolean canEqual(Object other) {
        return other instanceof TradeDTO;
    }

    public String toString() {
        return "TradeDTO(id=" + this.getId() + ", name=" + this.getName() + ")";
    }

    public TradeDTO(Long id, String name) {
       setId(id);
       setName(name);
    }

    public TradeDTO() {
    }

    public static class TradeDTOBuilder {
        private Long id;
        private String name;

        TradeDTOBuilder() {
        }

        public TradeDTO.TradeDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public TradeDTO.TradeDTOBuilder name(String name) {
            this.name = name;
            return this;
        }

        public TradeDTO build() {
            return new TradeDTO(this.id, this.name);
        }

        public String toString() {
            return "TradeDTO.TradeDTOBuilder(id=" + this.id + ", name=" + this.name + ")";
        }
    }
}
