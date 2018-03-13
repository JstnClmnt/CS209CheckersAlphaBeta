/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs209checkersv2;

/**
 *
 * @author kendrick
 */
public class Move {
    int[] source = null;
    int[] destination = null;
    String moveType = null; 
    int fitnessVal = 0;
    /*
        POSSIBLE VALUES OF MOVETYPE:
            FIRST CHARACTER:
                D - Double hop
                H - Hop
                M - Single Move
                S - Swap
            SECOND CHARACTER:
                N - North
                S - South
                E - East
                W - West
    */

    public Move() {
        source = null;
        destination = null;
        moveType = null;
        fitnessVal = 0;
    }
    
    public Move(int[] source) {
        this.source = source;
        this.destination = null;
        this.moveType = null;
        fitnessVal = 0;
    }
    
    public Move(int[] source, int[] destination) {
        this.source = source;
        this.destination = destination;
    }
    
    public void printMove() {
        System.out.println("{" + this.source[0] + ", " + this.source[1] + "} to {" + this.destination[0] + ", " + this.destination[1] + "}" );
    }
}
