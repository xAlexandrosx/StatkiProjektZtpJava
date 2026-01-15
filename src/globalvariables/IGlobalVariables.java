package globalvariables;

import players.IPlayer;

public interface IGlobalVariables {

    IPlayer getPlayer(int index);

    public boolean isPlayerExisting(int index);

    void setPlayer(IPlayer player, int index);

    int getBoardSize();

    void setBoardSize();

    int getAiDelay();

}