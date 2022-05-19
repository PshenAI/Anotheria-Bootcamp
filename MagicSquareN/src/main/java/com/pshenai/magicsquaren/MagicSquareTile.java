package com.pshenai.magicsquaren;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "magictiles")
public class MagicSquareTile {

    @Id
    @GeneratedValue
    private Long id;

    private Integer squareNum;
    private Integer value;
    private String tilePos;

    public MagicSquareTile(Integer squareNum, Integer value, String tilePos) {
        this.squareNum = squareNum;
        this.value = value;
        this.tilePos = tilePos;
    }
}
