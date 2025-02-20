package es.inditex.inditexapi.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PRICES")
public class Price {

    @Id
    @Column(name = "PRICE_LIST")
    private Long priceList;

    @ManyToOne
    @JoinColumn(name = "BRAND_ID", referencedColumnName = "BRAND_ID")
    private Brand brand;

    @Column(name = "START_DATE")
    private LocalDateTime startDate;

    @Column(name = "END_DATE")
    private LocalDateTime endDate;

    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Column(name = "PRIORITY")
    private Long priority;

    @Column(name = "PRICE")
    private BigDecimal priceAmount;

    @Column(name = "CURR")
    private String curr;
}
