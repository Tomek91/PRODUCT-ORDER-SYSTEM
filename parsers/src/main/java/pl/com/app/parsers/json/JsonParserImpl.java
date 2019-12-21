package pl.com.app.parsers.json;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import pl.com.app.exceptions.EErrorsMessage;
import pl.com.app.exceptions.MyException;

import java.util.List;

public class JsonParserImpl<T> implements JsonParser<T>{

    private Class<T> type;

    public JsonParserImpl(Class<T> cls){
        type= cls;
    }

    @Override
    public List<T> fromJsonInStringToModel(String jsonData) {
        List<T> model = null;
        try {
            if (jsonData == null){
                throw new NullPointerException("JSON DATA IS NULL");
            }
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            JavaType itemType = mapper.getTypeFactory().constructCollectionType(List.class, type);
            model = mapper.readValue(jsonData, itemType);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(String.join(";",
                    this.getClass().getCanonicalName(),
                    EErrorsMessage.PARSER_JSON_TO_MODEL.toString()));
        }
        return model;
    }

    @Override
    public List<T> fromJsonInFileToModel(String fileName) {
        return fromJsonInStringToModel(FileParser.fileParser(fileName));
    }

    @Override
    public String fromModelToJsonInString(List<T> model) {
        String jsonInString = null;
        try {
            if (model == null){
                throw new NullPointerException("MODEL IS NULL");
            }
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            jsonInString = mapper.writeValueAsString(model);
        } catch (Exception e) {
            throw new MyException(String.join(";",
                    this.getClass().getCanonicalName(),
                    EErrorsMessage.PARSER_MODEL_TO_JSON.toString()));
        }
        return jsonInString;
    }

    @Override
    public void fromModelToJsonInFile(String fileName, List<T> model) {
        FileParser.fileSave(fileName, fromModelToJsonInString(model));
    }
}
