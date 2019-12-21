package pl.com.app;


import pl.com.app.service.menu.ProductsOrdersSimulator;

public class App
{
    public static void main(String[] args) {

//        try {
//            new JsonParserImpl<CountryDTO>().fromModelToJsonInFile(FileNames.COUNTRIES, ModelsData.getCountries());
//            new JsonParserImpl<TradeDTO>().fromModelToJsonInFile(FileNames.TRADES, ModelsData.getTrades());
//            new JsonParserImpl<PaymentDTO>().fromModelToJsonInFile(FileNames.PAYMENTS, ModelsData.getPayments());
//            new JsonParserImpl<CategoryDTO>().fromModelToJsonInFile(FileNames.CATEGORIES, ModelsData.getCategories());
//            new JsonParserImpl<CustomerDTO>().fromModelToJsonInFile(FileNames.CUSTOMERS, ModelsData.getCustomers());
//            new JsonParserImpl<ProducerDTO>().fromModelToJsonInFile(FileNames.PRODUCERS, ModelsData.getProducers());
//            new JsonParserImpl<ShopDTO>().fromModelToJsonInFile(FileNames.SHOPS, ModelsData.getShops());
//            new JsonParserImpl<ProductDTO>().fromModelToJsonInFile(FileNames.PRODUCTS, ModelsData.getProducts());
//            new JsonParserImpl<StockDTO>().fromModelToJsonInFile(FileNames.STOCKS, ModelsData.getStocks());
//            new JsonParserImpl<CustomerOrderDTO>().fromModelToJsonInFile(FileNames.CUSTOMER_ORDERS, ModelsData.getCustomerOrders());
//
//        } catch (MyException e) {
//            System.err.println(e.getMessage());
//            AppAddService appAddService = new AppAddService();
//            appAddService.addError(ErrorsDTO.builder()
//                    .message(e.getExceptionMessage())
//                    .dateTime(e.getExceptionDateTime())
//                    .build());
//        }


        new ProductsOrdersSimulator().productsOrdersSimulator();
    }
}
