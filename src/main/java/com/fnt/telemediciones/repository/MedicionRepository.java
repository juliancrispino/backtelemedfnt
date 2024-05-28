package com.fnt.telemediciones.repository;

import com.fnt.telemediciones.entity.Medicion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicionRepository extends JpaRepository <Medicion, Long> {

    @Query("SELECT med FROM Medicion med WHERE med.serie = :serie ")
    Optional<List<Medicion>> findAllBySerie(String serie);
}
