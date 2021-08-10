package basic.zKernelUI.component.navigator;

import javax.swing.JLabel;

public interface INavigatorElementZZZ {
	public JLabel getLabel();
	public abstract void setLabel(JLabel label);
	public String getAlias();
	public abstract void setAlias(String sAlias);
	public int getPosition();
	public abstract void setPosition(int iIndex);
}
