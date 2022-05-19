package com.pshenai.magicsquaren;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MagicSquareRepository extends JpaRepository<MagicSquareTile, Long> {

}
