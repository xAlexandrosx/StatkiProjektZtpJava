package replayservice;

import matchhistory.MatchRecord;

public interface IReplayService {
    public int getTurnCounter();

    public String displayBoards();

    public void LoadMatch(MatchRecord matchRecord);

    public boolean NextMove();

    public boolean PreviousMove();
}
