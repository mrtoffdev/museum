package Loot.model;

import javax.swing.DefaultListModel;
import java.util.ArrayList;

public class BaseCateg {

    private String  CategID;
    private String  CategName;

    public BaseCateg(String in_CategName, String in_CategID){
        this.CategID    = in_CategID;
        this.CategName  = in_CategName;
    }

//    public static DefaultListModel<CategViewCtx> toListModel(ArrayList<BaseCateg> collection){
//        DefaultListModel<CategViewCtx> model = new DefaultListModel<>();
//        for (BaseCateg item : collection){
//            CategViewCtx CategCtx = new CategViewCtx(item);
//            model.addElement(CategCtx);
//        }
//        return model;
//    }

    // Getters & Setters
    public String getCategName() {
        return CategName;
    }
    public void setCategName(String categName) {
        CategName = categName;
    }

    public String getCategID() {
        return CategID;
    }
    public void setCategID(String categID) {
        CategID = categID;
    }
}
