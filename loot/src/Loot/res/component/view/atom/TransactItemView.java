/*
 * Created by JFormDesigner on Fri Dec 09 08:14:32 PST 2022
 */

package Loot.res.component.view.atom;

import Loot.model.BaseItem;
import Loot.model.BaseTransaction;

import java.awt.*;
import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author unknown
 */
public class TransactItemView extends JPanel {
    public TransactItemView() {
        initComponents();
    }

    public void     fill(BaseTransaction in_data){
        // Insert Sanitization Code
        if (in_data != null){
            populate(in_data);
        }
    }
    private void    populate(BaseTransaction in_data){
        int ItemCount   = in_data.getItemCount();

        ItemName.setText(in_data.getBaseItem().getName());
        String Format   = String.format("%.2f",in_data.getTotalCost());
        ItemPrice.setText(Format);
        ItemStock.setForeground(new Color(235, 159, 66));
        ItemStock.setText(ItemCount + " Pcs");
    }
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        panel1 = new JPanel();
        jLabel4 = new JLabel();
        ItemPrice = new JLabel();
        ItemName = new JLabel();
        ItemStock = new JLabel();

        //======== this ========
        setPreferredSize(null);
        setBackground(new Color(0x161616));
        setForeground(new Color(0x161616));

        //======== panel1 ========
        {
            panel1.setBackground(new Color(0x1a1a1a));
            panel1.setForeground(new Color(0x1a1a1a));

            //---- jLabel4 ----
            jLabel4.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            jLabel4.setForeground(Color.white);
            jLabel4.setHorizontalAlignment(SwingConstants.RIGHT);
            jLabel4.setText("\u20b1");

            //---- ItemPrice ----
            ItemPrice.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            ItemPrice.setForeground(Color.white);
            ItemPrice.setText("00.00");

            //---- ItemName ----
            ItemName.setFont(new Font("Segoe UI", Font.BOLD, 13));
            ItemName.setForeground(Color.white);
            ItemName.setText("Item Name");

            //---- ItemStock ----
            ItemStock.setForeground(new Color(0xeb9f42));
            ItemStock.setText("Stock");
            ItemStock.setFont(new Font("Segoe UI", Font.PLAIN, 14));

            GroupLayout panel1Layout = new GroupLayout(panel1);
            panel1.setLayout(panel1Layout);
            panel1Layout.setHorizontalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(panel1Layout.createParallelGroup()
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addComponent(ItemName, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(247, Short.MAX_VALUE))
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(2, 2, 2)
                                .addComponent(ItemPrice, GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(ItemStock, GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                                .addGap(235, 235, 235))))
            );
            panel1Layout.setVerticalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(ItemName, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addGroup(panel1Layout.createParallelGroup()
                            .addComponent(jLabel4)
                            .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(ItemPrice)
                                .addComponent(ItemStock)))
                        .addGap(16, 16, 16))
            );
        }

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addComponent(panel1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addComponent(panel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap())
        );
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel panel1;
    private JLabel jLabel4;
    private JLabel ItemPrice;
    private JLabel ItemName;
    private JLabel ItemStock;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
