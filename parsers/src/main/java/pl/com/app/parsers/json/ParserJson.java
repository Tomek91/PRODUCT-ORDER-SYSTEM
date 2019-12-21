package pl.com.app.parsers.json;

public class ParserJson<T> {

    private Class<T> type;
    public ParserJson(Class<T> cls)
    {
        type= cls;
    }
    public Class<T> getType(){return type;}

}
