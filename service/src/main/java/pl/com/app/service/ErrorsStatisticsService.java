package pl.com.app.service;

import pl.com.app.exceptions.EErrorsMessage;
import pl.com.app.exceptions.MyException;
import pl.com.app.repository.ErrorsRepository;
import pl.com.app.repository.impl.ErrorsRepositoryImpl;
import pl.com.app.repository.model.Errors;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ErrorsStatisticsService {
    private ErrorsRepository errorsRepository = new ErrorsRepositoryImpl();

    public String getTableNameGeneratedTheMostErrors() {
        String arrayName = null;
        try {
            arrayName = errorsRepository.findAll()
                    .stream()
                    .filter(x -> x.getMessage().contains(";"))
                    .map(x -> x.getMessage().split(";")[0])
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                    .entrySet()
                    .stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElseThrow(NullPointerException::new);
        } catch (Exception e) {
            throw new MyException(String.join(";",
                    this.getClass().getCanonicalName(),
                    EErrorsMessage.GET_TABLE_NAME_GENERATED_THE_MOST_ERRORS.toString()));
        }
        return arrayName;
    }

    public LocalDate getDateWhichGeneratedTheMostErrors() {
        LocalDate date = null;
        try {
            date = errorsRepository.findAll()
                    .stream()
                    .filter(x -> x.getMessage().contains(";"))
                    .map(Errors::getDateTime)
                    .map(LocalDateTime::toLocalDate)
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                    .entrySet()
                    .stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElseThrow(NullPointerException::new);

        } catch (Exception e) {
            throw new MyException(String.join(";",
                    this.getClass().getCanonicalName(),
                    EErrorsMessage.GET_DATE_GENERATED_THE_MOST_ERRORS.toString()));
        }
        return date;
    }

    public String getMessageWhichGeneratedTheMostErrors() {
        String message = null;
        try {
            message = errorsRepository.findAll()
                    .stream()
                    .filter(x -> x.getMessage().contains(";"))
                    .map(x -> x.getMessage().split(";")[1])
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                    .entrySet()
                    .stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElseThrow(NullPointerException::new);

        } catch (Exception e) {
            throw new MyException(String.join(";",
                    this.getClass().getCanonicalName(),
                    EErrorsMessage.GET_MESSAGE_GENERATED_THE_MOST_ERRORS.toString()));
        }
        return message;
    }
}
