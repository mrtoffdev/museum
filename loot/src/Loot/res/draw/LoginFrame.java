/*
 * Created by JFormDesigner on Tue Dec 13 20:45:09 PST 2022
 */

package Loot.res.draw;

import java.awt.event.*;
import Loot.util.controller.EventList;
import java.awt.*;
import javax.swing.*;
import javax.swing.GroupLayout;
import java.awt.Graphics;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import javax.swing.border.*;

public class LoginFrame extends JPanel {

    static final String USER = "Admin";
    static final String PASS = "Admin";

    private EventList EventList;

    public LoginFrame() {
        initComponents();
        UserField.setBackground(new Color(14, 14, 14));
        PassField.setBackground(new Color(14, 14, 14));
    }

    public void setRootEventList(EventList EventList){
        this.EventList = EventList;
    }

    @Override
    protected void paintComponent(Graphics g) {
        ImageIcon Image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Loot/res/img/loginframe.png")));
        super.paintComponent(g);
        g.drawImage(Image.getImage(), 0, 0, null);
    }

    private void Login(ActionEvent e) throws IOException, URISyntaxException, FontFormatException, InterruptedException {
        String UserFieldIn = UserField.getText();
        String PassFieldIn = String.valueOf(PassField.getPassword());

        System.out.printf("UserField: %s - PassField: %s\n", UserFieldIn, PassFieldIn);

        if (UserFieldIn.equals(USER) && PassFieldIn.equals(PASS)){
            System.out.println("Login Confirmed");

            JFileChooser FileChooser = new JFileChooser();
            FileChooser.showOpenDialog(this);

            if (FileChooser.getSelectedFile() != null){
                try {
                    String FileURL  = FileChooser.getSelectedFile().getAbsolutePath();
                    EventList.StartLootRuntime(FileURL);
                    EventList.SetFileURL(FileURL);
                    this.setVisible(false);
                    SwingUtilities.getWindowAncestor(this).dispose();
                } catch (IOException | FontFormatException | URISyntaxException ignored) {
                }
            }
        }

    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        UserField = new JTextField();
        label1 = new JLabel();
        label2 = new JLabel();
        Login = new JButton();
        PassField = new JPasswordField();
        label3 = new JLabel();

        //======== this ========

        //---- UserField ----
        UserField.setForeground(Color.white);
        UserField.setBorder(new EmptyBorder(5, 10, 5, 10));
        UserField.setText("Username");

        //---- label1 ----
        label1.setText("Username:");
        label1.setForeground(Color.white);
        label1.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));

        //---- label2 ----
        label2.setText("Password:");
        label2.setForeground(Color.white);
        label2.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));

        //---- Login ----
        Login.setText("Login");
        Login.setBackground(new Color(0x242424));
        Login.setFocusPainted(false);
        Login.setForeground(Color.white);
        Login.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        Login.setBorder(new EmptyBorder(5, 10, 5, 10));
        Login.addActionListener(e -> {
            try {
                Login(e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (URISyntaxException ex) {
                throw new RuntimeException(ex);
            } catch (FontFormatException ex) {
                throw new RuntimeException(ex);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });

        //---- PassField ----
        PassField.setForeground(Color.white);
        PassField.setBorder(new EmptyBorder(5, 10, 5, 10));

        //---- label3 ----
        label3.setText("Sign In:");
        label3.setForeground(Color.white);
        label3.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addGap(57, 57, 57)
                    .addGroup(layout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(UserField, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                                .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(label2)
                                    .addGap(0, 267, Short.MAX_VALUE))
                                .addComponent(PassField, GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                                .addComponent(Login, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE))
                            .addGap(63, 63, 63))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup()
                                .addComponent(label3)
                                .addComponent(label1))
                            .addGap(0, 326, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(372, Short.MAX_VALUE)
                    .addComponent(label3)
                    .addGap(18, 18, 18)
                    .addComponent(label1)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(UserField, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(label2)
                    .addGap(12, 12, 12)
                    .addComponent(PassField, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(Login, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
                    .addGap(74, 74, 74))
        );
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JTextField UserField;
    private JLabel label1;
    private JLabel label2;
    private JButton Login;
    private JPasswordField PassField;
    private JLabel label3;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
