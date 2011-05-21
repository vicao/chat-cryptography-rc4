/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor.gui;

/**
 *
 * @author noname
 */
public class ServidorGUI {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Servidor servidor = new Servidor();
        servidor.setVisible(true);
        servidor.run(servidor);
        
    }
}
