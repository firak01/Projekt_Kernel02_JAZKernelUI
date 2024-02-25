package basic.zKernelUI.component.tray;

import org.jdesktop.jdic.tray.SystemTray;
import org.jdesktop.jdic.tray.TrayIcon;

import javax.swing.ImageIcon;
import javax.swing.JPopupMenu;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;
import basic.zKernel.flag.IListenerObjectFlagZsetZZZ;
import basic.zKernel.status.IStatusLocalMapForStatusLocalUserZZZ;

//Der Tray soll also sowhol selbst Flags benutzen, als auch auf die Flag-Aenderung anderer Objekte reagieren koennen.
//Ausserdem soll er einkommende StatusÄaenderungen empfangen und darauf reagieren. Daher die StatusHashMap.
public interface ITrayZZZ extends IFlagZUserZZZ, IListenerObjectFlagZsetZZZ, IStatusLocalMapForStatusLocalUserZZZ {	
	public abstract boolean switchStatus(IEnumSetMappedZZZ objEnum)throws ExceptionZZZ;
	public abstract boolean unload() throws ExceptionZZZ;
	
	public abstract JPopupMenu getMenu() throws ExceptionZZZ;
	public abstract void setMenu(JPopupMenu menu) throws ExceptionZZZ;
	public abstract JPopupMenu createMenuCustom() throws ExceptionZZZ;
	
	public abstract SystemTray getSystemTray() throws ExceptionZZZ;
	public abstract void setSystemTray(SystemTray tray);
	
	public abstract ImageIcon getImageIconByStatus(IEnumSetMappedZZZ enumStatusMapped) throws ExceptionZZZ;
	public abstract String getCaptionByStatus(IEnumSetMappedZZZ enumStatusMapped) throws ExceptionZZZ;

	public abstract TrayIcon getTrayIcon() throws ExceptionZZZ;
	public abstract void setTrayIcon(TrayIcon objIcon);
		
	public abstract IActionTrayZZZ getActionListenerTrayIcon() throws ExceptionZZZ;
	public abstract void setActionListenerTrayIcon(IActionTrayZZZ objActionListener);
}
