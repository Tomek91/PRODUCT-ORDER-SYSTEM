package pl.com.app.service;

import pl.com.app.exceptions.EErrorsMessage;
import pl.com.app.exceptions.MyException;
import pl.com.app.repository.*;
import pl.com.app.repository.impl.*;
import pl.com.app.repository.model.*;
import pl.com.app.repository.model.enums.EPayment;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AppGetDataService {
    private CustomerRepository customerRepository = new CustomerRepositoryImpl();
    private StockRepository stockRepository = new StockRepositoryImpl();
    private CountryRepository countryRepository = new CountryRepositoryImpl();
    private ProducerRepository producerRepository = new ProducerRepositoryImpl();
    private ProductRepository productRepository = new ProductRepositoryImpl();
    private ShopRepository shopRepository = new ShopRepositoryImpl();
    private PaymentRepository paymentRepository = new PaymentRepositoryImpl();

    public Optional<Customer> getCustomerWithNameSurnameAndCountry(String name, String surname, String countryName) {
        Optional<Customer> opt;
        try {
            if (name == null || surname == null || countryName == null){
                throw new NullPointerException("CONDITIONS ARE NULL");
            }
            opt = customerRepository.findByNameSurname(name, surname)
                    .stream()
                    .filter(x -> x.getCountry().getName().equals(countryName))
                    .findFirst();
        } catch (Exception e) {
            throw new MyException(String.join(";",
                    this.getClass().getCanonicalName(),
                    EErrorsMessage.GET_CUSTOMER_WITH_NAME_SURNAME_AND_COUNTRY_NAME.toString()));
        }
        return opt;
    }

    public Optional<Producer> getProducerWithNameTradeAndCountry(String name, String tradeName, String countryName) {
        Optional<Producer> opt;
        try {
            if (name == null || tradeName == null || countryName == null){
                throw new NullPointerException("CONDITIONS ARE NULL");
            }
            opt = producerRepository.findAll()
                    .stream()
                    .filter(x -> x.getName().equals(name))
                    .filter(x -> x.getTrade().getName().equals(tradeName))
                    .filter(x -> x.getCountry().getName().equals(countryName))
                    .findFirst();
        } catch (Exception e) {
            throw new MyException(String.join(";",
                    this.getClass().getCanonicalName(),
                    EErrorsMessage.GET_PRODUCER_WITH_NAME_TRADE_AND_COUNTRY_NAME.toString()));
        }
        return opt;
    }

    public Optional<Product> getProductWithNameCategoryProducerCountryAndTrade(String name, BigDecimal price, String categoryName, String producerName, String countryName, String tradeName) {
        Optional<Product> opt;
        try {
            if (name == null || price == null || categoryName == null || producerName == null || countryName == null || tradeName == null){
                throw new NullPointerException("CONDITIONS ARE NULL");
            }
            opt = productRepository.findAll()
                    .stream()
                    .filter(x -> x.getName().equals(name))
                    .filter(x -> x.getPrice().equals(price))
                    .filter(x -> x.getCategory().getName().equals(categoryName))
                    .filter(x -> x.getProducer().getName().equals(producerName))
                    .filter(x -> x.getProducer().getCountry().getName().equals(countryName))
                    .filter(x -> x.getProducer().getTrade().getName().equals(tradeName))
                    .findFirst();
        } catch (Exception e) {
            throw new MyException(String.join(";",
                    this.getClass().getCanonicalName(),
                    EErrorsMessage.GET_PRODUCT_WITH_NAME_CATEGORY_AND_PRODUCER.toString()));
        }
        return opt;
    }

    public Optional<Shop> getShopWithNameAndCountry(String name, String countryName) {
        Optional<Shop> opt;
        try {
            if (name == null || countryName == null){
                throw new NullPointerException("CONDITIONS ARE NULL");
            }
            opt = shopRepository.findAll()
                    .stream()
                    .filter(x -> x.getName().equals(name))
                    .filter(x -> x.getCountry().getName().equals(countryName))
                    .findFirst();
        } catch (Exception e) {
            throw new MyException(String.join(";",
                    this.getClass().getCanonicalName(),
                    EErrorsMessage.GET_SHOP_WITH_NAME_AND_COUNTRY.toString()));
        }
        return opt;
    }

    public Optional<Country> getCountryWithName(String name) {
        Optional<Country> opt;
        try {
            if (name == null){
                throw new NullPointerException("CONDITION IS NULL");
            }
            opt = countryRepository.findByName(name);
        } catch (Exception e) {
            throw new MyException(String.join(";",
                    this.getClass().getCanonicalName(),
                    EErrorsMessage.GET_COUNTRY_WITH_NAME.toString()));
        }
        return opt;
    }

    public List<Stock> getStockWithProductAndShop(String productName, String categoryName, String shopName, String shopCountry) {
        List<Stock> stockList;
        try {
            if (productName == null || categoryName == null || shopName == null || shopCountry == null){
                throw new NullPointerException("CONDITIONS ARE NULL");
            }
            stockList = stockRepository.findAll()
                    .stream()
                    .filter(x -> x.getProduct().getName().equals(productName))
                    .filter(x -> x.getProduct().getCategory().getName().equals(categoryName))
                    .filter(x -> x.getShop().getName().equals(shopName))
                    .filter(x -> x.getShop().getCountry().getName().equals(shopCountry))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new MyException(String.join(";",
                    this.getClass().getCanonicalName(),
                    EErrorsMessage.GET_STOCK_WITH_PRODUCT_AND_SHOP.toString()));
        }
        return stockList;
    }

    public Optional<Stock> getStockWithProductAndShop(Long productId, Long shopId) {
        Optional<Stock> opt;
        try {
            if (productId == null || shopId == null){
                throw new NullPointerException("CONDITIONS ARE NULL");
            }
            opt = stockRepository.findByProductIdAndShopId(productId, shopId);
        } catch (Exception e) {
            throw new MyException(String.join(";",
                    this.getClass().getCanonicalName(),
                    EErrorsMessage.GET_STOCK_WITH_PRODUCT_AND_SHOP.toString()));
        }
        return opt;
    }

    public List<Product> getProductsByNameAndCategoryName(String name, String categoryName) {
        List<Product> productList;
        try {
            if (name == null || categoryName == null){
                throw new NullPointerException("CONDITIONS ARE NULL");
            }
            productList = productRepository.findAll()
                    .stream()
                    .filter(x -> x.getName().equals(name))
                    .filter(x -> x.getCategory().getName().equals(categoryName))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new MyException(String.join(";",
                    this.getClass().getCanonicalName(),
                    EErrorsMessage.GET_PRODUCT_BY_NAME_AND_CATEGORY_NAME.toString()));
        }
        return productList;
    }

    public Optional<Payment> getPaymentByName(EPayment ePayment) {
        Optional<Payment> opt;
        try {
            if (ePayment == null){
                throw new NullPointerException("PAYMENT IS NULL");
            }
            opt = paymentRepository.findByName(ePayment);
        } catch (Exception e) {
            throw new MyException(String.join(";",
                    this.getClass().getCanonicalName(),
                    EErrorsMessage.GET_PAYMENT_WITH_NAME.toString()));
        }
        return opt;
    }

    public Optional<Product> getProductByNameCategoryProducerTradeAndCountry(String name, String categoryName, String producerName, String tradeName, String countryName) {
        Optional<Product> opt;
        try {
            if (name == null || categoryName == null || producerName == null || tradeName == null || countryName == null){
                throw new NullPointerException("CONDITIONS ARE NULL");
            }
            opt = productRepository.findAll()
                    .stream()
                    .filter(x -> x.getName().equals(name))
                    .filter(x -> x.getCategory().getName().equals(categoryName))
                    .filter(x -> x.getProducer().getName().equals(producerName))
                    .filter(x -> x.getProducer().getTrade().getName().equals(tradeName))
                    .filter(x -> x.getProducer().getCountry().getName().equals(countryName))
                    .findFirst();
        } catch (Exception e) {
            throw new MyException(String.join(";",
                    this.getClass().getCanonicalName(),
                    EErrorsMessage.GET_PRODUCT_BY_NAME_CATEGORY_PRODUCER_TRADE_AND_COUNTRY.toString()));
        }
        return opt;
    }
}
