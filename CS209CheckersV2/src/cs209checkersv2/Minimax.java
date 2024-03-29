/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs209checkersv2;

import static cs209checkersv2.CS209CheckersV2.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author kendrick
 */
public class Minimax {
    private Player player_me;
    private int counter1;
    
    public Move generateMove(Player [] player,int curPlayer, Tile[][] gameBoard) {
        player_me = player[curPlayer];
        return alphabetaHelper(player,curPlayer, gameBoard);
    }
    
    //since depth limited gamit dito, dito nagiinitialize kung ilan yung depth.
    //yung getPossibleMoves(), nagawa ko na yung emthod na yun.
    public Move alphabetaHelper(Player [] player,int curPlayer, Tile[][] gameBoard) {
        ArrayList<Move> moves = CS209CheckersV2.findPossibleMoves(player[curPlayer]);
        
        List<Double> values = new ArrayList();
        double alpha = Double.NEGATIVE_INFINITY;
        double beta = Double.POSITIVE_INFINITY;
        
        Move bestMove = null;
        double bestValue = (player_me == player[curPlayer]) ? alpha : beta;
        counter1 = 0;
        int depth = 3;
        
        for(Move move : moves) {
            System.out.println(move.source[0]+","+move.source[1]+" to "+move.destination[0]+","+move.destination[1]);
            
            swap(move,curPlayer);
            double score = alphabeta(player,
                        otherPlayer(curPlayer), gameBoard, alpha, beta, depth);
            System.out.println(counter1);
            undoSwap(move);
            
            //now do the weighting
            //score += (double)(weights[board.indices[m]][m])/100;
            
            values.add(score);

            //you want to maximize your score
            if(player_me == player[curPlayer]) {
                if (score > bestValue) {
                    bestValue = score;
                    bestMove = move;
                }
            } else { //minimize score
                if (score < bestValue) {
                    bestValue = score;
                    bestMove = move;
                }
            }
        }

        System.out.println("Evaluated " + counter1 + " nodes");

        for (int i = 0; i < moves.size(); i++) {
            System.out.print("(" + moves.get(i) + "," + values.get(i)+") ");
        }
        System.out.println("");
        return bestMove;
    }
    
    public double alphabeta(
            Player[]player,int curPlayer, Tile[][] node,double alpha, double beta, int depth)
    {
        //game is over when isGameOver() == 1 or 2
        if (depth == 0 || checkWin(node) != 0) {
            counter1++;
            return evaluateBoard(node);
        }
        testPrintBoard(gameBoard);
        System.out.println(player[curPlayer].name);
        //player max
        if (player[curPlayer] == player_me) {
            double score = alpha;
            for(Move move : CS209CheckersV2.findPossibleMoves(player[curPlayer])) {
                swap(move,curPlayer);
                score = alphabeta(player,
                        otherPlayer(curPlayer), node, alpha, beta, depth-1);
                undoSwap(move);
                //find the max score
                if (score > alpha)
                    alpha = score; //you have found a better move
                if (alpha >= beta)
                    return alpha; //cutoff
            }
            return alpha;
        }
        //if  (player != player_me)
        else {
            double score = beta;
            for(Move move : CS209CheckersV2.findPossibleMoves(player[curPlayer])) {
                swap(move,curPlayer);
                score = alphabeta(player,
                        otherPlayer(curPlayer), node, alpha, beta, depth-1);
                undoSwap(move);
                //find the min score
                if (score < beta)
                    beta = score; //opponent has found a better move
                if (alpha >= beta)
                    return beta; //cutoff
            }
            return beta;
        }
    }
    
    public int otherPlayer(int player) {
        return (player == 0) ? 1 : 0;
    }
    
    public static void swap(Move m,int curPlayer) {
        Tile sourceTile = gameBoard[m.source[0]][m.source[1]];
        Tile destinationTile = gameBoard[m.destination[0]][m.destination[1]];
        System.out.println(m.source[0]+","+m.source[1]+ " to "+m.destination[0]+","+m.destination[1]);
        //if destination tile is empty, source tile will have no more piece, destination tile will have the piece.
        if(destinationTile.hasPiece == false) {
            destinationTile.heldPiece = sourceTile.heldPiece;
            destinationTile.hasPiece = true;
            sourceTile.hasPiece = false;
            sourceTile.heldPiece = null;
            destinationTile.heldPiece = new Piece(m.destination[0], m.destination[1], (curPlayer == 0) ? "W": "B", players[curPlayer]);
            //the piece will change its coordinates
            destinationTile.heldPiece.xcoord = m.destination[0];
            destinationTile.heldPiece.ycoord = m.destination[1];
            
            //the player will update its list of owned pieces
            players[curPlayer].updateOwnedPieces(m.source, m.destination);
        }
        
        //if destination tile contains another player's piece(only happens in swaps),
        //source's piece will be held in a temp holder variable
        //source tile will then hold destination tile's piece
        //destination tile will then hold source tile's piece
        //then update both piece's coordinates and update both players' list of owned pieces
        else if(destinationTile.hasPiece == true) {
            Piece tempPiece = sourceTile.heldPiece;
            sourceTile.heldPiece = destinationTile.heldPiece;
            destinationTile.heldPiece = tempPiece;
            destinationTile.heldPiece = new Piece(m.destination[0], m.destination[1], (curPlayer == 0) ? "W": "B", players[curPlayer]);
            destinationTile.heldPiece.xcoord = m.source[0];
            destinationTile.heldPiece.ycoord = m.source[1];
            sourceTile.heldPiece.xcoord = m.destination[0];
            sourceTile.heldPiece.ycoord = m.destination[1];
            
            
            //keep track of other player to update their list of owned pieces
            int otherPlayer;
            
            //if current player is white, other player is black. else other player is white
            if(curPlayer == 0)
                otherPlayer = 1;
            else
                otherPlayer = 0;
            
            players[curPlayer].updateOwnedPieces(m.source, m.destination);
            players[otherPlayer].updateOwnedPieces(m.destination, m.source);
        }
    }
    
    public static void undoSwap(Move m) {
        Tile sourceTile = gameBoard[m.destination[0]][m.destination[1]];
        Tile destinationTile = gameBoard[m.source[0]][m.source[1]];
             
        //if destination tile is empty, source tile will have no more piece, destination tile will have the piece.
        if(destinationTile.hasPiece == false) {
            destinationTile.heldPiece = sourceTile.heldPiece;
            destinationTile.hasPiece = true;
            sourceTile.hasPiece = false;
            sourceTile.heldPiece = null;
            
            //the piece will change its coordinates
            destinationTile.heldPiece.xcoord = m.destination[0];
            destinationTile.heldPiece.ycoord = m.destination[1];
            
            //the player will update its list of owned pieces
            players[curPlayer].updateOwnedPieces(m.source, m.destination);
        }
        
        //if destination tile contains another player's piece(only happens in swaps),
        //source's piece will be held in a temp holder variable
        //source tile will then hold destination tile's piece
        //destination tile will then hold source tile's piece
        //then update both piece's coordinates and update both players' list of owned pieces
        else if(destinationTile.hasPiece == true) {
            Piece tempPiece = sourceTile.heldPiece;
            sourceTile.heldPiece = destinationTile.heldPiece;
            destinationTile.heldPiece = tempPiece;
            
            destinationTile.heldPiece.xcoord = m.source[0];
            destinationTile.heldPiece.ycoord = m.source[1];
            sourceTile.heldPiece.xcoord = m.destination[0];
            sourceTile.heldPiece.ycoord = m.destination[1];
            
            
            //keep track of other player to update their list of owned pieces
            int otherPlayer;
            
            //if current player is white, other player is black. else other player is white
            if(curPlayer == 0)
                otherPlayer = 1;
            else
                otherPlayer = 0;
            
            players[curPlayer].updateOwnedPieces(m.source, m.destination);
            players[otherPlayer].updateOwnedPieces(m.destination, m.source);
        }
    }
    
    public static byte checkWin(Tile[][] b) {
        //black wins
        if(b[0][0].hasPiece && b[0][1].hasPiece && b[0][2].hasPiece
                    && b[1][0].hasPiece && b[1][1].hasPiece
                    && b[2][0].hasPiece) {
            if(b[0][0].heldPiece.color.equals("B") && b[0][1].heldPiece.color.equals("B") && b[0][2].heldPiece.color.equals("B")
                    && b[1][0].heldPiece.color.equals("B") && b[1][1].heldPiece.color.equals("B")
                    && b[2][0].heldPiece.color.equals("B")) {
                return -1;
            }
        }
        //white wins
        if(b[1][3].hasPiece 
                && b[2][2].hasPiece && b[2][3].hasPiece
                && b[3][1].hasPiece && b[3][2].hasPiece && b[3][3].hasPiece) {
            if(b[1][3].heldPiece.color.equals("W") 
                    && b[2][2].heldPiece.color.equals("W") && b[2][3].heldPiece.color.equals("W")
                    && b[3][1].heldPiece.color.equals("W") && b[3][2].heldPiece.color.equals("W") && b[3][3].heldPiece.color.equals("W")) {
                return 1;
            }
        }
        
        //no winner yet
        return 0;
    }
    
    public static void testPrintBoard(Tile[][] boardz) {
        for(int i=0; i<boardz.length; i++) {
            for(int j=0; j<boardz.length; j++) {
                if(j!=boardz.length-1) {
                    if(boardz[i][j].hasPiece) {
                        System.out.print(boardz[i][j].heldPiece.color + "\t");
                    }
                    else
                        System.out.print("O" + "\t");
                }
                else {
                    if(boardz[i][j].hasPiece) {
                        System.out.println(boardz[i][j].heldPiece.color);
                    }
                    else
                        System.out.println("O");
                }
            }
        }
    }
    
    public static double evaluateBoard(Tile[][] gameBoard) {
        double evaluationValue = 0;
        //weigh the different types of moves
        //DOUBLE HOP FORWARD > HOP FORWARD > MOVE FORWARD > SWAP FORWARD > PASS > MOVE BACK> HOP BACKWARD > DOUBLE HOP BACKWARD
        int otherPlayer = (curPlayer==0) ? 1 : 0;
        
        
        for(int i = 0; i < players[curPlayer].ownedPieces.length; i++) {
            if(players[curPlayer].name.equals("Black")) {
                if(players[curPlayer].ownedPieces[i].xcoord==0 && players[curPlayer].ownedPieces[i].ycoord==0) {
                    evaluationValue += 6;
                }
                else if(players[curPlayer].ownedPieces[i].xcoord==0 && players[curPlayer].ownedPieces[i].ycoord==1) {
                    evaluationValue += 4;
                }
                else if(players[curPlayer].ownedPieces[i].xcoord==1 && players[curPlayer].ownedPieces[i].ycoord==0) {
                    evaluationValue += 4;
                }
                else if(players[curPlayer].ownedPieces[i].xcoord==1 && players[curPlayer].ownedPieces[i].ycoord==1) {
                    evaluationValue += 2;
                }
                else if(players[curPlayer].ownedPieces[i].xcoord==0 && players[curPlayer].ownedPieces[i].ycoord==2) {
                    evaluationValue += 2;
                }
                else if(players[curPlayer].ownedPieces[i].xcoord==2 && players[curPlayer].ownedPieces[i].ycoord==0) {
                    evaluationValue += 2;
                }
                else {
                    evaluationValue -= 1;
                }
            }
            else if(players[curPlayer].name.equals("White")){
                if(players[curPlayer].ownedPieces[i].xcoord==3 && players[curPlayer].ownedPieces[i].ycoord==3) {
                    evaluationValue += 6;
                }
                else if(players[curPlayer].ownedPieces[i].xcoord==3 && players[curPlayer].ownedPieces[i].ycoord==2) {
                    evaluationValue += 4;
                }
                else if(players[curPlayer].ownedPieces[i].xcoord==2 && players[curPlayer].ownedPieces[i].ycoord==3) {
                    evaluationValue += 4;
                }
                else if(players[curPlayer].ownedPieces[i].xcoord==2 && players[curPlayer].ownedPieces[i].ycoord==2) {
                    evaluationValue += 2;
                }
                else if(players[curPlayer].ownedPieces[i].xcoord==3 && players[curPlayer].ownedPieces[i].ycoord==1) {
                    evaluationValue += 2;
                }
                else if(players[curPlayer].ownedPieces[i].xcoord==1 && players[curPlayer].ownedPieces[i].ycoord==3) {
                    evaluationValue += 2;
                }
                else {
                    evaluationValue -= 1;
                }
            }
        }
        
        
        
        return evaluationValue;
    }
    
    /*public static Move alphaBeta(int depth, int alpha, int beta, Move move, Player[] players, int curPlayer) {
        //Say that AI is listOfPlayers[1]
        Move newMove;
        Move bestMove = null;
        System.out.println("Player: " + players[curPlayer].name);
        Player currentPlayer = players[curPlayer];
        //ArrayList<PossibleMove> possibleMoveList = BoardAIController.getPossibleMoves(currentPlayer);
        ArrayList<Move> possibleMoveList = CS209CheckersV2.findPossibleMoves(currentPlayer);
        for(Move lol: possibleMoveList) {
            
        }
        int[][] possibleSources = CS209CheckersV2.findPossibleSources(currentPlayer.ownedPieces);
        int bestValue;

        if (depth == MAXDEPTH) {
            Piece newPiece = move.getPiece();
            newPiece.setLayoutX(move.getNewXPost() * 100);
            newPiece.setLayoutY(move.getNewYPost() * 100);
            int fitnessValue = BoardAIController.evaluatePiece(currentPlayer, move.getPiece(), move.getNewXPost(), move.getNewYPost());
            int fitnessVal = Minimax.evaluateBoard(gameBoard, moveType);
            System.out.println("Fitness value of move is: " + fitnessVal);
            newMove = new Move(currentPlayer, move, fitnessValue);
            return newMove;
        }

        for (int i = 0; i < possibleMoveList.size() - 1; i++) {
            //Maximizing player
            if (player == 1) {
                //Initialize current best move
                bestMove = new PlayerMove(currentPlayer, move, MIN);

                System.out.println("Who is the player: " + currentPlayer);
                newMove = alphaBeta(depth - 1, alpha, beta, possibleMoveList.get(i), listOfPlayers, Math.abs(player - 1));
                bestMove.setFitnessValue(Math.max(bestMove.getFitnessValue(), newMove.getFitnessValue()));
                alpha = Math.max(alpha, bestMove.getFitnessValue());

                //Pruning
                if (beta <= alpha) {
                    break;
                }
            } //Minimizing player
            else {
                //Initialize current best move
                bestMove = new PlayerMove(currentPlayer, move, MAX);
                System.out.println("Who is the player: " + currentPlayer);
                newMove = alphaBeta(depth - 1, alpha, beta, possibleMoveList.get(i), listOfPlayers, Math.abs(player + 1));
                bestMove.setFitnessValue(Math.max(bestMove.getFitnessValue(), newMove.getFitnessValue()));
                beta = Math.max(alpha, bestMove.getFitnessValue());

                //Pruning
                if (beta <= alpha) {
                    break;
                }
            }
        }
        return bestMove;

    }*/
}
