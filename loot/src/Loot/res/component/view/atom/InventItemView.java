/*
 * Created by JFormDesigner on Sat Dec 10 22:43:23 PST 2022
 */

package Loot.res.component.view.atom;

import Loot.model.ItemViewCtx;

import java.awt.*;
import javax.swing.*;
import javax.swing.GroupLayout;

public class InventItemView extends JPanel {
    public InventItemView() {
        initComponents();
    }

    public void fill(ItemViewCtx item){
        // Insert Sanitization Code
        if (item != null){
            populate(item);
        }
    }

    private void populate(ItemViewCtx item){
        this.Name.setText(item.getItemName());
        if (item.getCategoryName() != null){
            this.Category.setText(item.getCategoryName());
        } else {
            this.Category.setText("No Category");
        }
        this.Price.setText(Double.toString(item.getItemPrice()));

        int stock   = item.getItemStock();
        if (stock <= 10){
            Stock.setForeground(new Color(235, 66, 66));
        } else if (stock >= 30){
            Stock.setForeground(new Color(162, 235, 66));
        } else {
            Stock.setForeground(new Color(235, 159, 66));
        }
        Stock.setText(Integer.toString(stock));

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        panel1 = new JPanel();
        Name = new JLabel();
        Stock = new JLabel();
        Category = new JLabel();
        Price = new JLabel();
        jLabel4 = new JLabel();
        ImageSplash = new JLabel();

        //======== this ========
        setMinimumSize(new Dimension(214, 45));
        setPreferredSize(new Dimension(400, 45));
        setBackground(new Color(0x161616));
        setMaximumSize(new Dimension(770, 45));
        setBorder(null);

        //======== panel1 ========
        {
            panel1.setBackground(new Color(0x1a1a1a));
            panel1.setBorder(null);

            //---- Name ----
            Name.setFont(new Font("Segoe UI", Font.BOLD, 12));
            Name.setForeground(Color.white);
            Name.setText("Item Name");

            //---- Stock ----
            Stock.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            Stock.setForeground(Color.white);
            Stock.setText("12");

            //---- Category ----
            Category.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            Category.setForeground(Color.white);
            Category.setText("Item");

            //---- Price ----
            Price.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            Price.setForeground(Color.white);
            Price.setText("00.00");

            //---- jLabel4 ----
            jLabel4.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            jLabel4.setForeground(Color.white);
            jLabel4.setHorizontalAlignment(SwingConstants.RIGHT);
            jLabel4.setText("\u20b1");

            //---- ImageSplash ----
            ImageSplash.setBackground(new Color(0x323131));

            GroupLayout panel1Layout = new GroupLayout(panel1);
            panel1.setLayout(panel1Layout);
            panel1Layout.setHorizontalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(Name, GroupLayout.PREFERRED_SIZE, 233, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Stock, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Category, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
                        .addGroup(panel1Layout.createParallelGroup()
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 145, Short.MAX_VALUE)
                                .addComponent(ImageSplash, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(jLabel4)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Price, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(174, Short.MAX_VALUE))))
            );
            panel1Layout.setVerticalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGroup(panel1Layout.createParallelGroup()
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(Stock)
                                    .addComponent(Category)
                                    .addComponent(Name)
                                    .addComponent(jLabel4)
                                    .addComponent(Price, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)))
                            .addComponent(ImageSplash))
                        .addGap(7, 7, 7))
            );
        }

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addComponent(panel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(0, 0, Short.MAX_VALUE))
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
    private JLabel Name;
    private JLabel Stock;
    private JLabel Category;
    private JLabel Price;
    private JLabel jLabel4;
    private JLabel ImageSplash;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
