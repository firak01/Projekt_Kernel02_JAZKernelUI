package basic.zKernelUI.component.navigator;

import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JLabel;

import basic.zBasic.ExceptionZZZ;
import basic.zKernelUI.component.IPanelCascadedZZZ;

public interface INavigatorElementZZZ {
	public IPanelCascadedZZZ getPanelParent();
	public abstract void setPanelParent(IPanelCascadedZZZ panelParent);
	
	public JLabel getLabel();
	public abstract void setLabel(JLabel label);
	
	public String getAlias();
	public abstract void setAlias(String sAlias);
	
	public int getPosition();
	public abstract void setPosition(int iIndex);
	
	//Damit wird das NavigatorElement clickbar gemacht
	public INavigatorElementMouseListenerZZZ createMouseListener(IPanelCascadedZZZ panelParent) throws ExceptionZZZ;
	public void addMouseListener(INavigatorElementMouseListenerZZZ listener) throws ExceptionZZZ;
	
	//TODOGOON; //Das umbennennen in setSelected(boolean bSelected)
	public void setVisible(boolean bVisible); 
}
