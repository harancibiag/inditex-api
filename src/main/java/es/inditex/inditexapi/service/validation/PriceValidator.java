package es.inditex.inditexapi.service.validation;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Component
public class PriceValidator {

    public void validate(LocalDateTime applicationDate, Long productId, Long brandId) {
        if (applicationDate == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha de aplicación es obligatoria");
        }

        if (productId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El ID del producto es obligatorio");
        }

        if (brandId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El ID de la marca es obligatorio");
        }
    }
}

