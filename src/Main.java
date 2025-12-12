import Game.Game;
import board.BattleshipDeployer;
import board.IBattleshipDeployer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import matchhistory.IMatchHistoryService;
import matchhistory.MatchHistoryService;
import menu.ConsoleMenu;
import menu.IMenu;
import players.*;
import players.playerstrategy.IPlayerStrategy;
import players.playerstrategy.IPlayerSupplier;
import players.playerstrategy.PlayerStrategy;
import players.playerstrategy.PlayerSupplier;
import registrationservice.IRegistrationService;
import registrationservice.RegistrationService;
import registrationservice.RegistrationServiceAccessProxy;

void main() {

    Random random = new Random();

    IBattleshipDeployer battleshipDeployer = new BattleshipDeployer(random);

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    Scanner scanner = new Scanner(System.in);

    Game g = new Game(scanner);

    IMatchHistoryService matchHistoryService = new MatchHistoryService(gson, g);

    Map<Integer, IPlayer> playerMap = new HashMap<>();
    playerMap.put(0, new HumanPlayer("", null, matchHistoryService, scanner));
    playerMap.put(1, new AiPlayerEasy("",null, matchHistoryService, random));
    playerMap.put(2, new AiPlayerMedium("",null, matchHistoryService, random));
    playerMap.put(3, new AiPlayerDifficult("",null, matchHistoryService, random));
    IPlayerStrategy playerStrategy = new PlayerStrategy(playerMap);
    IPlayerSupplier playerSupplier = new PlayerSupplier(playerStrategy);

    g.setPlayerSupplier(playerSupplier);

    IRegistrationService registrationService = new RegistrationService(g, gson, scanner, matchHistoryService);

    IRegistrationService registrationServiceProxy = new RegistrationServiceAccessProxy(registrationService);

    IMenu consoleMenu = new ConsoleMenu(playerSupplier, battleshipDeployer, matchHistoryService, registrationServiceProxy, scanner, g);

    int option;
    do {
        consoleMenu.display();
        option = consoleMenu.handleInput();
    } while (option != -1);
}
