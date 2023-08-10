/*
 * Created by JFormDesigner on Tue Dec 13 07:39:16 PST 2022
 */

package Loot.res.component.view.modal;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.border.*;

/**
 * @author unknown
 */
public class AddCategoryModal extends JDialog {
    public AddCategoryModal(Window owner) {
        super(owner);
        initComponents();
        CategName.setBackground(new Color(43, 43, 43));
    }

    private void ConfirmAdd(ActionEvent e) {
    }
    public void disposeModal(){
        dispose();
    }

    public String getCategName(){
        return CategName.getText();
    }

    public void setActionListener(ActionListener Listener){
        okButton.addActionListener(Listener);
    }

    private void cancel(ActionEvent e) {
        dispose();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        label1 = new JLabel();
        CategName = new JTextField();
        buttonBar = new JPanel();
        okButton = new JButton();
        cancelButton = new JButton();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setBackground(new Color(0x161616));
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setBackground(new Color(0x161616));

                //---- label1 ----
                label1.setText("Add Category;");
                label1.setForeground(Color.white);
                label1.setFont(new Font("Segoe UI", Font.PLAIN, 14));

                //---- CategName ----
                CategName.setForeground(Color.white);
                CategName.setBorder(new EmptyBorder(5, 5, 5, 5));

                GroupLayout contentPanelLayout = new GroupLayout(contentPanel);
                contentPanel.setLayout(contentPanelLayout);
                contentPanelLayout.setHorizontalGroup(
                    contentPanelLayout.createParallelGroup()
                        .addGroup(contentPanelLayout.createSequentialGroup()
                            .addComponent(label1)
                            .addGap(0, 116, Short.MAX_VALUE))
                        .addComponent(CategName, GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                );
                contentPanelLayout.setVerticalGroup(
                    contentPanelLayout.createParallelGroup()
                        .addGroup(contentPanelLayout.createSequentialGroup()
                            .addComponent(label1)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(CategName)
                            .addGap(0, 0, 0))
                );
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setBackground(new Color(0x0e0e0e));

                //---- okButton ----
                okButton.setText("OK");
                okButton.setBackground(new Color(0x272727));
                okButton.setFocusPainted(false);
                okButton.setBorderPainted(false);
                okButton.setBorder(new EmptyBorder(5, 5, 5, 5));
                okButton.setForeground(Color.white);
                okButton.addActionListener(e -> ConfirmAdd(e));

                //---- cancelButton ----
                cancelButton.setText("Cancel");
                cancelButton.setBackground(new Color(0x272727));
                cancelButton.setFocusPainted(false);
                cancelButton.setBorderPainted(false);
                cancelButton.setBorder(new EmptyBorder(5, 5, 5, 5));
                cancelButton.setForeground(Color.white);
                cancelButton.addActionListener(e -> cancel(e));

                GroupLayout buttonBarLayout = new GroupLayout(buttonBar);
                buttonBar.setLayout(buttonBarLayout);
                buttonBarLayout.setHorizontalGroup(
                    buttonBarLayout.createParallelGroup()
                        .addGroup(buttonBarLayout.createSequentialGroup()
                            .addGap(0, 0, 0)
                            .addComponent(okButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGap(5, 5, 5)
                            .addComponent(cancelButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
                buttonBarLayout.setVerticalGroup(
                    buttonBarLayout.createParallelGroup()
                        .addGroup(buttonBarLayout.createSequentialGroup()
                            .addGap(0, 0, 0)
                            .addGroup(buttonBarLayout.createParallelGroup()
                                .addComponent(okButton)
                                .addComponent(cancelButton)))
                );
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JLabel label1;
    private JTextField CategName;
    private JPanel buttonBar;
    private JButton okButton;
    private JButton cancelButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
