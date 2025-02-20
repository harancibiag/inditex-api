package es.inditex.inditexapi.service.implementation;

import es.inditex.inditexapi.persistence.entity.Price;
import es.inditex.inditexapi.persistence.repository.PriceRepository;
import es.inditex.inditexapi.presentation.dto.PriceResponse;
import es.inditex.inditexapi.service.interfaces.IPriceService;
import es.inditex.inditexapi.service.validation.PriceValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements IPriceService {

    private final PriceRepository priceRepository;
    private final PriceValidator priceValidator; // Nueva clase para validaciones

    @Override
    public PriceResponse getApplicablePrice(LocalDateTime applicationDate,
                                            Long productId,
                                            Long brandId) {

        // Validar parámetros
        priceValidator.validate(applicationDate, productId, brandId);

        // Buscar precio aplicable
        Price price = priceRepository.findApplicablePrice(applicationDate, productId, brandId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró precio aplicable"));

        // Convertir a DTO
        return convertToDto(price);
    }

    private PriceResponse convertToDto(Price price) {
        return PriceResponse.builder()
                .productId(price.getProductId())
                .brandId(price.getBrand().getBrandId())
                .priceList(price.getPriceList())
                .startDate(price.getStartDate())
                .endDate(price.getEndDate())
                .priceAmount(price.getPriceAmount())
                .build();
    }
}
