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

    public ServiceLocator(IPlayerSupplier playerSupplier) {
        this.playerSupplier = playerSupplier;
    }

    public final Random random = new Random();
    public final Scanner scanner = new Scanner(System.in);
    public final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public final IGlobalVariables globalVariables = new GlobalVariables(this);
    public final NotificationManager notificationManager = new NotificationManager();
    public final IBattleshipDeployer battleshipDeployer = new BattleshipDeployer(this);
    public final IMenu consoleMenu = new ConsoleMenu(this);
    private final IRegistrationService registrationService = new RegistrationService(this);
    public final IRegistrationService registrationServiceProxy = new RegistrationServiceAccessProxy(registrationService);
    public final IMatchHistoryService matchHistoryService = new MatchHistoryService(this);
    public final IStatisticsService statisticsService = new StatisticsService(this);
    public final IReplayService replayService = new ReplayService(this);
    public final IPlayerSupplier playerSupplier;
}