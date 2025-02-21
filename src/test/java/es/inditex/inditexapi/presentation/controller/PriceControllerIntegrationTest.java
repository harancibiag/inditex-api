package es.inditex.inditexapi.presentation.controller;

import es.inditex.inditexapi.security.configuration.TestSecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
@SpringBootTest
@Import(TestSecurityConfig.class)
@AutoConfigureMockMvc
class PriceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    private final Long PRODUCT_ID = 356455L;
    private final Long BRAND_ID = 1L; // ZARA

    @Test
    //@WithMockUser(username = "ADMIN", password = "1234", roles = {"ADMIN"})
    @DisplayName("Test 1: Request at 10:00 on day 14 for product 35455 and brand 1 (ZARA)")
    void testGetPrice_Day14At10AM() throws Exception {
        LocalDateTime requestDate = LocalDateTime.of(2020, 6, 14, 10, 0);
        String formattedDate = requestDate.format(formatter);

        mockMvc.perform(get("/prices")
                        .param("date", formattedDate)
                        .param("productId", PRODUCT_ID.toString())
                        .param("brandId", BRAND_ID.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId", is(PRODUCT_ID.intValue())))
                .andExpect(jsonPath("$.brandId", is(BRAND_ID.intValue())))
                .andExpect(jsonPath("$.priceList", is(1)))
                .andExpect(jsonPath("$.priceAmount", is(35.5)));
    }

    @Test
    @WithMockUser(username = "ADMIN", roles = {"ADMIN"})
    @DisplayName("Test 2: Request at 16:00 on day 14 for product 35455 and brand 1 (ZARA)")
    void testGetPrice_Day14At16PM() throws Exception {
        LocalDateTime requestDate = LocalDateTime.of(2020, 6, 14, 16, 0);
        String formattedDate = requestDate.format(formatter);

        mockMvc.perform(get("/prices")
                        .param("date", formattedDate)
                        .param("productId", PRODUCT_ID.toString())
                        .param("brandId", BRAND_ID.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId", is(PRODUCT_ID.intValue())))
                .andExpect(jsonPath("$.brandId", is(BRAND_ID.intValue())))
                .andExpect(jsonPath("$.priceList", is(2)))
                .andExpect(jsonPath("$.priceAmount", is(25.45)));
    }

    @Test
    @WithMockUser(username = "ADMIN", roles = {"ADMIN"})
    @DisplayName("Test 3: Request at 21:00 on day 14 for product 35455 and brand 1 (ZARA)")
    void testGetPrice_Day14At21PM() throws Exception {
        LocalDateTime requestDate = LocalDateTime.of(2020, 6, 14, 21, 0);
        String formattedDate = requestDate.format(formatter);

        mockMvc.perform(get("/prices")
                        .param("date", formattedDate)
                        .param("productId", PRODUCT_ID.toString())
                        .param("brandId", BRAND_ID.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId", is(PRODUCT_ID.intValue())))
                .andExpect(jsonPath("$.brandId", is(BRAND_ID.intValue())))
                .andExpect(jsonPath("$.priceList", is(1)))
                .andExpect(jsonPath("$.priceAmount", is(35.50)));
    }

    @Test
    @WithMockUser(username = "ADMIN", roles = {"ADMIN"})
    @DisplayName("Test 4: Request at 10:00 on day 15 for product 35455 and brand 1 (ZARA)")
    void testGetPrice_Day15At10AM() throws Exception {
        LocalDateTime requestDate = LocalDateTime.of(2020, 6, 15, 10, 0);
        String formattedDate = requestDate.format(formatter);

        mockMvc.perform(get("/prices")
                        .param("date", formattedDate)
                        .param("productId", PRODUCT_ID.toString())
                        .param("brandId", BRAND_ID.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId", is(PRODUCT_ID.intValue())))
                .andExpect(jsonPath("$.brandId", is(BRAND_ID.intValue())))
                .andExpect(jsonPath("$.priceList", is(3)))
                .andExpect(jsonPath("$.priceAmount", is(30.50)));
    }

    @Test
    @WithMockUser(username = "ADMIN", roles = {"ADMIN"})
    @DisplayName("Test 5: Request at 21:00 on day 16 for product 35455 and brand 1 (ZARA)")
    void testGetPrice_Day16At21PM() throws Exception {
        LocalDateTime requestDate = LocalDateTime.of(2020, 6, 16, 21, 0);
        String formattedDate = requestDate.format(formatter);

        mockMvc.perform(get("/prices")
                        .param("date", formattedDate)
                        .param("productId", PRODUCT_ID.toString())
                        .param("brandId", BRAND_ID.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId", is(PRODUCT_ID.intValue())))
                .andExpect(jsonPath("$.brandId", is(BRAND_ID.intValue())))
                .andExpect(jsonPath("$.priceList", is(4)))
                .andExpect(jsonPath("$.priceAmount", is(38.95)));
    }

    @Test
    @WithMockUser(username = "USER", roles = {"USER"})
    @DisplayName("Test with USER role (should have access)")
    void testGetPrice_WithUserRole() throws Exception {
        LocalDateTime requestDate = LocalDateTime.of(2020, 6, 14, 10, 0);
        String formattedDate = requestDate.format(formatter);

        mockMvc.perform(get("/prices")
                        .param("date", formattedDate)
                        .param("productId", PRODUCT_ID.toString())
                        .param("brandId", BRAND_ID.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
