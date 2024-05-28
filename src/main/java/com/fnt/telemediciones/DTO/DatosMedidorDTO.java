package com.fnt.telemediciones.DTO;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@Data
public class DatosMedidorDTO {

    private String secret;

    private String timestamp;

    private String serie;

    private String modelo;

    private List<Map<String, String>> points;

}
