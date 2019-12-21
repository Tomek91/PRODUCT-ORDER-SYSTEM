package pl.com.app.parsers.json;


import java.util.List;

public interface JsonParser<T> {
    List<T> fromJsonInStringToModel(String jsonData);
    List<T> fromJsonInFileToModel(String fileName);
    String fromModelToJsonInString(List<T> model);
    void fromModelToJsonInFile(String fileName, List<T> model);


}
