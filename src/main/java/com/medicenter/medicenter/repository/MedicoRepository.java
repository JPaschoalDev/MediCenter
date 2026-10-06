package com.medicenter.medicenter.repository;

import com.medicenter.medicenter.entity.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

    boolean existsByCpf(String cpf);

    boolean existsByCpfAndIdNot(String cpf, Long id);

    boolean existsByCrm(String crm);

    boolean existsByCrmAndIdNot(String crm, Long id);
}