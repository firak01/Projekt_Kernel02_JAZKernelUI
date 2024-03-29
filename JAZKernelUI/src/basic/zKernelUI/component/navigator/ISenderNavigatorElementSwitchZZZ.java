package basic.zKernelUI.component.navigator;

import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;


/**Dieses Interface enthaelt Methoden, die von den Klassen implementiert werden m�ssen, die den Kernel eigenen Event verwalten sollen.
 * @author lindhaueradmin
 *
 */
public interface ISenderNavigatorElementSwitchZZZ {
	public abstract void fireEvent(EventNavigatorElementSwitchZZZ event) throws ExceptionZZZ;

	public abstract void removeListenerNavigatorElementSwitch(IListenerNavigatorElementSwitchZZZ lnterfaceUser);

	public abstract void addListenerNavigatorElementSwitch(IListenerNavigatorElementSwitchZZZ lnterfaceUser);
	
	public abstract ArrayList<IListenerNavigatorElementSwitchZZZ> getListenerRegisteredAll();
}