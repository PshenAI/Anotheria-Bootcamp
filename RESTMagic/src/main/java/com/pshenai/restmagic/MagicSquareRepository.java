package com.pshenai.restmagic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MagicSquareRepository extends JpaRepository<MagicSquareTile, Long> {
    List<MagicSquareTile> findAllBySquareNum(Integer squareNum);
}
