package debug.zBasicUI.layoutmanager;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;

import basic.zBasicUI.layoutmanager.GridLayout4VisibleZZZ;

/**https://stackoverflow.com/questions/7727728/invisible-components-still-take-up-space-jpanel
 * @author Fritz Lindhauer, 11.11.2021, 08:52:44
 * 
 */
public class DebugGridLayout4VisibleZZZ {
	 public static void main(String[] args)
	    {
	        final JPanel innerPane = new JPanel();
	        JScrollPane scr  = new JScrollPane(innerPane);

	        innerPane.setLayout(new GridLayout4VisibleZZZ(0, 3));


	        for (int i = 0; i < 30; i++)
	        {
	            JPanel ret = new JPanel();
	            JLabel lbl = new JLabel("This is  pane " + i);

	            ret.add(lbl);
	            ret.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
	            ret.setBackground(Color.gray);

	            innerPane.add(ret);
	        }

	        JFrame frame = new JFrame();
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.add(scr);
	        frame.setBounds(400, 0, 400, 700);
	        frame.setVisible(true);

	        javax.swing.Timer timer = new javax.swing.Timer(2000, new ActionListener()
	        {
	            public void actionPerformed(ActionEvent e)
	            {
	            	int length=innerPane.getComponentCount();
	                for (int i = 0; i < length; i++)
	                {
	                    if (i%2==0)
	                        if(innerPane.getComponent(i)!=null) innerPane.getComponent(i).setVisible(false);
	                }

	            }
	        });
	        timer.setRepeats(false);
	        timer.start();

	    }
}
