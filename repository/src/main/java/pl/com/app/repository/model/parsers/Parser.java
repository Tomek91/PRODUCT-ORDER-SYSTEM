package pl.com.app.repository.model.parsers;

@FunctionalInterface
public interface Parser<T> {
    T parse(final String line);
}
