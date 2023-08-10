package Loot.lib;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.io.FileWriter;

import javax.swing.*;

public class login implements ActionListener {
    JFrame frame;
    JTextField name;
    JPasswordField pass;
    ImageIcon logoWhite, buttonO;
    JLabel labUsername, labPassword, logo, desc, err_con;
    JButton btnLoginTab, btnSignUpTab, btnLogin, btnSignUp, btnGuest, btnAdmin;
    JPanel pnLogin, pnRegister;

    void WindowLogin(){
        frame = new JFrame ("Login");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.setSize(960, 540);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null); 
        frame.getContentPane().setBackground(new Color(44, 47, 51));
        frame.setBackground(new Color(44, 47, 51));

        buttonO = new ImageIcon(new ImageIcon("O.png").getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
        btnAdmin = new JButton(buttonO);
        btnAdmin.setBounds(252, 255, 25, 25);
        btnAdmin.setBorder(BorderFactory.createEmptyBorder());
        btnAdmin.setContentAreaFilled(false);
        btnAdmin.addActionListener(this);

        logoWhite = new ImageIcon(new ImageIcon("LOOT_WHITE.png").getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT));
        logo = new JLabel(logoWhite);
        logo.setBounds(150, 100, 200, 200);
    
        desc = new JLabel("<html><div style='text-align: center;'>" + "We offer the best cutting edge services to keep track " + "<br>" + " of your valuable warehouse stocks amogus amogus sussus. Pioneered by the best software engineers across the globe ur mom" + "</html>");
        desc.setBounds(50, 300, 400, 100);
        desc.setForeground(new Color(246, 246, 246));

        pnLogin = new JPanel();
        pnLogin.setBounds(500, 100, 400, 350);
        pnLogin.setBackground(new Color(246, 246, 246));

        btnLoginTab = new JButton("LOG-IN");
        btnLoginTab.setBounds(500, 50, 100, 50);
        btnLoginTab.setForeground(new Color(88, 101, 242));
        btnLoginTab.setBackground(new Color(246, 246, 246));
        btnLoginTab.setBorder(null);
        btnLoginTab.setFocusable(false);
        btnLoginTab.addActionListener(this);

        btnSignUpTab = new JButton("SIGN-UP");
        btnSignUpTab.setBounds(603, 50, 100, 50);
        btnSignUpTab.setForeground(new Color(246, 246, 246));
        btnSignUpTab.setBackground(new Color(153, 170, 181));
        btnSignUpTab.setBorder(null);
        btnSignUpTab.setFocusable(false);
        btnSignUpTab.addActionListener(this);
        
        labUsername = new JLabel("USERNAME");
        labUsername.setBounds(550, 125, 200, 50);
        labUsername.setForeground(new Color(44, 47, 51));

        name = new JTextField();
        name.setBounds(550, 175, 300, 30);
        name.setBackground(new Color(255, 255, 255));
        name.setForeground(new Color(44, 47, 51));
        name.setBorder(null);

        labPassword = new JLabel("PASSWORD");
        labPassword.setBounds(550, 200, 200, 50);
        labPassword.setForeground(new Color(44, 47, 51));

        pass = new JPasswordField();
        pass.setBounds(550, 250, 300, 30);
        pass.setBackground(new Color(255, 255, 255));
        pass.setForeground(new Color(44, 47, 51));
        pass.setBorder(null);

        btnLogin = new JButton("LOG-IN");
        btnLogin.setBounds(550, 300, 100, 50);
        btnLogin.setForeground(new Color(255, 255, 255));
        btnLogin.setBackground(new Color(88, 101, 242));
        btnLogin.setBorder(null);
        btnLogin.setFocusable(true);
        btnLogin.addActionListener(this);

        btnSignUp = new JButton("SIGN-UP");
        btnSignUp.setBounds(550, 300, 100, 50);
        btnSignUp.setForeground(new Color(255, 255, 255));
        btnSignUp.setBackground(new Color(88, 101, 242));
        btnSignUp.setBorder(null);
        btnSignUp.setFocusable(true);
        btnSignUp.setVisible(false);
        btnSignUp.addActionListener(this);

        err_con = new JLabel("");
        err_con.setBounds(550, 360, 175, 20);

        btnGuest = new JButton("Continue as Guest");
        btnGuest.setBounds(550, 390, 105, 20);
        btnGuest.setForeground(new Color(153, 170, 181));
        btnGuest.setBackground(new Color(246, 246, 246));
        btnGuest.setBorder(null); btnGuest.setFocusable(false);

        frame.add(btnAdmin); frame.add(logo); frame.add(desc);
        frame.add(btnLoginTab); frame.add(btnSignUpTab);
        frame.add(labUsername); frame.add(name);
        frame.add(labPassword); frame.add(pass);
        frame.add(btnLogin); frame.add(btnSignUp); 
        frame.add(err_con); frame.add(btnGuest);
        frame.add(pnLogin);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == btnAdmin) {
            err_con.setForeground(new Color(254, 231, 92));
            err_con.setText("OH SHI- YOU'RE AN ADMIN!");
            //add code to redirect to admin dashboard
        }

        if(e.getSource() == btnLoginTab) {
            btnLoginTab.setForeground(new Color(88, 101, 242));
            btnLoginTab.setBackground(new Color(246, 246, 246));
            btnSignUpTab.setForeground(new Color(246, 246, 246));
            btnSignUpTab.setBackground(new Color(153, 170, 181));
            btnSignUp.setVisible(false);
            btnLogin.setVisible(true);
            err_con.setText("");
        }

        if(e.getSource() == btnSignUpTab) {
            btnLoginTab.setForeground(new Color(246, 246, 246));
            btnLoginTab.setBackground(new Color(153, 170, 181));
            btnSignUpTab.setForeground(new Color(88, 101, 242));
            btnSignUpTab.setBackground(new Color(246, 246, 246));
            btnSignUp.setVisible(true);
            btnLogin.setVisible(false);
            err_con.setText("");
        }

        if(e.getSource() == btnSignUp) {
            try{
                FileWriter fw = new FileWriter("users.txt", true);
                fw.write(name.getText() + "\t" + String.valueOf(pass.getPassword()) + "\n");
                fw.close();
            } catch (Exception a) {}

            name.setText(""); pass.setText("");
            err_con.setForeground(new Color(87, 242, 135));
            err_con.setText("Successfully Signed-Up!");
        }

        if(e.getSource() == btnLogin) {
            boolean matched = false;
            String uname = name.getText().toString();
            String pword = String.valueOf(pass.getPassword());
            
            if (uname.contentEquals("adminrr") && pword.contentEquals("bingchilling")) {
                err_con.setForeground(new Color(87, 242, 135));
                err_con.setText("The DEV is back!");
                //add code to redirect to the dev dashboard
            } else {
                try{
                    FileReader fr = new FileReader("users.txt");
                    BufferedReader br = new BufferedReader(fr);
                    String str;
                    while ((str = br.readLine())!=null){
                        if (str.equals(uname + "\t" + pword)) {
                            matched = true;
                            break;
                        }
                    } 
                    fr.close();
                } catch (Exception a) {}

                if (matched) {
                    name.setText(""); pass.setText("");
                    err_con.setForeground(new Color(87, 242, 135));
                    err_con.setText("Successfully Logged-In!");
                    //add code to redirect to the user dashboard
                } else {
                    err_con.setForeground(new Color(235, 69, 158));
                    err_con.setText("Invalid Username / Password");
                }
            }
        }

        if(e.getSource() == btnGuest) {
            //add code to redirect to guest program
        }
        
    }

    public static void main (String[] arg) {
        login log = new login();
        log.WindowLogin();
    }
}
