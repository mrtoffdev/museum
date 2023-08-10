package Loot.util.controller;
/*  Author: TOFF
    DOCS ==================================

    [X] Func: <FnName:ReturnT> ------------

    [X] Notes: ----------------------------

    GraphicsEnvironment gui_env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    gui_env.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(getClass().getResource("../font/Inter-Bold.ttf").toURI())));
    Std panel size: 865x546
*/
import Loot.model.*;
import Loot.res.draw.Dashboard;
import Loot.res.draw.LoginFrame;
import Loot.util.crud.Parser;
import Loot.util.crud.Search;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import static Loot.Main.WIN_HEIGHT;
import static Loot.Main.WIN_WIDTH;

public class EventList {
    // Debug Env
    private static final int DEBUG_MODE = 0;

    private Dashboard       MainDashboard;
    private GlobalContext   Database;
    private String          FILE_URL;

    public void Init() throws IOException, URISyntaxException, FontFormatException {

        if (DEBUG_MODE == 1){
            // Generate Random Items
            System.out.print("Call : EventList.Init()");
            ArrayList<BaseCateg>    Categs      = EventList.debug_InitCategArr(10);
            ArrayList<BaseItem>     Items       = EventList.debug_InitListingArr(10);
        }

        // Login Frame
        LoginFrame loginpanel = new LoginFrame();
        loginpanel.setRootEventList(this);

        JFrame frame = new JFrame("LOOT: Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().add(loginpanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public void StartLootRuntime(String FileURL) throws IOException, URISyntaxException, FontFormatException {
        ArrayList<BaseCateg>    Categs;
        ArrayList<BaseItem>     Items;
        // Global Env
        try {
            Categs = Parser.parseCategs(FileURL);
        } catch (NoSuchElementException ex){
            Categs = new ArrayList<>();
        }

        try {
            Items = Parser.parseFile(FileURL);
        } catch (NoSuchElementException ex){
            Items = new ArrayList<>();
        }

        ArrayList<BaseTransaction>  Transacts   = new ArrayList<>();    // Empty Arraylist
        GlobalContext               Database    = new GlobalContext(Categs, Items, Transacts);

        // Window Settings
        MainDashboard = new Dashboard(Database);    // Set GlobalContext
        MainDashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainDashboard.setSize(WIN_WIDTH,WIN_HEIGHT);
        MainDashboard.setLocationRelativeTo(null);
        MainDashboard.setTitle("LOOT: Inventory System");

        // Init GUI
        MainDashboard.setRootEventList(this);
        MainDashboard.setVisible(true);
    }

    // Utils =====================================================
    public void CheckoutCall(){
        MainDashboard.getDatabase().clearTransactions();
        MainDashboard.updatePanels();
    }
    public void GlobalReload(){
        this.MainDashboard.updatePanels();
    }

    // Modals & External Panels Callbacks ========================
    public void AddItemModal(BaseTransaction Item, int Count){
        // Refresh Database
        Database = MainDashboard.getDatabase();

        // References
        ArrayList<BaseTransaction>  Transactions    = Database.getTransacts();
        BaseTransaction             FindDupe        = Search.FetchTransactByID(Transactions, Item.getItemID());
        BaseItem                    Original        = Search.util_fetchByID(Database.getItems(),Item.getItemID());

        if (FindDupe != null){
            int TransactIndex   = Transactions.indexOf(FindDupe);
            Transactions.get(TransactIndex).setItemCount((FindDupe.getItemCount()) + Count);
            Transactions.get(TransactIndex).setTotalCost(FindDupe.getTotalCost() + (FindDupe.getBaseItem().getPrice() * Count));
        } else {
            Item.setItemCount(Count);
            Item.setTotalCost(Item.getBaseItem().getPrice() * Count);
            Database.addTransaction(Item);
        }

        int OriginalStock   = Original.getStock();
        Original.setStock(OriginalStock - Count);
        MainDashboard.updatePanels();
    }
    public void EditTransactionModal(BaseTransaction Item, String SrcItemID){
        // Refresh Database
        Database = MainDashboard.getDatabase();

        /*
        *   Analysis:
        *   Checks received BaseTransaction if transaction is Edit or Delete
        *
        *   Criteria:
        *   If BaseTransaction has 0 ItemCount; consider as Delete()
        *   If BaseTransaction > 0; consider as Edit()
        *
        *   Delete:
        *   Searches for a Transaction with similar ItemID in TransactionList
        *   If Transaction is found in TransactionList, remove Transaction
        *   If Transaction is not found, invalid path.
        *
        *   Edit:
        *   Searches for a Transaction with similar ItemID in TransactionList
        *   Edits / Updates the values stored in the Transaction in Transactionlist..
        *   ... with received data from received Transaction
        *   Searches for a BaseItem with similar ItemID in received Transaction
        *   Edits the GlobalContext / Database BaseItem's stock count (Formula Below:)
        *
        *   Formula:
        *   (Received)TransactionItem.getItemCount + (Original)BaseItem.getStock = OriginalStock
        *   (Original)BaseItem.setStock = OriginalStock - (Received)TransactionItem.getItemCount
        */

        ArrayList<BaseTransaction> OriginalTransactionList = this.Database.getTransacts();
        int TransactItemCount   = Item.getItemCount();

        // Fetch Contexts
        BaseTransaction OrigTransaction     = Search.FetchTransactByID(OriginalTransactionList, Item.getItemID());
        final int OldItemCount              = OrigTransaction.getItemCount();
        int OrigTransactionIndex        = this.Database.getTransacts().indexOf(OrigTransaction);

        BaseItem OrigItem               = Search.util_fetchByID(this.Database.getItems(), Item.getItemID());
        int OrigItemIndex               = this.Database.getItems().indexOf(OrigItem);

        int CurrentStock                = OrigItem.getStock();
        int OrigStock                   = Item.getBaseItem().getStock() + OldItemCount;
        int NewStock                    = OrigStock - TransactItemCount;

        if (TransactItemCount > 0){
            // Edit Transaction
            System.out.println("[EditTransactionModel] Debug : Calling Search for BaseTransact");

            // Process TransactionList Changes
            this.Database.getTransacts().get(OrigTransactionIndex).setItemCount(TransactItemCount);

            // Process ItemlistingList Changes
            this.Database.getItems().get(OrigItemIndex).setStock(NewStock);

        } else {
            // Delete Transaction
            this.Database.getTransacts().remove(OrigTransactionIndex);
            this.Database.getItems().get(OrigItemIndex).setStock(OrigStock);
        }

        MainDashboard.updatePanels();
    }
    public String GetFileURL(){
        return this.FILE_URL;
    }
    public void SetFileURL(String FILE_URL){
        this.FILE_URL   = FILE_URL;
    }

    // Debug Utils ===============================================
    private static ArrayList<BaseCateg> debug_InitCategArr(int in_ItemCount){
        ArrayList<BaseCateg> arr        = new ArrayList<>();
        for (int i = 0; i < in_ItemCount; i++){
            arr.add(new BaseCateg("Demo Categ " + (i+1), "" + (20*i)));
        }
        return arr;
    }
    private static ArrayList<BaseItem> debug_InitListingArr(int in_ItemCount){
        ArrayList<BaseItem> arr         = new ArrayList<>();
        for (int i = 0; i < in_ItemCount; i++){
            double idgen    = Math.floor(Math.random()*(50+1)+0);
            double pricegen = Math.floor(Math.random()*(5000-1+1)+1);
            arr.add(new BaseItem("Demo Item " + (i+1),
                                pricegen, (int) idgen,
                        "" + (i+1)*10,
                        "" + (i*7),
                        ""));
        }
        return arr;
    }
    private static DefaultListModel<?> debug_InitListingArr(String in_Identifier, int in_size){
        DefaultListModel<ItemViewCtx> model = new DefaultListModel<>();
        BaseItem sub_temp                   = new BaseItem(in_Identifier, 00.00, 15, "", "", "");
        ItemViewCtx temp                    = new ItemViewCtx(sub_temp);

        for (int i = 0; i < in_size; i++){
            model.addElement(temp);
        }

        return model;
    }

    private static void debug_InitCategArr(String in_Identifier, int in_size){
        DefaultListModel<ItemViewCtx> model = new DefaultListModel<>();
        BaseItem sub_temp                   = new BaseItem(in_Identifier, 00.00, 15, "", "", "");
        ItemViewCtx temp                    = new ItemViewCtx(sub_temp);

        for (int i = 0; i < in_size; i++){
            model.addElement(temp);
        }

    }
}
