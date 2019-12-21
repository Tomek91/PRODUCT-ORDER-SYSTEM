package pl.com.app.service.menu;

import pl.com.app.dto.ErrorsDTO;
import pl.com.app.exceptions.MyException;
import pl.com.app.reader.DataReader;
import pl.com.app.repository.generic.connection.DbConnection;
import pl.com.app.service.AddItems;
import pl.com.app.service.AppAddService;
import pl.com.app.service.DataInitializeService;
import pl.com.app.service.Statistics;

public class ProductsOrdersSimulator {

    private DataReader dataReader = new DataReader();
    public static final String PREFIX = "<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>";

    public void productsOrdersSimulator() {
        System.out.println("Witaj w symulatorze zarządzania systemem zamówień produktów !!!");
        System.out.println("Oto Twoje menu. Każda liczba odpowiada akcji, którę chcesz wykonać.\n" +
                "Pamiętaj, w każdej chwili możesz zakończyć aplikację naciskając 'q'.");

        try{
            Menu menu = Menu.builder().menuList(Menu.createMenu()).build();
            String data = null;
            do {
                System.out.println(ProductsOrdersSimulator.PREFIX);
                System.out.println(Menu.showMenu(menu.getMenuList()));
                System.out.println("Wybierz pozycję menu...");
                data = dataReader.getString();
                retrieveData(data);
            } while (!data.equalsIgnoreCase("q"));
        } catch (MyException e) {
            System.err.println(e.getMessage());
            AppAddService appAddService = new AppAddService();
            appAddService.addError(ErrorsDTO.builder()
                    .message(e.getExceptionMessage())
                    .dateTime(e.getExceptionDateTime())
                    .build());
        }
    }

    private void retrieveData(String data) {
        switch (data){
            case "1":{
                new AddItems().addCustomer();
                break;
            }
            case "2":{
                new AddItems().addShop();
                break;
            }
            case "3":{
                new AddItems().addProducer();
                break;
            }
            case "4":{
                new AddItems().addProduct();
                break;
            }
            case "5":{
                new AddItems().addStock();
                break;
            }
            case "6":{
                new AddItems().addCustomerOrder();
                break;
            }
            case "7":{
                new Statistics().statistics();
                break;
            }
            case "8":{
                new Statistics().statisticsErrors();
                break;
            }
            case "9":{
                loadDefaultData();
                break;
            }
            case "q":{
                System.out.println("Koniec programu.");
                DbConnection.closeEntityManagerFactory();
                DataReader.close();

                break;
            }
            default:
                System.out.println("Niepopoprawny kod menu. Spróbuj jeszcze raz.");
        }
    }

    private void loadDefaultData() {
        System.out.println("Ładowanie domyślnych danych...");
        DataInitializeService dataInitializeService = new DataInitializeService();
        dataInitializeService.initialize();
        System.out.println("Pomyślnie załadowano dane do bazy danych.");
    }
}
