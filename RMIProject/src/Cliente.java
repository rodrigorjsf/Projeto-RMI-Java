import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Cliente {

    public static final String modeOne = "single";
    public static final String modeTwo = "multi";

    public static void main(String[] args) throws IOException, InterruptedException, RemoteException, NotBoundException {
        Scanner ler = new Scanner(System.in);
        int op = 0;

        clean();
        do {
            System.out.println(" ╔═════════ M E N U ══════════╗");
            System.out.println(" ║ 1 - Jogar                  ║");
            System.out.println(" ║ 0 - Sair                   ║");
            System.out.println(" ╚════════════════════════════╝");

            System.out.println("");
            System.out.printf(" → Digite uma opção:");
            op = ler.nextInt();
            switch (op) {
                case 0:
                    break;
                case 1:
                    menu();
                    break;
                default:
                    clean();
                    System.out.printf("!!! '%d' É uma opção invalida !!!\n", op);
                    sleep(2);
                    clean();
                    break;

            }
        } while (op != 0);


        clean();
        System.out.println("Final do programa.");

    }

    public static void menu() throws IOException, InterruptedException, RemoteException, NotBoundException {
        Scanner ler = new Scanner(System.in);
        int op = 0;

        Registry registro = LocateRegistry.getRegistry(9999);
        DamasRemote remoto = (DamasRemote) registro.lookup("Damas");

        clean();
        do {
            System.out.println(" ╔═════════ M E N U ══════════╗");
            System.out.println(" ║   1 - SinglePlayer         ║");
            System.out.println(" ║   2 - MultiPlayer          ║");
            System.out.println(" ║   0 - Voltar               ║");
            System.out.println(" ╚════════════════════════════╝");
            System.out.println();
            System.out.printf(" → Digite uma opção:");
            op = ler.nextInt();
            switch (op) {
                case 0:
                    break;
                case 1:
                    SinglePlayer(remoto);
                    break;
                case 2:
                    MultiPlayer(remoto);
                    break;
                default:
                    clean();
                    System.out.printf("!!! '%d' É uma opção invalida !!!\n", op);
                    sleep(2);
                    clean();
                    break;

            }
        } while (op != 0);
        clean();
    }

    public static void clean() throws IOException {
        for (int i = 0; i < 100; ++i)
            System.out.println();
    }

    public static void sleep(float num) throws InterruptedException {
        Thread.sleep((long) (num * 1000));
    }

    public static int checkGame(int result, DamasRemote remoto) throws InterruptedException, IOException {
        if (result == 1) {
            clean();
            System.out.printf(remoto.getPlayerOne() + " ganhou!");
            sleep(5);
            return 1;
        } else if (result == 2) {
            clean();
            System.out.printf(remoto.getPlayerOne() + " perdeu!");
            sleep(5);
            return 2;
        }
        return 0;
    }

    public static int checkGameMult(int result, int codigo) throws IOException, InterruptedException {
        if (result != codigo && result != 0) {
            clean();
            System.out.printf("--> Você perdeu!");
            sleep(2);
            return 1;
        } else if (result == codigo) {
            clean();
            System.out.printf("--> Você ganhou!");
            sleep(2);
            return 2;
        }
        return 0;
    }

    public static void SinglePlayer(DamasRemote remoto) throws IOException, InterruptedException {
        BufferedReader jill = new BufferedReader(new InputStreamReader(System.in));
        String nome;
        remoto.setGameMode(modeOne);
        remoto.setGame();
        sleep((float) 3.5);
        System.out.print("Digite o nome do primeiro jogador: ");
        nome = jill.readLine();
        System.out.println("Você irá controlar as pedras × " + remoto.getPlayerOne());
        System.out.println();
        if (remoto.getGameMode().equals(modeOne)) {
            System.out.println("CPU irá controlar as pedras o");
            System.out.println();
        }
        remoto.setPlayerOne(nome);
        while (true) {
            System.out.print("Agora é a vez de " + remoto.getPlayerOne() + ": ");
            remoto.playerOne();
            printTable(remoto.getBoard(), remoto);
            System.out.println(remoto.endGame());
            if (checkGame(remoto.endGame(), remoto) != 0) {
                break;
            }
            clean();
            System.out.print("Vez da CPU.");
            remoto.jogaCPU();
            printTable(remoto.getBoard(), remoto);
            if (checkGame(remoto.endGame(), remoto) != 0) {
                break;
            }
        }

        clean();

    }

    public static void MultiPlayer(DamasRemote remoto) throws IOException, RemoteException, InterruptedException {
        int statusInicial = remoto.getStatus();
        BufferedReader jill = new BufferedReader(new InputStreamReader(System.in));
        int codigo = 0;
        String simbolo = new String();
        String nome;
        clean();
        remoto.setGameMode(modeTwo);
        remoto.setGame();
        sleep((float) 3.5);


        if (statusInicial == 0) {
            codigo = 1;
            remoto.setStatus(codigo);
            System.out.print("Digite o nome do primeiro jogador: ");
            nome = jill.readLine();
            remoto.setPlayerOne(nome);
            System.out.println("Você irá controlar as pedras '×' " + remoto.getPlayerOne());
            System.out.println();


            aguardaOponenteLogar(remoto, codigo);
            System.out.println("--> Oponente Logado, você começa.");
            sleep(2);
            clean();
        } else {
            codigo = 2;
            System.out.print("Digite o nome do oponente de " + remoto.getPlayerOne() + ": ");
            nome = jill.readLine();
            remoto.setPlayerTwo(nome);
            System.out.println("Você irá controlar as pedras 'o' " + remoto.getPlayerTwo());
            System.out.println();
            System.out.println("--> Oponente Logado, você é o segundo a jogar.");
            remoto.setStatus(2);
            sleep(2);
            clean();
            remoto.setStatus(1);
            aguardaOponenteJogar(remoto, codigo);

        }
        printTable(remoto.getBoard(), remoto);
        while (true) {
            if (codigo == 1) {
                System.out.print("Agora é a vez de " + remoto.getPlayerOne() + ": ");
                remoto.playerOne();
                printTable(remoto.getBoard(), remoto);
            } else {
                System.out.print("Agora é a vez de " + remoto.getPlayerTwo() + ": ");
                remoto.playerOne();
                printTable(remoto.getBoard(), remoto);
            }

            if (checkGameMult(remoto.endGame(), codigo) != 0) {
                break;
            }
            aguardaOponenteJogar(remoto, codigo);
            clean();
            if (checkGameMult(remoto.endGame(), codigo) != 0) {
                break;
            }
            System.out.println("--> Oponente jogou, sua vez.");
            sleep(3);
            clean();
        }
        clean();
    }

    public static void aguardaOponenteJogar(DamasRemote remoto, int status) throws RemoteException, InterruptedException, IOException {
        String pontos = new String();

        System.out.println("--> Aguardando jogada do oponente....");
        do {
            sleep(2);
        } while (status != remoto.getStatus());
        clean();
    }

    public static void aguardaOponenteLogar(DamasRemote remoto, int status) throws RemoteException, InterruptedException, IOException {
        String pontos = new String();

        System.out.println("--> Aguardando oponente logar......");
        do {
            sleep((float) 2);
        } while (status == remoto.getStatus());
        clean();
    }

    public static void printTable(String[][] board, DamasRemote remoto) throws RemoteException {
        if (remoto.getGameMode().equals("single")) {
            System.out.println("Player one stones: " + remoto.getPlayerOneStones());
            System.out.println("CPU stones: " + remoto.getCpuStones());
        } else {
            System.out.println("Player one stones: " + remoto.getPlayerOneStones());
            System.out.println("CPU stones: " + remoto.getPlayerTwoStones());
        }

        for (int y = 0; y < 10; y++) {
            System.out.print(board[0][y]);
            if (y != 9) {
                System.out.print("│");
            }

        }
        System.out.println();
        System.out.println("─────────────────────────────────────");
        for (int x = 1; x < 9; x++) {
            for (int y = 0; y < 10; y++) {

                System.out.print(board[x][y]);
                if (y != 9) {
                    System.out.print("│");
                }

            }
            System.out.println();
            if (x != 8) {
                System.out.println("─────────────────────────────────────");
            }

        }
        System.out.println("─────────────────────────────────────");
        for (int y = 0; y < 10; y++) {
            System.out.print(board[9][y]);
            if (y != 9) {
                System.out.print("│");
            }
        }
        System.out.println();
    }
}
