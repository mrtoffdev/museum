package Loot.res.draw;
/*  Author: TOFF
    DOCS ==================================

    [X] Props: ----------------------------

    ItemName<String>
    ItemIcon<Icon>
    ItemID<String>
    ItemStock<Int>
    ItemPrice<Int>
    ItemCategID<Int>

    [X] Func: <FnName:ReturnT> ------------

    getName():String
    setName(String):Void

    getID():String
    setID(String):Void

    getIcon():Icon
    setIcon(javax.swing.Icon):Void

    DOCS ==================================
*/

import java.awt.event.*;

import Loot.model.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;
import javax.swing.*;

import Loot.res.component.view.panel.*;
import Loot.res.component.view.panel.DashboardView;
import Loot.util.controller.EventList;
import Loot.util.crud.Parser;

public class Dashboard extends javax.swing.JFrame {

    private EventList RootEventList;
    private GlobalContext Database;
    int VisiblePanel = 0;

    public Dashboard(GlobalContext Database) throws FontFormatException, IOException, URISyntaxException {

        initComponents();
        designComponents();
        this.Database   = Database;

        DashboardPanel.populate(this.Database);
        InventoryPanel.populate(this.Database);

    }

    @SuppressWarnings("unchecked")

    // Utils ====================================================

    public void designComponents(){
        // Top Bar
        bt_Inventory.setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass()
                .getResource("/Loot/res/img/stock.png")))
                .getImage()
                .getScaledInstance(17,17,Image.SCALE_DEFAULT)));

        bt_SignOut.setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass()
                .getResource("/Loot/res/img/signout.png")))
                .getImage()
                .getScaledInstance(17,17,Image.SCALE_DEFAULT)));
    }
    public void setRootEventList(EventList in_eventlist){
        this.RootEventList  = in_eventlist;
        DashboardPanel.setEventList(this.RootEventList);
        InventoryPanel.setEventList(this.RootEventList);
    }

    public GlobalContext getDatabase(){
        return this.Database;
    }

    public void updatePanels(){
        this.DashboardPanel.reloadAll();
        this.DashboardPanel.reloadListing();
        this.InventoryPanel.populate(this.Database);
        this.InventoryPanel.updateEditor();
        this.InventoryPanel.reloadListing();
    }

    // Listeners ================================================



    // Init =====================================================

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {

        });
    }

    private void thisWindowClosed(WindowEvent e) {
        System.exit(0);
    }

    private void SwitchToInv(MouseEvent e) {

        if (VisiblePanel == 0){
            VisiblePanel = 1;
            DashboardPanel.setVisible(false);
            InventoryPanel.setVisible(true);
        } else {
            VisiblePanel = 0;
            InventoryPanel.setVisible(false);
            DashboardPanel.setVisible(true);
        }
    }

    private void SignOut(MouseEvent e) throws IOException {
        Parser.saveFile(this.Database.getItems(), this.Database.getCategs(), this.RootEventList.GetFileURL());
    }

    //#region Variables declaration - do not modify//GEN-BEGIN:variables
    private JPanel Dashboard_Panel;
    private JPanel Panel_Toolbar;
    private JLabel Login_Name;
    private JPanel Panel_Points;
    private JLabel Login_Name1;
    private JPanel ViewFinder;
    private DashboardView DashboardPanel;
    private InventoryView InventoryPanel;
    private JButton bt_SignOut;
    private JButton bt_Inventory;
    //#endregion End of variables declaration//GEN-END:variables

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        Dashboard_Panel = new JPanel();
        Panel_Toolbar = new JPanel();
        Login_Name = new JLabel();
        Panel_Points = new JPanel();
        Login_Name1 = new JLabel();
        ViewFinder = new JPanel();
        DashboardPanel = new DashboardView();
        InventoryPanel = new InventoryView();
        bt_SignOut = new JButton();
        bt_Inventory = new JButton();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(new Rectangle(0, 0, 0, 0));
        setMaximizedBounds(new Rectangle(0, 0, 0, 0));
        setMinimumSize(new Dimension(1295, 750));
        setName("Loot");
        setSize(new Dimension(1280, 720));
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                thisWindowClosed(e);
            }
        });
        var contentPane = getContentPane();

        //======== Dashboard_Panel ========
        {
            Dashboard_Panel.setBackground(new Color(0x0e0e0e));
            Dashboard_Panel.setMinimumSize(new Dimension(1280, 720));
            Dashboard_Panel.setPreferredSize(new Dimension(1280, 720));
            Dashboard_Panel.setMaximumSize(new Dimension(1280, 720));

            //======== Panel_Toolbar ========
            {
                Panel_Toolbar.setBackground(new Color(0x161616));
                Panel_Toolbar.setForeground(Color.white);

                //---- Login_Name ----
                Login_Name.setFont(new Font("Segoe UI", Font.PLAIN, 15));
                Login_Name.setForeground(Color.white);
                Login_Name.setText("Admin");

                GroupLayout Panel_ToolbarLayout = new GroupLayout(Panel_Toolbar);
                Panel_Toolbar.setLayout(Panel_ToolbarLayout);
                Panel_ToolbarLayout.setHorizontalGroup(
                    Panel_ToolbarLayout.createParallelGroup()
                        .addGroup(Panel_ToolbarLayout.createSequentialGroup()
                            .addGap(30, 30, 30)
                            .addComponent(Login_Name)
                            .addContainerGap(673, Short.MAX_VALUE))
                );
                Panel_ToolbarLayout.setVerticalGroup(
                    Panel_ToolbarLayout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, Panel_ToolbarLayout.createSequentialGroup()
                            .addGap(0, 0, Short.MAX_VALUE)
                            .addComponent(Login_Name, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
                );
            }

            //======== Panel_Points ========
            {
                Panel_Points.setBackground(new Color(0x161616));
                Panel_Points.setForeground(Color.white);

                //---- Login_Name1 ----
                Login_Name1.setFont(new Font("Segoe UI", Font.PLAIN, 15));
                Login_Name1.setForeground(Color.white);
                Login_Name1.setText("Loot Inventory System");

                GroupLayout Panel_PointsLayout = new GroupLayout(Panel_Points);
                Panel_Points.setLayout(Panel_PointsLayout);
                Panel_PointsLayout.setHorizontalGroup(
                    Panel_PointsLayout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, Panel_PointsLayout.createSequentialGroup()
                            .addContainerGap(29, Short.MAX_VALUE)
                            .addComponent(Login_Name1, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
                            .addContainerGap(112, Short.MAX_VALUE))
                );
                Panel_PointsLayout.setVerticalGroup(
                    Panel_PointsLayout.createParallelGroup()
                        .addComponent(Login_Name1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                );
            }

            //======== ViewFinder ========
            {
                ViewFinder.setBackground(new Color(0x0e0e0e));

                //---- InventoryPanel ----
                InventoryPanel.setVisible(false);

                GroupLayout ViewFinderLayout = new GroupLayout(ViewFinder);
                ViewFinder.setLayout(ViewFinderLayout);
                ViewFinderLayout.setHorizontalGroup(
                    ViewFinderLayout.createParallelGroup()
                        .addGroup(ViewFinderLayout.createSequentialGroup()
                            .addGap(0, 0, 0)
                            .addComponent(InventoryPanel, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                            .addGap(0, 0, 0)
                            .addComponent(DashboardPanel, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                            .addGap(0, 0, 0))
                );
                ViewFinderLayout.setVerticalGroup(
                    ViewFinderLayout.createParallelGroup()
                        .addGroup(ViewFinderLayout.createSequentialGroup()
                            .addGroup(ViewFinderLayout.createParallelGroup()
                                .addComponent(DashboardPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(InventoryPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addGap(0, 0, 0))
                );
            }

            //---- bt_SignOut ----
            bt_SignOut.setFocusPainted(false);
            bt_SignOut.setBackground(new Color(0x161616));
            bt_SignOut.setBorder(null);
            bt_SignOut.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        SignOut(e);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });

            //---- bt_Inventory ----
            bt_Inventory.setFocusPainted(false);
            bt_Inventory.setBackground(new Color(0x161616));
            bt_Inventory.setBorder(null);
            bt_Inventory.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    SwitchToInv(e);
                }
            });

            GroupLayout Dashboard_PanelLayout = new GroupLayout(Dashboard_Panel);
            Dashboard_Panel.setLayout(Dashboard_PanelLayout);
            Dashboard_PanelLayout.setHorizontalGroup(
                Dashboard_PanelLayout.createParallelGroup()
                    .addGroup(Dashboard_PanelLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(Dashboard_PanelLayout.createParallelGroup()
                            .addComponent(ViewFinder, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(Dashboard_PanelLayout.createSequentialGroup()
                                .addComponent(Panel_Toolbar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bt_Inventory, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bt_SignOut, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                .addGap(25, 25, 25)
                                .addComponent(Panel_Points, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                        .addGap(50, 50, 50))
            );
            Dashboard_PanelLayout.setVerticalGroup(
                Dashboard_PanelLayout.createParallelGroup()
                    .addGroup(Dashboard_PanelLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(Dashboard_PanelLayout.createParallelGroup()
                            .addComponent(Panel_Points, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(bt_SignOut, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                            .addComponent(bt_Inventory, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                            .addComponent(Panel_Toolbar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addComponent(ViewFinder, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(67, 67, 67))
            );
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(Dashboard_Panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(Dashboard_Panel, GroupLayout.PREFERRED_SIZE, 729, GroupLayout.PREFERRED_SIZE)
        );
        setSize(1280, 760);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }
}
