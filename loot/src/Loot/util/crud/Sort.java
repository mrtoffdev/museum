package Loot.util.crud;
/*  Author: YNA
    DOCS ==================================

    [X] Func: <FnName:ReturnT>
        + Pass By Value Methods:
        util_sortByName(ArrayList<BaseItem>):ArrayList<BaseItem>

        + Pass By Reference Methods:
        util_sortByPrice(ArrayList<BaseItem>):void
        util_sortByStock(ArrayList<BaseItem>):void
        util_sortReverse(ArrayList<BaseItem>):void

    [X] Notes:
        - Has to be able to:
		1. Sort the internal collection of BaseItems by Name
		    (Will be integrated to the search tab in Dashboard)
		2. Sort the internal collection of BaseItems by Price
		    (Will be integrated to the category list in Dashboard)
        3. Sort the internal collection of BaseItems by Stock
            (Will be integrated to the category list in Dashboard)

	   - Preferred features/methods:
		1. ArrayList<BaseItem> util_fetchByName(ArrayList<BaseItem> InventoryList, String ItemName)
		    Takes in a String search query & the current inventory list and
			returns an arraylist of BaseItems that match the search query
			(Can have multiple instances/results)
		2. ArrayList<BaseItem> util_fetchByCategID(ArrayList<BaseItem> InventoryList, int ItemCategID)
		    Takes in an int ItemCategID and returns an ArrayList of BaseItems that
			match the searched ItemCategID

*/
import java.util.Collections;
import java.util.Comparator;
import Loot.model.BaseItem;
import java.util.ArrayList;

public class Sort {

    public static ArrayList<BaseItem> util_sortByName(ArrayList<BaseItem> InventoryList) {
        System.out.println("\nSorted according to Name: ");
        InventoryList.sort(new Comparator<BaseItem>() {
            @Override
            public int compare(BaseItem item1, BaseItem item2) {
                return item1.getName().compareTo(item2.getName());
            }
        });
        return InventoryList;
    }

    public static void util_sortByPrice(ArrayList<BaseItem> InventoryList) {
        System.out.println("\nSorted according to Price: ");
        InventoryList.sort(new Comparator<BaseItem>() {
            @Override
            public int compare(BaseItem item1, BaseItem item2) {
                return Double.compare(item1.getPrice(), item2.getPrice());
            }
        });
    }
    public static void util_sortByStock(ArrayList<BaseItem> InventoryList) {
        System.out.println("\nSorted according to Stock: ");
        InventoryList.sort(new Comparator<BaseItem>() {
            @Override
            public int compare(BaseItem item1, BaseItem item2) {
                return Integer.compare(item1.getStock(), item2.getStock());
            }
        });
    }
    public static void util_sortReverse(ArrayList<BaseItem> InventoryList) {
        System.out.println("\nReversed: ");
        Collections.reverse(InventoryList);
    }
}