package pl.com.app.repository.model.parsers;



import pl.com.app.exceptions.EErrorsMessage;
import pl.com.app.exceptions.MyException;

import java.time.LocalDate;

public class DateParser implements Parser<LocalDate> {

    @Override
    public LocalDate parse(String dateInString) {
        LocalDate date;
        try{
            date = LocalDate.parse(dateInString);
        } catch (Exception e){
            throw new MyException(String.join(";",
                    this.getClass().getCanonicalName(),
                    EErrorsMessage.DATE_PARSE_ERROR.toString()));
        }
        return date;
    }
}
