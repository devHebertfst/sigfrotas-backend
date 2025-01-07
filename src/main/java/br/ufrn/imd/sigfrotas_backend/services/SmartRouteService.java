package br.ufrn.imd.sigfrotas_backend.services;

import br.ufrn.imd.sigfrotas_backend.domain.Rota;
import br.ufrn.imd.sigfrotas_backend.domain.Endereco;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    public Rota calcularRotaOtimizadaComOptimization(Endereco origem, Endereco destino, List<Endereco> pontosIntermediarios) {
        String url = "https://api.openrouteservice.org/optimization";

        List<Job> jobs = new ArrayList<>();
        int jobId = 1;
        for (Endereco ponto : pontosIntermediarios) {
            jobs.add(new Job(jobId++, new double[]{ponto.getLongitude(), ponto.getLatitude()}, 300, List.of(1)));
        }

        // Configurar o veículo
        Vehicle vehicle = new Vehicle(
                1,
                "driving-car",
                new double[]{origem.getLongitude(), origem.getLatitude()},
                new double[]{destino.getLongitude(), destino.getLatitude()},
                List.of(1),
                new int[]{10},
                new int[]{28800, 4200*2} // Exemplo de janela de tempo (08:00 - 12:00)
        );

        OptimizationRequest request = new OptimizationRequest(jobs, List.of(vehicle));

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<OptimizationRequest> entity = new HttpEntity<>(request, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            String responseBody = response.getBody();
            return processarRespostaOptimization(responseBody, origem, destino, pontosIntermediarios);
        } else {
            throw new RuntimeException("Erro ao calcular rota otimizada: " + response.getStatusCode());
        }
    }

    private Rota processarRespostaOptimization(String responseBody, Endereco origem, Endereco destino, List<Endereco> pontosIntermediarios) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(responseBody);

            JsonNode steps = root.path("routes").get(0).path("steps");

            List<Endereco> pontosOrdenados = new ArrayList<>();
            for (JsonNode step : steps) {
                int jobId = step.path("job").asInt();
                if (jobId > 0) {
                    Endereco ponto = pontosIntermediarios.get(jobId - 1);
                    pontosOrdenados.add(ponto);
                }
            }

            Rota rotaOtimizada = new Rota();
            rotaOtimizada.setOrigem(origem);
            rotaOtimizada.setDestino(destino);
            rotaOtimizada.setPontosIntermediarios(pontosOrdenados);

            return rotaOtimizada;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao processar resposta de otimização");
        }
    }

    // Classes auxiliares para o corpo da requisição
    private static class OptimizationRequest {
        private final List<Job> jobs;
        private final List<Vehicle> vehicles;

        public OptimizationRequest(List<Job> jobs, List<Vehicle> vehicles) {
            this.jobs = jobs;
            this.vehicles = vehicles;
        }

        public List<Job> getJobs() {
            return jobs;
        }

        public List<Vehicle> getVehicles() {
            return vehicles;
        }
    }

    private static class Job {
        private final int id;
        private final double[] location;
        private final int service;
        private final List<Integer> skills;

        public Job(int id, double[] location, int service, List<Integer> skills) {
            this.id = id;
            this.location = location;
            this.service = service;
            this.skills = skills;
        }

        public int getId() {
            return id;
        }

        public double[] getLocation() {
            return location;
        }

        public int getService() {
            return service;
        }

        public List<Integer> getSkills() {
            return skills;
        }
    }

    private static class Vehicle {
        private final int id;
        private final String profile;
        private final double[] start;
        private final double[] end;
        private final List<Integer> skills;
        private final int[] capacity;
        private final int[] time_window;

        public Vehicle(int id, String profile, double[] start, double[] end, List<Integer> skills, int[] capacity, int[] time_window) {
            this.id = id;
            this.profile = profile;
            this.start = start;
            this.end = end;
            this.skills = skills;
            this.capacity = capacity;
            this.time_window = time_window;
        }

        public int getId() {
            return id;
        }

        public String getProfile() {
            return profile;
        }

        public double[] getStart() {
            return start;
        }

        public double[] getEnd() {
            return end;
        }

        public List<Integer> getSkills() {
            return skills;
        }

        public int[] getCapacity() {
            return capacity;
        }

        public int[] getTimeWindow() {
            return time_window;
        }
    }

}
