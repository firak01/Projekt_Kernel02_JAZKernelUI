package basic.zKernelUI.component;

import org.jdesktop.jdic.tray.SystemTray;
import org.jdesktop.jdic.tray.TrayIcon;
import javax.swing.JPopupMenu;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;

public interface ITrayZZZ {	
	public abstract boolean switchStatus(IEnumSetMappedZZZ objEnum)throws ExceptionZZZ;
	public abstract boolean unload() throws ExceptionZZZ;
	
	public abstract JPopupMenu getMenu() throws ExceptionZZZ;
	public abstract void setMenu(JPopupMenu menu) throws ExceptionZZZ;
	public abstract JPopupMenu createMenuCustom() throws ExceptionZZZ;
	
	public abstract SystemTray getSystemTray() throws ExceptionZZZ;
	public abstract void setSystemTray(SystemTray tray);
	
	public abstract TrayIcon getTrayIcon() throws ExceptionZZZ;
	public abstract void setTrayIcon(TrayIcon objIcon);
	
	public abstract IActionCascadedZZZ getActionListenerTrayIcon() throws ExceptionZZZ;
	public abstract void setActionListenerTrayIcon(IActionCascadedZZZ objActionListener);
}
