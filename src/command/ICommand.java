package command;

public interface ICommand { // Interfejs klasy ShootCommand
    boolean execute();
    void undo();
}
