/*
 * Created by JFormDesigner on Fri Dec 09 21:36:00 PST 2022
 */

package Loot.res.component.view.modal;

import Loot.model.BaseItem;
import Loot.model.BaseTransaction;
import Loot.util.controller.EventList;

import java.awt.*;
import java.awt.event.*;
import java.util.Objects;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.border.*;

public class AddItemModal extends JDialog {

    private static final int ADD_MODE   = 0;
    private static final int EDIT_MODE  = 1;

    private double  ItemPrice       = 0;
    private double  Total           = 0;
    private int     ItemCount       = 1;
    private int     OrigStock       = 0;
    private int     NewStock        = 0;

    private BaseTransaction AddableItem;
    private BaseTransaction EditableItem;
    private EventList       EventList;
    private int             OperationMode;
    private int             startListener;

    public AddItemModal(Window owner, EventList EventList, BaseItem Item, BaseTransaction Transaction) {

        // Component Init : Mandatory
        super(owner);
        initComponents();
        this.EventList  = EventList;

        /*
         *   Mode List:
         *   0 = AddItem Mode
         *   1 = EditTransaction Mode
        */

        IncItem.setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass()
                .getResource("/Loot/res/img/addicon.png")))
                .getImage()
                .getScaledInstance(15,15,Image.SCALE_DEFAULT)));

        DecItem.setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass()
                .getResource("/Loot/res/img/subtracticon.png")))
                .getImage()
                .getScaledInstance(15,15,Image.SCALE_DEFAULT)));

        GrabAll.setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass()
                .getResource("/Loot/res/img/doubletick.png")))
                .getImage()
                .getScaledInstance(15,15,Image.SCALE_DEFAULT)));

        if (Item != null){
            System.out.print("[Modal] Debug : Found BaseItem\n");
            OperationMode   = 0;
            // AddItem Composer
            this.ItemPrice  = Item.getPrice();
            this.OrigStock  = Item.getStock();
            this.Total      = Item.getPrice();

            this.AddableItem = BaseItem.toTransaction(Item);

            System.out.printf("Modal's BaseItem: %s", AddableItem.getItemID());

            ItemName.setText(Item.getName());
            this.NewStock   = this.OrigStock - 1;
        }
        else if (Transaction != null) {
            System.out.print("[Modal] Debug : Found Transaction\n");
            OperationMode   = 1;
            this.EditableItem = new BaseTransaction(Transaction.getBaseItem());
            // EditItem Composer
            this.ItemPrice  = Transaction.getBaseItem().getPrice();
            this.OrigStock  = Transaction.getBaseItem().getStock() + Transaction.getItemCount();
            this.NewStock   = (OrigStock - Transaction.getItemCount()) - 1;
            this.Total      = Transaction.getTotalCost();
            this.ItemCount  = Transaction.getItemCount();
            UpdateFrame();
        }
        else {
            System.out.println("Err : AddItemModal() - Invalid Mode Code");
        }


        UpdateFrame();
        startListener   = 1;

        // Bind Field to Int Characters Only
        CustomCount.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyChar() >= '0' && e.getKeyChar() <= '9' || e.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
                    CustomCount.setEditable(true);
                } else {
                    CustomCount.setEditable(false);
                }
            }
        });
    }

    private void AddItem(MouseEvent e) {
        if (OperationMode == ADD_MODE){
            EventList.AddItemModal(AddableItem, ItemCount);
        } else
        if (OperationMode == EDIT_MODE){
            EditableItem.setItemCount(this.ItemCount);
            EditableItem.setTotalCost(this.Total);
            EventList.EditTransactionModal(this.EditableItem, this.EditableItem.getItemID());
        }
        dispose();
    }
    private void onClose(MouseEvent e) {
        this.setVisible(false);
        dispose();
    }


    private void IncItem(MouseEvent e) {
        if (NewStock >= 1){
            NewStock--;
            ItemCount++;
            Total += ItemPrice;
            UpdateFrame();
        }
    }
    private void DecItem(MouseEvent e) {
        if (ItemCount >= 1){
            NewStock++;
            ItemCount--;
            Total -= ItemPrice;
            UpdateFrame();
        }
    }
    private void GrabAll(MouseEvent e) {
        ItemCount = Integer.parseInt(CustomCount.getText()) + NewStock;
        NewStock = 0;
        Total += ItemPrice * (ItemCount + 1);
        UpdateFrame();
    }

    private void UpdateFrame(){
        NewStock        = OrigStock - ItemCount;
        this.Total      = ItemPrice*ItemCount;
        String dialogue = String.format("Available: %d", this.NewStock);

        IncItem.setEnabled(NewStock >= 1);
        DecItem.setEnabled(ItemCount >= 1);

        Stock.setText(dialogue);
        TotalCost.setText(Double.toString(Total));
        CustomCount.setText(Integer.toString(this.ItemCount));

        // Button Configuration
        if (OperationMode == ADD_MODE){
            if (ItemCount > 0){
                AddItem.setEnabled(true);
                AddItem.setText("ADD ITEM");
            } else {
                AddItem.setEnabled(false);
            }
        } else if (OperationMode == EDIT_MODE){
            if (ItemCount > 0){
                AddItem.setEnabled(true);
                AddItem.setText("SAVE TRANSACTION");
            } else {
                AddItem.setText("DELETE TRANSACTION");
            }
        }
    }

    private void CustomCountKeyReleased(KeyEvent e) {
        System.out.println("Update Called");
        int temp = Integer.parseInt(CustomCount.getText());
        if (startListener != 0){
            if (temp < 1){
                if (OperationMode == 1){
                    this.ItemCount = 0;
                    AddItem.setText("Remove Item");
                } else {
                    AddItem.setEnabled(false);
                }
            } else if (temp > OrigStock){
                CustomCount.setText(Integer.toString(OrigStock));
            } else {
                this.ItemCount = temp;
                AddItem.setEnabled(true);
            }
        }
        UpdateFrame();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        panel4 = new JPanel();
        panel1 = new JPanel();
        label1 = new JLabel();
        ItemName = new JLabel();
        Stock = new JLabel();
        panel3 = new JPanel();
        CustomCount = new JTextField();
        IncItem = new JButton();
        DecItem = new JButton();
        GrabAll = new JButton();
        TotalCost = new JLabel();
        jLabel4 = new JLabel();
        Cancel = new JButton();
        AddItem = new JButton();

        //======== this ========
        setModal(true);
        setBackground(new Color(0x0e0e0e));
        setUndecorated(true);
        var contentPane = getContentPane();

        //======== panel4 ========
        {
            panel4.setBackground(new Color(0x181818));

            //======== panel1 ========
            {
                panel1.setBackground(new Color(0x161616));
                panel1.setBorder(BorderFactory.createEmptyBorder());

                //---- ItemName ----
                ItemName.setFont(new Font("Segoe UI", Font.BOLD, 13));
                ItemName.setForeground(Color.white);
                ItemName.setText("Item Name");

                //---- Stock ----
                Stock.setForeground(Color.white);
                Stock.setText("0");

                //======== panel3 ========
                {
                    panel3.setBackground(new Color(0x161616));

                    //---- CustomCount ----
                    CustomCount.setBorder(new EmptyBorder(0, 4, 0, 0));
                    CustomCount.setBackground(new Color(0x272727));
                    CustomCount.setText("1");
                    CustomCount.setForeground(Color.white);
                    CustomCount.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyReleased(KeyEvent e) {
                            CustomCountKeyReleased(e);
                        }
                    });

                    //---- IncItem ----
                    IncItem.setBackground(new Color(0x272727));
                    IncItem.setFocusPainted(false);
                    IncItem.setForeground(Color.white);
                    IncItem.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            IncItem(e);
                        }
                    });

                    //---- DecItem ----
                    DecItem.setBackground(new Color(0x272727));
                    DecItem.setFocusPainted(false);
                    DecItem.setForeground(Color.white);
                    DecItem.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            DecItem(e);
                        }
                    });

                    //---- GrabAll ----
                    GrabAll.setBackground(new Color(0x272727));
                    GrabAll.setFocusPainted(false);
                    GrabAll.setForeground(Color.white);
                    GrabAll.setFont(new Font("Segoe UI", Font.BOLD, 10));
                    GrabAll.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            GrabAll(e);
                        }
                    });

                    GroupLayout panel3Layout = new GroupLayout(panel3);
                    panel3.setLayout(panel3Layout);
                    panel3Layout.setHorizontalGroup(
                        panel3Layout.createParallelGroup()
                            .addGroup(GroupLayout.Alignment.TRAILING, panel3Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(CustomCount, GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                                .addGap(8, 8, 8)
                                .addComponent(IncItem, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(DecItem, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(GrabAll, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20))
                    );
                    panel3Layout.setVerticalGroup(
                        panel3Layout.createParallelGroup()
                            .addGroup(panel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(CustomCount, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(IncItem, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(DecItem, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(GrabAll, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
                    );
                }

                //---- TotalCost ----
                TotalCost.setForeground(Color.white);
                TotalCost.setText("0.00");

                //---- jLabel4 ----
                jLabel4.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                jLabel4.setForeground(Color.white);
                jLabel4.setHorizontalAlignment(SwingConstants.RIGHT);
                jLabel4.setText("\u20b1");

                //---- Cancel ----
                Cancel.setBackground(new Color(0x161616));
                Cancel.setFocusPainted(false);
                Cancel.setForeground(Color.white);
                Cancel.setBorder(BorderFactory.createEmptyBorder());
                Cancel.setText("X");
                Cancel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                Cancel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        onClose(e);
                    }
                });

                //---- AddItem ----
                AddItem.setText("ADD ITEM");
                AddItem.setBackground(new Color(0x272727));
                AddItem.setFocusPainted(false);
                AddItem.setForeground(Color.white);
                AddItem.setBorder(BorderFactory.createEmptyBorder());
                AddItem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                AddItem.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        AddItem(e);
                    }
                });

                GroupLayout panel1Layout = new GroupLayout(panel1);
                panel1.setLayout(panel1Layout);
                panel1Layout.setHorizontalGroup(
                    panel1Layout.createParallelGroup()
                        .addComponent(panel3, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(AddItem, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panel1Layout.createSequentialGroup()
                            .addGap(26, 26, 26)
                            .addGroup(panel1Layout.createParallelGroup()
                                .addComponent(ItemName, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
                                .addGroup(panel1Layout.createSequentialGroup()
                                    .addComponent(jLabel4)
                                    .addGap(3, 3, 3)
                                    .addComponent(TotalCost, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(Stock, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)))
                            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(panel1Layout.createSequentialGroup()
                            .addComponent(Cancel, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 205, Short.MAX_VALUE))
                        .addComponent(label1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                );
                panel1Layout.setVerticalGroup(
                    panel1Layout.createParallelGroup()
                        .addGroup(panel1Layout.createSequentialGroup()
                            .addComponent(Cancel, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                            .addComponent(label1, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(ItemName, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(TotalCost)
                                .addComponent(Stock))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(panel3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(AddItem, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
                );
            }

            GroupLayout panel4Layout = new GroupLayout(panel4);
            panel4.setLayout(panel4Layout);
            panel4Layout.setHorizontalGroup(
                panel4Layout.createParallelGroup()
                    .addGroup(panel4Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(panel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(20, Short.MAX_VALUE))
            );
            panel4Layout.setVerticalGroup(
                panel4Layout.createParallelGroup()
                    .addGroup(panel4Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(panel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
            );
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(panel4, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(panel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel panel4;
    private JPanel panel1;
    private JLabel label1;
    private JLabel ItemName;
    private JLabel Stock;
    private JPanel panel3;
    private JTextField CustomCount;
    private JButton IncItem;
    private JButton DecItem;
    private JButton GrabAll;
    private JLabel TotalCost;
    private JLabel jLabel4;
    private JButton Cancel;
    private JButton AddItem;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
