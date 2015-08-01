/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package applications.resources.components;

import javax.swing.*;
import java.awt.*;

/**
 * Custom swing JButton extension.
 */
public class Button extends JButton {

    /**
     * Set the font and text of the button.
     *
     * @param label Text to draw within the Button.
     * @param font  Font size to draw.
     */
    public Button(String label, Integer font) {

        super(label);

        setFont(new Font("Arial", Font.PLAIN, font != null ? font : 12));
        setName(label);
    }

    /**
     * @param g Graphics component passed by super JButton
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

        g2d.setPaint(new Color(47, 149, 155));
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.setFont(getFont());
        g2d.setPaint(new Color(255, 255, 255));
        g2d.drawString(getName(),
                getWidth() / 2 - g2d.getFontMetrics().stringWidth(getName()) / 2,
                getHeight() / 2 + g2d.getFontMetrics().getAscent() / 2 - g2d.getFontMetrics().getDescent() / 2
        );
        g2d.dispose();
    }
}
