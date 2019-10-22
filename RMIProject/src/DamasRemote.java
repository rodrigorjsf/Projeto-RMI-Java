import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DamasRemote extends Remote {



    // Funções Jogo SinglePlayer
    public void jogaCPU() throws IOException;

    public int getCpuStones() throws RemoteException;
    public int getPlayerTwoStones() throws RemoteException;
    public int getPlayerOneStones() throws RemoteException;
    public String[][] getBoard() throws RemoteException;
    public void setPlayerOne (String nome) throws IOException;
    public void setGameMode(String mode) throws RemoteException;
    public void setPlayerTwo (String nome) throws IOException;
    public void setGame() throws IOException;
    public void clean() throws IOException;
    public void playerTwo() throws IOException;
    public void playerOne() throws IOException;
    public int endGame() throws RemoteException;
    public String getPlayerOne() throws RemoteException;
    public String getPlayerTwo() throws RemoteException;
    public String getGameMode() throws RemoteException;
    public int getStatus()throws RemoteException;
    public void setStatus(int status)throws RemoteException;

}
