package custom.zKernelUI.module.config;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

import basic.zBasic.ExceptionZZZ;
import basic.zKernelUI.component.AbstractKernelActionCascadedZZZ;
import basic.zKernelUI.component.KernelJFrameCascadedZZZ;
import basic.zKernelUI.component.KernelJPanelCascadedZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;
import custom.zKernel.LogZZZ;
import custom.zKernelUI.module.config.DLG.DLGBOX4INIZZZ;

public class PanelConfig_SOUTHZZZ extends KernelJPanelCascadedZZZ {
	  private IKernelZZZ objKernel2Config;
	
	public  PanelConfig_SOUTHZZZ(IKernelZZZ objKernel, JPanel panelParent, IKernelZZZ objKernel2Config) throws ExceptionZZZ{
		super(objKernel, panelParent);
	
		this.setKernel2ConfigObject(objKernel2Config);
		
		//Button, �ber den ein weiteres Frame gestartet wird, mit dem das eingestellte Modul bearbeitet werden kann.
		JButton buttonEdit = new JButton("Edit module configuration");
		ActionEditModule objActSAVE_SECTION = new ActionEditModule(objKernel,objKernel2Config, this);		
		buttonEdit.addActionListener(objActSAVE_SECTION);
		this.add(buttonEdit);
		this.setComponent("ButtonEdit", buttonEdit);
	}

	//#######################################
	//### Getter / Setter
	public IKernelZZZ getKernel2ConfigObject(){
		return this.objKernel2Config;
	}
	public void setKernel2ConfigObject(IKernelZZZ objKernel){
		this.objKernel2Config = objKernel;
	}
	
	
	//#######################################
	//Innere Klasse, welche eine Action behandelt
	class ActionEditModule  extends AbstractKernelActionCascadedZZZ{ //KernelUseObjectZZZ  implements ActionListener{
		/**
		 * This class does not extent KernelActionCascadedZZZ, because of the second KernelObject, which has to be passed in the constructor !!!
		 */
		private IKernelZZZ objKernel2Config;
				
		public ActionEditModule(IKernelZZZ objKernel, IKernelZZZ objKernel2Config, KernelJPanelCascadedZZZ panelParent) throws ExceptionZZZ{
			super(objKernel, panelParent);			
			this.objKernel2Config = objKernel2Config;
		} 
		
		public boolean actionPerformCustom(ActionEvent arg0, boolean bContinue) {
			boolean bReturn = false;
			main:{
				try {
						
					// TODO: Ggf. eine alternative Methode verwenden, die Java "nativer" ist. 
					//+++ Zugriff auf das Panel, in dem die Informationen stehen 
						
						
					/* Versuch die Suche nach dem 'BasisPanel' zu standardisieren
					PanelConfig_SOUTHZZZ panelParent  = (PanelConfig_SOUTHZZZ)this.panelParent;
					
					//Weil das Panel die PanelCascadedZZZ-Klasse erweitert, kann man davon ausgehend auf das Parentpanel kommen			
					PanelConfigZZZ panelConfig = (PanelConfigZZZ) panelParent.getPanelParent();
					
					//Vom grossen Panel kann man dann auf das "Nachbarpanel" per Alias kommen
					PanelConfig_CENTERZZZ panelSubCenter = (PanelConfig_CENTERZZZ) panelConfig.getPanelSub("CENTER");
					PanelConfigZZZ panelDLGBox = (PanelConfigZZZ) panelSubCenter.getPanelParent();
					*/
					
					//TODO: Sofort auf das Nachbarpanel kommen. z.B. neue Methode panelParent.searchPanelNeighbour...(sAlias);
					KernelJPanelCascadedZZZ panelParent = (KernelJPanelCascadedZZZ) this.getPanelParent();
					KernelJPanelCascadedZZZ panelRoot = (KernelJPanelCascadedZZZ) panelParent.searchPanelRoot();
						
					//Einen Elternframe definieren, damit der Hauptframe beim Schliessen der Dialogbox nicht auch geschlossen wird.
					//FrameConfigZZZ frameParent = (FrameConfigZZZ) panelDLGBox.getFrameParent();
					//TODO: Sofort auf das ParentFrame-kommen. z.B: durch this.searchFrameParent();
					//TODO: Eine M�glichkeit auf den aufrufenden Frame zuzugreifen w�re this.searchFrameRoot();
					KernelJFrameCascadedZZZ frameParent = (KernelJFrameCascadedZZZ) panelRoot.getFrameParent();
					
					//Panel_DLGBOXZZZ objPanelDLGBox = (Panel_DLGBOXZZZ)objPanelSubEast.getPanelParent();
					//Panel_CENTERZZZ objPanelCenter = (Panel_CENTERZZZ)objPanelDLGBox.getPanelSub("CENTER");
					
					DLGBOX4INIZZZ frameDLG = new DLGBOX4INIZZZ(objKernel, frameParent);
					
					//Vom grossen Panel kann man dann auf das "Nachbarpanel" per Alias kommen
					PanelConfig_CENTERZZZ panelSubCenter = (PanelConfig_CENTERZZZ) panelRoot.getPanelSub("CENTER");
					//PanelConfigZZZ panelDLGBox = (PanelConfigZZZ) panelSubCenter.getPanelParent();
					
		//			Dieses bietet die M�glichkeit auf die Komponenten zuzugreifen.
					String sModule = panelSubCenter.getModuleSelected();			
					
					
					//Einige Test- /Protokollausgaben					
					//System.out.println("Anzahl der Componenten im Parent-Panel: " + panelSubSouth.getComponentCount());
					this.getLogObject().WriteLineDate("Action Edit Config to perform on module: " + sModule);
					
					
					
					String[] saParam = new String[2];
					saParam[0]=this.objKernel2Config.getSystemKey();			
					saParam[1]=sModule; 
					frameDLG.launch(saParam);
					
					
					//TODO GOON Zugriff auf die JLabel und JTextFields des Center Panels, am besten als Hashtable
					//System.out.println("Wert des Textfeldes an der Position 0: '" + objPanelCenter.getValue(0) + "'");
					
				
				 } catch (ExceptionZZZ ez) {				
					this.getLogObject().WriteLineDate(ez.getDetailAllLast());
				}
			}//end main
		return bReturn;
		}//END actionPerformed(ActionEvent arg0) {
		
		
		//#######################################
		//### Getter / Setter
		public IKernelZZZ getKernel2ConfigObject(){
			return this.objKernel2Config;
		}
		public void setKernel2ConfigObject(IKernelZZZ objKernel){
			this.objKernel2Config = objKernel;
		}
		
		//##########################################
//		### Methoden kommen aus den Schnittstellen
		public ExceptionZZZ getExceptionObject() {
			// TODO Auto-generated method stub
			return null;
		}
		public void setExceptionObject(ExceptionZZZ objException) {
			// TODO Auto-generated method stub			
		}

		public IKernelZZZ getKernelObject() {
			return this.objKernel;
		}

		public void setKernelObject(IKernelZZZ objKernel) {
			this.objKernel = objKernel;
		}

		public LogZZZ getLogObject() {
			return this.objLog;		
		}

		public void setLogObject(LogZZZ objLog) {
			this.objLog = objLog;
		}

		public boolean actionPerformQueryCustom(ActionEvent ae) throws ExceptionZZZ {
			return true;
		}

		public void actionPerformPostCustom(ActionEvent ae, boolean bQueryResult) throws ExceptionZZZ {			
		}

		public void actionPerformCustomOnError(ActionEvent ae, ExceptionZZZ ez) {
			// TODO Auto-generated method stub
			
		}
	}//END Class Action handler
}//End class
