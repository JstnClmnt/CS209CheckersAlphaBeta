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
    public Player owner;

    public Piece(int xcoord, int ycoord, String color, Player owner) {
        this.xcoord = xcoord;
        this.ycoord = ycoord;
        this.color = color;
        this.isForwardable = false;
        this.isHoppable = false;
        this.isHoppable2 = false;
        this.isSwappable = false;
        this.owner = owner;
    }
    
    public boolean checkMovability(Tile[][] gameBoard, Player current) {
        boolean isMovableUp = false;
        boolean isMovableDown = false;
        boolean isMovableRight = false;
        boolean isMovableLeft = false;
        boolean isHoppableUp = false;
        boolean isHoppableDown = false;
        boolean isHoppableRight = false;
        boolean isHoppableLeft = false;
        boolean isHoppableUp2 = false;
        boolean isHoppableDown2 = false;
        boolean isHoppableRight2 = false;
        boolean isHoppableLeft2 = false;
        boolean isSwappableUp = false;
        boolean isSwappableDown = false;
        boolean isSwappableRight = false;
        boolean isSwappableLeft = false;
        boolean areAllOwnedForwardable = current.checkForwardability();
        
        //checks if the piece is not in the bottom row (row D)
        if(this.xcoord < 3) {
            //if tile below piece is empty
            if(gameBoard[this.xcoord+1][this.ycoord].hasPiece == false)
                isMovableDown = true;
        }
        //checks if the piece is not in the top row (row A)
        if(this.xcoord > 0) {
            //if tile above piece is empty
            if(gameBoard[this.xcoord-1][this.ycoord].hasPiece == false)
                isMovableUp = true;
        }
        
        //checks if the piece is not in the rightmost column (col 4)
        if(this.ycoord < 3) {
            //if tile to the right of piece is empty
            if(gameBoard[this.xcoord][this.ycoord+1].hasPiece == false)
                isMovableRight = true;
        }
        //checks if the piece is not in the leftmost columns (col 1)
        if(this.ycoord > 0) {
            //if tile to the left of piece is empty
            if(gameBoard[this.xcoord][this.ycoord-1].hasPiece == false)
                isMovableLeft = true;
        }
        
        if(current.name.equals("White")) {
            this.isForwardable = (isMovableRight || isMovableDown);
        }
        else if(current.name.equals("Black")) {
            this.isForwardable = (isMovableLeft || isMovableUp);
        }
        
        //
        if(areAllOwnedForwardable == false) {
            
            //SINGLE HOPS
            //if x coordinate is 0 or 1 then hop down is checkable
            if(this.xcoord < 2) {
                if(gameBoard[this.xcoord+2][this.ycoord].hasPiece == false && gameBoard[this.xcoord+1][this.ycoord].hasPiece) {
                    isHoppableDown = true;
                }
            }
            //if x coordinate is 2 or 3 then hop up is checkable
            if(this.xcoord > 1) {
                if(gameBoard[this.xcoord-2][this.ycoord].hasPiece == false && gameBoard[this.xcoord-1][this.ycoord].hasPiece) {
                    isHoppableUp = true;
                }
            }
            
            //if y coordinate is 0 or 1 then hop right is checkable
            if(this.ycoord < 2) {
                if(gameBoard[this.xcoord][this.ycoord+2].hasPiece == false && gameBoard[this.xcoord][this.ycoord+1].hasPiece) {
                    isHoppableRight = true;
                }
            }
            //if y coordinate is 2 or 3 then hop left is checkable
            if(this.ycoord > 1) {
                if(gameBoard[this.xcoord][this.ycoord-2].hasPiece == false && gameBoard[this.xcoord][this.ycoord-1].hasPiece) {
                    isHoppableLeft = true;
                }
            }
            
            this.isHoppable = (isHoppableUp || isHoppableDown || isHoppableRight || isHoppableLeft);
            
            //DOUBLE HOPS
            //if x coordinate is 0 double hop down is checkable
            if(this.xcoord == 0) {
                if(gameBoard[this.xcoord+3][this.ycoord].hasPiece == false && gameBoard[this.xcoord+1][this.ycoord].hasPiece && gameBoard[this.xcoord+2][this.ycoord].hasPiece) {
                    isHoppableDown2 = true;
                }
            }
            //if x coordinate is 3 then double hop up is checkable
            if(this.xcoord == 3) {
                if(gameBoard[this.xcoord-3][this.ycoord].hasPiece == false && gameBoard[this.xcoord-1][this.ycoord].hasPiece && gameBoard[this.xcoord-2][this.ycoord].hasPiece) {
                    isHoppableUp2 = true;
                }
            }
            
            //if y coordinate is 0 then double hop right is checkable
            if(this.ycoord == 0) {
                if(gameBoard[this.xcoord][this.ycoord+3].hasPiece == false && gameBoard[this.xcoord][this.ycoord+1].hasPiece && gameBoard[this.xcoord][this.ycoord+2].hasPiece) {
                    isHoppableRight2 = true;
                }
            }
            //if y coordinate is 3 then double hop left is checkable
            if(this.ycoord == 3) {
                if(gameBoard[this.xcoord][this.ycoord-3].hasPiece == false && gameBoard[this.xcoord][this.ycoord-1].hasPiece && gameBoard[this.xcoord][this.ycoord-2].hasPiece) {
                    isHoppableLeft2 = true;
                }
            }
            
            this.isHoppable2 = (isHoppableUp2 || isHoppableDown2 || isHoppableRight2 || isHoppableLeft2);
            
            //SWAPS
            //checks if the piece is not on the rightmost column
            if(this.ycoord < 3) {
                //if cell to the right is piece of the opposite color
                if(gameBoard[this.xcoord][this.ycoord+1].hasPiece && !gameBoard[this.xcoord][this.ycoord+1].heldPiece.owner.name.equals(current.name)) {
                    isSwappableRight = true;
                }
            }
            
            //checks if the piece is not on the leftmost column
            if(this.ycoord > 0) {
                //if cell to the left is piece of the opposite color
                if(gameBoard[this.xcoord][this.ycoord-1].hasPiece && !gameBoard[this.xcoord][this.ycoord-1].heldPiece.owner.name.equals(current.name)) {
                    isSwappableLeft = true;
                }
            }
            
            //checks if the piece is not in the bottom row
            if(this.xcoord < 3) {
                //if cell below is piece of the opposite color
                if(gameBoard[this.xcoord+1][this.ycoord].hasPiece && !gameBoard[this.xcoord+1][this.ycoord].heldPiece.owner.name.equals(current.name)) {
                    isSwappableDown = true;
                }
            }
            //checks if the piece is not in the top row
            if(this.xcoord > 0) {
                //if cell above is piece of the opposite color
                if(gameBoard[this.xcoord-1][this.ycoord].hasPiece && !gameBoard[this.xcoord-1][this.ycoord].heldPiece.owner.name.equals(current.name)) {
                    isSwappableUp = true;
                }
            }
            
            this.isSwappable = (isSwappableUp || isSwappableDown || isSwappableRight || isSwappableLeft);
        }
        
        return (isMovableUp || isMovableDown || isMovableRight || isMovableLeft || this.isHoppable || this.isSwappable);
    }
    
    public boolean isForwardable(Tile[][] gameBoard, Player current) {
        boolean isMovableUp = false;
        boolean isMovableDown = false;
        boolean isMovableRight = false;
        boolean isMovableLeft = false;
        
        //checks if the piece is in the top row (row A)
        if(this.xcoord==0) {
            //if tile below piece is empty
            if(gameBoard[this.xcoord+1][this.ycoord].hasPiece == false)
                isMovableDown = true;
        }
        //checks if the piece is in the middle rows (row B or C)
        else if(this.xcoord==1 || this.xcoord==2) {
            //if tile above piece is empty
            if(gameBoard[this.xcoord-1][this.ycoord].hasPiece == false)
                isMovableUp = true;
            //if tile below piece is empty
            if(gameBoard[this.xcoord+1][this.ycoord].hasPiece == false)
                isMovableDown = true;
        }
        //checks if the piece is in the bottom row (row D)
        else {
            //if tile above piece is empty
            if(gameBoard[this.xcoord-1][this.ycoord].hasPiece == false)
                isMovableUp = true;
        }
        
        //checks if the piece is in the leftmost column (col 1)
        if(this.ycoord==0) {
            //if tile to the right of piece is empty
            if(gameBoard[this.xcoord][this.ycoord+1].hasPiece == false)
                isMovableRight = true;
        }
        //checks if the piece is in the middle columns (col 2 or 3)
        else if(this.ycoord==1 || this.ycoord==2) {
            //if tile to the left of piece is empty
            if(gameBoard[this.xcoord][this.ycoord-1].hasPiece == false)
                isMovableLeft = true;
            //if tile to the right of piece is empty
            if(gameBoard[this.xcoord][this.ycoord+1].hasPiece == false)
                isMovableRight = true;
        }
        //checks if the piece is in the rightmost column (col 4)
        else {
            //if tile to the left of piece is empty
            if(gameBoard[this.xcoord][this.ycoord-1].hasPiece == false)
                isMovableLeft = true;
        }
        
        if(current.name.equals("White")) {
            this.isForwardable = (isMovableRight || isMovableDown);
        }
        else if(current.name.equals("Black")) {
            this.isForwardable = (isMovableLeft || isMovableUp);
        }
        
        return this.isForwardable;
    }
    
        
    public boolean isForwardable(Tile[][] gameBoard, AI current) {
        boolean isMovableUp = false;
        boolean isMovableDown = false;
        boolean isMovableRight = false;
        boolean isMovableLeft = false;
        
        //checks if the piece is in the top row (row A)
        if(this.xcoord==0) {
            //if tile below piece is empty
            if(gameBoard[this.xcoord+1][this.ycoord].hasPiece == false)
                isMovableDown = true;
        }
        //checks if the piece is in the middle rows (row B or C)
        else if(this.xcoord==1 || this.xcoord==2) {
            //if tile above piece is empty
            if(gameBoard[this.xcoord-1][this.ycoord].hasPiece == false)
                isMovableUp = true;
            //if tile below piece is empty
            if(gameBoard[this.xcoord+1][this.ycoord].hasPiece == false)
                isMovableDown = true;
        }
        //checks if the piece is in the bottom row (row D)
        else {
            //if tile above piece is empty
            if(gameBoard[this.xcoord-1][this.ycoord].hasPiece == false)
                isMovableUp = true;
        }
        
        //checks if the piece is in the leftmost column (col 1)
        if(this.ycoord==0) {
            //if tile to the right of piece is empty
            if(gameBoard[this.xcoord][this.ycoord+1].hasPiece == false)
                isMovableRight = true;
        }
        //checks if the piece is in the middle columns (col 2 or 3)
        else if(this.ycoord==1 || this.ycoord==2) {
            //if tile to the left of piece is empty
            if(gameBoard[this.xcoord][this.ycoord-1].hasPiece == false)
                isMovableLeft = true;
            //if tile to the right of piece is empty
            if(gameBoard[this.xcoord][this.ycoord+1].hasPiece == false)
                isMovableRight = true;
        }
        //checks if the piece is in the rightmost column (col 4)
        else {
            //if tile to the left of piece is empty
            if(gameBoard[this.xcoord][this.ycoord-1].hasPiece == false)
                isMovableLeft = true;
        }
        
        if(current.name.equals("White")) {
            this.isForwardable = (isMovableRight || isMovableDown);
        }
        else if(current.name.equals("Black")) {
            this.isForwardable = (isMovableLeft || isMovableUp);
        }
        
        return this.isForwardable;
    }
}