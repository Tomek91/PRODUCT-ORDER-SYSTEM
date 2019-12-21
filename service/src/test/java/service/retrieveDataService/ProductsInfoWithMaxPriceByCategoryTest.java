package service.retrieveDataService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import pl.com.app.dto.ProductInfoDTO;
import pl.com.app.repository.ProductRepository;
import pl.com.app.repository.model.*;
import pl.com.app.service.RetrieveDataService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ProductsInfoWithMaxPriceByCategoryTest {

    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private RetrieveDataService retrieveDataService;


    @Test
    @DisplayName("find products with max price by category")
    public void test1() {

        Mockito
                .when(productRepository.findAll())
                .thenReturn(List.of(
                        Product.builder().id(17L).name("A").price(new BigDecimal(2)).category(Category.builder().id(1L).name("AA").build()).producer(Producer.builder().name("AAA").country(Country.builder().name("HHHH").build()).build()).customerOrders(new HashSet<>()).build(),
                        Product.builder().id(16L).name("B").price(new BigDecimal(4)).category(Category.builder().id(1L).name("AA").build()).producer(Producer.builder().name("BBB").country(Country.builder().name("JJJJ").build()).build()).customerOrders(new HashSet<>()).build(),
                        Product.builder().id(14L).name("C").price(new BigDecimal(1)).category(Category.builder().id(2L).name("CC").build()).producer(Producer.builder().name("BBB").country(Country.builder().name("EEEE").build()).build()).customerOrders(new HashSet<>()).build(),
                        Product.builder().id(12L).name("D").price(new BigDecimal(11)).category(Category.builder().id(2L).name("CC").build()).producer(Producer.builder().name("CCC").country(Country.builder().name("BBBB").build()).build()).customerOrders(new HashSet<>()).build(),
                        Product.builder().id(11L).name("E").price(new BigDecimal(3.3)).category(Category.builder().id(1L).name("AA").build()).producer(Producer.builder().name("BBB").country(Country.builder().name("BBBB").build()).build()).customerOrders(new HashSet<>()).build()));

        var productsInfoWithMaxPriceByCategory = retrieveDataService.getProductsInfoWithMaxPriceByCategory();
        System.out.println(productsInfoWithMaxPriceByCategory);
        Assertions.assertAll(
                () -> Assertions.assertEquals(2, productsInfoWithMaxPriceByCategory.size())
        );
        Mockito.verify(productRepository, Mockito.times(1)).findAll();
    }

}
