package pl.com.app.repository.model.parsers;


import pl.com.app.exceptions.EErrorsMessage;
import pl.com.app.exceptions.MyException;

import java.math.BigDecimal;

public class BigDecimalParser implements Parser<BigDecimal> {

    @Override
    public BigDecimal parse(String line) {
        BigDecimal value;
        try{
            value = new BigDecimal(line);
        } catch (Exception e){
            throw new MyException(String.join(";",
                    this.getClass().getCanonicalName(),
                    EErrorsMessage.BIGDECIMAL_PARSE_ERROR.toString()));
        }
        return value;
    }
}
