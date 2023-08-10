package Loot.model;

import javax.swing.ImageIcon;

public class ItemViewCtx {

    private ImageIcon   ItemIcon;
    private String      ItemName;
    private String      ItemID;
    private double      ItemPrice;
    private int         ItemStock;
    private boolean     IsSelected;
    private String      CategoryName;

    private BaseItem    CtxBaseItem;

    public ItemViewCtx(BaseItem in_BaseItem){
        this.CtxBaseItem= in_BaseItem;

        this.ItemIcon   = in_BaseItem.getIcon();
        this.ItemID     = in_BaseItem.getID();
        this.ItemName   = in_BaseItem.getName();
        this.ItemStock  = in_BaseItem.getStock();
        this.ItemPrice  = in_BaseItem.getPrice();
    }

    // Gettrs & Setters
    public String getCategoryName(){
        return this.CategoryName;
    }

    public void setCategoryName(String CategName){
        this.CategoryName = CategName;
    }

    public int getItemStock() {
        return ItemStock;
    }
    public void setItemStock(int itemStock) {
        ItemStock = itemStock;
    }

    public double getItemPrice() {
        return ItemPrice;
    }
    public void setItemPrice(double itemPrice) {
        ItemPrice = itemPrice;
    }

    public String getItemName() {
        return ItemName;
    }
    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public ImageIcon getItemIcon() {
        return ItemIcon;
    }
    public void setItemIcon(ImageIcon itemIcon) {
        ItemIcon = itemIcon;
    }

    public boolean isSelected() {
        return IsSelected;
    }
    public void setSelected(boolean selected) {
        IsSelected = selected;
    }

    public String getItemID() {
        return ItemID;
    }
    public void setItemID(String itemID) {
        ItemID = itemID;
    }

    public BaseItem returnBaseItem(){
        return CtxBaseItem;
    }
}
