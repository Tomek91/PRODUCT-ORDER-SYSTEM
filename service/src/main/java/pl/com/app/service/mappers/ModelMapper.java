package pl.com.app.service.mappers;

import pl.com.app.dto.*;
import pl.com.app.repository.model.*;

import java.util.HashSet;

public class ModelMapper {
    public CategoryDTO fromCategoryToCategoryDTO(Category category) {
        return category == null ? null : CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public Category fromCategoryDTOToCategory(CategoryDTO categoryDTO) {
        return categoryDTO == null ? null : Category.builder()
                .id(categoryDTO.getId())
                .name(categoryDTO.getName())
                .products(new HashSet<>())
                .build();
    }

    public CountryDTO fromCountryToCountryDTO(Country country) {
        return country == null ? null : CountryDTO.builder()
                .id(country.getId())
                .name(country.getName())
                .build();
    }

    public Country fromCountryDTOToCountry(CountryDTO countryDTO) {
        return countryDTO == null ? null : Country.builder()
                .id(countryDTO.getId())
                .name(countryDTO.getName())
                .customers(new HashSet<>())
                .producers(new HashSet<>())
                .shops(new HashSet<>())
                .build();
    }

    public CustomerDTO fromCustomerToCustomerDTO(Customer customer) {
        return customer == null ? null : CustomerDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .surname(customer.getSurname())
                .birthDate(customer.getBirthDate())
                .countryDTO(customer.getCountry() == null ? null : fromCountryToCountryDTO(customer.getCountry()))
                .build();
    }

    public Customer fromCustomerDTOToCustomer(CustomerDTO customerDTO) {
        return customerDTO == null ? null : Customer.builder()
                .id(customerDTO.getId())
                .name(customerDTO.getName())
                .surname(customerDTO.getSurname())
                .birthDate(customerDTO.getBirthDate())
                .country(customerDTO.getCountryDTO() == null ? null : fromCountryDTOToCountry(customerDTO.getCountryDTO()))
                .customerOrders(new HashSet<>())
                .build();
    }

    public PaymentDTO fromPaymentToPaymentDTO(Payment payment) {
        return payment == null ? null : PaymentDTO.builder()
                .id(payment.getId())
                .ePayment(payment.getEPayment())
                .build();
    }

    public Payment fromPaymentDTOToPayment(PaymentDTO paymentDTO) {
        return paymentDTO == null ? null : Payment.builder()
                .id(paymentDTO.getId())
                .ePayment(paymentDTO.getEPayment())
                .customerOrders(new HashSet<>())
                .build();
    }

    public TradeDTO fromTradeToTradeDTO(Trade trade) {
        return trade == null ? null : TradeDTO.builder()
                .id(trade.getId())
                .name(trade.getName())
                .build();
    }

    public Trade fromTradeDTOToTrade(TradeDTO tradeDTO) {
        return tradeDTO == null ? null : Trade.builder()
                .id(tradeDTO.getId())
                .name(tradeDTO.getName())
                .producers(new HashSet<>())
                .build();
    }

    public ProducerDTO fromProducerToProducerDTO(Producer producer) {
        return producer == null ? null : ProducerDTO.builder()
                .id(producer.getId())
                .name(producer.getName())
                .tradeDTO(producer.getTrade() == null ? null : fromTradeToTradeDTO(producer.getTrade()))
                .countryDTO(producer.getCountry() == null ? null : fromCountryToCountryDTO(producer.getCountry()))
                .build();
    }

    public Producer fromProducerDTOToProducer(ProducerDTO producerDTO) {
        return producerDTO == null ? null : Producer.builder()
                .id(producerDTO.getId())
                .name(producerDTO.getName())
                .trade(producerDTO.getTradeDTO() == null ? null : fromTradeDTOToTrade(producerDTO.getTradeDTO()))
                .country(producerDTO.getCountryDTO() == null ? null : fromCountryDTOToCountry(producerDTO.getCountryDTO()))
                .products(new HashSet<>())
                .build();
    }

    public ErrorsDTO fromErrorsToErrorsDTO(Errors errors) {
        return errors == null ? null : ErrorsDTO.builder()
                .id(errors.getId())
                .dateTime(errors.getDateTime())
                .message(errors.getMessage())
                .build();
    }

    public Errors fromErrorsDTOToErrors(ErrorsDTO errorsDTO) {
        return errorsDTO == null ? null : Errors.builder()
                .id(errorsDTO.getId())
                .dateTime(errorsDTO.getDateTime())
                .message(errorsDTO.getMessage())
                .build();
    }

    public ShopDTO fromShopToShopDTO(Shop shop) {
        return shop == null ? null : ShopDTO.builder()
                .id(shop.getId())
                .name(shop.getName())
                .countryDTO(shop.getCountry() == null ? null : fromCountryToCountryDTO(shop.getCountry()))
                .build();
    }

    public Shop fromShopDTOToShop(ShopDTO shopDTO) {
        return shopDTO == null ? null : Shop.builder()
                .id(shopDTO.getId())
                .name(shopDTO.getName())
                .country(shopDTO.getCountryDTO() == null ? null : fromCountryDTOToCountry(shopDTO.getCountryDTO()))
                .stocks(new HashSet<>())
                .build();
    }

    public CustomerOrderDTO fromCustomerOrderToCustomerOrderDTO(CustomerOrder customerOrder) {
        return customerOrder == null ? null : CustomerOrderDTO.builder()
                .id(customerOrder.getId())
                .date(customerOrder.getDate())
                .discount(customerOrder.getDiscount())
                .quantity(customerOrder.getQuantity())
                .customerDTO(customerOrder.getCustomer() == null ? null : fromCustomerToCustomerDTO(customerOrder.getCustomer()))
                .paymentDTO(customerOrder.getPayment() == null ? null : fromPaymentToPaymentDTO(customerOrder.getPayment()))
                .productDTO(customerOrder.getProduct() == null ? null : fromProductToProductDTO(customerOrder.getProduct()))
                .build();
    }

    public CustomerOrder fromCustomerOrderDTOToCustomerOrder(CustomerOrderDTO customerOrderDTO) {
        return customerOrderDTO == null ? null : CustomerOrder.builder()
                .id(customerOrderDTO.getId())
                .date(customerOrderDTO.getDate())
                .discount(customerOrderDTO.getDiscount())
                .quantity(customerOrderDTO.getQuantity())
                .customer(customerOrderDTO.getCustomerDTO() == null ? null : fromCustomerDTOToCustomer(customerOrderDTO.getCustomerDTO()))
                .payment(customerOrderDTO.getPaymentDTO() == null ? null : fromPaymentDTOToPayment(customerOrderDTO.getPaymentDTO()))
                .product(customerOrderDTO.getProductDTO() == null ? null : fromProductDTOToProduct(customerOrderDTO.getProductDTO()))
                .build();
    }

    public ProductDTO fromProductToProductDTO(Product product) {
        return product == null ? null : ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .producerDTO(product.getProducer() == null ? null : fromProducerToProducerDTO(product.getProducer()))
                .categoryDTO(product.getCategory() == null ? null : fromCategoryToCategoryDTO(product.getCategory()))
                .eGuarantees(new HashSet<>())
                .build();
    }

    public Product fromProductDTOToProduct(ProductDTO productDTO) {
        return productDTO == null ? null : Product.builder()
                .id(productDTO.getId())
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .producer(productDTO.getProducerDTO() == null ? null : fromProducerDTOToProducer(productDTO.getProducerDTO()))
                .category(productDTO.getCategoryDTO() == null ? null : fromCategoryDTOToCategory(productDTO.getCategoryDTO()))
                .producer(productDTO.getProducerDTO() == null ? null : fromProducerDTOToProducer(productDTO.getProducerDTO()))
                .customerOrders(new HashSet<>())
                .eGuarantees(productDTO.getEGuarantees())
                .stock(new HashSet<>())
                .build();
    }

    public StockDTO fromStockToStockDTO(Stock stock) {
        return stock == null ? null : StockDTO.builder()
                .id(stock.getId())
                .quantity(stock.getQuantity())
                .productDTO(stock.getProduct() == null ? null : fromProductToProductDTO(stock.getProduct()))
                .shopDTO(stock.getShop() == null ? null : fromShopToShopDTO(stock.getShop()))
                .build();
    }

    public Stock fromStockDTOToStock(StockDTO stockDTO) {
        return stockDTO == null ? null : Stock.builder()
                .id(stockDTO.getId())
                .quantity(stockDTO.getQuantity())
                .product(stockDTO.getProductDTO() == null ? null : fromProductDTOToProduct(stockDTO.getProductDTO()))
                .shop(stockDTO.getShopDTO() == null ? null : fromShopDTOToShop(stockDTO.getShopDTO()))
                .build();
    }
}
