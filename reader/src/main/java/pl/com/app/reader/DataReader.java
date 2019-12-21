package pl.com.app.reader;


import pl.com.app.repository.model.enums.EGuarantee;
import pl.com.app.repository.model.enums.EPayment;
import pl.com.app.repository.model.parsers.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

public class DataReader {
    private static Scanner sc = new Scanner(System.in);

    public static void close() {
        sc.close();
    }

    public BigDecimal getBigDecimal(){
        return new BigDecimalParser().parse(sc.nextLine());
    }

    public Integer getInteger(){
        return new IntegerParser().parse(sc.nextLine());
    }

    public LocalDate getLocalDate(){
        return new DateParser().parse(sc.nextLine());
    }

    public EGuarantee getEGuarantee(){
        return new EGuaranteeParser().parse(sc.nextLine());
    }

    public EPayment getEPayment(){
        return new EPaymentParser().parse(sc.nextLine());
    }

    public String getString(){
        return sc.nextLine();
    }
}
