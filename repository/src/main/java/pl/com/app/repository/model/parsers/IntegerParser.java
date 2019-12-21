package pl.com.app.repository.model.parsers;


import pl.com.app.exceptions.EErrorsMessage;
import pl.com.app.exceptions.MyException;

public class IntegerParser implements Parser<Integer> {
    @Override
    public Integer parse(String line) {
        Integer value;
        try{
            value = Integer.valueOf(line);
        } catch (Exception e){
            throw new MyException(String.join(";",
                    this.getClass().getCanonicalName(),
                    EErrorsMessage.INTEGER_PARSE_ERROR.toString()));
        }
        return value;
    }
}
