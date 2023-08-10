package Loot.model;

public class CategViewCtx {

    private String  CategName;
    private int     CategItems;

    private BaseCateg SrcCateg;

    public CategViewCtx(BaseCateg in_BaseCateg, int ItemCount){
        this.CategName  = in_BaseCateg.getCategName();
        this.CategItems = ItemCount;
        this.SrcCateg   = in_BaseCateg;
    }

    public String getCategName(){
        return this.CategName;
    }
    public void setCategName(String CategName){
        this.CategName = CategName;
    }
    public int getCategItems(){
        return this.CategItems;
    }
    public void setCategItems(int Items){
        this.CategItems = Items;
    }
    public BaseCateg returnBaseCateg(){
        return this.SrcCateg;
    }
}
