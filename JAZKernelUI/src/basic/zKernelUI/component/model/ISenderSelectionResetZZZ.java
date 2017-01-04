package basic.zKernelUI.component.model;

import java.util.ArrayList;


/**Dieses Interface enth�lt Methoden, die von den Klassen implementiert werden m�ssen, die den Kernel eigenen Event verwalten sollen.
 * @author lindhaueradmin
 *
 */
public interface ISenderSelectionResetZZZ {
	public abstract void fireEvent(EventComponentSelectionResetZZZ event);

	public abstract void removeListenerSelectionReset(IListenerSelectionResetZZZ lnterfaceUser);

	public abstract void addListenerSelectionReset(IListenerSelectionResetZZZ lnterfaceUser);
	
	public abstract ArrayList getListenerRegisteredAll();
}