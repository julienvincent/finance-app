/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package applications.resources.components;

import javax.swing.*;
import java.awt.*;

/**
 * Custom swing JLabel.
 */
public class Label extends JLabel {

    /**
     * Set the font and text of the label.
     *
     * @param label Text to write within the label
     */
    public Label(String label) {

        super(label);

        setFont(new Font("Arial", Font.PLAIN, 15));
        setName(label);
        setForeground(new Color(255, 255, 255));
        repaint();
    }

    /**
     * Draw a custom swing JLabel
     *
     * @param g Graphics components passed by super.
     */
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT);
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);

        g2d.setFont(getFont());
        g2d.setPaint(new Color(255, 255, 255));
        g2d.drawString(getName(), getWidth(), getHeight());
        g2d.dispose();
    }
}
