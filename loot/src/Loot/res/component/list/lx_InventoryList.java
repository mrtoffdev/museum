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

import Loot.model.ItemViewCtx;
import Loot.res.component.view.atom.InventItemView;

import java.awt.Color;
import java.awt.Component;
import javax.swing.*;

public class lx_InventoryList<T> extends JList<T> {

    private final DefaultListModel<T> model = new DefaultListModel<>();

    // Constructor
    public lx_InventoryList(){
        // Component Properties
        setBackground(Color.black);
//        setFixedCellHeight(40);
        setModel(model);
    }

    // Util
    public void util_addItem(ItemViewCtx in_BaseItem){
        ((DefaultListModel<ItemViewCtx>)this.getModel()).addElement(in_BaseItem);
    }

    public void util_removeItem(int in_index){
        ((DefaultListModel<ItemViewCtx>) this.getModel()).remove(in_index);
    }

    public void util_removeItem(Object in_ref){
        model.removeElement(in_ref);
    }

    public void refreshTable(){
        ((DefaultListModel<ItemViewCtx>) this.getModel()).removeAllElements();
    }

    @Override
    public ListCellRenderer<? super T> getCellRenderer() {
        return new DefaultListCellRenderer(){
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object val, int index, boolean isSelected, boolean cellHasFocus) {
                InventItemView item = new InventItemView();
                item.fill((ItemViewCtx) val);
                return item;
            }
        };
    }
}
