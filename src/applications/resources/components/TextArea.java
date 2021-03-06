/*
 |--------------------------------------------------------------------------
 | Created by Julien Vincent
 |--------------------------------------------------------------------------
 **/

package applications.resources.components;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;

public class TextArea extends JTextArea {

    public TextArea() {

        super();

        this.setOpaque(false);
        this.setEditable(false);
        this.setAutoscrolls(true);
        DefaultCaret caret = (DefaultCaret)this.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        this.setForeground(Color.DARK_GRAY);
        this.setCaretColor(Color.black);
        this.setBorder(BorderFactory.createEmptyBorder(5, 25, 3, 5));
        this.setFont(new Font("Arial", Font.PLAIN, 17));
        this.setForeground(new Color(255, 255, 255));

        this.setWrapStyleWord(true);
        this.setLineWrap(true);
        this.setWrapStyleWord(true);
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

        g2d.setPaint(new Color(86, 137, 146));
        g2d.fillRect(0, 0, getWidth(), getHeight());

        super.paintComponent(g);
        g2d.dispose();
    }
}
