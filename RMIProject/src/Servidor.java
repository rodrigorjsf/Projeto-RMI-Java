import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Servidor {
    public static void main(String[] args) {
        try {
            Registry registro = LocateRegistry.createRegistry(9999);
            DamasRemote objeto = (DamasRemote) new Damas();
            registro.rebind("Damas", objeto);
            System.out.println("Objeto Remoto Criado com sucesso.");

        } catch (RemoteException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}