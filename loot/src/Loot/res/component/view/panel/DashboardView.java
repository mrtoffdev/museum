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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.border.*;

import Loot.model.*;
import Loot.res.component.view.modal.AddCategoryModal;
import Loot.res.component.view.modal.ConfirmDeleteItem;
import Loot.res.component.view.ui.ScrollBar;
import Loot.res.component.list.*;
import Loot.res.component.list.lx_CategList;
import Loot.res.component.list.lx_DashboardList;
import Loot.res.component.view.modal.AddItemModal;
import Loot.util.controller.EventList;
import Loot.util.crud.ReceiptParser;
import Loot.util.crud.Search;
import Loot.util.crud.Sort;

public class DashboardView extends JPanel {

    private GlobalContext   Database;
    EventList               EventList;

    private String          activeCateg = "";
    ArrayList<BaseItem>     LocalRenderableListing;
    ArrayList<BaseCateg>    LocalRenderableCategs;

    public DashboardView() {

        initComponents();
        DesignComponents();

    }

    // [X] Utils ====================================================

    public void  DesignComponents(){
        // ListingListView
        ScrollBar ListingScrollBar  = new ScrollBar();
        ListingScrollView.setVerticalScrollBar(ListingScrollBar);
        ListingScrollView.setHorizontalScrollBar(null);
        ListingScrollView.getVerticalScrollBar().setUnitIncrement(20);
        ListingSearchField.setBackground(new Color(43, 43, 43));
        ListingSearchBtn.setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass()
                .getResource("/Loot/res/img/search.png")))
                .getImage()
                .getScaledInstance(15,15,Image.SCALE_DEFAULT)));

        // CategListView
        ScrollBar CategScrollBar    = new ScrollBar();
        CategScrollView.setVerticalScrollBar(CategScrollBar);
        CategScrollView.setHorizontalScrollBar(null);
        CategScrollView.getVerticalScrollBar().setUnitIncrement(20);
        CategSearchField.setBackground(new Color(43, 43, 43));
        CategSearchBtn.setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass()
                .getResource("/Loot/res/img/search.png")))
                .getImage()
                .getScaledInstance(15,15,Image.SCALE_DEFAULT)));

        ScrollBar TransactScrollBar = new ScrollBar();
        TransactionScrollView.setVerticalScrollBar(TransactScrollBar);
        TransactionScrollView.setHorizontalScrollBar(null);
        TransactionScrollView.getVerticalScrollBar().setUnitIncrement(20);
        
        AddCateg.setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass()
                .getResource("/Loot/res/img/addicon.png")))
                .getImage()
                .getScaledInstance(15,15,Image.SCALE_DEFAULT)));
    }

    public void  setEventList(EventList in_EventList){
        this.EventList = in_EventList;
    }
    public void  populate(GlobalContext Database){

        this.Database           = Database;
        this.LocalRenderableListing  = new ArrayList<>();

        this.LocalRenderableListing.addAll(this.Database.getItems());
        reloadAll();
    }

    // Reloading Panels
    public void reloadAll(){
        reloadCateg();
        reloadListing();
        reloadTransact();
    }
    private void reloadCateg(){
        DefaultListModel<CategViewCtx> List = new DefaultListModel<>();
        for (BaseCateg item : this.Database.getCategs()){
            int CategItems = Search.util_fetchByCategID(this.Database.getItems(), item.getCategID()).size();
            CategViewCtx Categ = new CategViewCtx(item, CategItems);
            List.addElement(Categ);
        }
        CategListView.setModel(List);
        System.out.println("Call : reloadCateg()\n");
    }
    public void  reloadListing(){
        // Clears LocalList and Fetches Updated Items From Database
        LocalRenderableListing.clear();
        LocalRenderableListing.addAll(this.Database.getItems());

        ListingListView.setModel(BaseItem.toListModel(LocalRenderableListing));
    }
    private void reloadTransact(){
        TransactListView.setModel(BaseTransaction.toListModel(this.Database.getTransacts()));
        System.out.println("Call : reloadListing()\n");
    }

    // [X] Listeners ================================================

    // Listing Listeners
    private void ListingSearchCall(MouseEvent e) {
        String query    = ListingSearchField.getText();
        SearchListing(query);
        reloadListing();

        System.out.println("Call : ListingSearchCall()");
    }
    private void ListingListSelect(MouseEvent e) {

        if (SwingUtilities.isLeftMouseButton(e)){
            JList<ItemViewCtx> list = (JList<ItemViewCtx>)e.getSource();
            int index               = list.locationToIndex(e.getPoint());
            ItemViewCtx selected    = list.getModel().getElementAt(index);
            BaseItem selected_base  = selected.returnBaseItem();

            if (selected_base.getStock() != 0){
                AddItemModal modal  = new AddItemModal(SwingUtilities.getWindowAncestor(this),
                                                        this.EventList,
                                                        selected_base,
                                                        null);
                modal.setVisible(true);
                reloadTransact();
                System.out.printf("Selected Item Id : %s\n", selected.getItemID());
            }
        } else if (SwingUtilities.isRightMouseButton(e)) {
            System.out.println("Right Clicked");
        }

    }
    private void ListingSearchHasQuery(KeyEvent e) {
        // Insert Sanitization Code
        String query = ListingSearchField.getText();
        if (!query.equals("")){
            SearchListing(query);
        } else {
            reloadListing();
        }

        // Debug:
        System.out.println("Database Monitor:");
        for (BaseItem item : this.Database.getItems()){
            System.out.printf("%s\n", item.getName());
        }

        System.out.println("\nRenderableList Monitor:");
        for (BaseItem item : this.LocalRenderableListing){
            System.out.printf("%s\n", item.getName());
        }
    }

    // Category Listeners
    private void AddCateg(MouseEvent e) {
        AddCategoryModal AddCategModal = new AddCategoryModal(SwingUtilities.getWindowAncestor(this));
        AtomicReference<String> newCategName = new AtomicReference<>();
        ActionListener ConfirmListener = e1 -> {
            System.out.println("Clicked confirm");
            if (!AddCategModal.getCategName().equals("")){
                newCategName.set(AddCategModal.getCategName());
                System.out.printf("Returned: %s\n",newCategName);
                AddCategModal.disposeModal();
                this.Database.addCateg(new BaseCateg(newCategName.toString(),Integer.toString(this.Database.generateNewCategID())));
                EventList.GlobalReload();
                reloadAll();
            }
        };
        AddCategModal.setActionListener(ConfirmListener);
        AddCategModal.setVisible(true);
    }
    private void CategListSelect(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)){
            JList<CategViewCtx> list = (JList<CategViewCtx>)e.getSource();
            int index                = list.locationToIndex(e.getPoint());
            CategViewCtx selected    = list.getModel().getElementAt(index);
            BaseCateg categRef       = selected.returnBaseCateg();

            if (!activeCateg.equals(selected.getCategName())){
                if (selected.getCategItems() != 0){
                    activeCateg = selected.getCategName();
                    this.LocalRenderableListing.clear();
                    ArrayList<BaseItem> FilteredByCateg = Search.util_fetchByCategID(this.Database.getItems(),categRef.getCategID());
                    this.LocalRenderableListing.addAll(FilteredByCateg);
                    ListingListView.setModel(BaseItem.toListModel(this.LocalRenderableListing));
                    System.out.printf("Selected Categ Id : %s\n", categRef.getCategID());
                }
            } else {
                activeCateg = "";
                reloadListing();
            }

        } else if (SwingUtilities.isRightMouseButton(e)) {
            System.out.println("Right Clicked");
        }
    }

    // Transaction Listeners
    private void TransactListSelect(MouseEvent e) {
        // Modal Mode : Edit
        JList<BaseTransaction> list = (JList<BaseTransaction>)e.getSource();
        int index               = list.locationToIndex(e.getPoint());
        BaseTransaction selected    = list.getModel().getElementAt(index);

        if (SwingUtilities.isLeftMouseButton(e)){
            if (selected != null){
                AddItemModal modal  = new AddItemModal(SwingUtilities.getWindowAncestor(this),
                        this.EventList,
                        null,
                        selected);
                modal.setVisible(true);
                reloadTransact();
                System.out.printf("Selected Item Id : %s\n", selected.getItemID());
            }
        }
        // Modal Mode : Delete
        else if (SwingUtilities.isRightMouseButton(e)) {
            System.out.println("Right Clicked");

            BaseItem OrigItem   = Search.util_fetchByID(this.Database.getItems(), selected.getBaseItem().getID());
            int OrigItemIndex   = this.Database.getItems().indexOf(OrigItem);
            int OrigItemStock   = OrigItem.getStock();

            MouseAdapter OKClicked = new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Database.getItems().get(OrigItemIndex).setStock(OrigItemStock + selected.getItemCount());
                    selected.setItemCount(0);
                    EventList.EditTransactionModal(selected, selected.getItemID()); // Shortcut to Modal's Callback
                }
            };
            ConfirmDeleteItem ConfirmDialogue = new ConfirmDeleteItem(SwingUtilities.getWindowAncestor(this));
            ConfirmDialogue.setListener(OKClicked);
            ConfirmDialogue.setVisible(true);

        }
    }

    private void Checkout(MouseEvent e){
        for (BaseTransaction transact : this.Database.getTransacts()){
            System.out.printf("Name: %s | Count :%d\n", transact.getBaseItem().getName(), transact.getItemCount());
        }
        try {
            ReceiptParser.parseReceipt(this.Database.getTransacts());

        } catch (IOException ignored) {

        }
        this.Database.clearTransactions();
        this.EventList.CheckoutCall();
        EventList.GlobalReload();
        reloadTransact();
    }

    // [X] Toggles / Filters ========================================


    // Listing Search Field
    private void SearchListing(String query){
        // Test Integrity:
        System.out.println("Integrity Test 1: Pre Search\n");

        for (BaseItem item : this.LocalRenderableListing){
            System.out.printf("%s\n", item.getName());
        }

        ArrayList<BaseItem> queried = Search.util_fetchByName(this.Database.getItems(), query);

        this.LocalRenderableListing.clear();
        this.LocalRenderableListing.addAll(queried);
        System.out.println("Integrity Test 1.5: Post Clear\n");
        for (BaseItem item : LocalRenderableListing){
            System.out.printf("%s\n", item.getName());
        }

        ListingListView.setModel(BaseItem.toListModel(this.LocalRenderableListing));

        System.out.println("Integrity Test 2: Post Search\n");
        for (BaseItem item : this.LocalRenderableListing){
            System.out.printf("%s\n", item.getName());
        }
    }

    // Sort By Name Algorithm
    private boolean fetchedSBN      = false;    // Save toggle position
    private void SortByName(MouseEvent e) {
        ArrayList<BaseItem> SBN = Sort.util_sortByName(LocalRenderableListing);

        if (!fetchedSBN){
            fetchedSBN = true;
        } else {
            Sort.util_sortReverse(SBN);
            fetchedSBN = false;
        }

        DefaultListModel<ItemViewCtx> model = BaseItem.toListModel(SBN);
        ListingListView.setModel(model);
    }

    // Sort By Price Algorithm
    private boolean fetchedSBP      = false;    // Save toggle position
    private void SortByPrice(MouseEvent e) {
        ArrayList<BaseItem> SBP = Sort.util_sortByName(LocalRenderableListing);
        Sort.util_sortByPrice(SBP);

        if (!fetchedSBP){
            fetchedSBP = true;
        } else {
            Sort.util_sortReverse(SBP);
            fetchedSBP = false;
        }

        DefaultListModel<ItemViewCtx> model = BaseItem.toListModel(SBP);
        ListingListView.setModel(model);
    }

    // Sort By Stock Algorithm
    private boolean fetchedSBS      = false;    // Save toggle position
    private void SortByStock(MouseEvent e) {
        ArrayList<BaseItem> SBS = Sort.util_sortByName(LocalRenderableListing);
        Sort.util_sortByStock(SBS);

        if (!fetchedSBS){
            fetchedSBS = true;
        } else {
            Sort.util_sortReverse(SBS);
            fetchedSBS = false;
        }

        DefaultListModel<ItemViewCtx> model = BaseItem.toListModel(SBS);
        ListingListView.setModel(model);
    }

    private void CategSearchCall(MouseEvent e) {
        // TODO add your code here
    }

    // [X] Init =====================================================

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        panel2 = new JPanel();
        Panel_Listing = new JPanel();
        ListingSearchField = new JTextField();
        Header3 = new JLabel();
        ListingSearchBtn = new JButton();
        ListingScrollView = new JScrollPane();
        ListingListView = new lx_DashboardList<>();
        Header4 = new JLabel();
        SortName = new JButton();
        SortPrice = new JButton();
        SortStock = new JButton();
        Panel_Categories = new JPanel();
        Header1 = new JLabel();
        Header2 = new JLabel();
        CategScrollView = new JScrollPane();
        CategListView = new lx_CategList<>();
        CategSearchField = new JTextField();
        CategSearchBtn = new JButton();
        AddCateg = new JButton();
        Panel_Checkout = new JPanel();
        bt_Checkout = new JButton();
        Header5 = new JLabel();
        TransactionScrollView = new JScrollPane();
        TransactListView = new lx_TransactList<>();

        //======== this ========
        setPreferredSize(new Dimension(865, 546));
        setMinimumSize(new Dimension(865, 546));

        //======== panel2 ========
        {
            panel2.setBackground(new Color(0x0e0e0e));

            //======== Panel_Listing ========
            {
                Panel_Listing.setBackground(new Color(0x161616));
                Panel_Listing.setForeground(Color.white);

                //---- ListingSearchField ----
                ListingSearchField.setForeground(Color.white);
                ListingSearchField.setToolTipText("Search");
                ListingSearchField.setBorder(new EmptyBorder(0, 15, 0, 0));
                ListingSearchField.setHighlighter(null);
                ListingSearchField.setMargin(new Insets(2, 10, 2, 6));
                ListingSearchField.setSelectionColor(new Color(0xcccccc));
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
                        ListingSearchCall(e);
                    }
                });

                //======== ListingScrollView ========
                {
                    ListingScrollView.setBackground(new Color(0x1e1e1e));
                    ListingScrollView.setBorder(null);
                    ListingScrollView.setForeground(new Color(0x1e1e1e));
                    ListingScrollView.setHorizontalScrollBar(null);
                    ListingScrollView.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

                    //---- ListingListView ----
                    ListingListView.setBackground(new Color(0x161616));
                    ListingListView.setBorder(null);
                    ListingListView.setForeground(new Color(0x161616));
                    ListingListView.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            ListingListSelect(e);
                        }
                    });
                    ListingScrollView.setViewportView(ListingListView);
                }

                //---- Header4 ----
                Header4.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                Header4.setForeground(Color.white);
                Header4.setText("Sort By:");

                //---- SortName ----
                SortName.setText("Name");
                SortName.setBackground(new Color(0x272727));
                SortName.setBorder(null);
                SortName.setForeground(Color.white);
                SortName.setFocusPainted(false);
                SortName.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        SortByName(e);
                    }
                });

                //---- SortPrice ----
                SortPrice.setText("Price");
                SortPrice.setBackground(new Color(0x272727));
                SortPrice.setBorder(null);
                SortPrice.setForeground(Color.white);
                SortPrice.setFocusPainted(false);
                SortPrice.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        SortByPrice(e);
                    }
                });

                //---- SortStock ----
                SortStock.setText("Stock");
                SortStock.setBackground(new Color(0x272727));
                SortStock.setBorder(null);
                SortStock.setForeground(Color.white);
                SortStock.setFocusPainted(false);
                SortStock.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        SortByStock(e);
                    }
                });

                GroupLayout Panel_ListingLayout = new GroupLayout(Panel_Listing);
                Panel_Listing.setLayout(Panel_ListingLayout);
                Panel_ListingLayout.setHorizontalGroup(
                    Panel_ListingLayout.createParallelGroup()
                        .addGroup(Panel_ListingLayout.createSequentialGroup()
                            .addGap(30, 30, 30)
                            .addGroup(Panel_ListingLayout.createParallelGroup()
                                .addGroup(Panel_ListingLayout.createSequentialGroup()
                                    .addComponent(ListingSearchField)
                                    .addGap(0, 0, 0)
                                    .addComponent(ListingSearchBtn, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                                .addGroup(Panel_ListingLayout.createSequentialGroup()
                                    .addGroup(Panel_ListingLayout.createParallelGroup()
                                        .addComponent(Header3)
                                        .addGroup(Panel_ListingLayout.createSequentialGroup()
                                            .addComponent(Header4)
                                            .addGap(18, 18, 18)
                                            .addComponent(SortName, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(SortPrice, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(SortStock, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)))
                                    .addGap(0, 189, Short.MAX_VALUE))
                                .addComponent(ListingScrollView, GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE))
                            .addGap(30, 30, 30))
                );
                Panel_ListingLayout.setVerticalGroup(
                    Panel_ListingLayout.createParallelGroup()
                        .addGroup(Panel_ListingLayout.createSequentialGroup()
                            .addGap(30, 30, 30)
                            .addComponent(Header3)
                            .addGap(21, 21, 21)
                            .addGroup(Panel_ListingLayout.createParallelGroup()
                                .addGroup(Panel_ListingLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(SortName, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(SortPrice, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(SortStock, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
                                .addGroup(GroupLayout.Alignment.TRAILING, Panel_ListingLayout.createSequentialGroup()
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 5, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Header4)))
                            .addGap(18, 18, 18)
                            .addGroup(Panel_ListingLayout.createParallelGroup()
                                .addComponent(ListingSearchBtn, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                .addComponent(ListingSearchField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addComponent(ListingScrollView, GroupLayout.PREFERRED_SIZE, 348, GroupLayout.PREFERRED_SIZE)
                            .addGap(31, 31, 31))
                );
            }

            //======== Panel_Categories ========
            {
                Panel_Categories.setBackground(new Color(0x161616));
                Panel_Categories.setPreferredSize(new Dimension(280, 546));

                //---- Header1 ----
                Header1.setFont(new Font("Segoe UI", Font.BOLD, 18));
                Header1.setForeground(Color.white);
                Header1.setText("Inventory");

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

                //---- AddCateg ----
                AddCateg.setBackground(new Color(0x161616));
                AddCateg.setForeground(Color.white);
                AddCateg.setBorder(null);
                AddCateg.setFocusPainted(false);
                AddCateg.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        AddCateg(e);
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
                                    .addGap(117, 117, 117)
                                    .addComponent(AddCateg, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                                .addGroup(Panel_CategoriesLayout.createSequentialGroup()
                                    .addGap(30, 30, 30)
                                    .addGroup(Panel_CategoriesLayout.createParallelGroup()
                                        .addComponent(CategScrollView, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                                        .addGroup(Panel_CategoriesLayout.createSequentialGroup()
                                            .addComponent(Header1)
                                            .addGap(0, 0, Short.MAX_VALUE))))
                                .addGroup(Panel_CategoriesLayout.createSequentialGroup()
                                    .addContainerGap(31, Short.MAX_VALUE)
                                    .addComponent(CategSearchField, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, 0)
                                    .addComponent(CategSearchBtn, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)))
                            .addGap(30, 30, 30))
                );
                Panel_CategoriesLayout.setVerticalGroup(
                    Panel_CategoriesLayout.createParallelGroup()
                        .addGroup(Panel_CategoriesLayout.createSequentialGroup()
                            .addGap(30, 30, 30)
                            .addComponent(Header1)
                            .addGap(23, 23, 23)
                            .addGroup(Panel_CategoriesLayout.createParallelGroup()
                                .addGroup(Panel_CategoriesLayout.createSequentialGroup()
                                    .addGap(2, 2, 2)
                                    .addComponent(Header2))
                                .addComponent(AddCateg, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(Panel_CategoriesLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(CategSearchField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                .addComponent(CategSearchBtn, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addComponent(CategScrollView, GroupLayout.PREFERRED_SIZE, 348, GroupLayout.PREFERRED_SIZE)
                            .addGap(31, 31, 31))
                );
            }

            //======== Panel_Checkout ========
            {
                Panel_Checkout.setBackground(new Color(0x161616));
                Panel_Checkout.setMinimumSize(new Dimension(500, 700));

                //---- bt_Checkout ----
                bt_Checkout.setBackground(new Color(0x242424));
                bt_Checkout.setFont(new Font("Segoe UI", Font.PLAIN, 18));
                bt_Checkout.setForeground(Color.white);
                bt_Checkout.setText("Checkout");
                bt_Checkout.setBorder(new EmptyBorder(2, 2, 2, 2));
                bt_Checkout.setFocusPainted(false);
                bt_Checkout.setMaximumSize(new Dimension(296, 50));
                bt_Checkout.setMinimumSize(new Dimension(296, 50));
                bt_Checkout.setRequestFocusEnabled(false);
                bt_Checkout.setRolloverEnabled(false);
                bt_Checkout.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        Checkout(e);
                    }
                });

                //---- Header5 ----
                Header5.setFont(new Font("Segoe UI", Font.BOLD, 18));
                Header5.setForeground(Color.white);
                Header5.setText("Transaction Details:");

                //======== TransactionScrollView ========
                {
                    TransactionScrollView.setBackground(new Color(0x161616));
                    TransactionScrollView.setBorder(null);
                    TransactionScrollView.setForeground(new Color(0x161616));
                    TransactionScrollView.setHorizontalScrollBar(null);
                    TransactionScrollView.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

                    //---- TransactListView ----
                    TransactListView.setBackground(new Color(0x161616));
                    TransactListView.setBorder(null);
                    TransactListView.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            TransactListSelect(e);
                        }
                    });
                    TransactionScrollView.setViewportView(TransactListView);
                }

                GroupLayout Panel_CheckoutLayout = new GroupLayout(Panel_Checkout);
                Panel_Checkout.setLayout(Panel_CheckoutLayout);
                Panel_CheckoutLayout.setHorizontalGroup(
                    Panel_CheckoutLayout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, Panel_CheckoutLayout.createSequentialGroup()
                            .addGap(0, 0, Short.MAX_VALUE)
                            .addComponent(bt_Checkout, GroupLayout.PREFERRED_SIZE, 296, GroupLayout.PREFERRED_SIZE))
                        .addGroup(GroupLayout.Alignment.TRAILING, Panel_CheckoutLayout.createSequentialGroup()
                            .addGap(30, 30, 30)
                            .addGroup(Panel_CheckoutLayout.createParallelGroup()
                                .addComponent(Header5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(TransactionScrollView, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE))
                            .addGap(30, 30, 30))
                );
                Panel_CheckoutLayout.setVerticalGroup(
                    Panel_CheckoutLayout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, Panel_CheckoutLayout.createSequentialGroup()
                            .addGap(28, 28, 28)
                            .addComponent(Header5)
                            .addGap(30, 30, 30)
                            .addComponent(TransactionScrollView, GroupLayout.PREFERRED_SIZE, 366, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bt_Checkout, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
                );
            }

            GroupLayout panel2Layout = new GroupLayout(panel2);
            panel2.setLayout(panel2Layout);
            panel2Layout.setHorizontalGroup(
                panel2Layout.createParallelGroup()
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addGap(0, 0, 0)
                        .addComponent(Panel_Categories, 273, 273, GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(Panel_Listing, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(25, 25, 25)
                        .addComponent(Panel_Checkout, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            );
            panel2Layout.setVerticalGroup(
                panel2Layout.createParallelGroup()
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addGap(0, 0, 0)
                        .addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(Panel_Categories, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Panel_Listing, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Panel_Checkout, GroupLayout.PREFERRED_SIZE, 546, GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
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
    private lx_DashboardList<ItemViewCtx> ListingListView;
    private JLabel Header4;
    private JButton SortName;
    private JButton SortPrice;
    private JButton SortStock;
    private JPanel Panel_Categories;
    private JLabel Header1;
    private JLabel Header2;
    private JScrollPane CategScrollView;
    private lx_CategList<CategViewCtx> CategListView;
    private JTextField CategSearchField;
    private JButton CategSearchBtn;
    private JButton AddCateg;
    private JPanel Panel_Checkout;
    private JButton bt_Checkout;
    private JLabel Header5;
    private JScrollPane TransactionScrollView;
    private lx_TransactList<BaseTransaction> TransactListView;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
