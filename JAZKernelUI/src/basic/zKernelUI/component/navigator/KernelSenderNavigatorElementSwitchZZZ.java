package basic.zKernelUI.component.navigator;

import java.util.ArrayList;

import basic.zKernel.IKernelZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zKernel.KernelUseObjectZZZ;

/** Diese Klasse implementiert alles, was ben�tigt wird, damit die eigenen Events "Auswahl hat sich geaendert" abgefeuert werden kann
 *   und auch von den Componenten, die hier registriert sind empfangen wird. Damit fungieren Objekte dieser Klasse als "EventBroker".
 *   
 *   Wichtig: Diese Klasse darf nicht final sein, damit sie von anderen Klassen geerbt werden kann.
 *               Die Methoden dieser Klasse sind allerdings final.
 * @author lindhaueradmin
 *
 */
public class KernelSenderNavigatorElementSwitchZZZ extends KernelUseObjectZZZ implements ISenderNavigatorElementSwitchZZZ{
	public KernelSenderNavigatorElementSwitchZZZ(IKernelZZZ objKernel) throws ExceptionZZZ{
		super(objKernel);
	}
	
	/* (non-Javadoc)
	 * @see use.via.client.module.export.ISenderEventComponentReset#fireEvent(basic.zKernelUI.component.model.KernelEventComponentSelectionResetZZZ)
	 */
	private ArrayList<IListenerNavigatorElementSwitchZZZ> listaLISTENER_REGISTERED = new ArrayList<IListenerNavigatorElementSwitchZZZ>();  //Das ist die Arrayliste, in welche  die registrierten Komponenten eingetragen werden 
																							  //wichtig: Sie muss private sein und kann nicht im Interace global definiert werden, weil es sonst nicht m�glich ist 
	                                                                                          //             mehrere Events, an verschiedenen Komponenten, unabh�ngig voneinander zu verwalten.
	
	@Override
	public void addListenerNavigatorElementSwitch(IListenerNavigatorElementSwitchZZZ lnterfaceUser) {
		this.getListenerRegisteredAll().add(lnterfaceUser);
	}	
	
	/* (non-Javadoc)
	 * @see use.via.client.module.export.ISenderEventComponentReset#removeSelectionResetListener(basic.zKernelUI.component.model.ISelectionResetListener)
	 */
	public final void removeListenerNavigatorElementSwitch(IListenerNavigatorElementSwitchZZZ l){
		this.getListenerRegisteredAll().remove(l);
	}
	

	
	public final ArrayList getListenerRegisteredAll(){
		return listaLISTENER_REGISTERED;
	}

	@Override
	public void fireEvent(EventNavigatorElementSwitchZZZ event) throws ExceptionZZZ {
		if(event.getSource() instanceof ISenderNavigatorElementSwitchZZZ){
			ISenderNavigatorElementSwitchZZZ sender = (ISenderNavigatorElementSwitchZZZ) event.getSource();
			for(int i = 0 ; i < sender.getListenerRegisteredAll().size(); i++){
				IListenerNavigatorElementSwitchZZZ l = (IListenerNavigatorElementSwitchZZZ) sender.getListenerRegisteredAll().get(i);
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "# EventNavigatorElementSwitchZZZ by " + event.getSource().getClass().getName() + " fired: " + i);
				l.doSwitch(event);
			}
		}else{
			for(int i = 0 ; i < this.getListenerRegisteredAll().size(); i++){
				IListenerNavigatorElementSwitchZZZ l = (IListenerNavigatorElementSwitchZZZ) this.getListenerRegisteredAll().get(i);					
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "# EventNavigatorElementSwitchZZZ by " + this.getClass().getName() + " - object (d.h. this - object) fired: " + i);
				l.doSwitch(event);			
			}
		}
	}
}
