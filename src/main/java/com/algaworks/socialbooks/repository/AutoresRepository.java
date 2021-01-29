package com.algaworks.socialbooks.repository;

import com.algaworks.socialbooks.domain.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutoresRepository extends JpaRepository<Autor, Long> {

}
