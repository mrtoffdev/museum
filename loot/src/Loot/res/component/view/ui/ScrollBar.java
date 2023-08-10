package Loot.res.component.view.ui;


import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JScrollBar;

public class ScrollBar extends JScrollBar {

    public ScrollBar() {
        setUI(new ScrollBarUI());
        setPreferredSize(new Dimension(10, 12));
        setForeground(new Color(255, 255, 255));
        setBackground(new Color(0, 0, 11));
    }
}
