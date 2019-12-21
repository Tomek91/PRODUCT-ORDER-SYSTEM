package pl.com.app.service;

import pl.com.app.dto.*;
import pl.com.app.exceptions.EErrorsMessage;
import pl.com.app.exceptions.MyException;
import pl.com.app.parsers.json.FileNames;
import pl.com.app.parsers.json.JsonParserImpl;
import pl.com.app.repository.*;
import pl.com.app.repository.impl.*;
import pl.com.app.service.mappers.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class DataInitializeService {
    private ProducerRepository producerRepository = new ProducerRepositoryImpl();
    private ProductRepository productRepository = new ProductRepositoryImpl();
    private CategoryRepository categoryRepository = new CategoryRepositoryImpl();
    private CustomerOrderRepository customerOrderRepository = new CustomerOrderRepositoryImpl();
    private TradeRepository tradeRepository = new TradeRepositoryImpl();
    private ShopRepository shopRepository = new ShopRepositoryImpl();
    private StockRepository stockRepository = new StockRepositoryImpl();
    private CustomerRepository customerRepository = new CustomerRepositoryImpl();
    private CountryRepository countryRepository = new CountryRepositoryImpl();
    private PaymentRepository paymentRepository = new PaymentRepositoryImpl();
    private ModelMapper modelMapper = new ModelMapper();
    private AppGetDataService appGetDataService = new AppGetDataService();


    public void initialize() {
        try {
            customerOrderRepository.deleteAll();
            stockRepository.deleteAll();
            productRepository.deleteAll();
            producerRepository.deleteAll();
            customerRepository.deleteAll();
            shopRepository.deleteAll();
            categoryRepository.deleteAll();
            tradeRepository.deleteAll();
            countryRepository.deleteAll();
            paymentRepository.deleteAll();

            List<CountryDTO> countryDTOList =  new JsonParserImpl<>(CountryDTO.class).fromJsonInFileToModel(FileNames.COUNTRIES);
            List<ProducerDTO> producerDTOList = new JsonParserImpl<>(ProducerDTO.class).fromJsonInFileToModel(FileNames.PRODUCERS);
            List<ProductDTO> productDTOList = new JsonParserImpl<>(ProductDTO.class).fromJsonInFileToModel(FileNames.PRODUCTS);
            List<CategoryDTO> categoryDTOList = new JsonParserImpl<>(CategoryDTO.class).fromJsonInFileToModel(FileNames.CATEGORIES);
            List<CustomerOrderDTO> customerOrderDTOList = new JsonParserImpl<>(CustomerOrderDTO.class).fromJsonInFileToModel(FileNames.CUSTOMER_ORDERS);
            List<TradeDTO> tradeDTOList = new JsonParserImpl<>(TradeDTO.class).fromJsonInFileToModel(FileNames.TRADES);
            List<ShopDTO> shopDTOList = new JsonParserImpl<>(ShopDTO.class).fromJsonInFileToModel(FileNames.SHOPS);
            List<StockDTO> stockDTOList = new JsonParserImpl<>(StockDTO.class).fromJsonInFileToModel(FileNames.STOCKS);
            List<CustomerDTO> customerDTOList = new JsonParserImpl<>(CustomerDTO.class).fromJsonInFileToModel(FileNames.CUSTOMERS);
            List<PaymentDTO> paymentDTOList = new JsonParserImpl<>(PaymentDTO.class).fromJsonInFileToModel(FileNames.PAYMENTS);

//
            countryRepository.addAll(countryDTOList.stream().map(modelMapper::fromCountryDTOToCountry).collect(Collectors.toList()));
            categoryRepository.addAll(categoryDTOList.stream().map(modelMapper::fromCategoryDTOToCategory).collect(Collectors.toList()));
            tradeRepository.addAll(tradeDTOList.stream().map(modelMapper::fromTradeDTOToTrade).collect(Collectors.toList()));
            paymentRepository.addAll(paymentDTOList.stream().map(modelMapper::fromPaymentDTOToPayment).collect(Collectors.toList()));

            customerRepository.addAll(
                    customerDTOList
                        .stream()
                        .map(modelMapper::fromCustomerDTOToCustomer)
                        .peek(x ->
                                x.setCountry(countryRepository.findByName(x.getCountry().getName()).orElseThrow(NullPointerException::new))
                        )
                        .collect(Collectors.toList())
            );

            shopRepository.addAll(
                    shopDTOList
                        .stream()
                        .map(modelMapper::fromShopDTOToShop)
                        .peek(x ->
                                x.setCountry(countryRepository.findByName(x.getCountry().getName()).orElseThrow(NullPointerException::new))
                        )
                        .collect(Collectors.toList())
            );

            producerRepository.addAll(
                    producerDTOList
                        .stream()
                        .map(modelMapper::fromProducerDTOToProducer)
                        .peek(x ->
                                x.setCountry(countryRepository.findByName(x.getCountry().getName()).orElseThrow(NullPointerException::new))
                        )
                        .peek(x ->
                                x.setTrade(tradeRepository.findByName(x.getTrade().getName()).orElseThrow(NullPointerException::new))
                        )
                        .collect(Collectors.toList())
            );

            productRepository.addAll(
                    productDTOList
                            .stream()
                            .map(modelMapper::fromProductDTOToProduct)
                            .peek(x ->
                                    x.setCategory(categoryRepository.findByName(x.getCategory().getName()).orElseThrow(NullPointerException::new))
                            )
                            .peek(x ->
                                    x.setProducer(
                                            appGetDataService
                                            .getProducerWithNameTradeAndCountry(
                                                    x.getProducer().getName(),
                                                    x.getProducer().getTrade().getName(),
                                                    x.getProducer().getCountry().getName()
                                            )
                                            .orElseThrow(NullPointerException::new)
                                    )
                            )
                            .collect(Collectors.toList())
            );

            stockRepository.addAll(
                    stockDTOList
                        .stream()
                        .map(modelMapper::fromStockDTOToStock)
                        .peek(x ->
                                x.setShop(appGetDataService.getShopWithNameAndCountry(x.getShop().getName(), x.getShop().getCountry().getName()).orElseThrow(NullPointerException::new))
                        )
                        .peek(x ->
                                x.setProduct(
                                        appGetDataService.getProductByNameCategoryProducerTradeAndCountry(
                                            x.getProduct().getName(),
                                            x.getProduct().getCategory().getName(),
                                            x.getProduct().getProducer().getName(),
                                            x.getProduct().getProducer().getTrade().getName(),
                                            x.getProduct().getProducer().getCountry().getName()
                                        ) .orElseThrow(NullPointerException::new)
                                )
                        )
                        .collect(Collectors.toList())
            );

            customerOrderRepository.addAll(
                    customerOrderDTOList
                            .stream()
                            .map(modelMapper::fromCustomerOrderDTOToCustomerOrder)
                            .peek(x ->
                                    x.setPayment(paymentRepository.findByName(x.getPayment().getEPayment()).orElseThrow(NullPointerException::new))
                            )
                            .peek(x ->
                                    x.setProduct(
                                            appGetDataService.getProductByNameCategoryProducerTradeAndCountry(
                                                    x.getProduct().getName(),
                                                    x.getProduct().getCategory().getName(),
                                                    x.getProduct().getProducer().getName(),
                                                    x.getProduct().getProducer().getTrade().getName(),
                                                    x.getProduct().getProducer().getCountry().getName()
                                            ) .orElseThrow(NullPointerException::new)
                                    )
                            )
                            .peek(x ->
                                    x.setCustomer(
                                            appGetDataService.getCustomerWithNameSurnameAndCountry(
                                                    x.getCustomer().getName(),
                                                    x.getCustomer().getSurname(),
                                                    x.getCustomer().getCountry().getName()
                                            ).orElseThrow(NullPointerException::new)
                                    )
                            )
                            .collect(Collectors.toList())
            );

        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(String.join(";",
                    this.getClass().getCanonicalName(),
                    EErrorsMessage.DATA_INITIALIZATION_EXCEPTION.toString()));
        }
    }

}
