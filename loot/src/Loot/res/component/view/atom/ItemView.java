package Loot.res.component.view.atom;
/*  Author: TOFF
    DOCS ==================================

    [X] Components: -----------------------

    iv_ItemName<JLabel>
    iv_ItemPrice<JLabel>
    iv_ItemStock<JLabel>
    iv_ItemSplash<JLabel->Icon/Img>

    [X] Func: <FnName:ReturnT> ------------

    fill(Object):Void   // Takes generic Object; BaseItem instance recommended

    DOCS ==================================
*/

import java.awt.*;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle;

import Loot.model.BaseItem;
import Loot.model.ItemViewCtx;

import java.awt.Color;
import java.awt.Image;
import java.util.Objects;
import javax.swing.ImageIcon;

public class ItemView extends javax.swing.JPanel {

    private boolean selected    = false;

    public ItemView() {
        initComponents();
        if (selected){
            iv_SelectedIndicator.setBackground(new Color(255,255,255));
        } else {
            iv_SelectedIndicator.setBackground(new Color(26,26,26));
        }
    }
    public void     fill(Object in_data){
        // Insert Sanitization Code
        if (in_data instanceof ItemViewCtx){
            populate((ItemViewCtx) in_data);
        } 
        else {
            ItemViewCtx blank   = new ItemViewCtx(
                                    new BaseItem("Generic Item",
                                            200.00,
                                            1,
                                            "GenericItem00",
                                            "GenericCateg00",
                                            "")
                                );

            iv_ItemName.setText(blank + "");
        }
    }

    public void     setSelected(){
        selected = true;
    }

    private void    populate(ItemViewCtx in_data){
        iv_ItemName.setText(in_data.getItemName());
        iv_ItemPrice.setText(Double.toString(in_data.getItemPrice()));
        iv_ItemStock.setText(Integer.toString(in_data.getItemStock()));

        ImageIcon imageIcon = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass()
                .getResource("/Loot/res/img/sample.png")))
                .getImage()
                .getScaledInstance(122, 75, Image.SCALE_DEFAULT));
        iv_ItemSplash.setIcon(imageIcon);

        if (in_data.getItemStock() <= 10){
            iv_ItemStock.setForeground(new Color(235, 66, 66));
        } else if (in_data.getItemStock() >= 30){
            iv_ItemStock.setForeground(new Color(162, 235, 66));
        } else {
            iv_ItemStock.setForeground(new Color(235, 159, 66));
        }
        iv_ItemStock.setText(in_data.getItemStock() + " In Stock");
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        jPanel1 = new JPanel();
        iv_ItemPrice = new JLabel();
        iv_ItemStock = new JLabel();
        iv_ItemName = new JLabel();
        jLabel4 = new JLabel();
        iv_ItemSplash = new JLabel();
        iv_SelectedIndicator = new JLabel();

        //======== this ========
        setBackground(new Color(0x161616));
        setMaximumSize(new Dimension(515, 81));

        //======== jPanel1 ========
        {
            jPanel1.setBackground(new Color(0x1a1a1a));

            //---- iv_ItemPrice ----
            iv_ItemPrice.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            iv_ItemPrice.setForeground(Color.white);
            iv_ItemPrice.setText("00.00");

            //---- iv_ItemStock ----
            iv_ItemStock.setForeground(Color.white);
            iv_ItemStock.setText("Stock");

            //---- iv_ItemName ----
            iv_ItemName.setFont(new Font("Segoe UI", Font.BOLD, 13));
            iv_ItemName.setForeground(Color.white);
            iv_ItemName.setText("Item Name");

            //---- jLabel4 ----
            jLabel4.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            jLabel4.setForeground(Color.white);
            jLabel4.setHorizontalAlignment(SwingConstants.RIGHT);
            jLabel4.setText("\u20b1");

            //---- iv_ItemSplash ----
            iv_ItemSplash.setBackground(Color.white);

            GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
            jPanel1.setLayout(jPanel1Layout);
            jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup()
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(iv_SelectedIndicator, GroupLayout.PREFERRED_SIZE, 5, GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup()
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(iv_ItemName, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(jLabel4)
                                .addGap(2, 2, 2)
                                .addComponent(iv_ItemPrice, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE))
                            .addComponent(iv_ItemStock, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 72, Short.MAX_VALUE)
                        .addComponent(iv_ItemSplash, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE))
            );
            jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup()
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup()
                            .addComponent(iv_ItemSplash, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
                            .addComponent(iv_SelectedIndicator, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(jPanel1Layout.createParallelGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel4)
                                        .addComponent(iv_ItemPrice, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                                    .addComponent(iv_ItemName, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                                .addComponent(iv_ItemStock)))
                        .addGap(0, 0, 0))
            );
        }

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JPanel jPanel1;
    private JLabel iv_ItemPrice;
    private JLabel iv_ItemStock;
    private JLabel iv_ItemName;
    private JLabel jLabel4;
    private JLabel iv_ItemSplash;
    private JLabel iv_SelectedIndicator;
    // End of variables declaration//GEN-END:variables
}
