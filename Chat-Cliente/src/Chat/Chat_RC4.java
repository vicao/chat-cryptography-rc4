package Chat;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

public class Chat_RC4 extends SingleFrameApplication {

    Socket socketCliente;
    Chat_RC4Application1View view;
    String host = "localhost";
    int porta = 22222;
    String chave;

    public Chat_RC4() {
    }

    @Override
    protected void startup() {
        view = new Chat_RC4Application1View(this);
        show(view);
    }

    public void vai(Socket socketCliente, String chave) {
        this.chave = chave;
        view.setSocketCliente(socketCliente);

        try {
            Recebedor receber;

            DataInputStream inCliente = new DataInputStream(socketCliente.getInputStream());
            receber = new Recebedor(inCliente, view, chave);
            receber.start();

        } catch (UnknownHostException ex) {
            System.out.println("Falha 1");
        } catch (IOException ex) {
            System.out.println("Falha 2");
        }

    }

    @Override
    protected void configureWindow(java.awt.Window root) {
    }

    public static Chat_RC4 getApplication() {
        return Application.getInstance(Chat_RC4.class);
    }

    public static void main(String[] args) {
        launch(Chat_RC4.class, args);
    }
}

class Recebedor extends Thread {

    DataInputStream inCliente;
    Chat_RC4Application1View view;
    String chave;

    Recebedor(DataInputStream Cliente, Chat_RC4Application1View view, String chave) {
        inCliente = Cliente;
        this.view = view;
        this.chave = chave;
    }

    @Override
    public void run() {
        Cifrador cifrador = new Cifrador();

        while (true) {
            String host = null;
            StringBuffer msgCriptografada = null;
            try {
                host = inCliente.readUTF().toString();
                msgCriptografada = new StringBuffer(inCliente.readUTF());
                
            } catch (IOException ex) {
                Logger.getLogger(Recebedor.class.getName()).log(Level.SEVERE, null, ex);
            }

            cifrador.Crypt(msgCriptografada, new StringBuffer(chave));
            
            view.tf_msgRecebida.append(host + " disse  -> " + msgCriptografada.toString());
            view.tf_msgRecebida.setCaretPosition(view.tf_msgRecebida.getText().length());

        }
    }
}
