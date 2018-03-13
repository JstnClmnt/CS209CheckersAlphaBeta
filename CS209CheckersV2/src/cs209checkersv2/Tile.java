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
public class Tile {
    public boolean hasPiece;
    public Piece heldPiece;

    public Tile(boolean hasPiece, Piece heldPiece) {
        this.hasPiece = hasPiece;
        this.heldPiece = heldPiece;
    }

    public Tile() {
        this.hasPiece = false;
        this.heldPiece = null;
    }
}
