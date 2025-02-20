package es.inditex.inditexapi.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BRAND")
public class Brand {

    @Id
    @Column(name = "BRAND_ID")
    private Long brandId;

    @Column(name = "BRAND_NAME")
    private String brandName;
}