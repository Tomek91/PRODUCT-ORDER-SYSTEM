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
import pl.com.app.exceptions.MyException;
import pl.com.app.repository.CustomerOrderRepository;
import pl.com.app.repository.ProductRepository;
import pl.com.app.repository.model.*;
import pl.com.app.repository.model.enums.EPayment;
import pl.com.app.service.RetrieveDataService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ProductsOrdersByCustomersWithCountryAndAgeTest {

    @Mock
    private CustomerOrderRepository customerOrderRepository;
    @InjectMocks
    private RetrieveDataService retrieveDataService;


    @Test
    @DisplayName("find products with country and age order by customer")
    public void test1() {

        Mockito
                .when(customerOrderRepository.findAll())
                .thenReturn(List.of(
                        CustomerOrder.builder().id(1L).customer(Customer.builder().name("ANNA").country(Country.builder().name("HHHH").build()).birthDate(LocalDate.of(1990, 11, 1)).build()).discount(BigDecimal.ZERO).product(Product.builder().id(17L).name("A").price(new BigDecimal(2)).category(Category.builder().id(1L).name("AA").build()).producer(Producer.builder().name("AAA").country(Country.builder().name("HHHH").build()).build()).customerOrders(new HashSet<>()).build()).quantity(2).date(LocalDate.now()).payment(Payment.builder().ePayment(EPayment.CARD).build()).build(),
                        CustomerOrder.builder().id(2L).customer(Customer.builder().name("ANNA2").country(Country.builder().name("HHHH").build()).birthDate(LocalDate.of(1995, 11, 1)).build()).discount(BigDecimal.TEN).product(Product.builder().id(17L).name("A").price(new BigDecimal(2)).category(Category.builder().id(1L).name("AA").build()).producer(Producer.builder().name("AAA").country(Country.builder().name("HHHH").build()).build()).customerOrders(new HashSet<>()).build()).quantity(2).date(LocalDate.now()).payment(Payment.builder().ePayment(EPayment.MONEY_TRANSFER).build()).build(),
                        CustomerOrder.builder().id(3L).customer(Customer.builder().name("ANNA3").country(Country.builder().name("JJJJ").build()).birthDate(LocalDate.of(1990, 5, 1)).build()).discount(BigDecimal.ZERO).product( Product.builder().id(16L).name("B").price(new BigDecimal(4)).category(Category.builder().id(1L).name("AA").build()).producer(Producer.builder().name("BBB").country(Country.builder().name("JJJJ").build()).build()).customerOrders(new HashSet<>()).build()).quantity(1).date(LocalDate.now()).payment(Payment.builder().ePayment(EPayment.CASH).build()).build()));


        var productsInfoWithMaxPriceByCategory = retrieveDataService.getProductsOrdersByCustomersWithCountryAndAge("HHHH", 10, 50);
        System.out.println(productsInfoWithMaxPriceByCategory);
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, productsInfoWithMaxPriceByCategory.size())
        );
        Mockito.verify(customerOrderRepository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("find products with country and age order by customer - argument is null")
    public void test2() {

        var e = Assertions.assertThrows(MyException.class, () -> retrieveDataService.getProductsOrdersByCustomersWithCountryAndAge("HHHH", null, 50));
        Assertions.assertEquals("pl.com.app.service.RetrieveDataService;GET_PRODUCTS_ORDERS_BY_CUSTOMERS_WITH_COUNTRY_AND_AGE", e.getExceptionMessage());

    }

}
