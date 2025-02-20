package es.inditex.inditexapi.presentation.controller;

import es.inditex.inditexapi.presentation.dto.PriceResponse;
import es.inditex.inditexapi.service.implementation.PriceServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/prices")
@RequiredArgsConstructor
public class PriceController {
    private final PriceServiceImpl priceService;

    @Operation(summary = "Obtener precio aplicable",
            description = "Devuelve el precio activo para un producto, cadena y fecha específicos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Precio encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PriceResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Precio no encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Parametros Invalidos",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error en el Servidor",
                    content = @Content)

    })
    @GetMapping
    public ResponseEntity<PriceResponse> getApplicablePrice(
            @Parameter(description = "Fecha de aplicación (formato: yyyy-MM-dd HH:mm:ss)", example = "2020-06-14 10:00:00")
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime date,

            @Parameter(description = "ID del producto", example = "356455")
            @RequestParam Long productId,

            @Parameter(description = "ID de la cadena", example = "1")
            @RequestParam Long brandId) {

        PriceResponse response = priceService.getApplicablePrice(date, productId, brandId);
        return ResponseEntity.ok(response);
    }
}