package basic.zKernelUI.component.labelGroup;

/** Interface muss von den Komponenten implementiert werden, die den Event-Broker verwenden wollen, um einen Event abzufeuern.
 *   Merke: Die Komponenten, die lediglich auf den Event "h�ren" brauchen dieses Interface nicht !!!
 *    
 * @author lindhaueradmin
 *
 */
public interface IEventBrokerSwitchComponentUserZZZ {
	public abstract ISenderComponentGroupSwitchZZZ getSenderUsed();
	public abstract void setSenderUsed(ISenderComponentGroupSwitchZZZ objEventSender);
}
