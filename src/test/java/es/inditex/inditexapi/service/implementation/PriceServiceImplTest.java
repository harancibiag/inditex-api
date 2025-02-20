package es.inditex.inditexapi.service.implementation;

import es.inditex.inditexapi.persistence.entity.Brand;
import es.inditex.inditexapi.persistence.entity.Price;
import es.inditex.inditexapi.persistence.repository.PriceRepository;
import es.inditex.inditexapi.presentation.dto.PriceResponse;
import es.inditex.inditexapi.service.validation.PriceValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PriceServiceImplTest {

    @Mock
    private PriceRepository priceRepository;

    @Mock
    private PriceValidator priceValidator;

    @InjectMocks
    private PriceServiceImpl priceService;

    private final Long PRODUCT_ID = 35455L;
    private final Long BRAND_ID = 1L; // ZARA
    private Brand brand;

    @BeforeEach
    void setUp() {
        // Common setup for all tests
        brand = new Brand();
        brand.setBrandId(BRAND_ID);
        brand.setBrandName("ZARA");

        // Configure validator to do nothing (validation passes)
        lenient().doNothing().when(priceValidator).validate(any(LocalDateTime.class), any(Long.class), any(Long.class));
    }

    @Test
    @DisplayName("Test 1: Request at 10:00 on day 14 for product 35455 and brand 1 (ZARA)")
    void testGetApplicablePrice_Day14At10AM() {
        // Arrange
        LocalDateTime requestDate = LocalDateTime.of(2020, 6, 14, 10, 0);

        Price expectedPrice = createPrice(requestDate, LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                BigDecimal.valueOf(35.50), 1L);

        doReturn(Optional.of(expectedPrice))
                .when(priceRepository)
                .findApplicablePrice(eq(requestDate), eq(PRODUCT_ID), eq(BRAND_ID));

        // Act
        PriceResponse response = priceService.getApplicablePrice(requestDate, PRODUCT_ID, BRAND_ID);

        // Assert
        assertNotNull(response);
        assertEquals(PRODUCT_ID, response.getProductId());
        assertEquals(BRAND_ID, response.getBrandId());
        assertEquals(BigDecimal.valueOf(35.50), response.getPriceAmount());
        assertEquals(1, response.getPriceList());

        // Verify interactions
        verify(priceValidator).validate(requestDate, PRODUCT_ID, BRAND_ID);
        verify(priceRepository).findApplicablePrice(requestDate, PRODUCT_ID, BRAND_ID);
    }

    @Test
    @DisplayName("Test 2: Request at 16:00 on day 14 for product 35455 and brand 1 (ZARA)")
    void testGetApplicablePrice_Day14At16PM() {
        // Arrange
        LocalDateTime requestDate = LocalDateTime.of(2020, 6, 14, 16, 0);

        Price expectedPrice = createPrice(requestDate, LocalDateTime.of(2020, 6, 14, 18, 30, 0),
                BigDecimal.valueOf(25.45), 2L);

        doReturn(Optional.of(expectedPrice))
                .when(priceRepository)
                .findApplicablePrice(eq(requestDate), eq(PRODUCT_ID), eq(BRAND_ID));

        // Act
        PriceResponse response = priceService.getApplicablePrice(requestDate, PRODUCT_ID, BRAND_ID);

        // Assert
        assertNotNull(response);
        assertEquals(PRODUCT_ID, response.getProductId());
        assertEquals(BRAND_ID, response.getBrandId());
        assertEquals(BigDecimal.valueOf(25.45), response.getPriceAmount());
        assertEquals(2, response.getPriceList());
    }

    @Test
    @DisplayName("Test 3: Request at 21:00 on day 14 for product 35455 and brand 1 (ZARA)")
    void testGetApplicablePrice_Day14At21PM() {
        // Arrange
        LocalDateTime requestDate = LocalDateTime.of(2020, 6, 14, 21, 0);

        Price expectedPrice = createPrice(requestDate, LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                BigDecimal.valueOf(35.50), 1L);

        doReturn(Optional.of(expectedPrice))
                .when(priceRepository)
                .findApplicablePrice(eq(requestDate), eq(PRODUCT_ID), eq(BRAND_ID));

        // Act
        PriceResponse response = priceService.getApplicablePrice(requestDate, PRODUCT_ID, BRAND_ID);

        // Assert
        assertNotNull(response);
        assertEquals(PRODUCT_ID, response.getProductId());
        assertEquals(BRAND_ID, response.getBrandId());
        assertEquals(BigDecimal.valueOf(35.50), response.getPriceAmount());
        assertEquals(1, response.getPriceList());
    }

    @Test
    @DisplayName("Test 4: Request at 10:00 on day 15 for product 35455 and brand 1 (ZARA)")
    void testGetApplicablePrice_Day15At10AM() {
        // Arrange
        LocalDateTime requestDate = LocalDateTime.of(2020, 6, 15, 10, 0);

        Price expectedPrice = createPrice(requestDate, LocalDateTime.of(2020, 6, 15, 11, 0, 0),
                BigDecimal.valueOf(30.50), 3L);

        doReturn(Optional.of(expectedPrice))
                .when(priceRepository)
                .findApplicablePrice(eq(requestDate), eq(PRODUCT_ID), eq(BRAND_ID));

        // Act
        PriceResponse response = priceService.getApplicablePrice(requestDate, PRODUCT_ID, BRAND_ID);

        // Assert
        assertNotNull(response);
        assertEquals(PRODUCT_ID, response.getProductId());
        assertEquals(BRAND_ID, response.getBrandId());
        assertEquals(BigDecimal.valueOf(30.50), response.getPriceAmount());
        assertEquals(3, response.getPriceList());
    }

    @Test
    @DisplayName("Test 5: Request at 21:00 on day 16 for product 35455 and brand 1 (ZARA)")
    void testGetApplicablePrice_Day16At21PM() {
        // Arrange
        LocalDateTime requestDate = LocalDateTime.of(2020, 6, 16, 21, 0);

        Price expectedPrice = createPrice(requestDate, LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                BigDecimal.valueOf(38.95), 4L);

        doReturn(Optional.of(expectedPrice))
                .when(priceRepository)
                .findApplicablePrice(eq(requestDate), eq(PRODUCT_ID), eq(BRAND_ID));

        // Act
        PriceResponse response = priceService.getApplicablePrice(requestDate, PRODUCT_ID, BRAND_ID);

        // Assert
        assertNotNull(response);
        assertEquals(PRODUCT_ID, response.getProductId());
        assertEquals(BRAND_ID, response.getBrandId());
        assertEquals(BigDecimal.valueOf(38.95), response.getPriceAmount());
        assertEquals(4, response.getPriceList());
    }

    @Test
    @DisplayName("Test when no applicable price is found")
    void testGetApplicablePrice_NoPriceFound() {
        // Arrange
        LocalDateTime requestDate = LocalDateTime.of(2020, 7, 1, 10, 0);

        when(priceRepository.findApplicablePrice(eq(requestDate), eq(PRODUCT_ID), eq(BRAND_ID)))
                .thenReturn(Optional.empty());

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                priceService.getApplicablePrice(requestDate, PRODUCT_ID, BRAND_ID));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("No se encontró precio aplicable", exception.getReason());

        // Verify interactions
        verify(priceValidator).validate(requestDate, PRODUCT_ID, BRAND_ID);
        verify(priceRepository).findApplicablePrice(requestDate, PRODUCT_ID, BRAND_ID);
    }

    @Test
    @DisplayName("Test validation failure")
    void testGetApplicablePrice_ValidationFailure() {
        // Arrange
        LocalDateTime requestDate = LocalDateTime.of(2020, 6, 14, 10, 0);

        // Configure validator to throw exception
        doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid parameters"))
                .when(priceValidator).validate(eq(requestDate), eq(PRODUCT_ID), eq(null));

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                priceService.getApplicablePrice(requestDate, PRODUCT_ID, null));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Invalid parameters", exception.getReason());

        // Verify repository was never called
        verify(priceRepository, never()).findApplicablePrice(any(), any(), any());
    }

    // Helper method to create Price entities
    private Price createPrice(LocalDateTime startDate, LocalDateTime endDate,
                              BigDecimal priceAmount, Long priceList) {
        Price price = new Price();
        price.setBrand(brand);
        price.setStartDate(startDate);
        price.setEndDate(endDate);
        price.setPriceAmount(priceAmount);
        price.setPriceList(priceList);
        price.setProductId(PRODUCT_ID);
        price.setCurr("EUR");
        price.setPriority(0L);
        return price;
    }
}