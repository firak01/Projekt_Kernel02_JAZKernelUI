package basic.zKernelUI.component.navigator;

public interface INavigatorElementMouseListenerZZZ {
	public String getNavigatorElementAlias();
	public void setNavigatorElementAlias(String sNavigatorElementAlias);
	
	public ISenderNavigatorElementSwitchZZZ getSenderUsed(); //Der EventBroker. 
													  //Über ihn wird der Click auf das NavigatorElement "weiterverbreitet" an die daran registrierten Listener
													  //Merke: Auch die NavigatorElementCollection ist daran registriert, um z.B. andere Elemente umzuschalten/inaktiv zu setzen, etc.
	public void setSenderUsed(ISenderNavigatorElementSwitchZZZ eventBroker);
}
