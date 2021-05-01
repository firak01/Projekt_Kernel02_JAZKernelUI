package debug.zKernelUI.component.buttonSwitchLabelGroup;

import java.util.ArrayList;

import basic.zKernelUI.component.labelGroup.IListenerComponentGroupSwitchZZZ;


/**Dieses Interface enth�lt Methoden, die von den Klassen implementiert werden m�ssen, die den Kernel eigenen Event verwalten sollen.
 * @author lindhaueradmin
 *
 */
public interface ISenderComponentGroupSwitchZZZ {
	public abstract void fireEvent(EventComponentGroupSwitchZZZ event);

	public abstract void removeListenerComponentGroupSwitch(IListenerComponentGroupSwitchZZZ lnterfaceUser);

	public abstract void addListenerComponentGroupSwitch(IListenerComponentGroupSwitchZZZ lnterfaceUser);
	
	public abstract ArrayList getListenerRegisteredAll();
}