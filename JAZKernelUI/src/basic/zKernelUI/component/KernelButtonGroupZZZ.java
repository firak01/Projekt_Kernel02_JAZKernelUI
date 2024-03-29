package basic.zKernelUI.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JToggleButton;

import custom.zKernel.LogZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectZZZ;
import basic.zBasic.util.abstractList.HashMapExtendedZZZ;
import basic.zKernel.IKernelUserZZZ;
import basic.zKernel.AbstractKernelUseObjectZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;

public class KernelButtonGroupZZZ<T,X>  extends AbstractKernelUseObjectZZZ{

	HashMap<String,AbstractButton>hmButton=null;
	ArrayList<AbstractButton>listaButtonTemp=null; //Ziel: Vermeide die Verarbeitung doppelt registrierter Buttons.
                                                                                //Lösungsansatz: Führe eine ArrayList der bereits verarbeitetetn Buttons und prüfe auf Vorhandenheit in dieser Liste
	
	public KernelButtonGroupZZZ(){		
		super();
	}
	
	public HashMap<String,AbstractButton> getHashMapButton(){
		if(this.hmButton==null){
			this.hmButton = new HashMap<String,AbstractButton>();
		}
		return this.hmButton;
	}
	
	public ArrayList<AbstractButton> getArrayListHandledButton(){
		if(this.listaButtonTemp==null){
			this.listaButtonTemp = new ArrayList<AbstractButton>();
		}
		return this.listaButtonTemp;
	}
	public void clearArrayListHandledButton(){
		this.getArrayListHandledButton().clear();
	}
	
	public void add(String sKey, AbstractButton button){
		this.getHashMapButton().put(sKey, button);
	}
	public  AbstractButton getButton(String sKey){
		return this.getHashMapButton().get(sKey);
	}
	
	/**Den Button aus der HashMap holen und dann den Key dazu holen, um diesen, z.B. in anderen Methoden dieser Klasse zu nutzen.
	 * @param button
	 * @return
	 */
	public String getKey(JButton button){
		String sReturn = null;
		HashMap<String,AbstractButton>hm=this.getHashMapButton();
		Object obj = HashMapExtendedZZZ.getKeyFromValueFirst(hm, button);//Note that there is no single corresponding key - there may well be multiple keys mapping to the same value. 
		if(obj!=null){
			sReturn = obj.toString();
		}		
		return sReturn;
	}
	
	public void enableAll(){
		this.setEnabledAll(true);
	}
	public void disableAll(){
		this.setEnabledAll(false);
	}
	public void setEnabledAll(boolean bStatus){
		this.clearArrayListHandledButton();
		
		HashMap<String,AbstractButton> hmButton = this.getHashMapButton();
		Set<String>setKey = hmButton.keySet();
		for(String sKey : setKey){
			AbstractButton buttonTemp = hmButton.get(sKey);
			if(this.getArrayListHandledButton().contains(buttonTemp)){
				//nicht noch mal setzen
			}else{
				this.getArrayListHandledButton().add(buttonTemp);
				buttonTemp.setEnabled(bStatus);
			}
			
		}				
	}
	
	public void enable(JButton button){
		String sKey = this.getKey(button);
		this.enable(sKey);
	}
	public void enable(String sKey){
		this.setEnabled(sKey, true);
	}
	
	public void disable(JButton button){
		String sKey = this.getKey(button);
		this.disable(sKey);
	}
	public void disable(String sKey){
		this.setEnabled(sKey, false);
	}
	
	public void setEnabled(String sKey, boolean bStatus){
		HashMap<String,AbstractButton> hmButton = this.getHashMapButton();
		AbstractButton button = hmButton.get(sKey);
		if(button!=null){
			button.setEnabled(bStatus);
		}
	}
	
	public void enableOther(JButton button){
		String sKey = this.getKey(button);
		this.enableOther(sKey);
	}
	public void enableOther(String sKey){
		this.setEnabledOther(sKey, true);
	}
	
	public void disableOther(JButton button){
		String sKey = this.getKey(button);
		this.disableOther(sKey);
	}	
	public void disableOther(String sKey){
		this.setEnabledOther(sKey, false);
	}
	public void setEnabledOther(String sKeyNotToHandle, boolean bStatus){
		this.clearArrayListHandledButton();
		
		HashMap<String,AbstractButton> hmButton = this.getHashMapButton();
		Set<String>setKey = hmButton.keySet();
		for(String sKey : setKey){
			AbstractButton buttonTemp = hmButton.get(sKey);
			if(this.getArrayListHandledButton().contains(buttonTemp)){
			}else{				
				if(sKey.equalsIgnoreCase(sKeyNotToHandle)){
					//Nichst tun buttonTemp.setEnabled(!bStatus);				
				}else{				
					buttonTemp.setEnabled(bStatus);
				}
				this.getArrayListHandledButton().add(buttonTemp);
			}
		}
	}
		
	public void toggleAll(){
		this.clearArrayListHandledButton();
		
		HashMap<String,AbstractButton> hmButton = this.getHashMapButton();
		Set<String>setKey = hmButton.keySet();
		for(String sKey : setKey){
			this.toggle(sKey);
		}				
	}
	
	public void toggle(JButton button){
		String sKey = this.getKey(button);
		this.toggle(sKey);
	}
	public void toggle(String sKey){
		HashMap<String,AbstractButton> hmButton = this.getHashMapButton();
		AbstractButton button = this.getButton(sKey);
		if(button!=null){
			if(this.getArrayListHandledButton().contains(button)){
			}else{
					boolean bStatus = button.isEnabled();
					this.setEnabled(sKey, !bStatus);
					this.getArrayListHandledButton().add(button);
			}
		}
	}

	/**Ausgehend von dem angegebenen Button, wird dessen Status geändert.
	 *  Alle anderen Buttons bekommen einen anderen Status als der Ausgangsbutton.
	 * @param sKey
	 */
	public void differAll(JButton button){
		String sKey = this.getKey(button);
		this.differAll(sKey);
	}
	
	/**Ausgehend von dem angegebenen Button, wird dessen Status geändert.
	 *  Alle anderen Buttons bekommen einen anderen Status als der Ausgangsbutton.
	 * @param sKey
	 */
	public void differAll(String sKey){
		this.clearArrayListHandledButton();
		this.setEnabledDifferentAll(sKey);
	}
	
	/**Ausgehend von dem angegebenen Button, wird dessen Status geändert.
	 *  Alle anderen Buttons bekommen einen anderen Status als der Ausgangsbutton.
	 * @param sKey
	 */
	public void setEnabledDifferentAll(String sKey){	
		this.clearArrayListHandledButton();
		
		HashMap<String,AbstractButton> hmButton = this.getHashMapButton();
		AbstractButton buttonSource = hmButton.get(sKey);
		if(buttonSource!=null){			
			boolean bStatus;
			if(buttonSource instanceof JToggleButton){
				//Its' a toggle button, so disabled means 'not active', unclickable means 'you can not change the state'
				JToggleButton buttonToggle = (JToggleButton) buttonSource;
				bStatus = buttonSource.isSelected();
			}else{
				bStatus = buttonSource.isEnabled();	
			}
			this.getArrayListHandledButton().add(buttonSource);
			
			Set<String>setKey = hmButton.keySet();
			for(String sKeyTemp : setKey){
				AbstractButton buttonTemp = hmButton.get(sKeyTemp);
				if(buttonTemp!=null){					
					if(this.getArrayListHandledButton().contains(buttonTemp)){
					}else{
						if(sKey.equalsIgnoreCase(sKeyTemp)){ 
							if(buttonSource instanceof JToggleButton){
								System.out.println("Ändere den Referenzbutton (JToggle) NICHT");
							}else{
								System.out.println("Ändere den Referenzbutton nur ggfs.");
								buttonTemp.setEnabled(bStatus);
							}
						}else{
							buttonTemp.setEnabled(!bStatus);
						}
						this.getArrayListHandledButton().add(buttonTemp);
					}
					
				}
			}
		}		
	}

	/**Ausgehend von dem angegebenen Button, wird dessen Status geändert.
	 *  Alle anderen Buttons bekommen einen anderen Status als der Ausgangsbutton.
	 * @param sKey
	 */
	public void sameAll(String sKey){
		this.setEnabledSameAll(sKey);
	}
	
	/**Ausgehend von dem angegebenen Button, wird dessen Status geändert.
	 *  Alle anderen Buttons bekommen einen anderen Status als der Ausgangsbutton.
	 * @param sKey
	 */
	public void setEnabledSameAll(String sKey){	
		this.clearArrayListHandledButton();
		
		HashMap<String,AbstractButton> hmButton = this.getHashMapButton();
		AbstractButton buttonSource = hmButton.get(sKey);
		if(buttonSource!=null){
			boolean bStatus;
			if(buttonSource instanceof JToggleButton){
				//Its' a toggle button, so disabled means 'not active', unclickable means 'you can not change the state'
				JToggleButton buttonToggle = (JToggleButton) buttonSource;
				bStatus = buttonSource.isSelected();
			}else{
				bStatus = buttonSource.isEnabled();	
			}
			this.getArrayListHandledButton().add(buttonSource);
			
			Set<String>setKey = hmButton.keySet();
			for(String sKeyTemp : setKey){
				AbstractButton buttonTemp = hmButton.get(sKeyTemp);
				if(buttonTemp!=null){	
					if(this.getArrayListHandledButton().contains(buttonTemp)){//Vermeide die Verarbeitung doppelt registrierter Buttons.
						//Lösungsansatz: Führe eine ArrayList der bereits verarbeitetetn Buttons und prüfe auf Vorhandenheit in dieser Liste
					}else{
						if(sKey.equalsIgnoreCase(sKeyTemp)){																								
							if(buttonSource instanceof JToggleButton){
								System.out.println("Ändere den Referenzbutton (JToggle) NICHT");
							}else{
								System.out.println("Ändere den Referenzbutton nur ggfs.");
							    buttonTemp.setEnabled(bStatus);
							}
						}else{
							buttonTemp.setEnabled(bStatus);
						}
						this.getArrayListHandledButton().add(buttonTemp);
					}
				}
			}
		}		
	}

	
	
}//End class
