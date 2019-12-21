package pl.com.app.repository.model.parsers;

import pl.com.app.exceptions.EErrorsMessage;
import pl.com.app.exceptions.MyException;
import pl.com.app.repository.model.enums.EPayment;

public class EPaymentParser implements Parser<EPayment> {

    @Override
    public EPayment parse(String dateInString) {
        EPayment ePayment;
        try{
            ePayment = EPayment.valueOf(dateInString);
        } catch (Exception e){
            throw new MyException(String.join(";",
                    this.getClass().getCanonicalName(),
                    EErrorsMessage.EPAYMENT_PARSE_ERROR.toString()));
        }
        return ePayment;
    }
}
