package basic.zKernelUI.component.componentGroup;

import java.util.ArrayList;

import basic.zKernel.IKernelZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zKernel.KernelUseObjectZZZ;

/** Diese Klasse implementiert alles, was ben�tigt wird, damit die eigenen Events "Auswahl hat sich ge�ndert" abgefeuert werden kann
 *   und auch von den Componenten, die hier registriert sind empfangen wird. Damit fungieren Objekte dieser Klasse als "EventBroker".
 *   
 *   Wichtig: Diese Klasse darf nicht final sein, damit sie von anderen Klassen geerbt werden kann.
 *               Die Methoden dieser Klasse sind allerdings final.
 * @author lindhaueradmin
 *
 */
public class KernelSenderComponentGroupSwitchZZZ extends KernelUseObjectZZZ implements ISenderComponentGroupSwitchZZZ{
	public KernelSenderComponentGroupSwitchZZZ(IKernelZZZ objKernel) throws ExceptionZZZ{
		super(objKernel);
	}
	
	/* (non-Javadoc)
	 * @see use.via.client.module.export.ISenderEventComponentReset#fireEvent(basic.zKernelUI.component.model.KernelEventComponentSelectionResetZZZ)
	 */
	private ArrayList<IListenerComponentGroupSwitchZZZ> listaLISTENER_REGISTERED = new ArrayList<IListenerComponentGroupSwitchZZZ>();  //Das ist die Arrayliste, in welche  die registrierten Komponenten eingetragen werden 
																							  //wichtig: Sie muss private sein und kann nicht im Interace global definiert werden, weil es sonst nicht m�glich ist 
	                                                                                          //             mehrere Events, an verschiedenen Komponenten, unabh�ngig voneinander zu verwalten.
	
	@Override
	public void addListenerComponentGroupSwitch(IListenerComponentGroupSwitchZZZ lnterfaceUser) {
		this.getListenerRegisteredAll().add(lnterfaceUser);
	}	
	
	/* (non-Javadoc)
	 * @see use.via.client.module.export.ISenderEventComponentReset#removeSelectionResetListener(basic.zKernelUI.component.model.ISelectionResetListener)
	 */
	public final void removeListenerComponentGroupSwitch(IListenerComponentGroupSwitchZZZ l){
		this.getListenerRegisteredAll().remove(l);
	}
	

	
	public final ArrayList getListenerRegisteredAll(){
		return listaLISTENER_REGISTERED;
	}

	@Override
	public void fireEvent(EventComponentGroupSwitchZZZ event) {
		if(event.getSource() instanceof ISenderComponentGroupSwitchZZZ){
			ISenderComponentGroupSwitchZZZ sender = (ISenderComponentGroupSwitchZZZ) event.getSource();
			for(int i = 0 ; i < sender.getListenerRegisteredAll().size(); i++){
				IListenerComponentGroupSwitchZZZ l = (IListenerComponentGroupSwitchZZZ) sender.getListenerRegisteredAll().get(i);
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "# EventComponentSelectionResetZZZ by " + event.getSource().getClass().getName() + " fired: " + i);
				l.doSwitch(event);
			}
		}else{
			for(int i = 0 ; i < this.getListenerRegisteredAll().size(); i++){
				IListenerComponentGroupSwitchZZZ l = (IListenerComponentGroupSwitchZZZ) this.getListenerRegisteredAll().get(i);	
				
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "# EventComponentSwitchZZZ by " + this.getClass().getName() + " - object (d.h. this - object) fired: " + i);
				l.doSwitch(event);			
			}
		}
	}
}
