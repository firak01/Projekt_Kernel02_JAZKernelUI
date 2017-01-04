package basic.zBasicUI.component;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * Aus den Beispielen zum Buch "Swing Hacks", Beispiel Nr. 69 "Translucent Drag And Drop".
 *
 */
public final class UIHelper
{
    public static JButton createButton(String text)
    {
        JButton button = new JButton(text);
        button.setFocusPainted(true);
        button.setBorderPainted(true);
        button.setContentAreaFilled(true);
        return button;
    }

    public static JButton createButton(String text, String icon)
    {
        return createButton(text, icon, false);
    }

    public static JButton createButton(String text, String icon, boolean flat)
    {
        ImageIcon iconNormal = readImageIcon(icon + ".png");
        ImageIcon iconHighlight = readImageIcon(icon + "_highlight.png");
        ImageIcon iconPressed = readImageIcon(icon + "_pressed.png");

        JButton button = new JButton(text, iconNormal);
        button.setFocusPainted(!flat);
        button.setBorderPainted(!flat);
        button.setContentAreaFilled(!flat);
        if (iconHighlight != null) 
        {
            button.setRolloverEnabled(true);
            button.setRolloverIcon(iconHighlight);
        }
        if (iconPressed != null)
            button.setPressedIcon(iconPressed);
        return button;
    }

    /**FGL 20130624: Erweitert und Generalisiert aus Buch "Swing Hacks", Code zu Beispiel 69
     * @param text
     * @param icon
     * @return
     */
    public static JLabel createLabelWithIcon(String text, String icon)
    {
        ImageIcon iconNormal = readImageIcon(icon);
        JLabel label = new JLabel(text, iconNormal, JLabel.LEFT);
        return label;
    }

    /**FGL 20130624: Erweitert und Generalisiert aus Buch "Swing Hacks", Code zu Beispiel 69*/
    public static ImageIcon readImageIcon(String filename)
    {
    	//System.out.println("Pfad zu den Images: " + filename );
    	
    	//FGL 20130624: Original, funktioniert aber wohl nur innerhalb des gleichen Projekt / Packages
    	//System.out.println("Get Resource; " + UIHelper.class.getResource(filename));
        //return new ImageIcon(Toolkit.getDefaultToolkit().getImage(UIHelper.class.getResource(filename)));
    	return new ImageIcon(Toolkit.getDefaultToolkit().getImage(filename));
    }
}
