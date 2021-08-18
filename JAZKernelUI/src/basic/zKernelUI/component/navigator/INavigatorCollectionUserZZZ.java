package basic.zKernelUI.component.navigator;

/** Interface muss von den Komponenten implementiert werden, die den Event-Broker verwenden wollen, um einen Event abzufeuern.
 *   Merke: Die Komponenten, die lediglich auf den Event "h�ren" brauchen dieses Interface nicht !!!
 *    
 * @author lindhaueradmin
 *
 */
public interface INavigatorCollectionUserZZZ {
	public abstract ISenderNavigatorElementSwitchZZZ getSenderUsed();
	public abstract void setSenderUsed(ISenderNavigatorElementSwitchZZZ objEventSender);
}
