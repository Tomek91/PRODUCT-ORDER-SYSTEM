package pl.com.app.repository.model.parsers;


import pl.com.app.exceptions.EErrorsMessage;
import pl.com.app.exceptions.MyException;
import pl.com.app.repository.model.enums.EGuarantee;

public class EGuaranteeParser implements Parser<EGuarantee> {

    @Override
    public EGuarantee parse(String dateInString) {
        EGuarantee eGuarantee;
        try{
            eGuarantee = EGuarantee.valueOf(dateInString);
        } catch (Exception e){
            throw new MyException(String.join(";",
                    this.getClass().getCanonicalName(),
                    EErrorsMessage.EGUARANTEE_PARSE_ERROR.toString()));
        }
        return eGuarantee;
    }
}
