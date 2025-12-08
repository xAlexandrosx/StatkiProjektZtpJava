package board;

import battleship.Battleship;

import java.util.List;

public interface IBattleshipDeployer {

    List<Battleship> getBattleshipsRandom(int size);

    Battleship placeShip(int size, int length, boolean[][] grid);

}
