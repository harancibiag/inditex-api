package es.inditex.inditexapi.persistence.repository;

import es.inditex.inditexapi.persistence.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceRepository extends JpaRepository<Price, Long> {
    @Query(value = "SELECT PRODUCT_ID, BRAND_ID,  PRICE_LIST,  START_DATE, END_DATE, PRICE, CURR, PRIORITY FROM PRICES " +
            "WHERE BRAND_ID = :brandId " +
            "AND PRODUCT_ID = :productId " +
            "AND :applicationDate BETWEEN START_DATE AND END_DATE " +
            "ORDER BY PRIORITY DESC LIMIT 1",
            nativeQuery = true)
    Optional<Price> findApplicablePrice(
            @Param("applicationDate") LocalDateTime applicationDate,
            @Param("productId") Long productId,
            @Param("brandId") Long brandId);
}