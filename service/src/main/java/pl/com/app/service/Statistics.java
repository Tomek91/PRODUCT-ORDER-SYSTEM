package pl.com.app.service;


import pl.com.app.reader.DataReader;
import pl.com.app.repository.model.enums.EGuarantee;
import pl.com.app.repository.model.parsers.EGuaranteeParser;
import pl.com.app.repository.model.parsers.Parser;
import pl.com.app.service.menu.Menu;
import pl.com.app.service.menu.ProductsOrdersSimulator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Statistics {
    private ErrorsStatisticsService errorsStatisticsService = new ErrorsStatisticsService();
    private RetrieveDataService retrieveDataService = new RetrieveDataService();
    private DataReader dataReader = new DataReader();


    public void statistics() {
        String data = null;
        do {
            System.out.println(ProductsOrdersSimulator.PREFIX);
            System.out.println("Statystyki.\nNapisz, co chcesz zobaczyć.");
            Menu statisticsMenu = Menu.builder().menuList(Menu.createStatisticsMenu()).build();
            System.out.println(Menu.showMenu(statisticsMenu.getMenuList()));
            data = dataReader.getString();
            System.out.println(ProductsOrdersSimulator.PREFIX);
            retrieveStatisticsData(data);
        } while (!data.equalsIgnoreCase("q"));
    }

    private void retrieveStatisticsData(String data) {
        switch (data){
            case "a":{
                productsInfoWithMaxPriceByCategory();
                break;
            }
            case "b":{
                productsOrdersByCustomersWithCountryAndAge();
                break;
            }
            case "c":{
                productsWithGuaranteeAndComponents();
                break;
            }
            case "d":{
                shopsAndProductsWithDiffCountries();
                break;
            }
            case "e":{
                producersWithTradeNameAndSumOfQuantity();
                break;
            }
            case "f":{
                customersOrdersWithDatesAndPriceMoreThan();
                break;
            }
            case "g":{
                productsByCustomer();
                break;
            }
            case "h":{
                productAndCustomerWithTheSameCountry();
                break;
            }
            case "q":{
                System.out.println("Główne menu programu.");
                break;
            }
            default:
                System.out.println("Niepopoprawny kod menu. Spróbuj jeszcze raz.");
        }
    }

    private void productsInfoWithMaxPriceByCategory() {
        System.out.println("Produkty o największej cenie w każdej kategorii");
        System.out.println(Converts.getOrAbsence(retrieveDataService.getProductsInfoWithMaxPriceByCategory()));
    }

    private void productsOrdersByCustomersWithCountryAndAge() {
        System.out.println("Podaj kraj ...");
        String countryName = dataReader.getString();
        System.out.println("Podaj wiek od ...");
        Integer ageFrom = dataReader.getInteger();
        System.out.println("Podaj wiek do ...");
        Integer ageTo = dataReader.getInteger();
        System.out.println("Produkty, które zamawiane były przez klientów pochodzących z kraju o nazwie i wieku");
        System.out.println(Converts
                .getOrAbsence(
                        retrieveDataService.getProductsOrdersByCustomersWithCountryAndAge(countryName, ageFrom, ageTo)));
    }

    private void productsWithGuaranteeAndComponents() {
        String data = null;
        List<EGuarantee> eGuarantees = new ArrayList<>();
        Parser<EGuarantee> eGuaranteeParser = new EGuaranteeParser();
        while(true){
            System.out.println("Podaj nazwy usług gwarancyjnych (q - kończy wprowadzanie)");
            data = dataReader.getString();
            if ("q" .equalsIgnoreCase(data)){
                break;
            }
            eGuarantees.add(eGuaranteeParser.parse(data));
        }
        System.out.println("Produkty, które obejmuje gwarancja.");
        System.out.println(Converts.getOrAbsence(retrieveDataService.getProductsWithGuaranteeAndComponents(eGuarantees)));
    }

    private void shopsAndProductsWithDiffCountries() {
        System.out.println("Sklepy, które w magazynie posiadają produkty, których kraj pochodzenia jest inny niż kraje magazynów");
        System.out.println(Converts.getOrAbsence(retrieveDataService.getShopsAndProductsWithDiffCountries()));
    }

    private void producersWithTradeNameAndSumOfQuantity() {
        System.out.println("Podaj nazwę branży ...");
        String tradeName = dataReader.getString();
        System.out.println("Podaj ilość sztuk produktów ...");
        Integer sumOfQuantity = dataReader.getInteger();
        System.out.println("Producenci o nazwie branży podanej przez użytkownika, którzy wyprodukowali produkty o łącznej ilości sztuk większej niż liczba podana przez użytkownika");
        System.out.println(Converts.getOrAbsence(retrieveDataService.getProducersWithTradeNameAndSumOfQuantity(tradeName, sumOfQuantity)));
    }

    private void customersOrdersWithDatesAndPriceMoreThan() {
        System.out.println("Podaj datę od ...");
        LocalDate dateFrom = dataReader.getLocalDate();
        System.out.println("Podaj datę do ...");
        LocalDate dateTo = dataReader.getLocalDate();
        System.out.println("Podaj kwotę zamówienia ...");
        BigDecimal chosenPrice = dataReader.getBigDecimal();
        System.out.println("Zamówienia, które złożono w przedziale dat i kwocie zamówienia większej niż wartość podana przez użytkownika.");
        System.out.println(
                Converts.getOrAbsence(
                        retrieveDataService.getCustomersOrdersWithDatesAndPriceMoreThan(dateFrom, dateTo, chosenPrice)));
    }

    private void productsByCustomer() {
        System.out.println("Podaj imię ...");
        String name = dataReader.getString();
        System.out.println("Podaj nazwisko ...");
        String surnname = dataReader.getString();
        System.out.println("Podaj kraj ...");
        String countryName = dataReader.getString();
        System.out.println("Lista produktów, które zamówił klient o imieniu, nazwisku oraz nazwie kraju pochodzenia pobranych od użytkownika");
        System.out.println(
                Converts.getOrAbsence(retrieveDataService.getProductsByCustomer(name, surnname, countryName)));
    }

    private void productAndCustomerWithTheSameCountry() {
        System.out.println("Klienci, którzy zamówili przynajmniej jeden produkt pochodzący z tego samego kraju co klient.");
        System.out.println(Converts.getOrAbsence(retrieveDataService.getProductAndCustomerWithTheSameCountry()));
    }

    public void statisticsErrors() {
        String data = null;
        do {
            System.out.println(ProductsOrdersSimulator.PREFIX);
            System.out.println("Statystyki błedów.\nNapisz, co chcesz zobaczyć.");
            Menu statisticsErrorsMenu = Menu.builder().menuList(Menu.createStatisticsErrorsMenu()).build();
            System.out.println(Menu.showMenu(statisticsErrorsMenu.getMenuList()));
            data = dataReader.getString();
            System.out.println(ProductsOrdersSimulator.PREFIX);
            retrieveStatisticsErrorsData(data);
        } while (!data.equalsIgnoreCase("q"));
    }

    private void retrieveStatisticsErrorsData(String data) {
        switch (data){
            case "e1":{
                new Statistics().tableNameGeneratedTheMostErrors();
                break;
            }
            case "e2":{
                new Statistics().dateWhichGeneratedTheMostErrors();
                break;
            }
            case "e3":{
                new Statistics().messageWhichGeneratedTheMostErrors();
                break;
            }
            case "q":{
                System.out.println("Główne menu programu.");
                break;
            }
            default:
                System.out.println("Niepopoprawny kod menu. Spróbuj jeszcze raz.");
        }
    }

    private void messageWhichGeneratedTheMostErrors() {
        System.out.println("Nazwa komunikatu błędu, który pojawiał się najczęściej w tabeli błędów.");
        System.out.println(Converts.getOrAbsence(Optional.of(errorsStatisticsService.getMessageWhichGeneratedTheMostErrors())));
    }

    private void dateWhichGeneratedTheMostErrors() {
        System.out.println("Data, dla której wystąpiło najwięcej błędów.");
        System.out.println(Converts.getOrAbsence(Optional.of(errorsStatisticsService.getDateWhichGeneratedTheMostErrors())));
    }

    private void tableNameGeneratedTheMostErrors() {
        System.out.println("Nazwa klasy, która najczęściej generowała błąd");
        System.out.println(Converts.getOrAbsence(Optional.of(errorsStatisticsService.getTableNameGeneratedTheMostErrors())));
    }
}
