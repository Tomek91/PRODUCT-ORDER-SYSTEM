package pl.com.app.service;



import pl.com.app.repository.model.enums.EPayment;

import java.math.BigDecimal;

public class ValidationService {
    private AppGetDataService appGetDataService = new AppGetDataService();

    public boolean isExistCustomerWithNameSurnameAndCountry(String name, String surname, String countryName) {
        return appGetDataService.getCustomerWithNameSurnameAndCountry(name, surname, countryName).isPresent();
    }

    public boolean isExistProducerWithNameTradeAndCountry(String name, String tradeName, String countryName) {
        return appGetDataService.getProducerWithNameTradeAndCountry(name, tradeName, countryName).isPresent();
    }

    public boolean isExistProductWithNameCategoryProducerCountryAndTrade(String name, BigDecimal price, String categoryName, String producerName, String countryName, String tradeName) {
        return appGetDataService.getProductWithNameCategoryProducerCountryAndTrade(name, price, categoryName, producerName, countryName, tradeName).isPresent();
    }

    public boolean isExistShopWithNameAndCountry(String name, String countryName) {
        return appGetDataService.getShopWithNameAndCountry(name, countryName).isPresent();
    }

    public boolean isExistCountryWithName(String name) {
        return appGetDataService.getCountryWithName(name).isPresent();
    }

    public boolean isExistStockWithProductAndShop(Long productId, Long shopId) {
        return appGetDataService.getStockWithProductAndShop(productId, shopId).isPresent();
    }

    public boolean isExistPaymentWithName(EPayment ePayment) {
        return appGetDataService.getPaymentByName(ePayment).isPresent();
    }
}
