package pl.com.app.parsers.json;


import pl.com.app.exceptions.EErrorsMessage;
import pl.com.app.exceptions.MyException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface FileParser {

    static String fileParser(String filename) {
        String jsonData = null;
        try (Stream<String> lines = Files.lines(Paths.get(filename))) {
            jsonData = lines
                    .filter(Objects::nonNull)
                    .collect(Collectors.joining(" "));
        } catch (Exception e) {
            throw new MyException(String.join(";",
                    JsonParser.class.getCanonicalName(),
                    EErrorsMessage.FILE_PARSE_EXCEPTION.toString()));
        }
        return jsonData;
    }

    static void fileSave(String fileName, String jasonData) {
        try {
            Path path = Paths.get(fileName);
            Files.write(path, jasonData.getBytes());

        } catch (Exception e) {
            throw new MyException(String.join(";",
                    JsonParser.class.getCanonicalName(),
                    EErrorsMessage.FILE_SAVE_EXCEPTION.toString()));
        }
    }

}
