package com.pshenai.magicsquaren;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MagicSquareNApplication implements CommandLineRunner {

    public final MagicSquareService squareService;

    public MagicSquareNApplication(MagicSquareService squareService) {
        this.squareService = squareService;
    }

    public static void main(String[] args) {
        SpringApplication.run(MagicSquareNApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        squareService.calculateSquare();
    }
}
