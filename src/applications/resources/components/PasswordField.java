/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package applications.resources.components;

import javax.swing.*;
import java.awt.*;

public class PasswordField extends JPasswordField {

    String placeholder;

    public PasswordField(String placeholder) {

        super();

        this.placeholder = placeholder;

        this.setOpaque(false);
        this.setBorder(BorderFactory.createEmptyBorder(12, 20, 15, -20));
        this.setFont(new Font("Arial", Font.PLAIN, 16));
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT);
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);

        g2d.setPaint(new Color(81, 116, 146));
        g2d.fillRect(0, 0, 250, 45);

        if (getText().isEmpty()) {

            g2d.setPaint(new Color(255, 255, 255));
            g2d.setBackground(Color.gray);
            g2d.setFont(getFont().deriveFont(Font.ITALIC));
            g2d.drawString(placeholder,
                    getWidth() / 2 - g2d.getFontMetrics().stringWidth(placeholder) / 2,
                    getHeight() / 2 + g2d.getFontMetrics().getAscent() / 2 - g2d.getFontMetrics().getDescent() / 2
            );
        }

        super.paintComponent(g);
        g2d.dispose();
    }
}
