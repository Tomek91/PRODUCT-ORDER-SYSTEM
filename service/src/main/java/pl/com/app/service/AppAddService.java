package pl.com.app.service;

import pl.com.app.dto.*;
import pl.com.app.exceptions.EErrorsMessage;
import pl.com.app.exceptions.MyException;
import pl.com.app.reader.DataReader;
import pl.com.app.repository.*;
import pl.com.app.repository.impl.*;
import pl.com.app.repository.model.*;
import pl.com.app.service.mappers.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class AppAddService {
    private ErrorsRepository errorsRepository = new ErrorsRepositoryImpl();
    private ProducerRepository producerRepository = new ProducerRepositoryImpl();
    private ProductRepository productRepository = new ProductRepositoryImpl();
    private CategoryRepository categoryRepository = new CategoryRepositoryImpl();
    private CustomerOrderRepository customerOrderRepository = new CustomerOrderRepositoryImpl();
    private TradeRepository tradeRepository = new TradeRepositoryImpl();
    private ShopRepository shopRepository = new ShopRepositoryImpl();
    private StockRepository stockRepository = new StockRepositoryImpl();
    private CustomerRepository customerRepository = new CustomerRepositoryImpl();
    private CountryRepository countryRepository = new CountryRepositoryImpl();
    private ModelMapper modelMapper = new ModelMapper();
    private ValidationService validationService = new ValidationService();
    private AppGetDataService appGetDataService = new AppGetDataService();
    private DataReader dataReader = new DataReader();

    public void addCustomer(CustomerDTO customerDTO) {
        try {
            if (customerDTO == null){
                throw new NullPointerException("CUSTOMER DTO IS NULL");
            }

            boolean isCustomerExist = validationService
                    .isExistCustomerWithNameSurnameAndCountry(customerDTO.getName(), customerDTO.getSurname(), customerDTO.getCountryDTO().getName());

            if (isCustomerExist) {
                throw new IllegalArgumentException(String.join(" ",
                        "CUSTOMER WITH NAME",
                        customerDTO.getName(),
                        "SURNAME",
                        customerDTO.getSurname(),
                        "COUNTRIES",
                        customerDTO.getCountryDTO().getName(),
                        "ALREADY EXISTS"));
            }

            Country country = appGetDataService.getCountryWithName(customerDTO.getCountryDTO().getName()).orElse(null);
            if (country == null) {
                countryRepository.addOrUpdate(modelMapper.fromCountryDTOToCountry(customerDTO.getCountryDTO()));
                country = countryRepository.findByName(customerDTO.getCountryDTO().getName())
                        .orElseThrow(() -> new NullPointerException("NO COUNTRIES WITH NAME " + customerDTO.getCountryDTO().getName() + " FOUND"));
            }

            Customer customer = modelMapper.fromCustomerDTOToCustomer(customerDTO);
            customer.setCountry(country);
            customerRepository.addOrUpdate(customer);

        } catch (Exception e) {
            throw new MyException(String.join(";",
                    this.getClass().getCanonicalName(),
                    EErrorsMessage.ADD_CUSTOMER_EXCEPTION.toString()));
        }
    }

    public void addShop(ShopDTO shopDTO) {
        try {
            if (shopDTO == null){
                throw new NullPointerException("SHOP DTO IS NULL");
            }

            boolean isShopExist = validationService
                    .isExistShopWithNameAndCountry(shopDTO.getName(), shopDTO.getCountryDTO().getName());

            if (isShopExist) {
                throw new IllegalArgumentException(String.join(" ",
                        "SHOP WITH NAME",
                        shopDTO.getName(),
                        "COUNTRIES",
                        shopDTO.getCountryDTO().getName(),
                        "ALREADY EXISTS"));
            }

            Country country = appGetDataService.getCountryWithName(shopDTO.getCountryDTO().getName()).orElse(null);
            if (country == null) {
                countryRepository.addOrUpdate(modelMapper.fromCountryDTOToCountry(shopDTO.getCountryDTO()));
                country = countryRepository.findByName(shopDTO.getCountryDTO().getName())
                        .orElseThrow(() -> new NullPointerException("NO COUNTRIES WITH NAME " + shopDTO.getCountryDTO().getName() + " FOUND"));
            }

            Shop shop = modelMapper.fromShopDTOToShop(shopDTO);
            shop.setCountry(country);
            shopRepository.addOrUpdate(shop);

        } catch (Exception e) {
            throw new MyException(String.join(";",
                    this.getClass().getCanonicalName(),
                    EErrorsMessage.ADD_CUSTOMER_EXCEPTION.toString()));
        }
    }

    public void addProducer(ProducerDTO producerDTO) {
        try {
            if (producerDTO == null){
                throw new NullPointerException("PRODUCER DTO IS NULL");
            }

            boolean isProducerExist = validationService
                    .isExistProducerWithNameTradeAndCountry(producerDTO.getName(), producerDTO.getTradeDTO().getName(), producerDTO.getCountryDTO().getName());

            if (isProducerExist) {
                throw new IllegalArgumentException(String.join(" ",
                        "PRODUCER WITH NAME",
                        producerDTO.getName(),
                        "TRADE",
                        producerDTO.getTradeDTO().getName(),
                        "COUNTRIES",
                        producerDTO.getCountryDTO().getName(),
                        "ALREADY EXISTS"));
            }

            Country country = appGetDataService.getCountryWithName(producerDTO.getCountryDTO().getName()).orElse(null);
            if (country == null) {
                countryRepository.addOrUpdate(modelMapper.fromCountryDTOToCountry(producerDTO.getCountryDTO()));
                country = countryRepository.findByName(producerDTO.getCountryDTO().getName())
                        .orElseThrow(() -> new NullPointerException("NO COUNTRIES WITH NAME " + producerDTO.getCountryDTO().getName() + " FOUND"));
            }

            Trade trade = tradeRepository.findByName(producerDTO.getTradeDTO().getName()).orElse(null);
            if (trade == null) {
                tradeRepository.addOrUpdate(modelMapper.fromTradeDTOToTrade(producerDTO.getTradeDTO()));
                trade = tradeRepository.findByName(producerDTO.getTradeDTO().getName())
                        .orElseThrow(() -> new NullPointerException("NO TRADE WITH NAME " + producerDTO.getTradeDTO().getName() + " FOUND"));
            }

            Producer producer = modelMapper.fromProducerDTOToProducer(producerDTO);
            producer.setCountry(country);
            producer.setTrade(trade);
            producerRepository.addOrUpdate(producer);

        } catch (Exception e) {
            throw new MyException(String.join(";",
                    this.getClass().getCanonicalName(),
                    EErrorsMessage.ADD_PRODUCER_EXCEPTION.toString()));
        }
    }

    public void addProduct(ProductDTO productDTO) {
        try {
            if (productDTO == null){
                throw new NullPointerException("PRODUCT DTO IS NULL");
            }

            boolean isProductExist = validationService
                        .isExistProductWithNameCategoryProducerCountryAndTrade(productDTO.getName(),
                            productDTO.getPrice(),
                            productDTO.getCategoryDTO().getName(),
                            productDTO.getProducerDTO().getName(),
                            productDTO.getProducerDTO().getCountryDTO().getName(),
                            productDTO.getProducerDTO().getTradeDTO().getName());

            if (isProductExist) {
                throw new IllegalArgumentException(String.join(" ",
                        "PRODUCT WITH NAME",
                        productDTO.getName(),
                        "PRICE",
                        productDTO.getPrice().toString(),
                        "CATEGORY",
                        productDTO.getCategoryDTO().getName(),
                        "PRODUCER",
                        productDTO.getProducerDTO().getName(),
                        "COUNTRIES",
                        productDTO.getProducerDTO().getCountryDTO().getName(),
                        "ALREADY EXISTS"));
            }

            Optional<Producer> producerOpt = appGetDataService
                    .getProducerWithNameTradeAndCountry(
                            productDTO.getProducerDTO().getName(),
                            productDTO.getProducerDTO().getTradeDTO().getName(),
                            productDTO.getProducerDTO().getCountryDTO().getName());

            if (!producerOpt.isPresent()) {
                throw new IllegalArgumentException(String.join(" ",
                        "PRODUCER WITH NAME",
                        productDTO.getProducerDTO().getName(),
                        "TRADE",
                        productDTO.getProducerDTO().getTradeDTO().getName(),
                        "COUNTRIES",
                        productDTO.getProducerDTO().getCountryDTO().getName(),
                        "ALREADY EXISTS"));
            }

            Category category = categoryRepository.findByName(productDTO.getCategoryDTO().getName()).orElse(null);
            if (category == null) {
                categoryRepository.addOrUpdate(modelMapper.fromCategoryDTOToCategory(productDTO.getCategoryDTO()));
                category = categoryRepository.findByName(productDTO.getCategoryDTO().getName())
                        .orElseThrow(() -> new NullPointerException("NO CATEGORY WITH NAME " + productDTO.getCategoryDTO().getName() + " FOUND"));
            }

            Product product = modelMapper.fromProductDTOToProduct(productDTO);
            product.setCategory(category);
            product.setProducer(producerOpt.get());
            productRepository.addOrUpdate(product);

        } catch (Exception e) {
            throw new MyException(String.join(";",
                    this.getClass().getCanonicalName(),
                    EErrorsMessage.ADD_PRODUCT_EXCEPTION.toString()));
        }
    }

    public void addStock(StockDTO stockDTO) {
        try {
            if (stockDTO == null){
                throw new NullPointerException("STOCK DTO IS NULL");
            }

            Shop shopToSave = appGetDataService
                    .getShopWithNameAndCountry(stockDTO.getShopDTO().getName(),
                            stockDTO.getShopDTO().getCountryDTO().getName()).orElse(null);

            if (shopToSave == null) {
                throw new IllegalArgumentException(String.join(" ",
                        "NO SHOP WITH NAME",
                        stockDTO.getShopDTO().getName(),
                        "COUNTRIES",
                        stockDTO.getShopDTO().getCountryDTO().getName(),
                        "FOUND"));
            }

            List<Product> productList = appGetDataService
                   .getProductsByNameAndCategoryName(stockDTO.getProductDTO().getName(), stockDTO.getProductDTO().getCategoryDTO().getName());

            Product productToSave;
            if (productList.isEmpty()) {
                throw new IllegalArgumentException(String.join(" ",
                        "NO PRODUCTS WITH NAME",
                        stockDTO.getProductDTO().getName(),
                        "CATEGORY",
                        stockDTO.getProductDTO().getCategoryDTO().getName(),
                        "FOUND"));
            } else if(productList.size() == 1){
                productToSave = productList.get(0);
            } else {
                System.out.println("Proszę wybrać produkt:");
                for (int i = 0; i < productList.size(); i++) {
                    System.out.println(String.join(" - ", String.valueOf(i),
                            productList.get(i).getName(),
                            productList.get(i).getCategory().toString(),
                            productList.get(i).getProducer().toString()));
                }
                Integer number = null;
                do {
                    if (number != null) {
                        System.out.println("Niewłaściwy numer pozycji. Spróbuj jeszcze raz.");
                    }
                    number = dataReader.getInteger();
                } while (number < 0 || number >= productList.size());
                productToSave = productList.get(number);
            }

            Stock stockToSave = appGetDataService.getStockWithProductAndShop(productToSave.getId(), shopToSave.getId()).orElse(null);

            if (stockToSave == null){
                stockToSave = Stock.builder()
                        .product(productToSave)
                        .shop(shopToSave)
                        .quantity(stockDTO.getQuantity())
                        .build();
            } else {
                stockToSave.setQuantity(stockToSave.getQuantity() + stockDTO.getQuantity());
            }

            stockRepository.addOrUpdate(stockToSave);

        } catch (Exception e) {
            throw new MyException(String.join(";",
                    this.getClass().getCanonicalName(),
                    EErrorsMessage.ADD_STOCK_EXCEPTION.toString()));
        }
    }

    public void addCustomerOrder(CustomerOrderDTO customerOrderDTO) {
        try {
            if (customerOrderDTO == null){
                throw new NullPointerException("CUSTOMER ORDER DTO IS NULL");
            }

            Customer customerToSave = appGetDataService.getCustomerWithNameSurnameAndCountry(customerOrderDTO.getCustomerDTO().getName(),
                    customerOrderDTO.getCustomerDTO().getSurname(),
                    customerOrderDTO.getCustomerDTO().getCountryDTO().getName()).orElse(null);

            if (customerToSave == null) {
                throw new IllegalArgumentException(String.join(" ",
                        "NO CUSTOMER WITH NAME",
                        customerOrderDTO.getCustomerDTO().getName(),
                        "SURNAME",
                        customerOrderDTO.getCustomerDTO().getSurname(),
                        "COUNTRIES",
                        customerOrderDTO.getCustomerDTO().getCountryDTO().getName(),
                        "FOUND"));
            }

            Payment paymentToSave = appGetDataService.getPaymentByName(customerOrderDTO.getPaymentDTO().getEPayment()).orElse(null);

            if (paymentToSave == null) {
                throw new IllegalArgumentException(String.join(" ",
                        "NO PAYMENT WITH NAME",
                        customerOrderDTO.getPaymentDTO().getEPayment().toString(),
                        "FOUND"));
            }

            List<Product> productList = appGetDataService
                    .getProductsByNameAndCategoryName(customerOrderDTO.getProductDTO().getName(), customerOrderDTO.getProductDTO().getCategoryDTO().getName());

            Product productToSave;
            Random random = new Random();
            if (productList.isEmpty()) {
                throw new IllegalArgumentException(String.join(" ",
                        "NO PRODUCTS WITH NAME",
                        customerOrderDTO.getProductDTO().getName(),
                        "CATEGORY",
                        customerOrderDTO.getProductDTO().getCategoryDTO().getName(),
                        "FOUND"));
            } else if(productList.size() == 1){
                productToSave = productList.get(0);
            } else {
                productToSave = productList.get(random.nextInt(productList.size()));
            }

            Stock stockToSave = new Stock();
            if (productToSave.getStock().isEmpty()){
                throw new IllegalArgumentException(String.join(" ",
                        "NO STOCKS WITH PRODUCTS NAME",
                        productToSave.getName(),
                        "FOUND"));
            } else if (productToSave.getStock().size() == 1){
                stockToSave = productToSave.getStock().iterator().next();
            } else {
                stockToSave = new ArrayList<>(productToSave.getStock()).get(random.nextInt(productToSave.getStock().size()));
            }

            if (stockToSave.getQuantity() <= customerOrderDTO.getQuantity()){
                throw new IllegalArgumentException(String.join(" ",
                        "QUANTITY ORDER",
                        customerOrderDTO.getQuantity().toString(),
                        "IS HIGHER THAN HAVE STOCK",
                        stockToSave.getQuantity().toString()));
            }

            stockToSave.setQuantity(stockToSave.getQuantity() - customerOrderDTO.getQuantity());
            stockRepository.addOrUpdate(stockToSave);

            CustomerOrder customerOrder = modelMapper.fromCustomerOrderDTOToCustomerOrder(customerOrderDTO);
            customerOrder.setCustomer(customerToSave);
            customerOrder.setPayment(paymentToSave);
            customerOrder.setProduct(productToSave);
            customerOrderRepository.addOrUpdate(customerOrder);

        } catch (Exception e) {
            throw new MyException(String.join(";",
                    this.getClass().getCanonicalName(),
                    EErrorsMessage.ADD_CUSTOMER_ORDER_EXCEPTION.toString()));
        }
    }

    public void addError(ErrorsDTO errorsDTO) {
        try {
            if (errorsDTO == null){
                throw new NullPointerException("ERRORS DTO IS NULL");
            }
            errorsRepository.addOrUpdate(modelMapper.fromErrorsDTOToErrors(errorsDTO));
        } catch (Exception e) {
            throw new MyException(String.join(";",
                    this.getClass().getCanonicalName(),
                    EErrorsMessage.ADD_ERROR_EXCEPTION.toString()));
        }
    }
}
