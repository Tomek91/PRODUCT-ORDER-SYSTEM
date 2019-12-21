package pl.com.app.service;

import pl.com.app.dto.CustomerInfoDTO;
import pl.com.app.dto.ProductInfoDTO;
import pl.com.app.exceptions.EErrorsMessage;
import pl.com.app.exceptions.MyException;
import pl.com.app.repository.CustomerOrderRepository;
import pl.com.app.repository.ProducerRepository;
import pl.com.app.repository.ProductRepository;
import pl.com.app.repository.StockRepository;
import pl.com.app.repository.impl.CustomerOrderRepositoryImpl;
import pl.com.app.repository.impl.ProducerRepositoryImpl;
import pl.com.app.repository.impl.ProductRepositoryImpl;
import pl.com.app.repository.impl.StockRepositoryImpl;
import pl.com.app.repository.model.*;
import pl.com.app.repository.model.enums.EGuarantee;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RetrieveDataService {
    private ProducerRepository producerRepository = new ProducerRepositoryImpl();
    private ProductRepository productRepository = new ProductRepositoryImpl();
    private CustomerOrderRepository customerOrderRepository = new CustomerOrderRepositoryImpl();
    private StockRepository stockRepository = new StockRepositoryImpl();

    public Map<Category, ProductInfoDTO> getProductsInfoWithMaxPriceByCategory() {
        Map<Category, ProductInfoDTO> productsInfoWithMaxPriceByCategory = null;
        try{
            productsInfoWithMaxPriceByCategory = productRepository.findAll()
                    .stream()
                    .collect(Collectors.groupingBy(Product::getCategory))
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            e -> e.getValue()
                                    .stream()
                                    .max(Comparator.comparing(Product::getPrice))
                                    .map(x -> ProductInfoDTO.builder()
                                            .name(x.getName())
                                            .price(x.getPrice())
                                            .categoryName(x.getCategory().getName())
                                            .producerName(x.getProducer().getName())
                                            .countryName(x.getProducer().getCountry().getName())
                                            .ordersCount(x.getCustomerOrders().size())
                                            .build()
                                    )
                                    .orElseThrow(NullPointerException::new)
                    ));
        }catch (Exception e){
            throw new MyException(String.join(";",
                    this.getClass().getCanonicalName(),
                    EErrorsMessage.GET_PRODUCTS_INFO_WITH_MAX_PRICE_BY_CATEGORY.toString()));
        }
        return productsInfoWithMaxPriceByCategory;
    }

    public List<ProductInfoDTO> getProductsOrdersByCustomersWithCountryAndAge(String countryName, Integer ageFrom, Integer ageTo) {
        List<ProductInfoDTO> productsOrdersByCustomersWithCountryAndAge = null;
        try{
            if (countryName == null || ageFrom == null || ageTo == null){
                throw new NullPointerException("CONDITIONS ARE NULL");
            }

            productsOrdersByCustomersWithCountryAndAge = customerOrderRepository.findAll()
                    .stream()
                    .filter(x -> x.getCustomer().getCountry().getName().equals(countryName))
                    .filter(x -> {
                        Integer age = Period.between(x.getCustomer().getBirthDate(), LocalDate.now()).getYears();
                        return age >= ageFrom && age <= ageTo;
                    })
                    .map(CustomerOrder::getProduct)
                    .map(x -> ProductInfoDTO.builder()
                            .name(x.getName())
                            .price(x.getPrice())
                            .categoryName(x.getCategory().getName())
                            .producerName(x.getProducer().getName())
                            .countryName(x.getProducer().getCountry().getName())
                            .ordersCount(0)
                            .build()
                    )
                    .sorted(Comparator.comparing(ProductInfoDTO::getPrice, Comparator.reverseOrder()))
                    .distinct()
                    .collect(Collectors.toList());
        }catch (Exception e){
            throw new MyException(String.join(";",
                    this.getClass().getCanonicalName(),
                    EErrorsMessage.GET_PRODUCTS_ORDERS_BY_CUSTOMERS_WITH_COUNTRY_AND_AGE.toString()));
        }
        return productsOrdersByCustomersWithCountryAndAge;
    }

    public Map<String, List<ProductInfoDTO>> getProductsWithGuaranteeAndComponents(List<EGuarantee> eGuarantees) {
        Map<String, List<ProductInfoDTO>> productsOrdersByCustomersWithCountryAndAge = null;
        try{
            if (eGuarantees == null){
                throw new NullPointerException("GUARANTEE ARRAY IS NULL");
            }

            productsOrdersByCustomersWithCountryAndAge = customerOrderRepository.findAllWithFetchGuarantee()
                    .stream()
                    .filter(x -> LocalDate.now().isBefore(x.getDate().plusYears(2L)))
                    .filter(x -> {
                        for (EGuarantee eG : eGuarantees) {
                            if (!x.getProduct().getEGuarantees().contains(eG)){
                                return false;
                            }
                        }
                        return true;
                    })
                    .map(CustomerOrder::getProduct)
                    .map(x -> ProductInfoDTO.builder()
                            .name(x.getName())
                            .price(x.getPrice())
                            .categoryName(x.getCategory().getName())
                            .producerName(x.getProducer().getName())
                            .countryName(x.getProducer().getCountry().getName())
                            .ordersCount(0)
                            .build()
                    )
                    .distinct()
                    .collect(Collectors.groupingBy(ProductInfoDTO::getCategoryName));
        }catch (Exception e){
            throw new MyException(String.join(";",
                    this.getClass().getCanonicalName(),
                    EErrorsMessage.GET_PRODUCTS_WITH_GUARANTEE_AND_COMPONENTS.toString()));
        }
        return productsOrdersByCustomersWithCountryAndAge;
    }

    public List<Shop> getShopsAndProductsWithDiffCountries() {
        List<Shop> shopsAndProductsWithDiffCountries = null;
        try{
            shopsAndProductsWithDiffCountries = stockRepository.findAll()
                    .stream()
                    .filter(x -> !x.getProduct().getProducer().getCountry().equals(x.getShop().getCountry()))
                    .map(Stock::getShop)
                    .distinct()
                    .collect(Collectors.toList());
        }catch (Exception e){
            throw new MyException(String.join(";",
                    this.getClass().getCanonicalName(),
                    EErrorsMessage.SHOPS_AND_PRODUCTS_WITH_DIFF_COUNTRIES.toString()));
        }
        return shopsAndProductsWithDiffCountries;
    }

    public List<Producer> getProducersWithTradeNameAndSumOfQuantity(String tradeName, Integer quantitySum) {
        List<Producer> producersWithTradeNameAndSumOfQuantity = null;
        try{
            if (tradeName == null || quantitySum == null){
                throw new NullPointerException("CONDITIONS ARE NULL");
            }

            producersWithTradeNameAndSumOfQuantity = producerRepository.findAllWithFetchProducts()
                    .stream()
                    .filter(x -> x.getTrade().getName().equals(tradeName))
                    .filter(x ->
                            quantitySum < x.getProducts()
                                    .stream()
                                    .flatMap(z -> z.getStock().stream())
                                    .mapToInt(Stock::getQuantity)
                                    .sum()
                    )
                    .collect(Collectors.toList());
        }catch (Exception e){
            throw new MyException(String.join(";",
                    this.getClass().getCanonicalName(),
                    EErrorsMessage.GET_PRODUCERS_WITH_TRADE_NAME_AND_SUM_OF_QUANTITY.toString()));
        }
        return producersWithTradeNameAndSumOfQuantity;
    }

    public List<CustomerOrder> getCustomersOrdersWithDatesAndPriceMoreThan(LocalDate dateFrom, LocalDate dateTo, BigDecimal price) {
        List<CustomerOrder> customersOrdersWithDatesAndPriceMoreThan = null;
        try{
            if (dateFrom == null || dateTo == null || price == null){
                throw new NullPointerException("CONDITIONS ARE NULL");
            }

            customersOrdersWithDatesAndPriceMoreThan = customerOrderRepository.findAllWithFetchGuarantee()
                    .stream()
                    .filter(x -> x.getDate().isAfter(dateFrom) && x.getDate().isBefore(dateTo))
                    .filter(x -> x.getProduct().getPrice()
                            .multiply(new BigDecimal(x.getQuantity()))
                            .multiply(new BigDecimal(1).subtract(x.getDiscount()))
                            .compareTo(price) > 0)
                    .collect(Collectors.toList());
        }catch (Exception e){
            throw new MyException(String.join(";",
                    this.getClass().getCanonicalName(),
                    EErrorsMessage.GET_CUSTOMERS_ORDERS_WITH_DATES_AND_PRICE_MORE_THAN.toString()));
        }
        return customersOrdersWithDatesAndPriceMoreThan;
    }

    public Map<Producer, List<Product>> getProductsByCustomer(String name, String surname, String countryName) {
        Map<Producer, List<Product>> productsByCustomer = null;
        try{
            if (name == null || surname == null || countryName == null){
                throw new NullPointerException("CONDITIONS ARE NULL");
            }

            productsByCustomer = customerOrderRepository.findAllWithFetchGuarantee()
                    .stream()
                    .filter(x -> x.getCustomer().getName().equals(name))
                    .filter(x -> x.getCustomer().getSurname().equals(surname))
                    .filter(x -> x.getCustomer().getCountry().getName().equals(countryName))
                    .map(CustomerOrder::getProduct)
                    .collect(Collectors.groupingBy(Product::getProducer));
        }catch (Exception e){
            throw new MyException(String.join(";",
                    this.getClass().getCanonicalName(),
                    EErrorsMessage.GET_PRODUCTS_BY_CUSTOMER.toString()));
        }
        return productsByCustomer;
    }

    public List<CustomerInfoDTO> getProductAndCustomerWithTheSameCountry() {
        List<CustomerInfoDTO> productAndCustomerWithTheSameCountry = null;
        try{
            final List<CustomerOrder> customerOrderList = customerOrderRepository.findAllWithFetchGuarantee();

            productAndCustomerWithTheSameCountry = customerOrderList
                    .stream()
                    .filter(x -> x.getCustomer().getCountry().equals(x.getProduct().getProducer().getCountry()))
                    .collect(Collectors.groupingBy(CustomerOrder::getCustomer))
                    .entrySet()
                    .stream()
                    .map(Map.Entry::getKey)
                    .map(x -> CustomerInfoDTO.builder()
                            .name(x.getName())
                            .surname(x.getSurname())
                            .age(Period.between(x.getBirthDate(), LocalDate.now()).getYears())
                            .countryName(x.getCountry().getName())
                            .ordersWithOtherCountryCount(
                                    customerOrderList
                                            .stream()
                                            .filter(z -> z.getCustomer().equals(x))
                                            .filter(z -> !x.getCountry().equals(z.getProduct().getProducer().getCountry()))
                                            .mapToInt(CustomerOrder::getQuantity)
                                            .count()
                            )
                            .build())
                    .collect(Collectors.toList());
        }catch (Exception e){
            throw new MyException(String.join(";",
                    this.getClass().getCanonicalName(),
                    EErrorsMessage.PRODUCT_AND_CUSTOMER_WITH_THE_SAME_COUNTRY.toString()));
        }
        return productAndCustomerWithTheSameCountry;
    }
}
