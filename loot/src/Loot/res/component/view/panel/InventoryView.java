package Loot.res.component.view.panel;
/*  Author: TOFF
    DOCS ==================================
    Desc: Dashboard's layout. Can be swapped
    w/ Inventory View's layout.

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

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.border.*;

import Loot.model.BaseItem;
import Loot.model.BaseTransaction;
import Loot.model.GlobalContext;
import Loot.model.ItemViewCtx;
import Loot.res.component.view.modal.AddCategPrompt;
import Loot.res.component.view.modal.AddItemModal;
import Loot.res.component.view.modal.ConfirmDeleteItem;
import Loot.res.component.view.modal.SetCategoryModal;
import Loot.res.component.view.ui.ScrollBar;
import Loot.res.component.list.*;
import Loot.util.controller.EventList;
import Loot.util.crud.Search;
import Loot.util.crud.Sort;
import jdk.dynalink.Operation;

public class InventoryView extends JPanel {

    private static final int ADD_MODE   = 0;
    private static final int EDIT_MODE  = 1;

    private GlobalContext   Database;
    private EventList       EventList;
    private int             OperationMode = EDIT_MODE;
    ArrayList<BaseItem>     LocalRenderableListing;
    BaseItem                ChangeableItem;

    public InventoryView() {
        
        initComponents();
        designComponents();

    }

    // Utils ====================================================

    public void     resetEditor(){
        this.ChangeableItem     = null;

        ItemNameField.setText("No Item Selected");
        ItemPriceField.setText("0.00");
        ItemStockField.setText("0");
        CategoryLabel.setText("No Item Selected");
        ItemIDLabel.setText("0");
        CategIDLabel.setText("0");
    }

    public void     updateEditor(){
        if (this.ChangeableItem != null){
            ItemNameField.setText(ChangeableItem.getName());
            ItemPriceField.setText(Double.toString(ChangeableItem.getPrice()));
            ItemStockField.setText(Integer.toString(ChangeableItem.getStock()));
            String CategName = Search.FetchCategName(this.Database.getCategs(), ChangeableItem.getCategID());
            if (CategName != null){
                CategoryLabel.setText(CategName);
            } else {
                CategoryLabel.setText("No Category");
            }
            ItemIDLabel.setText(ChangeableItem.getID());
            CategIDLabel.setText(ChangeableItem.getCategID());
        } else {
            resetEditor();
        }
    }
    public void     populate(GlobalContext Database){

        this.Database           = Database;
        reloadListing();

        Border dashed           = BorderFactory.createDashedBorder(new Color(100, 100, 100), 5, 5);
        Border margin           = new EmptyBorder(5,0,5,0);
        AddItem.setBorder(new CompoundBorder(dashed, margin));

        this.LocalRenderableListing = this.Database.getItems();
    }
    public void     setEventList(EventList in_EventList){
        this.EventList          = in_EventList;
    }
    public void     reloadListing(){

        DefaultListModel<ItemViewCtx> CustomModel = new DefaultListModel<>();
        for (BaseItem item : this.Database.getItems()){
            ItemViewCtx itemctx = new ItemViewCtx(item);
            itemctx.setCategoryName(Search.FetchCategName(this.Database.getCategs(), item.getCategID()));
            CustomModel.addElement(itemctx);
        }
        InventoryList.setModel(CustomModel);
        System.out.println("Call : reloadListing()\n");
    }
    private void    designComponents(){
        // ListingListView
        ScrollBar ListingScrollBar  = new ScrollBar();
        ListingScrollView.setVerticalScrollBar(ListingScrollBar);
        ListingScrollView.setHorizontalScrollBar(null);
        ListingScrollView.getVerticalScrollBar().setUnitIncrement(20);
        ListingSearchBtn.setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass()
                .getResource("/Loot/res/img/search.png")))
                .getImage()
                .getScaledInstance(15,15,Image.SCALE_DEFAULT)));

        ItemStockField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyChar() >= '0' &&
                        e.getKeyChar() <= '9' ||
                        e.getKeyChar() == KeyEvent.VK_BACK_SPACE ||
                        e.getKeyChar() == '.') {
                    ItemStockField.setEditable(true);
                } else {
                    ItemStockField.setEditable(false);
                }
            }
        });

        ItemPriceField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyChar() >= '0' &&
                        e.getKeyChar() <= '9' ||
                        e.getKeyChar() == KeyEvent.VK_BACK_SPACE ||
                        e.getKeyChar() == '.') {
                    ItemStockField.setEditable(true);
                } else {
                    ItemStockField.setEditable(false);
                }
            }
        });
    }

    // Listeners ================================================

    private void    InventoryListSelect(MouseEvent e) {
        if (OperationMode == EDIT_MODE){
            // Modal Mode : Edit
            JList<ItemViewCtx> list = (JList<ItemViewCtx>)e.getSource();
            int index               = list.locationToIndex(e.getPoint());
            ItemViewCtx selected    = list.getModel().getElementAt(index);

            if (SwingUtilities.isLeftMouseButton(e)){
                if (selected != null){
                    this.ChangeableItem = selected.returnBaseItem();
                    updateEditor();
                } else {
                    resetEditor();
                }
            }
            // Modal Mode : Delete
            else if (SwingUtilities.isRightMouseButton(e)) {
                System.out.println("Right Clicked");

                BaseItem OrigItem   = Search.util_fetchByID(this.Database.getItems(), selected.returnBaseItem().getID());
                int OrigItemIndex   = this.Database.getItems().indexOf(OrigItem);

                MouseAdapter OKClicked = new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        Database.getItems().remove(OrigItemIndex);
                        reloadListing();
                        EventList.GlobalReload();
                    }
                };
                ConfirmDeleteItem ConfirmDialogue = new ConfirmDeleteItem(SwingUtilities.getWindowAncestor(this));
                ConfirmDialogue.setListener(OKClicked);
                ConfirmDialogue.setVisible(true);
            }
        }
    }
    private void    SearchList(MouseEvent e) {
        // TODO add your code here
    }
    private void    SaveItem(MouseEvent e) {
        // [X ]Replace DB Item w/ ChangeableItem
        BaseItem newItem    = new BaseItem(ItemNameField.getText(),
                Double.parseDouble(ItemPriceField.getText()),
                Integer.parseInt(ItemStockField.getText()),
                Integer.toString(this.Database.generateNewID()),
                "",
                "");

        if (OperationMode == EDIT_MODE){
            // Find Original Item
            int OrigItemIndex = this.Database.
                                getItems().
                                indexOf(Search.util_fetchByID(this.Database.
                                                                getItems(),
                                                                ItemIDLabel.
                                                                getText()));

            // Remove and Replace w/ at same position
            newItem.setCategID(this.Database.getItems().get(OrigItemIndex).getCategID());
            this.Database.getItems().remove(OrigItemIndex);
            this.Database.getItems().add(OrigItemIndex, newItem);
        } else {
            AddItem.setText("Add Item");
            AddItem.setForeground(Color.white);
            this.Database.addItem(newItem);
            OperationMode = EDIT_MODE;
        }
        reloadListing();
        EventList.GlobalReload();
    }
    private void    AddItemMouseClicked(MouseEvent e) {
        if (OperationMode == EDIT_MODE){
            OperationMode = ADD_MODE;   // Enable ADD_MODE

            // Button Replacer
            AddItem.setText("Cancel Add item");
            AddItem.setForeground(new Color(255, 157, 50));

            this.ChangeableItem = new BaseItem("New Item Name",
                                                0.0,
                                                0,
                                                Integer.toString(this.Database.generateNewID()),
                                                "-1",
                                                "");
            updateEditor();
        } else {

            // Button Replacer
            AddItem.setText("Add Item");
            AddItem.setForeground(Color.white);
            OperationMode = EDIT_MODE;  // ENABLE EDIT_MODE
            resetEditor();
        }
        reloadListing();
        EventList.GlobalReload();
    }

    /*
     *   Local Renderable Models Hierarchy
     *   1. Search Query (Highest Priority)
     *   2. Sort Algorithms (Based on Toggle)
     */

    // Sort By Name Algorithm ===================================
    private boolean fetchedSBN      = false;    // Save toggle position
    private void    SortByName(MouseEvent e) {
        ArrayList<BaseItem> SBN = Sort.util_sortByName(LocalRenderableListing);

        if (!fetchedSBN){
            fetchedSBN = true;
        } else {
            Sort.util_sortReverse(SBN);
            fetchedSBN = false;
        }

        DefaultListModel<ItemViewCtx> model = BaseItem.toListModel(SBN);
        InventoryList.setModel(model);
    }

    // Sort By Price Algorithm ==================================
    private boolean fetchedSBP      = false;    // Save toggle position
    private void    SortByPrice(MouseEvent e) {
        ArrayList<BaseItem> SBP = LocalRenderableListing;
        Sort.util_sortByPrice(SBP);

        if (!fetchedSBP){
            fetchedSBP = true;
        } else {
            Sort.util_sortReverse(SBP);
            fetchedSBP = false;
        }

        DefaultListModel<ItemViewCtx> model = BaseItem.toListModel(SBP);
        InventoryList.setModel(model);
    }

    // Sort By Stock Algorithm ==================================
    private boolean fetchedSBS      = false;    // Save toggle position
    private void    SortByStock(MouseEvent e) {
        ArrayList<BaseItem> SBS = LocalRenderableListing;
        Sort.util_sortByStock(SBS);

        if (!fetchedSBS){
            fetchedSBS = true;
        } else {
            Sort.util_sortReverse(SBS);
            fetchedSBS = false;
        }

        DefaultListModel<ItemViewCtx> model = BaseItem.toListModel(SBS);
        InventoryList.setModel(model);
    }

    private void    ListingSearchHasQuery(KeyEvent e) {
        // Insert Sanitization Code
        String query = ListingSearchField.getText();
        if (!query.equals("")){
            SearchListing(query);
        } else {
            ResetLocalRenderableList();
            reloadListing();
        }
    }
    private void    ResetLocalRenderableList(){
        this.LocalRenderableListing = this.Database.getItems();
    }

    private void    SearchListing(String query){
        LocalRenderableListing = Search.util_fetchByName(this.Database.getItems(), query);
        InventoryList.setModel(BaseItem.toListModel(LocalRenderableListing));
    }

    private void    SetCategory(MouseEvent e) {
        if ((this.ChangeableItem != null) && (OperationMode != ADD_MODE)){

            System.out.println("called SetCategory: Item not null");
            AddCategPrompt SetCategModal = new AddCategPrompt(SwingUtilities.getWindowAncestor(this),
                                                                this.Database,
                                                                ChangeableItem.getID(),
                                                                this.EventList);
            String SelectedCateg = null;
            SetCategModal.setVisible(true);

            System.out.printf("SetCategModal Visibility: %b\n", SetCategModal.isVisible());
            SelectedCateg = SetCategModal.getSelectedCategID();

            if (!SetCategModal.isVisible()){
                System.out.printf("Received String: %s\n", SelectedCateg);
                this.ChangeableItem.setCategID(SelectedCateg);
            }
        }
    }

    // Init =====================================================

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        panel2 = new JPanel();
        Panel_Listing = new JPanel();
        ListingSearchField = new JTextField();
        Header3 = new JLabel();
        ListingSearchBtn = new JButton();
        ListingScrollView = new JScrollPane();
        InventoryList = new lx_InventoryList<>();
        Header4 = new JLabel();
        SortByName = new JButton();
        SortByPrice = new JButton();
        SortByStock = new JButton();
        label2 = new JLabel();
        label3 = new JLabel();
        label4 = new JLabel();
        label5 = new JLabel();
        AddItem = new JButton();
        Panel_Checkout = new JPanel();
        SaveItem = new JButton();
        Header5 = new JLabel();
        panel1 = new JPanel();
        ItemNameField = new JTextField();
        label1 = new JLabel();
        label6 = new JLabel();
        label7 = new JLabel();
        ItemStockField = new JTextField();
        ItemPriceField = new JTextField();
        label8 = new JLabel();
        CategoryLabel = new JLabel();
        label10 = new JLabel();
        ItemSplashImg = new JLabel();
        label12 = new JLabel();
        ItemIDLabel = new JLabel();
        label14 = new JLabel();
        CategIDLabel = new JLabel();

        //======== this ========
        setMinimumSize(new Dimension(865, 545));

        //======== panel2 ========
        {
            panel2.setBackground(new Color(0x0e0e0e));

            //======== Panel_Listing ========
            {
                Panel_Listing.setBackground(new Color(0x161616));
                Panel_Listing.setForeground(Color.white);

                //---- ListingSearchField ----
                ListingSearchField.setBackground(new Color(0x272727));
                ListingSearchField.setForeground(Color.white);
                ListingSearchField.setToolTipText("Search");
                ListingSearchField.setBorder(null);
                ListingSearchField.setHighlighter(null);
                ListingSearchField.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyReleased(KeyEvent e) {
                        ListingSearchHasQuery(e);
                    }
                });

                //---- Header3 ----
                Header3.setFont(new Font("Segoe UI", Font.BOLD, 18));
                Header3.setForeground(Color.white);
                Header3.setText("Listing");

                //---- ListingSearchBtn ----
                ListingSearchBtn.setBackground(new Color(0x242424));
                ListingSearchBtn.setForeground(Color.white);
                ListingSearchBtn.setBorder(null);
                ListingSearchBtn.setFocusPainted(false);
                ListingSearchBtn.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        SearchList(e);
                    }
                });

                //======== ListingScrollView ========
                {
                    ListingScrollView.setBackground(new Color(0x1e1e1e));
                    ListingScrollView.setBorder(null);
                    ListingScrollView.setForeground(new Color(0x1e1e1e));
                    ListingScrollView.setHorizontalScrollBar(null);
                    ListingScrollView.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

                    //---- InventoryList ----
                    InventoryList.setBackground(new Color(0x161616));
                    InventoryList.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            InventoryListSelect(e);
                        }
                    });
                    ListingScrollView.setViewportView(InventoryList);
                }

                //---- Header4 ----
                Header4.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                Header4.setForeground(Color.white);
                Header4.setText("Sort By:");

                //---- SortByName ----
                SortByName.setText("Name");
                SortByName.setBackground(new Color(0x272727));
                SortByName.setBorder(null);
                SortByName.setForeground(Color.white);
                SortByName.setFocusPainted(false);
                SortByName.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        SortByName(e);
                    }
                });

                //---- SortByPrice ----
                SortByPrice.setText("Price");
                SortByPrice.setBackground(new Color(0x272727));
                SortByPrice.setBorder(null);
                SortByPrice.setForeground(Color.white);
                SortByPrice.setFocusPainted(false);
                SortByPrice.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        SortByPrice(e);
                    }
                });

                //---- SortByStock ----
                SortByStock.setText("Stock");
                SortByStock.setBackground(new Color(0x272727));
                SortByStock.setBorder(null);
                SortByStock.setForeground(Color.white);
                SortByStock.setFocusPainted(false);
                SortByStock.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        SortByStock(e);
                    }
                });

                //---- label2 ----
                label2.setText("Name:");
                label2.setForeground(Color.white);

                //---- label3 ----
                label3.setText("Stock:");
                label3.setForeground(Color.white);

                //---- label4 ----
                label4.setText("Category:");
                label4.setForeground(Color.white);

                //---- label5 ----
                label5.setText("Price:");
                label5.setForeground(Color.white);

                //---- AddItem ----
                AddItem.setText("Add New Item");
                AddItem.setBackground(new Color(0x161616));
                AddItem.setBorder(new EmptyBorder(5, 0, 5, 0));
                AddItem.setFocusPainted(false);
                AddItem.setForeground(Color.white);
                AddItem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                AddItem.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        AddItemMouseClicked(e);
                    }
                });

                GroupLayout Panel_ListingLayout = new GroupLayout(Panel_Listing);
                Panel_Listing.setLayout(Panel_ListingLayout);
                Panel_ListingLayout.setHorizontalGroup(
                    Panel_ListingLayout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, Panel_ListingLayout.createSequentialGroup()
                            .addGap(30, 30, 30)
                            .addGroup(Panel_ListingLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(AddItem, GroupLayout.DEFAULT_SIZE, 769, Short.MAX_VALUE)
                                .addGroup(GroupLayout.Alignment.LEADING, Panel_ListingLayout.createSequentialGroup()
                                    .addComponent(Header4)
                                    .addGap(18, 18, 18)
                                    .addComponent(SortByName, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(SortByPrice, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(SortByStock, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 77, Short.MAX_VALUE)
                                    .addComponent(ListingSearchField, GroupLayout.PREFERRED_SIZE, 375, GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, 0)
                                    .addComponent(ListingSearchBtn, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                                .addGroup(GroupLayout.Alignment.LEADING, Panel_ListingLayout.createSequentialGroup()
                                    .addGroup(Panel_ListingLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(label2)
                                        .addComponent(Header3))
                                    .addGap(190, 190, 190)
                                    .addComponent(label3)
                                    .addGap(66, 66, 66)
                                    .addComponent(label4)
                                    .addGap(99, 99, 99)
                                    .addComponent(label5)
                                    .addGap(0, 246, Short.MAX_VALUE))
                                .addComponent(ListingScrollView, GroupLayout.Alignment.LEADING))
                            .addGap(30, 30, 30))
                );
                Panel_ListingLayout.setVerticalGroup(
                    Panel_ListingLayout.createParallelGroup()
                        .addGroup(Panel_ListingLayout.createSequentialGroup()
                            .addGap(30, 30, 30)
                            .addComponent(Header3)
                            .addGroup(Panel_ListingLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addGroup(Panel_ListingLayout.createSequentialGroup()
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(Panel_ListingLayout.createParallelGroup()
                                        .addComponent(ListingSearchBtn, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(ListingSearchField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)))
                                .addGroup(Panel_ListingLayout.createParallelGroup()
                                    .addGroup(Panel_ListingLayout.createSequentialGroup()
                                        .addGap(21, 21, 21)
                                        .addGroup(Panel_ListingLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(SortByName, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(SortByPrice, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(SortByStock, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(GroupLayout.Alignment.TRAILING, Panel_ListingLayout.createSequentialGroup()
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(Header4))))
                            .addGap(18, 18, 18)
                            .addGroup(Panel_ListingLayout.createParallelGroup()
                                .addComponent(label2)
                                .addComponent(label3)
                                .addComponent(label4)
                                .addComponent(label5, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(ListingScrollView, GroupLayout.PREFERRED_SIZE, 326, GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(AddItem)
                            .addGap(30, 30, 30))
                );
            }

            //======== Panel_Checkout ========
            {
                Panel_Checkout.setBackground(new Color(0x161616));
                Panel_Checkout.setMinimumSize(new Dimension(500, 700));

                //---- SaveItem ----
                SaveItem.setBackground(new Color(0x141414));
                SaveItem.setFont(new Font("Segoe UI", Font.PLAIN, 18));
                SaveItem.setForeground(Color.white);
                SaveItem.setText("Save Item");
                SaveItem.setBorder(new LineBorder(new Color(0x303030), 2));
                SaveItem.setFocusPainted(false);
                SaveItem.setMaximumSize(new Dimension(296, 50));
                SaveItem.setMinimumSize(new Dimension(296, 50));
                SaveItem.setRequestFocusEnabled(false);
                SaveItem.setRolloverEnabled(false);
                SaveItem.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        SaveItem(e);
                    }
                });

                //---- Header5 ----
                Header5.setFont(new Font("Segoe UI", Font.BOLD, 18));
                Header5.setForeground(Color.white);
                Header5.setText("Editor:");

                //======== panel1 ========
                {
                    panel1.setBackground(new Color(0x161616));

                    //---- ItemNameField ----
                    ItemNameField.setBorder(null);
                    ItemNameField.setBackground(new Color(0x161616));
                    ItemNameField.setForeground(Color.white);
                    ItemNameField.setFont(new Font("Segoe UI", Font.BOLD, 14));
                    ItemNameField.setText("No Item Selected");

                    //---- label1 ----
                    label1.setText("Name:");
                    label1.setForeground(Color.white);
                    label1.setBackground(new Color(0x161616));

                    //---- label6 ----
                    label6.setText("Stock:");
                    label6.setForeground(Color.white);
                    label6.setBackground(new Color(0x161616));

                    //---- label7 ----
                    label7.setText("Price:");
                    label7.setForeground(Color.white);
                    label7.setBackground(new Color(0x161616));

                    //---- ItemStockField ----
                    ItemStockField.setBorder(null);
                    ItemStockField.setBackground(new Color(0x161616));
                    ItemStockField.setForeground(Color.white);
                    ItemStockField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                    ItemStockField.setText("0");

                    //---- ItemPriceField ----
                    ItemPriceField.setBorder(null);
                    ItemPriceField.setBackground(new Color(0x161616));
                    ItemPriceField.setForeground(Color.white);
                    ItemPriceField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                    ItemPriceField.setText("0.00");

                    //---- label8 ----
                    label8.setText("Category:");
                    label8.setForeground(Color.white);
                    label8.setBackground(new Color(0x161616));
                    label8.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            SetCategory(e);
                        }
                    });

                    //---- CategoryLabel ----
                    CategoryLabel.setText("No Item Selected");
                    CategoryLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
                    CategoryLabel.setForeground(Color.white);
                    CategoryLabel.setBackground(new Color(0x1f1f1f));
                    CategoryLabel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            SetCategory(e);
                        }
                    });

                    //---- label10 ----
                    label10.setText("Splash Image:");
                    label10.setForeground(Color.white);
                    label10.setBackground(new Color(0x161616));

                    //---- ItemSplashImg ----
                    ItemSplashImg.setBackground(new Color(0x161616));

                    //---- label12 ----
                    label12.setText("ItemID:");
                    label12.setForeground(Color.white);
                    label12.setBackground(new Color(0x161616));

                    //---- ItemIDLabel ----
                    ItemIDLabel.setText("No Item Selected");
                    ItemIDLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                    ItemIDLabel.setForeground(Color.white);
                    ItemIDLabel.setBackground(new Color(0x161616));

                    //---- label14 ----
                    label14.setText("CategID:");
                    label14.setForeground(Color.white);
                    label14.setBackground(new Color(0x161616));

                    //---- CategIDLabel ----
                    CategIDLabel.setText("No Item Selected");
                    CategIDLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                    CategIDLabel.setForeground(Color.white);
                    CategIDLabel.setBackground(new Color(0x161616));

                    GroupLayout panel1Layout = new GroupLayout(panel1);
                    panel1.setLayout(panel1Layout);
                    panel1Layout.setHorizontalGroup(
                        panel1Layout.createParallelGroup()
                            .addComponent(ItemNameField)
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addComponent(label6)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(label7)
                                .addGap(78, 78, 78))
                            .addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                                .addGroup(panel1Layout.createParallelGroup()
                                    .addComponent(ItemStockField, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label12)
                                    .addComponent(ItemIDLabel))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(panel1Layout.createParallelGroup()
                                    .addComponent(label14)
                                    .addComponent(CategIDLabel)
                                    .addComponent(ItemPriceField, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)))
                            .addComponent(ItemSplashImg, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGroup(panel1Layout.createParallelGroup()
                                    .addComponent(label1)
                                    .addComponent(label10)
                                    .addComponent(label8))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(CategoryLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    );
                    panel1Layout.setVerticalGroup(
                        panel1Layout.createParallelGroup()
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(label1)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ItemNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label6)
                                    .addComponent(label7))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(ItemStockField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ItemPriceField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(label8)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(CategoryLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addGroup(panel1Layout.createSequentialGroup()
                                        .addComponent(label12)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(ItemIDLabel))
                                    .addGroup(panel1Layout.createSequentialGroup()
                                        .addComponent(label14)
                                        .addGap(6, 6, 6)
                                        .addComponent(CategIDLabel)))
                                .addGap(20, 20, 20)
                                .addComponent(label10)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ItemSplashImg, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE))
                    );
                }

                GroupLayout Panel_CheckoutLayout = new GroupLayout(Panel_Checkout);
                Panel_Checkout.setLayout(Panel_CheckoutLayout);
                Panel_CheckoutLayout.setHorizontalGroup(
                    Panel_CheckoutLayout.createParallelGroup()
                        .addGroup(Panel_CheckoutLayout.createSequentialGroup()
                            .addGap(30, 30, 30)
                            .addGroup(Panel_CheckoutLayout.createParallelGroup()
                                .addComponent(Header5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(panel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(30, 30, 30))
                        .addGroup(GroupLayout.Alignment.TRAILING, Panel_CheckoutLayout.createSequentialGroup()
                            .addGap(0, 0, Short.MAX_VALUE)
                            .addComponent(SaveItem, GroupLayout.PREFERRED_SIZE, 296, GroupLayout.PREFERRED_SIZE))
                );
                Panel_CheckoutLayout.setVerticalGroup(
                    Panel_CheckoutLayout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, Panel_CheckoutLayout.createSequentialGroup()
                            .addGap(28, 28, 28)
                            .addComponent(Header5)
                            .addGap(18, 18, 18)
                            .addComponent(panel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(SaveItem, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
                );
            }

            GroupLayout panel2Layout = new GroupLayout(panel2);
            panel2.setLayout(panel2Layout);
            panel2Layout.setHorizontalGroup(
                panel2Layout.createParallelGroup()
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addGap(0, 0, 0)
                        .addComponent(Panel_Listing, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(25, 25, 25)
                        .addComponent(Panel_Checkout, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            );
            panel2Layout.setVerticalGroup(
                panel2Layout.createParallelGroup()
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addGap(0, 0, 0)
                        .addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(Panel_Listing, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Panel_Checkout, GroupLayout.PREFERRED_SIZE, 547, GroupLayout.PREFERRED_SIZE)))
            );
        }

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addComponent(panel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(panel2, GroupLayout.PREFERRED_SIZE, 546, GroupLayout.PREFERRED_SIZE))
        );
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel panel2;
    private JPanel Panel_Listing;
    private JTextField ListingSearchField;
    private JLabel Header3;
    private JButton ListingSearchBtn;
    private JScrollPane ListingScrollView;
    private lx_InventoryList<ItemViewCtx> InventoryList;
    private JLabel Header4;
    private JButton SortByName;
    private JButton SortByPrice;
    private JButton SortByStock;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JLabel label5;
    private JButton AddItem;
    private JPanel Panel_Checkout;
    private JButton SaveItem;
    private JLabel Header5;
    private JPanel panel1;
    private JTextField ItemNameField;
    private JLabel label1;
    private JLabel label6;
    private JLabel label7;
    private JTextField ItemStockField;
    private JTextField ItemPriceField;
    private JLabel label8;
    private JLabel CategoryLabel;
    private JLabel label10;
    private JLabel ItemSplashImg;
    private JLabel label12;
    private JLabel ItemIDLabel;
    private JLabel label14;
    private JLabel CategIDLabel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
