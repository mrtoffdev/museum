package Loot.model;
//  Author: TOFF
//  DOCS ==================================
/*
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

    getStock():Int
    setStock(Int):Void

    getCategID():Int
    setCategID(Int):Void

    getPrice():Double
    setPrice(Double):Void

    DOCS ==================================
*/

import javax.swing.*;
import java.util.ArrayList;

public class BaseItem {

    private String      ItemName;
    private double      ItemPrice;
    private int         ItemStock;
    private String      ItemID;
    private String      ItemCategID;
    private ImageIcon   ItemIcon;

    public BaseItem(String in_ItemName, double in_ItemPrice, int in_ItemStock, String in_ItemID,  String in_CategID, String in_ItemSplashURL){
        this.ItemName   = in_ItemName;
        this.ItemPrice  = in_ItemPrice;
        this.ItemStock  = in_ItemStock;
        this.ItemID     = in_ItemID;
        this.ItemCategID= in_CategID;
    }

    // Getters & Setters
    public String getName(){
        return ItemName;
    }
    public void setName(String in_ItemName){
        this.ItemName = in_ItemName;
    }
    
    public String getID(){
        return this.ItemID;
    }
    public void setID(String in_ItemID){
        this.ItemID = in_ItemID;
    }
    
    public ImageIcon getIcon(){
        return this.ItemIcon;
    }
    public void setIcon(ImageIcon in_ItemIcon){
        this.ItemIcon = in_ItemIcon;
    }
    
    public int getStock(){
        return this.ItemStock;
    }
    public void setStock(int in_ItemStock){
        this.ItemStock = in_ItemStock;
    }
    
    public String getCategID(){
        return this.ItemCategID;
    }
    public void setCategID(String in_ItemCategID){
        this.ItemCategID = in_ItemCategID;
    }
    
    public double getPrice(){
        return this.ItemPrice;
    }
    public void setPrice(double in_ItemPrice){
        this.ItemPrice = in_ItemPrice;
    }

    // Utils
    public static DefaultListModel<ItemViewCtx> toListModel(ArrayList<BaseItem> collection){
        DefaultListModel<ItemViewCtx> model = new DefaultListModel<>();
        for (BaseItem item : collection){
            ItemViewCtx itemctx = new ItemViewCtx(item);
            model.addElement(itemctx);
        }
        return model;
    }
    public static BaseTransaction toTransaction(BaseItem item){
        return new BaseTransaction(item);

    }
}
