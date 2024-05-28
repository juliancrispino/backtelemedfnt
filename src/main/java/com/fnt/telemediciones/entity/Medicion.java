package com.fnt.telemediciones.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
public class Medicion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serie;

    private String modelo;

    private LocalDateTime fecha;

    private String tension;

    private String corriente;

    private String potenciaActiva;

    private String potenciaReactiva;

    private String factorPotencia;

    private String frecuencia;

    private String energiaActivaAcumulada;

    private String energiaReactivaAcumulada;

    private String potenciaAparente;

    }
