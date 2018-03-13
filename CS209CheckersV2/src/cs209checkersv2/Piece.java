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
public class Piece {
    public int xcoord;
    public int ycoord;
    public String color;
    public boolean isMovable;
    public boolean isForwardable;
    public boolean isSwappable;
    public boolean isHoppable;
    public boolean isHoppable2;
    public boolean isMovableUp = false;
    public boolean isMovableDown = false;
    public boolean isMovableRight = false;
    public boolean isMovableLeft = false;
    public boolean isHoppableUp = false;
    public boolean isHoppableDown = false;
    public boolean isHoppableRight = false;
    public boolean isHoppableLeft = false;
    public boolean isHoppableUp2 = false;
    public boolean isHoppableDown2 = false;
    public boolean isHoppableRight2 = false;
    public boolean isHoppableLeft2 = false;
    public boolean isSwappableUp = false;
    public boolean isSwappableDown = false;
    public boolean isSwappableRight = false;
    public boolean isSwappableLeft = false;
    public Player owner;

    public Piece(int xcoord, int ycoord, String color, Player owner) {
        this.xcoord = xcoord;
        this.ycoord = ycoord;
        this.color = color;
        this.isForwardable = false;
        this.isHoppable = false;
        this.isHoppable2 = false;
        this.isSwappable = false;
        this.isMovableUp = false;
        this.isMovableDown = false;
        this.isMovableRight = false;
        this.isMovableLeft = false;
        this.isHoppableUp = false;
        this.isHoppableDown = false;
        this.isHoppableRight = false;
        this.isHoppableLeft = false;
        this.isHoppableUp2 = false;
        this.isHoppableDown2 = false;
        this.isHoppableRight2 = false;
        this.isHoppableLeft2 = false;
        this.isSwappableUp = false;
        this.isSwappableDown = false;
        this.isSwappableRight = false;
        this.isSwappableLeft = false;
        this.owner = owner;
    }
    
    public boolean checkMovability(Tile[][] gameBoard, Player current) {
        this.resetMovabilities();
        boolean areAllOwnedForwardable = current.checkForwardability();
        
        //checks if the piece is not in the bottom row (row D)
        if(this.xcoord < 3) {
            //if tile below piece is empty
            if(gameBoard[this.xcoord+1][this.ycoord].hasPiece == false)
                this.isMovableDown = true;
        }
        //checks if the piece is not in the top row (row A)
        if(this.xcoord > 0) {
            //if tile above piece is empty
            if(gameBoard[this.xcoord-1][this.ycoord].hasPiece == false)
                this.isMovableUp = true;
        }
        
        //checks if the piece is not in the rightmost column (col 4)
        if(this.ycoord < 3) {
            //if tile to the right of piece is empty
            if(gameBoard[this.xcoord][this.ycoord+1].hasPiece == false)
                this.isMovableRight = true;
        }
        //checks if the piece is not in the leftmost columns (col 1)
        if(this.ycoord > 0) {
            //if tile to the left of piece is empty
            if(gameBoard[this.xcoord][this.ycoord-1].hasPiece == false)
                this.isMovableLeft = true;
        }
        
        if(current.name.equals("White")) {
            this.isForwardable = (this.isMovableRight || this.isMovableDown);
        }
        else if(current.name.equals("Black")) {
            this.isForwardable = (this.isMovableLeft || this.isMovableUp);
        }
        
        //
        if(areAllOwnedForwardable == false) {
            
            //SINGLE HOPS
            //if x coordinate is 0 or 1 then hop down is checkable
            if(this.xcoord < 2) {
                if(gameBoard[this.xcoord+2][this.ycoord].hasPiece == false && gameBoard[this.xcoord+1][this.ycoord].hasPiece) {
                    this.isHoppableDown = true;
                }
            }
            //if x coordinate is 2 or 3 then hop up is checkable
            if(this.xcoord > 1) {
                if(gameBoard[this.xcoord-2][this.ycoord].hasPiece == false && gameBoard[this.xcoord-1][this.ycoord].hasPiece) {
                    this.isHoppableUp = true;
                }
            }
            
            //if y coordinate is 0 or 1 then hop right is checkable
            if(this.ycoord < 2) {
                if(gameBoard[this.xcoord][this.ycoord+2].hasPiece == false && gameBoard[this.xcoord][this.ycoord+1].hasPiece) {
                    this.isHoppableRight = true;
                }
            }
            //if y coordinate is 2 or 3 then hop left is checkable
            if(this.ycoord > 1) {
                if(gameBoard[this.xcoord][this.ycoord-2].hasPiece == false && gameBoard[this.xcoord][this.ycoord-1].hasPiece) {
                    this.isHoppableLeft = true;
                }
            }
            
            this.isHoppable = (this.isHoppableUp || this.isHoppableDown || this.isHoppableRight || this.isHoppableLeft);
            
            //DOUBLE HOPS
            //if x coordinate is 0 double hop down is checkable
            if(this.xcoord == 0) {
                if(gameBoard[this.xcoord+3][this.ycoord].hasPiece == false && gameBoard[this.xcoord+1][this.ycoord].hasPiece && gameBoard[this.xcoord+2][this.ycoord].hasPiece) {
                    this.isHoppableDown2 = true;
                }
            }
            //if x coordinate is 3 then double hop up is checkable
            if(this.xcoord == 3) {
                if(gameBoard[this.xcoord-3][this.ycoord].hasPiece == false && gameBoard[this.xcoord-1][this.ycoord].hasPiece && gameBoard[this.xcoord-2][this.ycoord].hasPiece) {
                    this.isHoppableUp2 = true;
                }
            }
            
            //if y coordinate is 0 then double hop right is checkable
            if(this.ycoord == 0) {
                if(gameBoard[this.xcoord][this.ycoord+3].hasPiece == false && gameBoard[this.xcoord][this.ycoord+1].hasPiece && gameBoard[this.xcoord][this.ycoord+2].hasPiece) {
                    this.isHoppableRight2 = true;
                }
            }
            //if y coordinate is 3 then double hop left is checkable
            if(this.ycoord == 3) {
                if(gameBoard[this.xcoord][this.ycoord-3].hasPiece == false && gameBoard[this.xcoord][this.ycoord-1].hasPiece && gameBoard[this.xcoord][this.ycoord-2].hasPiece) {
                    this.isHoppableLeft2 = true;
                }
            }
            
            this.isHoppable2 = (this.isHoppableUp2 || this.isHoppableDown2 || this.isHoppableRight2 || this.isHoppableLeft2);
            
            //SWAPS
            //checks if the piece is not on the rightmost column
            if(this.ycoord < 3) {
                //if cell to the right is piece of the opposite color
                if(gameBoard[this.xcoord][this.ycoord+1].hasPiece && !gameBoard[this.xcoord][this.ycoord+1].heldPiece.owner.name.equals(current.name)) {
                    this.isSwappableRight = true;
                }
            }
            
            //checks if the piece is not on the leftmost column
            if(this.ycoord > 0) {
                //if cell to the left is piece of the opposite color
                if(gameBoard[this.xcoord][this.ycoord-1].hasPiece && !gameBoard[this.xcoord][this.ycoord-1].heldPiece.owner.name.equals(current.name)) {
                    this.isSwappableLeft = true;
                }
            }
            
            //checks if the piece is not in the bottom row
            if(this.xcoord < 3) {
                //if cell below is piece of the opposite color
                if(gameBoard[this.xcoord+1][this.ycoord].hasPiece && !gameBoard[this.xcoord+1][this.ycoord].heldPiece.owner.name.equals(current.name)) {
                    this.isSwappableDown = true;
                }
            }
            //checks if the piece is not in the top row
            if(this.xcoord > 0) {
                //if cell above is piece of the opposite color
                if(gameBoard[this.xcoord-1][this.ycoord].hasPiece && !gameBoard[this.xcoord-1][this.ycoord].heldPiece.owner.name.equals(current.name)) {
                    this.isSwappableUp = true;
                }
            }
            
            this.isSwappable = (this.isSwappableUp || this.isSwappableDown || this.isSwappableRight || this.isSwappableLeft);
        }
        
        this.isMovable = (this.isMovableUp || this.isMovableDown || this.isMovableRight || this.isMovableLeft || this.isHoppable || this.isHoppable2 || this.isSwappable);
        return (this.isMovableUp || this.isMovableDown || this.isMovableRight || this.isMovableLeft || this.isHoppable || this.isHoppable2 || this.isSwappable);
    }
    
    public boolean isForwardable(Tile[][] gameBoard, Player current) {
        this.resetMovabilities();
        //checks if the piece is not in the bottom row (row D)
        if(this.xcoord < 3) {
            //if tile below piece is empty
            if(gameBoard[this.xcoord+1][this.ycoord].hasPiece == false)
                this.isMovableDown = true;
        }
        //checks if the piece is not in the top row (row A)
        if(this.xcoord > 0) {
            //if tile above piece is empty
            if(gameBoard[this.xcoord-1][this.ycoord].hasPiece == false)
                this.isMovableUp = true;
        }
        
        //checks if the piece is not in the rightmost column (col 4)
        if(this.ycoord < 3) {
            //if tile to the right of piece is empty
            if(gameBoard[this.xcoord][this.ycoord+1].hasPiece == false)
                this.isMovableRight = true;
        }
        //checks if the piece is not in the leftmost columns (col 1)
        if(this.ycoord > 0) {
            //if tile to the left of piece is empty
            if(gameBoard[this.xcoord][this.ycoord-1].hasPiece == false)
                this.isMovableLeft = true;
        }
        
        if(current.name.equals("White")) {
            this.isForwardable = (this.isMovableRight || this.isMovableDown);
        }
        else if(current.name.equals("Black")) {
            this.isForwardable = (this.isMovableLeft || this.isMovableUp);
        }
        
        return this.isForwardable;
    }
    
    public void resetMovabilities() {
        this.isForwardable = false;
        this.isHoppable = false;
        this.isHoppable2 = false;
        this.isSwappable = false;
        this.isMovableUp = false;
        this.isMovableDown = false;
        this.isMovableRight = false;
        this.isMovableLeft = false;
        this.isHoppableUp = false;
        this.isHoppableDown = false;
        this.isHoppableRight = false;
        this.isHoppableLeft = false;
        this.isHoppableUp2 = false;
        this.isHoppableDown2 = false;
        this.isHoppableRight2 = false;
        this.isHoppableLeft2 = false;
        this.isSwappableUp = false;
        this.isSwappableDown = false;
        this.isSwappableRight = false;
        this.isSwappableLeft = false;
    }
}