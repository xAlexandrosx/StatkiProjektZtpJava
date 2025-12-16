package replay;

import ServiceLocator.ServiceLocator;
import matchhistory.MatchRecord;
import matchhistory.TurnRecord;
import players.ReplayPlayer;

import java.util.*;

public class Replay {
    ServiceLocator sl;
    MatchRecord matchRecord;
    String startingPlayer;

    private final ReplayPlayer p1;
    private final ReplayPlayer p2;



    public Replay(MatchRecord matchRecord, ServiceLocator sl){
        // Set up the variables
        this.matchRecord = matchRecord;

        startingPlayer = matchRecord.turns.getLast().player;

        // Create the replay player objects
        List<TurnRecord> player1Moves = new ArrayList<>();
        List<TurnRecord> player2Moves = new ArrayList<>();

        String player1Name = matchRecord.player1;
        String player2Name = matchRecord.player2;

        for(int i = 0; i < matchRecord.turns.size(); i++)
        {
            if(Objects.equals(matchRecord.turns.get(i).player, player1Name)){
                player1Moves.add(matchRecord.turns.get(i));
            }
            else{
                player2Moves.add(matchRecord.turns.get(i));
            }
        }

        p1 = new ReplayPlayer(player1Name, player1Moves, sl);
        p2 = new ReplayPlayer(player2Name, player2Moves, sl);

        // Odtwórz instancje planszy dla każdego z graczy
        /*
        Board b1 = sl.battleshipDeployer.getBattleshipsRandom(sl.globalVariables.getBoardSize());
        Board b2 = sl.battleshipDeployer.getBattleshipsRandom(sl.globalVariables.getBoardSize());

        p1.setOwnBoard(b1);
        p1.setEnemyBoard(b2);

        p2.setOwnBoard(b2);
        p2.setEnemyBoard(b1);
         */
    }

    public void watchReplay(){
        int turnCounter = 0;

        System.out.println("Starting a replay between " + p1.getName() + " and " + p2.getName());

        while (true) {
            System.out.println("0. Exit");
            System.out.println("1. Previous turn");
            System.out.println("2. Next turn");
            int choice = sl.scanner.nextInt();
            sl.scanner.nextLine();

            switch(choice){
                case 0 -> return;
                case 1 -> {
                    if(turnCounter % 2 == 0) {
                        System.out.println("\n==========================");
                        System.out.println("      " + p1.getName() + "'s turn");
                        System.out.println("==========================");
                        p1.takeTurn();

                        if (p2.getOwnBoard().getShips().isEmpty()) {
                            System.out.println(p1.getName() + " wins!");
                        }
                    }
                    else{
                        System.out.println("\n==========================");
                        System.out.println("      " + p2.getName() + "'s turn");
                        System.out.println("==========================");
                        p2.takeTurn();

                        if (p1.getOwnBoard().getShips().isEmpty()) {
                            System.out.println(p2.getName() + " wins!");
                        }
                    }
                    turnCounter++;
                }
                case 2 ->
            }


            System.out.println("\n==========================");
            System.out.println("      " + p1.getName() + "'s turn");
            System.out.println("==========================");
            p1.takeTurn();

            if (p2.getOwnBoard().getShips().isEmpty()) {
                System.out.println(p1.getName() + " wins!");
                winner = p1;
                loser = p2;
                break;
            }

            System.out.println("\n==========================");
            System.out.println("      " + p2.getName() + "'s turn");
            System.out.println("==========================");
            p2.takeTurn();

            if (p1.getOwnBoard().getShips().isEmpty()) {
                System.out.println(p2.getName() + " wins!");
                winner = p2;
                loser = p1;
                break;
            }
        }
    }
}
