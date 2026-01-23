# 3. Dokumentacja projektowa

## 3.1. Wstęp

### Architektura Systemu

Aplikacja została zaprojektowana w oparciu o modularną architekturę, której sercem jest wzorzec **Service Locator**.
Klasa `ServiceLocator` pełni rolę centralnego huba, inicjalizowanego przy starcie aplikacji w metodzie `Main`. Odpowiada
ona za utworzenie i przechowywanie pojedynczych instancji (Singleton) kluczowych serwisów:

* `RegistrationService` (zarządzanie danymi użytkowników),
* `NotificationManager` (komunikacja asynchroniczna),
* `StatisticsService` i `MatchHistoryService` (logika biznesowa danych),
* `ConsoleMenu` (interfejs użytkownika).

Dzięki temu podejściu, klasy w systemie nie muszą bezpośrednio zależeć od siebie nawzajem, a jedynie od
`ServiceLocatora`, co upraszcza graf zależności.

### Przepływ Sterowania (Control Flow)

1. **Inicjalizacja**: Po uruchomieniu, klasa `Main` konfiguruje strategie tworzenia graczy (`PlayerSupplier`) i
   inicjalizuje `ServiceLocator`. Następnie sterowanie przekazywane jest do pętli obsługi menu (`ConsoleMenu`).
2. **Konfiguracja Meczu**: Wybór trybu gry w menu powoduje utworzenie obiektu rozgrywki (klasa pochodna od `Match`).
   Klasa `Match` pobiera odpowiednich graczy (ludzi lub boty AI tworzone przez fabrykę) oraz generuje plansze za pomocą
   `BattleshipDeployer`.
3. **Pętla Gry**: Metoda `playMatch()` w klasie `Match` zarządza naprzemiennymi turami. W każdej turze gracz (`IPlayer`)
   wykonuje ruch, który jest realizowany za pomocą wzorca **Command** (`ShootCommand`). Pozwala to na separację
   intencji (strzał) od wykonania (zmiana stanu `Board`).

### Przepływ Danych (Data Flow)

System wykorzystuje wzorzec **Observer** do reaktywnego przetwarzania danych, co oddziela logikę gry od logiki
biznesowej:

* Podczas rozgrywki, kluczowe zdarzenia (wykonanie ruchu, koniec meczu) są publikowane przez `NotificationManager` jako
  obiekty notyfikacji (np. `TurnTakenNotification`).
* Niezależne serwisy (`StatisticsService`, `MatchHistoryService`) nasłuchują tych zdarzeń. Przykładowo, gdy gracz odda
  strzał, `StatisticsService` automatycznie aktualizuje jego licznik trafień/pudeł, a `MatchHistoryService` zapisuje
  ruch do historii.
* Po zakończeniu meczu, zaktualizowane profile graczy są utrwalane w pliku JSON za pośrednictwem `RegistrationService`,
  który (poprzez Proxy) autoryzuje operację zapisu tylko dla zalogowanych użytkowników.

## 3.2. Wykorzystane wzorce projektowe

W projekcie zastosowano następujące wzorce:

1. **Service Locator**
    * **Miejsce:** Klasa `ServiceLocator`.
    * **Działanie:** Pełni rolę centralnego rejestru, który przechowuje i udostępnia instancje serwisów (np.
      `NotificationManager`, `StatisticsService`). Eliminuje konieczność przekazywania wielu zależności przez
      konstruktory.

2. **Command (Polecenie)**
    * **Miejsce:** Klasa `ShootCommand` (implementuje `ICommand`).
    * **Działanie:** Enkapsuluje akcję "strzału" jako obiekt. Pozwala to na oddzielenie żądania wykonania akcji od jej
      realizacji oraz umożliwia łatwe dodanie funkcjonalności cofania ruchu (`undo`).

3. **Observer (Obserwator)**
    * **Miejsce:** Klasy `NotificationManager`, `Subscriber` oraz różne typy powiadomień (`TurnTakenNotification`).
    * **Działanie:** Umożliwia obiektom (subskrybentom) reagowanie na zdarzenia (np. koniec tury, koniec meczu) bez
      bezpośredniego powiązania z obiektem generującym zdarzenie. `MatchHistoryService` nasłuchuje zdarzeń, aby
      zapisywać przebieg gry.

4. **Singleton**
    * **Miejsce:** Klasa `ServiceLocator` (oraz zarządzane przez nią serwisy jak `GlobalVariables`).
    * **Działanie:** Gwarantuje, że istnieje tylko jedna instancja `ServiceLocator` w całej aplikacji, zapewniając
      spójny dostęp do zasobów.

5. **Proxy (Pełnomocnik)**
    * **Miejsce:** Klasa `RegistrationServiceAccessProxy`.
    * **Działanie:** Pośredniczy w dostępie do `RegistrationService`. Pełni funkcję ochronną – sprawdza, czy użytkownik
      jest zalogowany (chyba że jest gościem lub AI), zanim pozwoli na zapis lub aktualizację profilu gracza.

6. **Adapter**
    * **Miejsce:** Klasa `BattleshipAdapter`.
    * **Działanie:** Dostosowuje interfejs klasy `Battleship` do interfejsu `IBattleship` wymaganego przez resztę
      systemu, umożliwiając współpracę niekompatybilnych klas.

7. **Builder (Budowniczy)**
    * **Miejsce:** Klasa `BoardBuilder`.
    * **Działanie:** Umożliwia krokowe tworzenie złożonego obiektu `Board`, pozwalając na łatwe dodawanie statków o
      różnych rozmiarach i konfiguracjach przed finalnym utworzeniem planszy.

8. **Factory (Fabryka)**
    * **Miejsce:** `PlayerStrategy` oraz klasy graczy (`HumanPlayer`, `AiPlayerDifficult`).
    * **Działanie:** Umożliwia wymianę algorytmów sterowania graczem (człowiek vs różne poziomy trudności AI).
      `PlayerStrategy` pełni rolę fabryki, tworząc odpowiednie instancje graczy na podstawie wybranego poziomu
      trudności.

## 3.3. Opis klas

Poniżej przedstawiono opis zadań poszczególnych klas, pogrupowanych według pakietów:

### Pakiet `main`

* `Main`: Punkt wejścia aplikacji. Inicjalizuje `ServiceLocator`, konfiguruje graczy i uruchamia główne menu.

### Pakiet `ServiceLocator`

* `IServiceLocator`: Interfejs definiujący metody dostępu do globalnych serwisów.
* `ServiceLocator`: Implementacja wzorca Service Locator (Singleton). Zarządza cyklem życia i dostępem do wszystkich
  kluczowych serwisów w aplikacji (np. menu, statystyki, powiadomienia).

### Pakiet `globalvariables`

* `IGlobalVariables`: Interfejs dla zmiennych globalnych.
* `GlobalVariables`: Przechowuje globalne ustawienia gry, takie jak referencje do aktualnych graczy czy rozmiar planszy.

### Pakiet `menu`

* `IMenu`: Interfejs obsługi menu.
* `ConsoleMenu`: Obsługuje interakcję z użytkownikiem w konsoli – wyświetla opcje i przetwarza wybory użytkownika.

### Pakiet `board`

* `Board`: Reprezentuje planszę do gry. Przechowuje siatkę pól i zarządza rozmieszczeniem statków oraz logiką trafień.
* `BoardBuilder`: Implementacja wzorca Builder. Pozwala na stopniowe budowanie planszy poprzez dodawanie statków o
  różnej długości.
* `IBattleshipDeployer`: Interfejs dla algorytmów rozstawiania statków.
* `BattleshipDeployer`: Odpowiada za losowe rozmieszczenie statków na planszy dla graczy komputerowych.

### Pakiet `battleship`

* `IBattleship`: Interfejs reprezentujący statek na planszy.
* `Battleship`: Model danych statku (pozycja, długość, orientacja).
* `BattleshipAdapter`: Implementacja wzorca Adapter. Dostosowuje obiekt `Battleship` do interfejsu `IBattleship`,
  ułatwiając operacje na współrzędnych zajmowanych przez statek.

### Pakiet `players`

* `IPlayer`: Interfejs definiujący podstawowe zachowania gracza.
* `Player`: Klasa bazowa dla graczy, zawierająca wspólne pola (nazwa, profil, referencja do meczu).
* `HumanPlayer`: Reprezentuje gracza sterowanego przez człowieka.
* `AiPlayerBase`: Klasa bazowa dla graczy komputerowych, zawierająca wspólną logikę AI.
* `AiPlayerEasy`: Bot o niskim poziomie trudności (strzela losowo).
* `AiPlayerMedium`: Bot o średnim poziomie trudności.
* `AiPlayerDifficult`: Bot o wysokim poziomie trudności (zaawansowana strategia).

### Pakiet `players.playerstrategy`

* `IPlayerStrategy`: Interfejs strategii doboru graczy.
* `PlayerStrategy`: Implementacja wzorca Strategy/Factory. Tworzy odpowiednie instancje graczy w zależności od wybranego
  poziomu trudności.
* `IPlayerSupplier`: Interfejs dostaczający instancje graczy.
* `PlayerSupplier`: Klasa pośrednicząca w tworzeniu graczy przy użyciu strategii.

### Pakiet `command`

* `ICommand`: Interfejs wzorca Command (metody `execute` i `undo`).
* `ShootCommand`: Polecenie reprezentujące oddanie strzału. Przechowuje stan przed wykonaniem ruchu, umożliwiając jego
  cofnięcie.

### Pakiet `Match`

* `IMatch`: Interfejs kontrolera rozgrywki.
* `Match`: Główna logika przebiegu partii. Zarządza turami, sprawdza warunki końca gry i komunikuje się z graczami.
* `MatchFrame`: Okno aplikacji (Swing) służące do wizualizacji gry.
* `MatchRenderer`: Odpowiada za rysowanie stanu planszy w oknie graficznym.

### Pakiet `observer`

* `Subscriber`: Interfejs subskrybenta, który chce otrzymywać powiadomienia.
* `NotificationManager`: Zarządza listą subskrybentów i dystrybuuje powiadomienia do odpowiednich obiektów.

### Pakiet `observer.notifications`

* `Notification`: Klasa bazowa dla wszystkich typów powiadomień.
* `MatchConfiguredNotification`: Powiadomienie o skonfigurowaniu nowego meczu.
* `MatchFinishedNotification`: Powiadomienie o zakończeniu rozgrywki (zawiera informacje o zwycięzcy).
* `TurnTakenNotification`: Powiadomienie o wykonaniu ruchu (strzału).

### Pakiet `matchhistory`

* `IMatchHistoryService`: Interfejs serwisu historii meczów.
* `MatchHistoryService`: Nasłuchuje zdarzeń i zapisuje przebieg zakończonych gier.
* `MatchRecord`: Obiekt DTO (Data Transfer Object) przechowujący dane o całym meczu (statki, tury).
* `TurnRecord`: Obiekt DTO przechowujący dane o pojedynczym ruchu.

### Pakiet `statisticsservice`

* `IStatisticsService`: Interfejs serwisu statystyk.
* `StatisticsService`: Zlicza trafienia, pudła i wygrane mecze, aktualizując profil gracza w czasie rzeczywistym.

### Pakiet `registrationservice`

* `IRegistrationService`: Interfejs zarządzania użytkownikami.
* `PlayerProfile`: Model danych profilu gracza (statystyki, nick).
* `RegistrationService`: Logika rejestracji, logowania i zapisu danych użytkowników do pliku JSON.
* `RegistrationServiceAccessProxy`: Proxy zabezpieczające dostęp do serwisu rejestracji (wymaga zalogowania dla operacji
  zapisu).

### Pakiet `replayservice`

* `IReplayService`: Interfejs serwisu powtórek.
* `ReplayService`: Pozwala na odtworzenie przebiegu zakończonego meczu "krok po kroku", wykorzystując zapisane
  polecenia.
