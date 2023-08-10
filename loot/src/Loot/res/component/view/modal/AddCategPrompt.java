package Loot.res.component.view.modal;

import java.awt.*;
import java.awt.event.*;
import java.util.Objects;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.border.*;

import Loot.model.BaseCateg;
import Loot.model.BaseItem;
import Loot.model.CategViewCtx;
import Loot.model.GlobalContext;
import Loot.res.component.list.*;
import Loot.res.component.view.ui.ScrollBar;
import Loot.util.controller.EventList;
import Loot.util.crud.Search;

public class AddCategPrompt extends JDialog {
    GlobalContext Database;
    EventList EventList;
    String SelectedCategID;
    String SelectedItemID;

    public AddCategPrompt(Window owner, GlobalContext Database, String ItemID, EventList EventList) {
        super(owner);
        initComponents();

        this.Database = Database;
        this.SelectedItemID = ItemID;
        this.EventList = EventList;

        DefaultListModel<CategViewCtx> List = new DefaultListModel<>();
        for (BaseCateg item : this.Database.getCategs()){
            int CategItems = Search.util_fetchByCategID(this.Database.getItems(), item.getCategID()).size();
            CategViewCtx Categ = new CategViewCtx(item, CategItems);
            List.addElement(Categ);
        }
        CategListView.setModel(List);

        ScrollBar CategScrollBar    = new ScrollBar();
        CategScrollView.setVerticalScrollBar(CategScrollBar);
        CategScrollView.setHorizontalScrollBar(null);
        CategScrollView.getVerticalScrollBar().setUnitIncrement(20);
        CategSearchField.setBackground(new Color(43, 43, 43));
        CategSearchBtn.setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass()
                .getResource("/Loot/res/img/search.png")))
                .getImage()
                .getScaledInstance(15,15,Image.SCALE_DEFAULT)));
    }

    public void setListener(){

    }

    private void CategListSelect(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)){
            JList<CategViewCtx> list = (JList<CategViewCtx>)e.getSource();
            int index                = list.locationToIndex(e.getPoint());
            CategViewCtx selected    = list.getModel().getElementAt(index);
            BaseCateg categRef       = selected.returnBaseCateg();

            SelectedCategID          = categRef.getCategID();
            BaseItem ToBeEdited = Search.util_fetchByID(this.Database.getItems(), this.SelectedItemID);
            int Index           = this.Database.getItems().indexOf(ToBeEdited);
            this.Database.getItems().get(Index).setCategID(SelectedCategID);
            EventList.GlobalReload();
            dispose();
        }
    }

    public String getSelectedCategID(){
        return this.SelectedCategID;
    }

    private void CategSearchCall(MouseEvent e) {
        // TODO add your code here
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        Panel_Categories = new JPanel();
        Header1 = new JLabel();
        Header2 = new JLabel();
        CategScrollView = new JScrollPane();
        CategListView = new lx_CategList<>();
        CategSearchField = new JTextField();
        CategSearchBtn = new JButton();

        //======== this ========
        setBackground(new Color(0x161616));
        var contentPane = getContentPane();
        contentPane.setLayout(new FlowLayout());

        //======== Panel_Categories ========
        {
            Panel_Categories.setBackground(new Color(0x161616));
            Panel_Categories.setPreferredSize(new Dimension(280, 546));

            //---- Header1 ----
            Header1.setFont(new Font("Segoe UI", Font.BOLD, 18));
            Header1.setForeground(Color.white);
            Header1.setText("Set Category:");

            //---- Header2 ----
            Header2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            Header2.setForeground(Color.white);
            Header2.setText("Categories");

            //======== CategScrollView ========
            {
                CategScrollView.setBackground(new Color(0x161616));
                CategScrollView.setBorder(null);
                CategScrollView.setForeground(new Color(0x161616));

                //---- CategListView ----
                CategListView.setBackground(new Color(0x161616));
                CategListView.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        CategListSelect(e);
                    }
                });
                CategScrollView.setViewportView(CategListView);
            }

            //---- CategSearchField ----
            CategSearchField.setForeground(Color.black);
            CategSearchField.setToolTipText("Search");
            CategSearchField.setBorder(new EmptyBorder(0, 10, 0, 0));
            CategSearchField.setHighlighter(null);

            //---- CategSearchBtn ----
            CategSearchBtn.setBackground(new Color(0x242424));
            CategSearchBtn.setForeground(Color.white);
            CategSearchBtn.setBorder(null);
            CategSearchBtn.setFocusPainted(false);
            CategSearchBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    CategSearchCall(e);
                }
            });

            GroupLayout Panel_CategoriesLayout = new GroupLayout(Panel_Categories);
            Panel_Categories.setLayout(Panel_CategoriesLayout);
            Panel_CategoriesLayout.setHorizontalGroup(
                Panel_CategoriesLayout.createParallelGroup()
                    .addGroup(GroupLayout.Alignment.TRAILING, Panel_CategoriesLayout.createSequentialGroup()
                        .addGroup(Panel_CategoriesLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addGroup(Panel_CategoriesLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(Header2)
                                .addGap(147, 147, 147))
                            .addGroup(Panel_CategoriesLayout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(Panel_CategoriesLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addComponent(CategScrollView, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                                    .addGroup(GroupLayout.Alignment.LEADING, Panel_CategoriesLayout.createSequentialGroup()
                                        .addComponent(Header1)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(Panel_CategoriesLayout.createSequentialGroup()
                                        .addComponent(CategSearchField, GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                                        .addGap(0, 0, 0)
                                        .addComponent(CategSearchBtn, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)))))
                        .addGap(30, 30, 30))
            );
            Panel_CategoriesLayout.setVerticalGroup(
                Panel_CategoriesLayout.createParallelGroup()
                    .addGroup(Panel_CategoriesLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(Header1)
                        .addGap(25, 25, 25)
                        .addComponent(Header2)
                        .addGap(18, 18, 18)
                        .addGroup(Panel_CategoriesLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addComponent(CategSearchField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                            .addComponent(CategSearchBtn, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(CategScrollView, GroupLayout.PREFERRED_SIZE, 348, GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31))
            );
        }
        contentPane.add(Panel_Categories);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel Panel_Categories;
    private JLabel Header1;
    private JLabel Header2;
    private JScrollPane CategScrollView;
    private lx_CategList<CategViewCtx> CategListView;
    private JTextField CategSearchField;
    private JButton CategSearchBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
