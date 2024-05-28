package com.fnt.telemediciones.service;

import com.fnt.telemediciones.DTO.ResponseDTO;
import com.fnt.telemediciones.DTO.DatosMedidorDTO;
import com.fnt.telemediciones.entity.Medicion;
import com.fnt.telemediciones.repository.MedicionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class MedicionService {
    protected MedicionRepository medicionRepository;

    @Autowired
    public MedicionService(MedicionRepository medicionRepository){
        this.medicionRepository = medicionRepository;
    }

    public ResponseDTO saveMedicion(DatosMedidorDTO datosMedidorDTO){
        log.info("Inicio metodo: saveMedicion() - Datos recibidos: {}", datosMedidorDTO);
        ResponseDTO responseDTO = new ResponseDTO();

        try{
            Medicion medicion = mapearDatosMedidorAMedicion(datosMedidorDTO);
            medicionRepository.save(medicion);
            log.info("Medicion guardada: {}", medicion);
            responseDTO.setSuccess(true);
        } catch (Exception e){
            log.error("No se pudo guardar medicion");
            responseDTO.setSuccess(false);
            responseDTO.setErrorMsg(e.toString());
        }
        return responseDTO;
    }

    public ResponseDTO getMediciones(){
        log.info("Inicio metodo: getMediciones");
        ResponseDTO responseDTO = new ResponseDTO();
        try{
            List<Medicion> mediciones = medicionRepository.findAll();
            if (mediciones.isEmpty()){
                log.info("No se encontraron mediciones");
                responseDTO.setErrorMsg("No se encontraron mediciones");
            } else {
                responseDTO.setData(mediciones);
            }
            responseDTO.setSuccess(true);
        } catch (Exception e){
            log.error("No se pudieron obtener mediciones. Error: {}", e);
            responseDTO.setSuccess(false);
            responseDTO.setErrorMsg(e.toString());
        }
        return responseDTO;
    }

    public ResponseDTO getMedicionesBySerie(String serie){
        log.info("Inicio metodo: getMedicionesBySerie() - N° serie medidor: {}", serie);
        ResponseDTO responseDTO = new ResponseDTO();
        List<Medicion> medicionesBySerie = null;
        try{
            Optional<List<Medicion>> medicionesBySerieOp = medicionRepository.findAllBySerie(serie);
            if (medicionesBySerieOp.isPresent()){
                medicionesBySerie = medicionesBySerieOp.get();
            } else {
                log.info("No se encontraron mediciones para el numero de medidor");
            }
            responseDTO.setSuccess(true);
            responseDTO.setData(medicionesBySerie);
        } catch (Exception e){
            log.error("No se pudieron obtener mediciones. Error: {}", e);
            responseDTO.setSuccess(false);
            responseDTO.setErrorMsg(e.toString());
        }

        return responseDTO;
    }

    public ResponseDTO deleteMedicionById(Long id){
        log.info("Inicio metodo: deleteMedicionById() - id: {}", id);
        ResponseDTO responseDTO = new ResponseDTO();
        try{
            medicionRepository.deleteById(id);
            log.info("Medicion eliminada id: {}", id);
            responseDTO.setSuccess(true);
        } catch (Exception e){
            log.error("No se pudo eliminar medicion id {}", id);
            responseDTO.setSuccess(false);
            responseDTO.setErrorMsg(e.toString());
        }
        return responseDTO;
    }

    public Medicion mapearDatosMedidorAMedicion(DatosMedidorDTO datosMedidorDTO){
        log.info("Datos medidor que llega a mapearse: {}", datosMedidorDTO);
        Medicion medicion = new Medicion();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(datosMedidorDTO.getTimestamp(), formatter);

        medicion.setFecha(dateTime);
        medicion.setSerie(datosMedidorDTO.getSerie());
        medicion.setModelo(datosMedidorDTO.getModelo());
        medicion.setTension(datosMedidorDTO.getPoints().get(0).get("32.7.0"));
        medicion.setCorriente(datosMedidorDTO.getPoints().get(1).get("31.7.0"));
        medicion.setPotenciaActiva(datosMedidorDTO.getPoints().get(2).get("1.7.0"));
        medicion.setPotenciaReactiva(datosMedidorDTO.getPoints().get(3).get("3.7.0"));
        medicion.setFactorPotencia(datosMedidorDTO.getPoints().get(4).get("13.7.0"));
        medicion.setFrecuencia(datosMedidorDTO.getPoints().get(5).get("14.7.0"));
        medicion.setEnergiaActivaAcumulada(datosMedidorDTO.getPoints().get(6).get("15.8.0"));
        medicion.setEnergiaReactivaAcumulada(datosMedidorDTO.getPoints().get(7).get("3.8.0"));
        medicion.setPotenciaAparente(datosMedidorDTO.getPoints().get(8).get("1.6.0"));

        log.info("Medicion mapeada: {}",medicion);
        return medicion;
    }
}
