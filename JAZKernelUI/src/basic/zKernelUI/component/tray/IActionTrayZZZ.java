package basic.zKernelUI.component.tray;

import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.JFrame;

import basic.zKernel.IKernelUserZZZ;
import basic.zKernelUI.component.IActionZZZ;
import basic.zKernelUI.component.IButtonEventZZZ;

public interface IActionTrayZZZ extends IActionZZZ, IButtonEventZZZ, ActionListener{	
	public abstract ITrayZZZ getTrayParent();
	public abstract void setTrayParent(ITrayZZZ objTray);
}