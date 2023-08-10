package Loot.model;

import java.util.ArrayList;
import Loot.util.crud.Search;

/*  Flow of Operations:
    Parse Transaction:
        Searches items in BaseTransacts from BaseItems.
        Subtracts count in BaseTransacts from BaseItems,
        After subtract, BaseTransacts is cleared

    Edit BaseItem:
        Searches query in BaseItems,
        If found, saves index
        Removes item from index, replaces with modified item in same index
*/

public class GlobalContext {
    private ArrayList<BaseCateg>            db_BaseCategs;
    private ArrayList<BaseItem>             db_BaseItems;
    private ArrayList<BaseTransaction>      db_BaseTransacts;

    public GlobalContext(ArrayList<BaseCateg> in_BaseCategs,
                         ArrayList<BaseItem> in_BaseItems,
                         ArrayList<BaseTransaction> in_BaseTransacts){

        this.db_BaseItems       = in_BaseItems;
        this.db_BaseCategs      = in_BaseCategs;
        this.db_BaseTransacts   = in_BaseTransacts;
    }

    // Getters & Setters ==============================================

    public void setItemCollection(ArrayList<BaseItem> in_BaseItems){
        this.db_BaseItems       = in_BaseItems;
    }
    public void setCategCollection(ArrayList<BaseCateg> in_BaseItems){
        this.db_BaseCategs      = in_BaseItems;
    }
    public void setTransactCollection(ArrayList<BaseTransaction> in_BaseItems){
        this.db_BaseTransacts   = in_BaseItems;
    }

    public ArrayList<BaseItem> getItems(){
        return this.db_BaseItems;
    }
    public ArrayList<BaseCateg> getCategs(){
        return this.db_BaseCategs;
    }
    public ArrayList<BaseTransaction> getTransacts(){
        return this.db_BaseTransacts;
    }

    // Operations =====================================================

    public void parseTransaction(){

    }

    // BaseItem Operations ============================================

    public void addItem(BaseItem in_item){
        this.db_BaseItems.add(in_item);
    }
    public void editItemName(String in_ItemID, String in_ItemName){
        int temp_index  = this.db_BaseItems.indexOf(Search.util_fetchByID(this.db_BaseItems, in_ItemID));
        BaseItem temp   = this.db_BaseItems.get(temp_index);

        this.db_BaseItems.remove(temp_index);
        temp.setName(in_ItemName);
        this.db_BaseItems.add(temp_index, temp);
    }
    public void editItemStock(String in_ItemID, int in_ItemStock){
        int temp_index  = this.db_BaseItems.indexOf(Search.util_fetchByID(this.db_BaseItems, in_ItemID));
        BaseItem temp   = this.db_BaseItems.get(temp_index);

        this.db_BaseItems.remove(temp_index);
        temp.setStock(in_ItemStock);
        this.db_BaseItems.add(temp_index, temp);
    }
    public void mathItemStock(String in_ItemID, String in_Operation, int in_Counter){
        int temp_index  = this.db_BaseItems.indexOf(Search.util_fetchByID(this.db_BaseItems, in_ItemID));
        BaseItem temp   = this.db_BaseItems.get(temp_index);
        int oldStock    = temp.getStock();


        this.db_BaseItems.remove(temp_index);
        switch (in_Operation){
            case "ADD":
                temp.setStock((oldStock + in_Counter));
                break;
            case "SUBT":
                temp.setStock((oldStock - in_Counter));
                break;
            default:
                System.out.println("Err mathItemStock() : Undefined Case");
                break;
        }
        this.db_BaseItems.add(temp_index, temp);
    }
    public int  generateNewID(){
        int size    = this.db_BaseItems.size();
        int lastID  = 0;
        if (size != 0){
            for (BaseItem item : this.db_BaseItems){
                if (Integer.parseInt(item.getID()) > lastID) {
                    lastID = Integer.parseInt(item.getID());
                }
            }
        }
        return lastID + 1;
    }

    // BaseCateg Operations ===========================================

    public void addCateg(BaseCateg in_categ){
        this.db_BaseCategs.add(in_categ);
    }
    public void removeCateg(BaseCateg in_categ){
        this.db_BaseCategs.remove(in_categ);
    }
    public int generateNewCategID(){
        int size = this.db_BaseCategs.size();
        int lastID = 0;
        if (size > 0){
            lastID = Integer.parseInt(this.db_BaseCategs.get(size - 1).getCategID());
            return lastID + 1;
        }
        return lastID;
    }
    public void editCategName(){

    }

    // BaseTransaction Operations =====================================

    public void addTransaction(BaseTransaction in_item){
        this.db_BaseTransacts.add(in_item);
    }
    public void editTransaction(BaseTransaction in_item) {

    }
    public void removeTransaction(){

    }
    public void clearTransactions(){
        this.db_BaseTransacts.clear();
    }

}
