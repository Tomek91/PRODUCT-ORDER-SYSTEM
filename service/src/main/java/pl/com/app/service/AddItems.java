package pl.com.app.service;

import pl.com.app.dto.*;
import pl.com.app.reader.DataReader;
import pl.com.app.repository.model.enums.EGuarantee;
import pl.com.app.repository.model.parsers.EGuaranteeParser;
import pl.com.app.repository.model.parsers.Parser;
import pl.com.app.service.menu.ProductsOrdersSimulator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class AddItems {
    private AppAddService appAddService = new AppAddService();
    private DataReader dataReader = new DataReader();

    public void addCustomer() {
        System.out.println(ProductsOrdersSimulator.PREFIX);
        CustomerDTO customerDTO = new CustomerDTO();
        System.out.println("Podaj imie ...");
        customerDTO.setName(dataReader.getString());
        System.out.println("Podaj nazwisko ...");
        customerDTO.setSurname(dataReader.getString());
        System.out.println("Podaj date urodzenia ...");
        customerDTO.setBirthDate(dataReader.getLocalDate());
        System.out.println("Podaj kraj ...");
        customerDTO.setCountryDTO(CountryDTO.builder().name(dataReader.getString()).build());
        appAddService.addCustomer(customerDTO);
        System.out.println("Klient został dodany poprawnie.");
    }

    public void addShop() {
        System.out.println(ProductsOrdersSimulator.PREFIX);
        ShopDTO shopDTO = new ShopDTO();
        System.out.println("Podaj nazwe ...");
        shopDTO.setName(dataReader.getString());
        System.out.println("Podaj kraj ...");
        shopDTO.setCountryDTO(CountryDTO.builder().name(dataReader.getString()).build());
        appAddService.addShop(shopDTO);
        System.out.println("Sklep został dodany poprawnie.");
    }

    public void addProducer() {
        System.out.println(ProductsOrdersSimulator.PREFIX);
        ProducerDTO producerDTO = new ProducerDTO();
        System.out.println("Podaj nazwe ...");
        producerDTO.setName(dataReader.getString());
        System.out.println("Podaj kraj ...");
        producerDTO.setCountryDTO(CountryDTO.builder().name(dataReader.getString()).build());
        System.out.println("Podaj branżę ...");
        producerDTO.setTradeDTO(TradeDTO.builder().name(dataReader.getString()).build());
        appAddService.addProducer(producerDTO);
        System.out.println("Producent został dodany poprawnie.");
    }

    public void addProduct() {
        System.out.println(ProductsOrdersSimulator.PREFIX);
        ProductDTO productDTO = new ProductDTO();
        System.out.println("Podaj nazwe ...");
        productDTO.setName(dataReader.getString());
        System.out.println("Podaj cene ...");
        productDTO.setPrice(dataReader.getBigDecimal());
        System.out.println("Podaj kategorię ...");
        productDTO.setCategoryDTO(CategoryDTO.builder().name(dataReader.getString()).build());
        System.out.println("Podaj nazwe producenta ...");
        ProducerDTO producerDTO = ProducerDTO.builder().name(dataReader.getString()).build();
        System.out.println("Podaj kraj producenta ...");
        producerDTO.setCountryDTO(CountryDTO.builder().name(dataReader.getString()).build());
        System.out.println("Podaj branże producenta ...");
        producerDTO.setTradeDTO(TradeDTO.builder().name(dataReader.getString()).build());
        productDTO.setProducerDTO(producerDTO);
        String data = null;
        List<EGuarantee> eGuarantees = new ArrayList<>();
        Parser<EGuarantee> eGuaranteeParser = new EGuaranteeParser();
        while (true) {
            System.out.println("Podaj nazwy usług gwarancyjnych (q - kończy wprowadzanie)");
            data = dataReader.getString();
            if (data.equalsIgnoreCase("q")) {
                break;
            }
            eGuarantees.add(eGuaranteeParser.parse(data));
        }
        productDTO.setEGuarantees(new HashSet<>(eGuarantees));
        appAddService.addProduct(productDTO);
        System.out.println("Produkt został dodany poprawnie.");
    }

    public void addStock() {
        System.out.println(ProductsOrdersSimulator.PREFIX);
        StockDTO stockDTO = new StockDTO();
        System.out.println("Podaj nazwe produktu ...");
        ProductDTO productDTO = ProductDTO.builder().name(dataReader.getString()).build();
        System.out.println("Podaj kategorie produktu ...");
        productDTO.setCategoryDTO(CategoryDTO.builder().name(dataReader.getString()).build());
        ShopDTO shopDTO = new ShopDTO();
        System.out.println("Podaj nazwe sklepu ...");
        shopDTO.setName(dataReader.getString());
        System.out.println("Podaj kraj sklepu ...");
        shopDTO.setCountryDTO(CountryDTO.builder().name(dataReader.getString()).build());
        System.out.println("Podaj ilość sztuk ...");
        stockDTO.setQuantity(dataReader.getInteger());
        stockDTO.setShopDTO(shopDTO);
        stockDTO.setProductDTO(productDTO);
        appAddService.addStock(stockDTO);
        System.out.println("Pozycja w magazynie została dodana poprawnie.");
    }

    public void addCustomerOrder() {
        System.out.println(ProductsOrdersSimulator.PREFIX);
        CustomerOrderDTO customerOrderDTO = new CustomerOrderDTO();
        CustomerDTO customerDTO = new CustomerDTO();
        System.out.println("Podaj imie ...");
        customerDTO.setName(dataReader.getString());
        System.out.println("Podaj nazwisko ...");
        customerDTO.setSurname(dataReader.getString());
        System.out.println("Podaj kraj ...");
        customerDTO.setCountryDTO(CountryDTO.builder().name(dataReader.getString()).build());
        System.out.println("Podaj nazwe produktu ...");
        ProductDTO productDTO = ProductDTO.builder().name(dataReader.getString()).build();
        System.out.println("Podaj kategorie produktu ...");
        productDTO.setCategoryDTO(CategoryDTO.builder().name(dataReader.getString()).build());
        System.out.println("Podaj ilość zamawianych sztuk ...");
        customerOrderDTO.setQuantity(dataReader.getInteger());
        System.out.println("Podaj datę zamówienia ...");
        customerOrderDTO.setDate(dataReader.getLocalDate());
        System.out.println("Podaj rodaj płatności ...");
        customerOrderDTO.setPaymentDTO(PaymentDTO.builder().ePayment(dataReader.getEPayment()).build());
        System.out.println("Podaj zniżkę ...");
        customerOrderDTO.setDiscount(dataReader.getBigDecimal());
        customerOrderDTO.setProductDTO(productDTO);
        customerOrderDTO.setCustomerDTO(customerDTO);
        appAddService.addCustomerOrder(customerOrderDTO);
        System.out.println("Zamówienie zostało dodane poprawnie.");
    }
}
