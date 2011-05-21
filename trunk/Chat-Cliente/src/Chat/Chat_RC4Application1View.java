/*
 * DesktopApplication1View.java
 */
package Chat;

import java.net.UnknownHostException;
import java.util.logging.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import javax.net.ssl.SSLSocket;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;
import org.pushingpixels.substance.api.skin.SubstanceRavenLookAndFeel;

/**
 * The application's main frame.
 */
public class Chat_RC4Application1View extends FrameView {

    Socket socketCliente;
    Cifrador cifrador = new Cifrador();

    public Chat_RC4Application1View(SingleFrameApplication app) {
        super(app);
        try {
            UIManager.setLookAndFeel(new SubstanceRavenLookAndFeel());
            JFrame.setDefaultLookAndFeelDecorated(true);
            JDialog.setDefaultLookAndFeelDecorated(true);
        } catch (Exception e) {
            System.out.println("A definição do LookAndFeel 'Substance', falhou.");
        }
        initComponents();
        lb_conectado.setVisible(false);
        tf_msgRecebida.setEditable(false);
        tf_msgCifrada.setEditable(false);

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String) (evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer) (evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = Chat_RC4.getApplication().getMainFrame();
            aboutBox = new Chat_RC4AboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        Chat_RC4.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        bt_enviar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tf_msgRecebida = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        tf_msgEnviar = new javax.swing.JTextArea();
        tf_ip = new javax.swing.JTextField();
        bt_conectar = new javax.swing.JButton();
        lb_conectado = new javax.swing.JLabel();
        tf_chave = new javax.swing.JTextField();
        tf_msgCifrada = new javax.swing.JTextField();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        mainPanel.setName("mainPanel"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(Chat.Chat_RC4.class).getContext().getActionMap(Chat_RC4Application1View.class, this);
        bt_enviar.setAction(actionMap.get("a")); // NOI18N
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(Chat.Chat_RC4.class).getContext().getResourceMap(Chat_RC4Application1View.class);
        bt_enviar.setText(resourceMap.getString("bt_enviar.text")); // NOI18N
        bt_enviar.setName("bt_enviar"); // NOI18N
        bt_enviar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bt_enviarMouseClicked(evt);
            }
        });

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tf_msgRecebida.setColumns(20);
        tf_msgRecebida.setRows(5);
        tf_msgRecebida.setDropMode(javax.swing.DropMode.INSERT);
        tf_msgRecebida.setName("tf_msgRecebida"); // NOI18N
        jScrollPane1.setViewportView(tf_msgRecebida);

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        tf_msgEnviar.setColumns(20);
        tf_msgEnviar.setRows(5);
        tf_msgEnviar.setName("tf_msgEnviar"); // NOI18N
        jScrollPane2.setViewportView(tf_msgEnviar);

        tf_ip.setText(resourceMap.getString("tf_ip.text")); // NOI18N
        tf_ip.setName("tf_ip"); // NOI18N

        bt_conectar.setText(resourceMap.getString("bt_conectar.text")); // NOI18N
        bt_conectar.setName("bt_conectar"); // NOI18N
        bt_conectar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bt_conectarMouseClicked(evt);
            }
        });

        lb_conectado.setFont(resourceMap.getFont("lb_conectado.font")); // NOI18N
        lb_conectado.setText(resourceMap.getString("lb_conectado.text")); // NOI18N
        lb_conectado.setName("lb_conectado"); // NOI18N

        tf_chave.setText(resourceMap.getString("tf_chave.text")); // NOI18N
        tf_chave.setName("tf_chave"); // NOI18N

        tf_msgCifrada.setText(resourceMap.getString("tf_msgCifrada.text")); // NOI18N
        tf_msgCifrada.setName("tf_msgCifrada"); // NOI18N

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(tf_msgCifrada, javax.swing.GroupLayout.PREFERRED_SIZE, 626, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 702, Short.MAX_VALUE)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addComponent(lb_conectado)
                                .addGap(64, 64, 64)
                                .addComponent(tf_chave, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(tf_ip, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(bt_conectar))
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 642, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bt_enviar)))
                        .addGap(53, 53, 53))))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_conectar)
                    .addComponent(tf_ip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_conectado)
                    .addComponent(tf_chave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(bt_enviar)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(tf_msgCifrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setLabel(resourceMap.getString("exitMenuItem.label")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setLabel(resourceMap.getString("helpMenu.label")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setLabel(resourceMap.getString("aboutMenuItem.label")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);
        aboutMenuItem.getAccessibleContext().setAccessibleName(resourceMap.getString("aboutMenuItem.AccessibleContext.accessibleName")); // NOI18N

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 767, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 508, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(114, 114, 114)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(statusPanelLayout.createSequentialGroup()
                        .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(statusMessageLabel)
                            .addComponent(statusAnimationLabel))
                        .addGap(3, 3, 3))
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    public void setSocketCliente(Socket conexao) {
        socketCliente = conexao;
    }

    private void bt_enviarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_enviarMouseClicked
        boolean rodaWhile = true;

        while (rodaWhile) {
            DataOutputStream outCliente = null;

            Scanner scanner = new Scanner(System.in);
            try {
                outCliente = new DataOutputStream(socketCliente.getOutputStream());
            } catch (IOException ex) {
                Logger.getLogger(Chat_RC4Application1View.class.getName()).log(Level.SEVERE, null, ex);
            }


            String msg = tf_msgEnviar.getText() + "\n";
            
            StringBuffer msgCriptografa = new StringBuffer(msg);
            cifrador.Crypt(msgCriptografa, new StringBuffer(tf_chave.getText()));            
            try {
                outCliente.writeUTF(msgCriptografa.toString());
            } catch (IOException ex) {
                Logger.getLogger(Chat_RC4Application1View.class.getName()).log(Level.SEVERE, null, ex);
            }
            tf_msgEnviar.setText("");
            tf_msgCifrada.setText(msgCriptografa.toString());
            rodaWhile = false;
            podeLer = true;
        }
    }//GEN-LAST:event_bt_enviarMouseClicked

    private void bt_conectarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_conectarMouseClicked
        Socket clienteSocket = null;
        try {
            clienteSocket = new Socket(tf_ip.getText(), 22222);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Chat_RC4Application1View.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Chat_RC4Application1View.class.getName()).log(Level.SEVERE, null, ex);
        }
        Chat_RC4.getApplication().vai(clienteSocket, tf_chave.getText());
        this.socketCliente = socketCliente;
        lb_conectado.setVisible(true);

    }//GEN-LAST:event_bt_conectarMouseClicked
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton bt_conectar;
    public javax.swing.JButton bt_enviar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JLabel lb_conectado;
    public javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JTextField tf_chave;
    public javax.swing.JTextField tf_ip;
    public javax.swing.JTextField tf_msgCifrada;
    public javax.swing.JTextArea tf_msgEnviar;
    public javax.swing.JTextArea tf_msgRecebida;
    // End of variables declaration//GEN-END:variables
    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;
    private JDialog aboutBox;
    boolean podeLer = false;
}