/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luanprojetos.musicapostgrerest.models;

import java.util.Random;

/**
 *
 * @author Luan
 */
public class StaticFunctions {

    public static int getRandomNumberAsVerificationCod() {
        int min = 100000;
        int max = 1000000;
        Random r = new Random();
        return r.ints(min, (max + 1)).findFirst().getAsInt();
    }

    public static int getRandomNumberInRange(int min, int max) {
        Random r = new Random();
        return r.ints(min, (max + 1)).findFirst().getAsInt();

    }

}
