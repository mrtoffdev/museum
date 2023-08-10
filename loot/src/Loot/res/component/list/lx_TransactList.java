package Loot.res.component.list;
/*  Author: TOFF
    DOCS ==================================

    [X] Props:
    iv_ItemName<JLabel>
    iv_ItemPrice<JLabel>
    iv_ItemStock<JLabel>
    iv_ItemSplash<JLabel->Icon/Img>

    [X] Func: <FnName:ReturnT>

    fill(Object):Void   // Takes generic Object; BaseItem instance recommended
*/

import Loot.model.BaseItem;
import Loot.model.BaseTransaction;
import Loot.model.CategViewCtx;
import Loot.model.ItemViewCtx;
import Loot.res.component.view.atom.TransactItemView;

import java.awt.Color;
import java.awt.Component;
import javax.swing.*;

public class lx_TransactList<T> extends JList<T> {

    private final DefaultListModel<T> model = new DefaultListModel<>();

    // Constructor
    public lx_TransactList(){
        // Component Properties
        setBackground(Color.black);
        setFixedCellHeight(90);
        setModel(model);
    }

    // Util
    public void util_addItem(BaseItem in_BaseItem){
        ((DefaultListModel<BaseItem>)this.getModel()).addElement(in_BaseItem);
    }

    public void util_removeItem(int in_index){
        ((DefaultListModel<BaseItem>) this.getModel()).remove(in_index);
    }

    public void util_removeItem(Object in_ref){
        model.removeElement(in_ref);
    }

    public void refreshTable(){
        ((DefaultListModel<CategViewCtx>) this.getModel()).removeAllElements();
    }

    @Override
    public ListCellRenderer<? super T> getCellRenderer() {
        return new DefaultListCellRenderer(){
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object val, int index, boolean isSelected, boolean cellHasFocus) {
                TransactItemView item    = new TransactItemView();
                item.fill((BaseTransaction) val);
//                item.fill(val);
                return item;
            }
        };
    }
}
