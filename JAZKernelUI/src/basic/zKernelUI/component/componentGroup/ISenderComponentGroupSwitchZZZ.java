package basic.zKernelUI.component.componentGroup;

import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;


/**Dieses Interface enthaelt Methoden, die von den Klassen implementiert werden m�ssen, die den Kernel eigenen Event verwalten sollen.
 * @author lindhaueradmin
 *
 */
public interface ISenderComponentGroupSwitchZZZ {
	public abstract void fireEvent(EventComponentGroupSwitchZZZ event) throws ExceptionZZZ;

	public abstract void removeListenerComponentGroupSwitch(IListenerComponentGroupSwitchZZZ lnterfaceUser);

	public abstract void addListenerComponentGroupSwitch(IListenerComponentGroupSwitchZZZ lnterfaceUser);
	
	public abstract ArrayList<IListenerComponentGroupSwitchZZZ> getListenerRegisteredAll();
}