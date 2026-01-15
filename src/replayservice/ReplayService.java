package replayservice;

import ServiceLocator.ServiceLocator;
import battleship.Battleship;
import board.Board;
import command.ICommand;
import command.ShootCommand;
import matchhistory.MatchRecord;
import matchhistory.TurnRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

public class ReplayService implements IReplayService {
    Stack<ICommand> executedMoves;
    Stack<ICommand> remainingMoves;

    List<Battleship> ships1;
    List<Battleship> ships2;

    String player1;
    String player2;

    Board board1;
    Board board2;

    int turnCounter;

    public ReplayService(){
        executedMoves = new Stack<>();
        remainingMoves = new Stack<>();

        ships1 = new ArrayList<>();
        ships2 = new ArrayList<>();

        board1 = new Board();
        board2 = new Board();

        turnCounter = 0;
    }

    public int getTurnCounter(){
        return turnCounter;
    }

    public boolean displayBoard(String playerName){
        if(Objects.equals(player1, playerName)){
            board1.displayBoard(false);
            return true;
        }
        else if(Objects.equals(player2, playerName)){
            board1.displayBoard(false);
            return true;
        }
        return false;
    }

    public void LoadMatch(MatchRecord matchRecord){
        // Setting up player names
        player1 = matchRecord.player1;
        player2 = matchRecord.player2;

        // Recreating the board for player1
        for(List<int[]> coordinates : matchRecord.ships1){
            int startX = coordinates.getFirst()[0];
            int startY = coordinates.getFirst()[1];
            int len = coordinates.size();
            boolean vert = false;

            if(coordinates.get(0)[0] == coordinates.get(1)[0]){
                vert = true;
            }

            Battleship newShip = new Battleship(startX, startY, len, vert);
            ships1.add(newShip);
        }
        board1.importShips(ships1);

        // Recreating the board for player2
        for(List<int[]> coordinates : matchRecord.ships2){
            int startX = coordinates.getFirst()[0];
            int startY = coordinates.getFirst()[1];
            int len = coordinates.size();
            boolean vert = false;

            if(coordinates.get(0)[0] == coordinates.get(1)[0]){
                vert = true;
            }

            Battleship newShip = new Battleship(startX, startY, len, vert);
            ships2.add(newShip);
        }
        board2.importShips(ships2);

        // Recreating the command objects from turn records
        for(TurnRecord turnRecord : matchRecord.turns){
            ShootCommand newCommand;
            if(Objects.equals(turnRecord.player, matchRecord.player1)){
                newCommand = new ShootCommand(board2, turnRecord.x, turnRecord.y);
            }
            else{
                newCommand = new ShootCommand(board1, turnRecord.x, turnRecord.y);
            }

            remainingMoves.push(newCommand);
        }
    }

    public boolean PreviousMove(){
        if(!executedMoves.isEmpty()){
            turnCounter--;

            ICommand move = executedMoves.pop();
            move.undo();
            remainingMoves.add(move);

            return true;
        }
        return false;
    }

    public boolean NextMove(){
        if(!remainingMoves.isEmpty()){
            turnCounter++;

            ICommand move = remainingMoves.pop();
            move.execute();
            executedMoves.add(move);

            return true;
        }
        return false;
    }
}
