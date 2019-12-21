package service.retrieveDataService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import pl.com.app.exceptions.MyException;
import pl.com.app.repository.CustomerOrderRepository;
import pl.com.app.repository.StockRepository;
import pl.com.app.repository.model.*;
import pl.com.app.repository.model.enums.EGuarantee;
import pl.com.app.repository.model.enums.EPayment;
import pl.com.app.service.RetrieveDataService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ShopsAndProductsWithDiffCountriesTest {

    @Mock
    private StockRepository stockRepository;
    @InjectMocks
    private RetrieveDataService retrieveDataService;


    @Test
    @DisplayName("find shops and products with diff countries")
    public void test1() {

        Mockito
                .when(stockRepository.findAll())
                .thenReturn(List.of(
                        Stock.builder().quantity(2).product(Product.builder().producer(Producer.builder().country(Country.builder().name("AAA").build()).build()).build()).shop(Shop.builder().country(Country.builder().name("BBB").build()).build()).build(),
                        Stock.builder().quantity(2).product(Product.builder().producer(Producer.builder().country(Country.builder().name("CCC").build()).build()).build()).shop(Shop.builder().country(Country.builder().name("CCC").build()).build()).build(),
                        Stock.builder().quantity(2).product(Product.builder().producer(Producer.builder().country(Country.builder().name("DDD").build()).build()).build()).shop(Shop.builder().country(Country.builder().name("DDD").build()).build()).build()));


        var productsInfoWithMaxPriceByCategory = retrieveDataService.getShopsAndProductsWithDiffCountries();
        System.out.println(productsInfoWithMaxPriceByCategory);
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, productsInfoWithMaxPriceByCategory.size())
        );
        Mockito.verify(stockRepository, Mockito.times(1)).findAll();
    }

}
