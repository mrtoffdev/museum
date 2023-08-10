package Loot.util.crud;
/*  Author: YNA (BaseItem Operations), TOFF (BaseTransact & BaseCateg Operations)
    DOCS ==================================

    [X] Func: <FnName:ReturnT>

    [X] Notes:
        - Has to be able to:
		1.  Search the internal collection of BaseItems by Name
		    (Will be integrated to the search tab in Dashboard)
		2.  Search the internal collection of BaseItems by CategID
		    (Will be integrated to the category list in Dashboard)

	    - Preferred features/methods:
		1.  ArrayList<BaseItem> util_fetchByName(ArrayList<BaseItem> InventoryList, String ItemName)
		    Takes in a String search query & the current inventory list and
		    returns an arraylist of BaseItems that match the search query
			(Can have multiple instances/results)
		2.  ArrayList<BaseItem> util_fetchByCategID(ArrayList<BaseItem> InventoryList, int ItemCategID)
		    Takes in an int ItemCategID and returns an ArrayList of BaseItems that
			match the searched ItemCategID
*/

import Loot.model.BaseCateg;
import Loot.model.BaseItem;
import Loot.model.BaseTransaction;

import java.util.ArrayList;

public class Search {
    // BaseItem Search Operations ================================
    public static ArrayList<BaseItem>   util_fetchByName(ArrayList<BaseItem> InventoryList, String ItemName) {
        final ArrayList<BaseItem> itemMatchedList = new ArrayList<>();
        System.out.println("\nItemName \"" + ItemName + "\" matches: ");
        for (BaseItem baseItem : InventoryList) {
            if (baseItem.getName().toUpperCase().contains(ItemName.toUpperCase())) {
                System.out.println("\t" + baseItem.getName() + "\t" + baseItem.getID());
                itemMatchedList.add(baseItem);
            }
        }
        return itemMatchedList;
    }
    public static ArrayList<BaseItem>   util_fetchByCategID(ArrayList<BaseItem> InventoryList, String ItemCategID) {
        final ArrayList<BaseItem> itemMatchedList = new ArrayList<>();
        System.out.println("\nItemCategId \"" + ItemCategID + "\" matches: ");
        for (BaseItem baseItem : InventoryList) {
            if (baseItem.getCategID().equals(ItemCategID)) {
                System.out.println("\t" + baseItem.getName() + "\t" + baseItem.getID());
                itemMatchedList.add(baseItem);
            }
        }
        return itemMatchedList;
    }
    public static BaseItem              util_fetchByID(ArrayList<BaseItem> InventoryList, String ItemID) {
        System.out.println("\nItemCategId \"" + ItemID + "\" matches: ");
        for (BaseItem baseItem : InventoryList) {
            if (baseItem.getID().equals(ItemID)) {
                System.out.println("\t" + baseItem.getID() + "\t" + baseItem.getID());
                return baseItem;
            }
        }
        return null;
    }

    // BaseTransact Search Operations ============================
    public static BaseTransaction       FetchTransactByID(ArrayList<BaseTransaction> TransactionList, String ItemID){
        System.out.println("\nTransactCategId \"" + ItemID + "\" matches: ");
        for (BaseTransaction Transaction : TransactionList) {
            if (Transaction.getItemID().equals(ItemID)) {
                System.out.println("\t" + Transaction.getItemID() + "\t" + ItemID);
                return Transaction;
            }
        }
        return null;
    }

    // BaseCateg Search Operations ===============================
    public static String    FetchCategName(ArrayList<BaseCateg> CategList, String CategID){
        for (BaseCateg Categ : CategList){
            if (Categ.getCategID().equals(CategID)){
                return Categ.getCategName();
            }
        }
        return null;
    }

}
