package basic.zKernelUI.component.model;

/** Interface muss von den Komponenten implementiert werden, die den Event-Broker verwenden wollen, um einen Event abzufeuern.
 *   Merke: Die Komponenten, die lediglich auf den Event "hören" brauchen dieses Interface nicht !!!
 *    
 * @author lindhaueradmin
 *
 */
public interface IEventBrokerUserZZZ {
	//TODO KernelSenderComponentSelectionResetZZZ durch ein Interface ersetzen
	public abstract KernelSenderComponentSelectionResetZZZ getSenderUsed();
	public abstract void setSenderUsed(KernelSenderComponentSelectionResetZZZ objEventSender);
}
