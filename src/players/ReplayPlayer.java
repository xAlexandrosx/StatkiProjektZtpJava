package players;

import ServiceLocator.ServiceLocator;
import board.Board;
import matchhistory.TurnRecord;
import registrationservice.PlayerProfile;
import replay.Replay;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class ReplayPlayer extends Player {
    private Queue<TurnRecord> executedMoves;
    private Queue<TurnRecord> remainingMoves;

    public ReplayPlayer(String playerName, List<TurnRecord> moves, ServiceLocator sl){
        super(playerName, sl);
        executedMoves = new ArrayDeque<>();
        remainingMoves = new ArrayDeque<>();

        remainingMoves.addAll(moves);
    }

    @Override
    public void takeTurn(){
        // Execute move from the queue, etc.
    }

    public void undoTurn(){
        // to implement
    }
}
