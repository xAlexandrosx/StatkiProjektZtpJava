package command;

public interface ICommand {
    boolean execute();
    void undo();
}
