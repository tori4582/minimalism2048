package org;

import java.util.Random;

public class Coordinator {
    
    private static Random randomer = new Random();
    
    private Coordinator() {
        
    }
    
    public static int getRandom() {
        return randomer.nextInt(Engine2048.BOARD_SIZE);
    }
    
}
