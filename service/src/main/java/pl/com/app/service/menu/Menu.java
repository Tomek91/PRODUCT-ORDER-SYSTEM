package pl.com.app.service.menu;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.com.app.exceptions.EErrorsMessage;
import pl.com.app.exceptions.MyException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Menu {
    private List<MenuItem> menuList;

    public static String showMenu(List<MenuItem> menu){
        if (menu == null){
            throw new MyException(String.join(";",
                    Menu.class.getCanonicalName(),
                    EErrorsMessage.MENU_IS_NULL.toString()));

        }
        return menu
                .stream()
                .map(x -> String.join(" - ", x.getCode(), x.getName()))
                .collect(Collectors.joining("\n"));
    }

    public static List<MenuItem> createMenu() {
        return Arrays.asList(
                MenuItem.builder().code("1").name("dodaj klienta").build(),
                MenuItem.builder().code("2").name("dodaj sklep").build(),
                MenuItem.builder().code("3").name("dodaj producenta").build(),
                MenuItem.builder().code("4").name("dodaj produkt").build(),
                MenuItem.builder().code("5").name("dodaj pozycję w magazynie").build(),
                MenuItem.builder().code("6").name("dodaj zamówienie").build(),
                MenuItem.builder().code("7").name("statystyki").build(),
                MenuItem.builder().code("8").name("statystyka błędów").build(),
                MenuItem.builder().code("9").name("załaduj dane domyślne").build(),
                MenuItem.builder().code("q").name("koniec aplikacji").build()
        );
    }

    public static List<MenuItem> createStatisticsErrorsMenu() {
        return Arrays.asList(
                MenuItem.builder().code("e1").name("nazwa klasy, która najczęściej generowała błąd.").build(),
                MenuItem.builder().code("e2").name("data, dla której wystąpiło najwięcej błędów").build(),
                MenuItem.builder().code("e3").name("nazwa komunikatu błędu, który pojawiał się najczęściej w tabeli błędów.").build(),
                MenuItem.builder().code("q").name("wyjdź").build()
        );
    }

    public static List<MenuItem> createStatisticsMenu() {
        return Arrays.asList(
                MenuItem.builder().code("a").name("informacja na temat produktów o największej cenie w każdej kategorii.").build(),
                MenuItem.builder().code("b").name("lista wszystkich produktów, które zamawiane były przez klientów pochodzących z kraju o nazwie i wieku z przedziału określanego przez użytkownika").build(),
                MenuItem.builder().code("c").name("lista produktów, które obejmuje gwarancja.").build(),
                MenuItem.builder().code("d").name("lista sklepów, które w magazynie posiadają produkty, których kraj pochodzenia jest inny niż kraje magazynów.").build(),
                MenuItem.builder().code("e").name("producenci o nazwie branży podanej przez użytkownika, którzy wyprodukowali produkty o łącznej ilości sztuk większej niż liczba podana przez użytkownika").build(),
                MenuItem.builder().code("f").name("zamówienia, które złożono w przedziale dat i kwocie zamówienia większej niż wartość podana przez użytkownika.").build(),
                MenuItem.builder().code("g").name("lista produktów, które zamówił klient o imieniu, nazwisku oraz nazwie kraju pochodzenia pobranych od użytkownika.").build(),
                MenuItem.builder().code("h").name("klienci, którzy zamówili przynajmniej jeden produkt pochodzący z tego samego kraju co klient.").build(),
                MenuItem.builder().code("q").name("wyjdź").build()
        );
    }

}
