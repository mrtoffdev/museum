package Loot.res.component.view.atom;
/*  Author: TOFF
    DOCS ==================================

    [X] Components: -----------------------

    cv_CategName<JLabel>
    cv_CategItems<JLabel>

    [X] Props: ----------------------------

    CategName<String>
    CategItems<Int>

    [X] Func: <FnName:ReturnT> ------------

    Static toCategCtxList(ArrayList<BaseCateg>):ArrayList<CategViewCtx>

    DOCS ==================================
*/

import Loot.model.BaseCateg;
import Loot.model.CategViewCtx;

import java.awt.*;
import javax.swing.*;
import javax.swing.GroupLayout;

public class CategView extends JPanel {

    private String  CategName;
    private int     CategItems;

    public CategView() {
        initComponents();
    }
    public void     fill(Object in_data){
        // Insert Sanitization Code
        if (in_data instanceof CategViewCtx){
            this.CategName  = ((CategViewCtx) in_data).getCategName();
            this.CategItems = ((CategViewCtx) in_data).getCategItems();
            populate();
        }
        else {
            cv_CategName.setText("Blank Category");
            cv_CategItems.setText("0 Items");
        }
    }

//    public static ArrayList<CategViewCtx> toCategCtxList(){
//        ArrayList<CategViewCtx> out = new ArrayList<>();
//
//    }

    private void    populate(){
        cv_CategName.setText(this.CategName);
        cv_CategItems.setText(this.CategItems + " Items");
    }
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        panel1 = new JPanel();
        cv_CategName = new JLabel();
        cv_CategItems = new JLabel();

        //======== this ========
        setPreferredSize(new Dimension(211, 80));
        setBackground(new Color(0x161616));
        setForeground(new Color(0x1a1a1a));

        //======== panel1 ========
        {
            panel1.setBackground(new Color(0x1a1a1a));
            panel1.setForeground(new Color(0x1a1a1a));

            //---- cv_CategName ----
            cv_CategName.setText("Category Name");
            cv_CategName.setFont(new Font("Segoe UI", Font.BOLD, 13));
            cv_CategName.setForeground(Color.white);

            //---- cv_CategItems ----
            cv_CategItems.setText("ItemCount");
            cv_CategItems.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            cv_CategItems.setForeground(Color.white);

            GroupLayout panel1Layout = new GroupLayout(panel1);
            panel1.setLayout(panel1Layout);
            panel1Layout.setHorizontalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(panel1Layout.createParallelGroup()
                            .addComponent(cv_CategItems, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
                            .addComponent(cv_CategName, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(22, Short.MAX_VALUE))
            );
            panel1Layout.setVerticalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(cv_CategName)
                        .addGap(2, 2, 2)
                        .addComponent(cv_CategItems)
                        .addGap(20, 20, 20))
            );
        }

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addComponent(panel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addComponent(panel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(9, 9, 9))
        );
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel panel1;
    private JLabel cv_CategName;
    private JLabel cv_CategItems;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
