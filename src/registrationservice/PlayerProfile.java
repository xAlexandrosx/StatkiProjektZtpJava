package registrationservice;

public class PlayerProfile {
    // Identification variables
    private final String name;

    // Statistics saved in files
    private int matchesPlayed;
    private int matchesWon;

    private int hitCount;
    private int missCount;

    private int destroyedShipCount;
    private int moveCount; // Total number of moves ever made

    private int score;

    // Access methods
    public String Name(){ return name; }


    public int GetMatchesPlayed(){ return matchesPlayed; }
    public void SetMatchesPlayed(int matchesPlayed){ this.matchesPlayed = matchesPlayed; }

    public int GetMatchesWon(){ return matchesWon; }
    public void SetMatchesWon(int matchesWon){ this.matchesWon = matchesWon; }

    public int GetMatchesLost(){ return matchesPlayed-matchesWon; }


    public int GetHitCount(){ return hitCount; }
    public void SetHitCount(int hitCount){ this.hitCount = hitCount; }

    public int GetMissCount(){ return missCount; }
    public void SetMissCount(int missCount){ this.missCount = missCount; }

    public double Accuracy(){ return (double) hitCount /(hitCount + missCount); }


    public int GetDestroyedShipCount(){ return destroyedShipCount; }
    public void SetDestroyedShipCount(int destroyedShipCount){ this.destroyedShipCount = destroyedShipCount; }

    public int GetMoveCount(){ return moveCount; }
    public void SetMoveCount(int moveCount){ this.moveCount = moveCount; }

    public int GetScore(){ return score; }
    public void SetScore(int score){ this.score = score; }

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
        destroyedShipCount = 0;
        moveCount = 0;

        score = 0; // Fix later to use for real ELO ranking
    }

    /**
     * Constructor for recreating the user profile object from saved data
     */
    public PlayerProfile(String name, int matchesPlayed, int matchesWon, int hitCount, int missCount, int destroyedShipCount, int moveCount, int score){
        this.name = name;

        this.matchesPlayed = matchesPlayed;
        this.matchesWon = matchesWon;

        this.hitCount = hitCount;
        this.missCount = missCount;
        this.destroyedShipCount = destroyedShipCount;
        this.moveCount = moveCount;

        this.score = score;
    }
}
