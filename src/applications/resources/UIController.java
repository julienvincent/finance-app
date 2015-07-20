/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package applications.resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class UIController extends JComponent {

    public UIController() {
        setLayout(new GridBagLayout());
    }

    /**
     * Adds a listener to automatically hide all other panes in this component
     * when one is shown.
     *
     * @param pane
     */
    public void swap(JComponent pane) {
        pane.addComponentListener(new ComponentAdapter() {

            @Override
            public void componentShown(ComponentEvent e) {

                // Check all components on this layer
                for (Component component : UIController.this.getComponents()) {

                    // Hide all components except the component set to visible
                    if (!e.getComponent().equals(component)) {
                        component.setVisible(false);
                    }
                }
            }
        });
        pane.setVisible(false);

        GridBagConstraints defaultGridBagConstraints = new GridBagConstraints();

        defaultGridBagConstraints.gridx = 0;
        defaultGridBagConstraints.gridy = 0;
        defaultGridBagConstraints.gridwidth = 1;
        defaultGridBagConstraints.gridheight = 1;
        defaultGridBagConstraints.fill = GridBagConstraints.BOTH;
        defaultGridBagConstraints.anchor = GridBagConstraints.CENTER;
        defaultGridBagConstraints.weightx = 1;
        defaultGridBagConstraints.weighty = 1;

        this.add(pane, defaultGridBagConstraints);
    }
}
