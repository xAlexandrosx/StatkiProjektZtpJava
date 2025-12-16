package registrationservice;

public class PlayerProfile {
    // Identification variables
    private String name;

    // Statistics saved in files
    private int matchesPlayed;
    private int matchesWon;

    private int hitCount;
    private int missCount;

    private int moveCount; // Total number of moves ever made

    // Access methods
    public String getName(){
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public int GetMatchesPlayed(){ return matchesPlayed; }
    public void SetMatchesPlayed(int matchesPlayed){ this.matchesPlayed = matchesPlayed; }

    public int GetMatchesWon(){ return matchesWon; }
    public void SetMatchesWon(int matchesWon){ this.matchesWon = matchesWon; }

    public int GetMatchesLost(){ return matchesPlayed-matchesWon; }
    public double GetWinRatio() {
        if (matchesPlayed == 0){
            return 0;
        }
        return (double) matchesWon / matchesPlayed;
    }

    public int GetHitCount(){ return hitCount; }
    public void SetHitCount(int hitCount){ this.hitCount = hitCount; }

    public int GetMissCount(){ return missCount; }
    public void SetMissCount(int missCount){ this.missCount = missCount; }

    public double GetAccuracy(){
        if (hitCount + missCount == 0){
            return 0;
        }
        return (double) hitCount /(hitCount + missCount);
    }

    public int GetMoveCount(){ return moveCount; }
    public void SetMoveCount(int moveCount){ this.moveCount = moveCount; }

    public double GetMovesPerCount(){
        if (matchesPlayed == 0){
            return 0;
        }
        return (double) moveCount / matchesPlayed;
    }


    // Constructors
    /**
     * Constructor for creating new user profile from scratch
     */
    public PlayerProfile(String name){
        this.name = name;

        matchesPlayed = 0;
        matchesWon = 0;

        hitCount = 0;
        missCount = 0;
        moveCount = 0;
    }

    /**
     * Constructor for recreating the user profile object from saved data
     */
    public PlayerProfile(String name, int matchesPlayed, int matchesWon, int hitCount, int missCount, int moveCount){
        this.name = name;

        this.matchesPlayed = matchesPlayed;
        this.matchesWon = matchesWon;

        this.hitCount = hitCount;
        this.missCount = missCount;
        this.moveCount = moveCount;
    }
}
