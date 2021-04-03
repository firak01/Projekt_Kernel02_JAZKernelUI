package basic.zKernelUI.component.model;

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
public class KernelSenderComponentSelectionResetZZZ extends KernelUseObjectZZZ implements ISenderSelectionResetZZZ{
	public KernelSenderComponentSelectionResetZZZ(IKernelZZZ objKernel) throws ExceptionZZZ{
		super(objKernel);
	}
	
	/* (non-Javadoc)
	 * @see use.via.client.module.export.ISenderEventComponentReset#fireEvent(basic.zKernelUI.component.model.KernelEventComponentSelectionResetZZZ)
	 */
	private ArrayList listaLISTENER_REGISTERED = new ArrayList();  //Das ist die Arrayliste, in welche  die registrierten Komponenten eingetragen werden 
																							  //wichtig: Sie muss private sein und kann nicht im Interace global definiert werden, weil es sonst nicht m�glich ist 
	                                                                                          //             mehrere Events, an verschiedenen Komponenten, unabh�ngig voneinander zu verwalten.
	public final void fireEvent(EventComponentSelectionResetZZZ event){	
		if(event.getSource() instanceof ISenderSelectionResetZZZ){
			ISenderSelectionResetZZZ sender = (ISenderSelectionResetZZZ) event.getSource();
			for(int i = 0 ; i < sender.getListenerRegisteredAll().size(); i++){
				IListenerSelectionResetZZZ l = (IListenerSelectionResetZZZ) sender.getListenerRegisteredAll().get(i);
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "# EventComponentSelectionResetZZZ by " + event.getSource().getClass().getName() + " fired: " + i);
				l.doReset(event);
			}
		}else{
			for(int i = 0 ; i < this.getListenerRegisteredAll().size(); i++){
				IListenerSelectionResetZZZ l = (IListenerSelectionResetZZZ) this.getListenerRegisteredAll().get(i);				
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "# EventComponentSelectionResetZZZ by " + this.getClass().getName() + " - object (d.h. this - object) fired: " + i);
				l.doReset(event);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see use.via.client.module.export.ISenderEventComponentReset#removeSelectionResetListener(basic.zKernelUI.component.model.ISelectionResetListener)
	 */
	public final void removeListenerSelectionReset(IListenerSelectionResetZZZ l){
		this.getListenerRegisteredAll().remove(l);
	}
	
	/* (non-Javadoc)
	 * @see use.via.client.module.export.ISenderEventComponentReset#addSelectionResetListener(basic.zKernelUI.component.model.ISelectionResetListener)
	 */
	public final void addListenerSelectionReset(IListenerSelectionResetZZZ l){
		this.getListenerRegisteredAll().add(l);
	}
	
	public final ArrayList getListenerRegisteredAll(){
		return listaLISTENER_REGISTERED;
	}
}
