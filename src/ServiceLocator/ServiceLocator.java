package ServiceLocator;

import board.BattleshipDeployer;
import board.IBattleshipDeployer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import globalvariables.GlobalVariables;
import globalvariables.IGlobalVariables;
import matchhistory.IMatchHistoryService;
import matchhistory.MatchHistoryService;
import menu.ConsoleMenu;
import menu.IMenu;
import observer.NotificationManager;
import players.playerstrategy.IPlayerSupplier;
import registrationservice.IRegistrationService;
import registrationservice.RegistrationService;
import registrationservice.RegistrationServiceAccessProxy;
import replayservice.IReplayService;
import replayservice.ReplayService;
import statisticsservice.IStatisticsService;
import statisticsservice.StatisticsService;

import java.util.Random;
import java.util.Scanner;

public class ServiceLocator {
    private static ServiceLocator instance;

    //region Services
    // Built-in services
    private Random random;
    private Scanner scanner;
    private Gson gson;

    // Custom services
    private IMenu consoleMenu;
    private IGlobalVariables globalVariables;
    private NotificationManager notificationManager;
    private IBattleshipDeployer battleshipDeployer;
    private IRegistrationService registrationService;
    private IRegistrationService registrationServiceProxy;
    private IReplayService replayService;
    private IPlayerSupplier playerSupplier;

    // Observer-style services
    private IMatchHistoryService matchHistoryService;
    private IStatisticsService statisticsService;
    //endregion

    private ServiceLocator(IPlayerSupplier playerSupplier){
        this.playerSupplier = playerSupplier;
    }

    //region Field access
    public Random getRandom(){ return random; }
    public Scanner getScanner() { return scanner; }
    public Gson getGson() { return gson; }

    public IMenu getConsoleMenu() { return consoleMenu; }
    public IGlobalVariables getGlobalVariables() { return globalVariables; }
    public NotificationManager getNotificationManager() { return notificationManager; };
    public IBattleshipDeployer getBattleshipDeployer() { return battleshipDeployer; };
    public IRegistrationService getRegistrationServiceProxy() { return registrationServiceProxy; };
    public IReplayService getReplayService() { return replayService; };
    public IPlayerSupplier getPlayerSupplier() { return playerSupplier; };

    public IMatchHistoryService getMatchHistoryService() { return matchHistoryService; };
    public IStatisticsService getStatisticsService() { return statisticsService; };
    //endregion

    // Singleton methods
    public static void init(IPlayerSupplier playerSupplier){
        if (ServiceLocator.instance != null){
            throw new IllegalArgumentException("ServiceLocator has already been initialized!");
        }
        ServiceLocator.instance = new ServiceLocator(playerSupplier);

        // We set up the services AFTER the Service Locator instance has been created
        ServiceLocator sl = ServiceLocator.getInstance();

        sl.random = new Random();
        sl.scanner = new Scanner(System.in);
        sl.gson = new GsonBuilder().setPrettyPrinting().create();

        sl.consoleMenu = new ConsoleMenu();
        sl.globalVariables = new GlobalVariables();
        sl.notificationManager = new NotificationManager();
        sl.battleshipDeployer = new BattleshipDeployer();
        sl.registrationService = new RegistrationService();
        sl.registrationServiceProxy = new RegistrationServiceAccessProxy(sl.registrationService);
        sl.replayService = new ReplayService();

        sl.matchHistoryService = new MatchHistoryService();
        sl.statisticsService = new StatisticsService();
    }

    public static ServiceLocator getInstance(){
        if(ServiceLocator.instance == null){
            throw new IllegalArgumentException("ServiceLocator has not been initialized!");
        }

        return ServiceLocator.instance;
    }
}