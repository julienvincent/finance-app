/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package applications.resources.components;

import javax.swing.*;
import java.awt.*;

public class ScrollPane extends JScrollPane {

    public ScrollPane(Component component) {

        super(component, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        this.setOpaque(false);
        this.setAutoscrolls(true);
        this.getViewport().setOpaque(false);
        this.setBorder(BorderFactory.createEmptyBorder());
        this.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
        this.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        this.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));
    }
}
