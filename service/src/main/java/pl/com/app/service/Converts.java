package pl.com.app.service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public interface Converts {

    static <T> String getOrAbsence(Optional<T> movieOpt) {
        if (movieOpt.isPresent()){
            return movieOpt.get().toString();
        } else {
            return "BRAK";
        }
    }

    static <T> String getOrAbsence(List<T> items) {
        if (items.isEmpty()){
            return "BRAK";
        } else {
            return items.stream()
                    .map(Objects::toString)
                    .collect(Collectors.joining("\n"));
        }
    }

    static <T, R> String getOrAbsence(Map<T, R> items) {
        if (items.isEmpty()){
            return "BRAK";
        } else {
            return items
                    .entrySet()
                    .stream()
                    .map(e -> String.join(" ", e.getKey().toString(), e.getValue().toString()))
                    .collect(Collectors.joining("\n"));
        }
    }
}
