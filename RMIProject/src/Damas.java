import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;


public class Damas extends UnicastRemoteObject implements DamasRemote {

    int jogando;
    String playerOne;
    String playerTwo;
    int playerOneStones;
    int playerTwoStones;
    int cpuStones;
    String gameMode;
    BufferedReader jill = new BufferedReader(new InputStreamReader(System.in));
    String[][] board;

    public Damas() throws RemoteException {
        super();
    }

    public int getPlayerOneStones() throws RemoteException {
        return playerOneStones;
    }

    public int getPlayerTwoStones() throws RemoteException {
        return playerTwoStones;
    }

    public int getCpuStones() throws RemoteException {
        return cpuStones;
    }

    public String[][] getBoard() throws RemoteException {
        return board;
    }

    public void setPlayerOne(String nome) throws IOException {
        this.playerOne = nome;
    }

    public void setPlayerTwo(String nome) throws IOException {
        this.playerTwo = nome;
    }

    public String getPlayerOne() throws RemoteException {
        return this.playerOne;
    }

    public String getPlayerTwo() throws RemoteException {
        return this.playerTwo;
    }

    public void setGameMode(String mode) throws RemoteException {
        this.playerOneStones = 12;
        if (mode.equals("single")) {
            this.cpuStones = 12;
        } else {
            this.playerTwoStones = 12;
        }
        this.gameMode = mode;
    }

    public String getGameMode() throws RemoteException {
        return this.gameMode;
    }

    public void setGame() throws RemoteException {

        board = new String[][]{{"  ", " 0 ", " 1 ", " 2 ", " 3 ", " 4 ", " 5 ", " 6 ", " 7 ", "   "},
                {"0 ", "   ", " ○ ", "   ", " o ", "   ", " o ", "   ", " o ", " 0"},
                {"1 ", " ○ ", "   ", " o ", "   ", " o ", "   ", " o ", "   ", " 1"},
                {"2 ", "   ", " o ", "   ", " o ", "   ", " o ", "   ", " o ", " 2"},
                {"3 ", "   ", "   ", "   ", "   ", "   ", "   ", "   ", "   ", " 3"},
                {"4 ", "   ", "   ", "   ", "   ", "   ", "   ", "   ", "   ", " 4"},
                {"5 ", " × ", "   ", " × ", "   ", " × ", "   ", " × ", "   ", " 5"},
                {"6 ", "   ", " × ", "   ", " × ", "   ", " × ", "   ", " × ", " 6"},
                {"7 ", " × ", "   ", " × ", "   ", " × ", "   ", " × ", "   ", " 7"},
                {"  ", " 0 ", " 1 ", " 2 ", " 3 ", " 4 ", " 5 ", " 6 ", " 7 ", "   "}};
        clean();
        System.out.println(gameMode);
        System.out.println("\nInstruções: insira as ordenadas para mover primeiro e, em seguida, as ordenadas para onde você se moverá separadas por espaço.\n(Ex: 34 45)");
    }



    @Override
    public void jogaCPU() throws RemoteException {
        Random random = new Random();
        boolean trava = true;
        do {
            int a = random.nextInt(8) + 1;
            int b = random.nextInt(8) + 1;
            int c = random.nextInt(8) + 1;
            int d = random.nextInt(8) + 1;
            if (board[a][b].equals(" o ") && board[c][d].equals("   ") && ((a - c == 1) || a - c == -1) && (b - d == 1 || b - d == -1)) {
                board[a][b] = "   ";
                board[c][d] = " o ";
                trava = false;
            } else if (board[a + 1][b + 1].equals(" × ") && board[a][b].equals(" o ") && board[c][d].equals("   ")) {
                board[a + 1][b + 1] = "   ";
                board[a][b] = "   ";
                board[c][d] = " o ";
                this.playerOneStones--;
                trava = false;
            } else if (board[a + 1][b - 1].equals(" × ") && board[a][b].equals(" o ") && board[c][d].equals("   ")) {
                board[a + 1][b - 1] = "   ";
                board[a][b] = "   ";
                board[c][d] = " o ";
                this.playerOneStones--;
                trava = false;
            } else if (board[a - 1][b + 1].equals(" × ") && board[a][b].equals(" o ") && board[c][d].equals("   ")) {
                board[a - 1][b + 1] = "   ";
                board[a][b] = "   ";
                board[c][d] = " o ";
                this.playerOneStones--;
                trava = false;
            } else if (board[a - 1][b - 1].equals(" × ") && board[a][b].equals(" o ") && board[c][d].equals("   ")) {
                board[a - 1][b - 1] = "   ";
                board[a][b] = "   ";
                board[c][d] = " o ";
                this.playerOneStones--;
                trava = false;
            }
        } while (trava);
        clean();
    }


    public void clean() {
        for (int i = 0; i < 100; ++i)
            System.out.println();
    }

    public void playerOne() throws RemoteException, IOException {
        boolean trava = true;
        String move = jill.readLine();
        do {
            int a = Integer.parseInt("" + move.charAt(0)) + 1;
            int b = Integer.parseInt("" + move.charAt(1)) + 1;
            int c = Integer.parseInt("" + move.charAt(3)) + 1;
            int d = Integer.parseInt("" + move.charAt(4)) + 1;
            if (board[a][b].equals(" × ") && board[c][d].equals("   ") && ((a - c == 1) || a - c == -1) && (b - d == 1 || b - d == -1)) {
                board[a][b] = "   ";
                board[c][d] = " × ";
                trava = false;
            } else if (board[a + 1][b + 1].equals(" o ") && board[a][b].equals(" × ") && board[c][d].equals("   ")) {
                board[a + 1][b + 1] = "   ";
                board[a][b] = "   ";
                board[c][d] = " × ";
                if (gameMode.equals("single")) {
                    this.cpuStones--;
                } else {
                    this.playerTwoStones--;
                }
                trava = false;
            } else if (board[a + 1][b - 1].equals(" o ") && board[a][b].equals(" × ") && board[c][d].equals("   ")) {
                board[a + 1][b - 1] = "   ";
                board[a][b] = "   ";
                board[c][d] = " × ";
                if (gameMode.equals("single")) {
                    this.cpuStones--;
                } else {
                    this.playerTwoStones--;
                }
                trava = false;
            } else if (board[a - 1][b + 1].equals(" o ") && board[a][b].equals(" × ") && board[c][d].equals("   ")) {
                board[a - 1][b + 1] = "   ";
                board[a][b] = "   ";
                board[c][d] = " × ";
                if (gameMode.equals("single")) {
                    this.cpuStones--;
                } else {
                    this.playerTwoStones--;
                }
                trava = false;
            } else if (board[a - 1][b - 1].equals(" o ") && board[a][b].equals(" × ") && board[c][d].equals("   ")) {
                board[a - 1][b - 1] = "   ";
                board[a][b] = "   ";
                board[c][d] = " × ";
                if (gameMode.equals("single")) {
                    this.cpuStones--;
                } else {
                    this.playerTwoStones--;
                }
                trava = false;
            } else {
                System.out.println("Movimento invalido. tente novamente: ");
                move = jill.readLine();
            }
        } while (trava);
        clean();
    }

    public void playerTwo() throws RemoteException, IOException {
        boolean trava = true;
        System.out.print(this.playerTwo + "'s move: ");
        String move = jill.readLine();
        do {
            int a = Integer.parseInt("" + move.charAt(0)) + 1;
            int b = Integer.parseInt("" + move.charAt(1)) + 1;
            int c = Integer.parseInt("" + move.charAt(3)) + 1;
            int d = Integer.parseInt("" + move.charAt(4)) + 1;
            if (board[a][b].equals(" o ") && board[c][d].equals("   ") && ((a - c == 1) || a - c == -1) && (b - d == 1 || b - d == -1)) {
                board[a][b] = "   ";
                board[c][d] = " o ";
                trava = false;
            } else if (board[a + 1][b + 1].equals(" × ") && board[a][b].equals(" o ") && board[c][d].equals("   ")) {
                board[a + 1][b + 1] = "   ";
                board[a][b] = "   ";
                board[c][d] = " o ";
                this.playerOneStones--;
                trava = false;
            } else if (board[a + 1][b - 1].equals(" × ") && board[a][b].equals(" o ") && board[c][d].equals("   ")) {
                board[a + 1][b - 1] = "   ";
                board[a][b] = "   ";
                board[c][d] = " o ";
                this.playerOneStones--;
                trava = false;
            } else if (board[a - 1][b + 1].equals(" × ") && board[a][b].equals(" o ") && board[c][d].equals("   ")) {
                board[a - 1][b + 1] = "   ";
                board[a][b] = "   ";
                board[c][d] = " o ";
                this.playerOneStones--;
                trava = false;
            } else if (board[a - 1][b - 1].equals(" × ") && board[a][b].equals(" o ") && board[c][d].equals("   ")) {
                board[a - 1][b - 1] = "   ";
                board[a][b] = "   ";
                board[c][d] = " o ";
                this.playerOneStones--;
                trava = false;
            } else {
                System.out.println("Movimento invalido. tente novamente: ");
                move = jill.readLine();
            }
        } while (trava);
        clean();
    }

    @Override
    synchronized public int getStatus() throws RemoteException {
        return this.jogando;
    }

    @Override
    synchronized public void setStatus(int status) throws RemoteException {
        this.jogando = status;
    }

    public int endGame() throws RemoteException {
        if (gameMode.equals("single")) {
            if (this.cpuStones == 0) {
                return 1; //PLAYER ONE WIN
            } else if (this.playerOneStones == 0) {
                return 2; //CPU WIN
            }
        } else {
            if (this.playerTwoStones == 0) {
                return 1; //PLAYER ONE WIN
            } else if (this.playerOneStones == 0) {
                return 2; //PLAYER TWO WIN
            }

        }
        return 0; // NO ONE WINS YET
    }

}



