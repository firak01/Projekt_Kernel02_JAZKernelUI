package basic.zKernelUI.component.componentGroup;

import basic.zBasic.ExceptionZZZ;

/** Interface muss von den Komponenten implementiert werden, die den Event-Broker verwenden wollen, um einen Event abzufeuern.
 *   Merke: Die Komponenten, die lediglich auf den Event "h�ren" brauchen dieses Interface nicht !!!
 *    
 * @author lindhaueradmin
 *
 */
public interface IEventBrokerComponentGroupSwitchUserZZZ {
	public abstract ISenderComponentGroupSwitchZZZ getSenderUsed() throws ExceptionZZZ;
	public abstract void setSenderUsed(ISenderComponentGroupSwitchZZZ objEventSender);
}
