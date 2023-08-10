package Loot.model;

import javax.swing.*;
import java.util.ArrayList;

public class BaseTransaction {

    private BaseItem    RootItem;
    private String      ItemID;
    private int         ItemCount = 1;
    private double      TotalItemCost = 0;

    public BaseTransaction(BaseItem item){
        this.RootItem       = item;
        this.ItemID         = item.getID();
    }

    public BaseItem getBaseItem(){
        return this.RootItem;
    }
    public String   getItemID(){
        return this.RootItem.getID();
    }
    public int      getItemCount(){
        return this.ItemCount;
    }
    public double   getTotalCost(){return TotalItemCost;}
    public void     setTotalCost(double cost){this.TotalItemCost = cost;}

    public void     setBaseItem(BaseItem item){this.RootItem = item;}
    public void     setItemCount(int count){
        this.ItemCount = count;
    }

    public static DefaultListModel<BaseTransaction> toListModel(ArrayList<BaseTransaction> collection){
        DefaultListModel<BaseTransaction> model = new DefaultListModel<>();
        for (BaseTransaction item : collection){
            model.addElement(item);
        }
        return model;
    }
}
