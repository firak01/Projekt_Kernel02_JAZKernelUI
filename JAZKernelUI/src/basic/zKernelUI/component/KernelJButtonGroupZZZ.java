package basic.zKernelUI.component;

import java.util.HashMap;
import java.util.Set;

import javax.swing.JButton;

import custom.zKernel.LogZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IFlagZZZ;
import basic.zBasic.IObjectZZZ;
import basic.zKernel.IKernelUserZZZ;
import basic.zKernel.KernelUseObjectZZZ;
import basic.zKernel.KernelZZZ;

public class KernelJButtonGroupZZZ<T,X>  extends KernelUseObjectZZZ{

	HashMap<String,JButton>hmButton=null;
	
	public KernelJButtonGroupZZZ(){		
		super();
	}
	
	public HashMap<String,JButton> getHashMapButton(){
		if(this.hmButton==null){
			this.hmButton = new HashMap<String,JButton>();
		}
		return this.hmButton;
	}
	
	public void add(String sKey, JButton button){
		this.getHashMapButton().put(sKey, button);
	}
	public  JButton getButton(String sKey){
		return this.getHashMapButton().get(sKey);
	}
	
	public void enableAll(){
		this.setEnabledAll(true);
	}
	public void disableAll(){
		this.setEnabledAll(false);
	}
	public void setEnabledAll(boolean bStatus){
		HashMap<String,JButton> hmButton = this.getHashMapButton();
		Set<String>setKey = hmButton.keySet();
		for(String sKey : setKey){
			JButton buttonTemp = hmButton.get(sKey);
			buttonTemp.setEnabled(bStatus);
		}				
	}
	
	public void enable(String sKey){
		this.setEnabled(sKey, true);
	}
	public void disable(String sKey){
		this.setEnabled(sKey, false);
	}
	public void setEnabled(String sKey, boolean bStatus){
		HashMap<String,JButton> hmButton = this.getHashMapButton();
		JButton button = hmButton.get(sKey);
		if(button!=null){
			button.setEnabled(bStatus);
		}
	}
	
	public void enableOther(String sKey){
		this.setEnabledOther(sKey, true);
	}
	public void disableOther(String sKey){
		this.setEnabledOther(sKey, false);
	}
	public void setEnabledOther(String sKeyNotToHandle, boolean bStatus){
		HashMap<String,JButton> hmButton = this.getHashMapButton();
		Set<String>setKey = hmButton.keySet();
		for(String sKey : setKey){
			JButton buttonTemp = hmButton.get(sKey);
			if(sKey.equalsIgnoreCase(sKeyNotToHandle)){
				//Nichst tun buttonTemp.setEnabled(!bStatus);
			}else{
				buttonTemp.setEnabled(bStatus);
			}
		}
	}
		
		
	public void toggle(String sKey){
		HashMap<String,JButton> hmButton = this.getHashMapButton();
		JButton button = this.getButton(sKey);
		if(button!=null){
			boolean bStatus = button.isEnabled();
			this.setEnabledTogle(sKey, !bStatus);
		}
	}
	public void setEnabledTogle(String sKey, boolean bStatus){
		HashMap<String,JButton> hmButton = this.getHashMapButton();
		Set<String>setKey = hmButton.keySet();
		for(String sKeyTemp : setKey){
			JButton buttonTemp = hmButton.get(sKeyTemp);
			if(sKey.equalsIgnoreCase(sKeyTemp)){
				buttonTemp.setEnabled(bStatus);
			}else{
				buttonTemp.setEnabled(!bStatus);
			}
		}
	}
	
	public void setEnabledTogle(String sKey){		
		HashMap<String,JButton> hmButton = this.getHashMapButton();
		JButton buttonToToggle = hmButton.get(sKey);
		if(buttonToToggle!=null){
			boolean bStatus = buttonToToggle.isEnabled();
			
			Set<String>setKey = hmButton.keySet();
			for(String sKeyTemp : setKey){
				JButton buttonTemp = hmButton.get(sKey);
				if(sKey.equalsIgnoreCase(sKeyTemp)){
					buttonTemp.setEnabled(!bStatus);
				}else{
					buttonTemp.setEnabled(bStatus);
				}
			}
		}
	}

		
	
	
}//End class
