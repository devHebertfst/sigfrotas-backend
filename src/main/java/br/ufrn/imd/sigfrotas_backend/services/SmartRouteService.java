package br.ufrn.imd.sigfrotas_backend.services;

import br.ufrn.imd.sigfrotas_backend.domain.Rota;
import br.ufrn.imd.sigfrotas_backend.domain.Endereco;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class SmartRouteService {

    @Value("${openrouteservice.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public Rota calcularRotaOtimizada(Endereco origem, Endereco destino, List<Endereco> pontosIntermediarios) {
        String url = "https://api.openrouteservice.org/v2/directions/driving-car";

        List<double[]> coordenadas = new ArrayList<>();
        coordenadas.add(new double[]{Double.parseDouble(origem.getNumero()), Double.parseDouble(origem.getCep())});
        for (Endereco ponto : pontosIntermediarios) {
            coordenadas.add(new double[]{Double.parseDouble(ponto.getNumero()), Double.parseDouble(ponto.getCep())});
        }
        coordenadas.add(new double[]{Double.parseDouble(destino.getNumero()), Double.parseDouble(destino.getCep())});

        var requestBody = new OpenRouteRequestBody(coordenadas);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<OpenRouteRequestBody> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            String responseBody = response.getBody();
            return processarResposta(responseBody, origem, destino);
        } else {
            throw new RuntimeException("Falha ao calcular rota: " + response.getStatusCode());
        }
    }

    private Rota processarResposta(String responseBody, Endereco origem, Endereco destino) {
        Rota rota = new Rota();
        rota.setOrigem(origem);
        rota.setDestino(destino);
        rota.setPontosIntermediarios(new ArrayList<>()); // Adicionar pontos intermediários ordenados
        return rota;
    }

    private static class OpenRouteRequestBody {
        private final List<double[]> coordinates;

        public OpenRouteRequestBody(List<double[]> coordinates) {
            this.coordinates = coordinates;
        }

        public List<double[]> getCoordinates() {
            return coordinates;
        }
    }
}
