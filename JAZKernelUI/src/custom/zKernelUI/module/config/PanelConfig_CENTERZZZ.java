package custom.zKernelUI.module.config;

import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zKernelUI.component.KernelJPanelCascadedZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;

public class PanelConfig_CENTERZZZ extends KernelJPanelCascadedZZZ{
	/** Klasse enthaelt die Buttons zur Auswahl des zu bearbeitenden Moduls und Systems
	 * 
	 */	
	private IKernelZZZ objKernel2Config;
		
	public PanelConfig_CENTERZZZ(IKernelZZZ objKernel,JPanel objPanelParent, IKernelZZZ objKernel2Config) throws ExceptionZZZ{
		super(objKernel, objPanelParent);
		try{
		this.setKernelConfigObject(objKernel2Config);
		
		//### Layout Manager
		//this.setLayout(new GridLayout());
				
			
		//Es sollen in dieser Phase nur konfigurierte und existierende Module bearbeitet werden duerfen
		ArrayList listaModule = objKernel2Config.getFileConfigModuleAliasAll(true, true);
		if(listaModule.size()>= 1){
			//Comboboxen zum Einstellen des Moduls + Systems
			JComboBox comboModule = new JComboBox();
		
			for(int icount = 0; icount < listaModule.size(); icount++){
				
				//Die beruecksichtigten Module 
				this.getLogObject().WriteLineDate(ReflectCodeZZZ.getMethodCurrentName() + "#Modul fuer den Key '" + objKernel2Config.getSystemKey() + "' --- '"  + (String) listaModule.get(icount) + "'");
				
				comboModule.addItem((String) listaModule.get(icount));
			}			
			this.add(comboModule);
			this.setComponent("ComboModule", comboModule);
		}else{
			//Label, das keine Konfigurierten Module zur Verf�gung stehen
			JLabel labelModule = new JLabel("There is no module configured which has an existing configuration file.");
			this.add(labelModule);
			this.setComponent("LabelModule", labelModule);
		}
		
		}catch(ExceptionZZZ ez){
		   objKernel.getLogObject().WriteLineDate(ez.getDetailAllLast());
		}
		
	}

	/** String, liefert aus der Modul - Combobox den gerade ausgew�hlten String zur�ck
	* Lindhauer; 29.04.2006 19:03:12
	 * @return
	 */
	public String getModuleSelected() {
		String sReturn = new String("");
		main:{
			JComboBox comboModule = (JComboBox)this.getComponent("ComboModule");
			if(comboModule==null) break main;
			sReturn = (String) comboModule.getSelectedItem();
		} //END main
		return sReturn;
	}		
	
	//######################################
	//### Getter / Setter
	public void setKernelConfigObject(IKernelZZZ objKernel){
		this.objKernel2Config = objKernel;
	}
	public IKernelZZZ getKernelConfigObject(){
		return this.objKernel2Config;
	}
	
	
	//#####################################
	//### Von den Interfaces implementierte Methoden
	
}//END class




