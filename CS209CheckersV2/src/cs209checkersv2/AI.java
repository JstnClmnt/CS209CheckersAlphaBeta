/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs209checkersv2;

import static cs209checkersv2.CS209CheckersV2.gameBoard;

/**
 *
 * @author Justine Clemente
 */
public class AI extends Player{
    //public int turnCount;
    //public Piece[] ownedPieces;
    //public String name;

    public AI(String name) {
        super(name);
    }

    /**public AI(String name) {
        this.turnCount = 0;
        this.name = name;
    }**/
    
    //this method checks if passed coordinates correspond to a piece owned by player
    public boolean isOwner(int xcoord, int ycoord) {
        for(int i = 0; i < ownedPieces.length; i++) {
            if(ownedPieces[i].xcoord == xcoord && ownedPieces[i].ycoord == ycoord) {
                return true;
            }
        }
        return false;
    }
    
    public Piece obtainPiece(int xcoord, int ycoord) {
        for(int i = 0; i < ownedPieces.length; i++) {
            if(ownedPieces[i].xcoord == xcoord && ownedPieces[i].ycoord == ycoord) {
                return ownedPieces[i];
            }
        }
        return null;
    }
    
    public void updateOwnedPieces(int[] sourceCoordinate, int[] destinationCoordinate) {
        for(int i = 0; i < ownedPieces.length; i++) {
            if(ownedPieces[i].xcoord == sourceCoordinate[0] && ownedPieces[i].ycoord == sourceCoordinate[1]) {
                ownedPieces[i].xcoord = destinationCoordinate[0];
                ownedPieces[i].ycoord = destinationCoordinate[1];
                break;
            }
        }
    }
    
    //checks if any of the user's pieces are forwardable
    //will return true if at least one is forwardable
    //false if all are not forwardable
    public boolean checkForwardability() {
        boolean isAllForwardable = false;
        for(int i = 0; i < ownedPieces.length; i++) {
            ownedPieces[i].isForwardable(gameBoard, this);
            isAllForwardable = (isAllForwardable || ownedPieces[i].isForwardable);
        }
        
        return isAllForwardable;
    }

    
    
}
