package com.fnt.telemediciones.controller;

import com.fnt.telemediciones.DTO.DatosMedidorDTO;
import com.fnt.telemediciones.DTO.ResponseDTO;
import com.fnt.telemediciones.service.MedicionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/mediciones")
public class MedicionController {

    private final MedicionService medicionService;

    public MedicionController(MedicionService medicionService){
        this.medicionService = medicionService;
    }

    @PostMapping("/saveMedicion")
    public ResponseEntity<ResponseDTO> saveMedicion(@RequestBody DatosMedidorDTO datosMedidorDTO){
        log.info("saveMedicioon - INICIO - Datos recibidos: {}", datosMedidorDTO);
        ResponseDTO responseDTO = new ResponseDTO();
        try{
            responseDTO = medicionService.saveMedicion(datosMedidorDTO);
            responseDTO.setSuccess(true);
            log.info("Medicion Guardada correctamente");
        } catch (Exception e){
            log.error("Error al guardar medicion");
            responseDTO.setSuccess(false);
            responseDTO.setErrorMsg(e.toString());
        }
        log.info("saveMedicioon - FIN ");
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/getMedicionBySerie/{serie}")
    public ResponseEntity<ResponseDTO> getMedicionBySerie(@PathVariable String serie){
        log.info("getMedicionBySerie - INICIO - N° medidor: {}", serie);
        ResponseDTO responseDTO = new ResponseDTO();
        try{
            responseDTO = medicionService.getMedicionesBySerie(serie);
            responseDTO.setSuccess(true);
            log.info("Mediciones obtenidas correctamente");
        } catch (Exception e){
            log.error("Error al obtener mediciones");
            responseDTO.setSuccess(false);
            responseDTO.setErrorMsg(e.toString());
        }
        log.info("getMedicionBySerie - FIN ");
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/getMediciones")
    public ResponseEntity<ResponseDTO> getMediciones(){
        log.info("getMediciones - INICIO");
        ResponseDTO responseDTO = new ResponseDTO();
        try{
            responseDTO = medicionService.getMediciones();
            responseDTO.setSuccess(true);
            log.info("Mediciones obtenidas correctamente");
        } catch (Exception e){
            log.error("Error al obtener mediciones");
            responseDTO.setSuccess(false);
            responseDTO.setErrorMsg(e.toString());
        }
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/deleteMedicion/{idMedicion}")
    public ResponseEntity<ResponseDTO> deleteMedicion(@PathVariable Long id){
        log.info("deleteMedicion - INICIO - Medicion id: {}", id);
        ResponseDTO responseDTO = new ResponseDTO();
        try{
            responseDTO = medicionService.deleteMedicionById(id);
            responseDTO.setSuccess(true);
            log.info("Medicion id {} eliminada correctamente", id);
        } catch (Exception e){
            log.error("Error al eliminar medicion");
            responseDTO.setSuccess(false);
            responseDTO.setErrorMsg(e.toString());
        }
        log.info("deleteMedicion - FIN ");
        return ResponseEntity.ok(responseDTO);
    }


}
