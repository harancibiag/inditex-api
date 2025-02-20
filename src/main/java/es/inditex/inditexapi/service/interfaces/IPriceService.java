package es.inditex.inditexapi.service.interfaces;

import es.inditex.inditexapi.presentation.dto.PriceResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public interface IPriceService {

    public PriceResponse getApplicablePrice(LocalDateTime applicationDate,
                                            Long productId,
                                            Long brandId);
}
