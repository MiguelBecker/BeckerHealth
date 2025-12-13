package dev.beckerhealth.apresentacao.vaadin.paciente;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.beckerhealth.aplicacao.consultas.dto.ConsultaResumo;
import dev.beckerhealth.dominio.consultas.Consulta;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ConsultaApiClient {
    
    private static final String BASE_URL = "http://localhost:8080/api";
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    
    public ConsultaApiClient() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }
    
    public List<MedicoDTO> buscarMedicos() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/medicos"))
                    .GET()
                    .build();
            
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                List<MedicoResponse> medicosResponse = objectMapper.readValue(
                    response.body(), 
                    new TypeReference<List<MedicoResponse>>() {}
                );
                
                return medicosResponse.stream()
                    .map(m -> new MedicoDTO(m.getId(), m.getNome(), m.getEspecialidade()))
                    .toList();
            }
            return List.of();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
    
    public ConsultaResumo agendarConsulta(Long pacienteId, Long medicoId, 
                                          LocalDate data, LocalTime hora, 
                                          Consulta.TipoConsulta tipo) {
        try {
            AgendarConsultaRequest requestBody = new AgendarConsultaRequest();
            requestBody.pacienteId = pacienteId;
            requestBody.medicoId = medicoId;
            requestBody.dataConsulta = data;
            requestBody.horaConsulta = hora;
            requestBody.tipo = tipo;
            
            String jsonBody = objectMapper.writeValueAsString(requestBody);
            
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/consultas"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();
            
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), ConsultaResumo.class);
            }
            
            // Log do erro para debug
            System.err.println("Erro ao agendar consulta. Status: " + response.statusCode());
            System.err.println("Response body: " + response.body());
            System.err.println("Request URL: " + BASE_URL + "/consultas");
            System.err.println("Request body: " + jsonBody);
            
            throw new RuntimeException("Erro ao agendar consulta: " + response.statusCode() + " - " + response.body());
        } catch (java.net.ConnectException e) {
            throw new RuntimeException("Não foi possível conectar ao backend. Certifique-se de que o backend está rodando na porta 8080.", e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao agendar consulta: " + e.getMessage(), e);
        }
    }
    
    private static class MedicoResponse {
        private Long id;
        private String nome;
        private String especialidade;
        
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }
        public String getEspecialidade() { return especialidade; }
        public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }
    }
    
    private static class AgendarConsultaRequest {
        public Long pacienteId;
        public Long medicoId;
        public LocalDate dataConsulta;
        public LocalTime horaConsulta;
        public Consulta.TipoConsulta tipo;
    }
}

