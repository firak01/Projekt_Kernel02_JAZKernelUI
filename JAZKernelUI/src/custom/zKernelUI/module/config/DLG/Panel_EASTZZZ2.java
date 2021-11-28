package custom.zKernelUI.module.config.DLG;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelUseObjectZZZ;
import basic.zKernelUI.KernelUIZZZ;
import basic.zKernelUI.component.KernelJPanelCascadedZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.component.IKernelModuleZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public class Panel_EASTZZZ2  extends KernelJPanelCascadedZZZ {
	private IKernelZZZ objKernelChoosen=null;
	private IKernelModuleZZZ objModuleChoosen=null;
	
	private static final int iLABEL_COLUMN_DEFAULT = 10;
	
	public Panel_EASTZZZ2(IKernelZZZ objKernel, JPanel panelParent, IKernelZZZ objKernelChoosen, IKernelModuleZZZ objModuleChoosen, String sProgram) throws ExceptionZZZ {
		super(objKernel, panelParent);
		main:{
		try{	
		this.setKernelChoosen(objKernelChoosen);//TODOGOON 20210310: Kann man kernelChoosen komplett durch ModuleChoosen ersetzen????
		this.setModuleChoosen(objModuleChoosen);//Merke 20210310: Das ist ggfs. auch ein ganz abstraktes Moduluobjekt, also nicht etwas, das konkret existiert wie z.B. ein anderes Panel.
		
		
			//TODO Komplizierteres aber sch�neres Layout durch einen anderen Layoutmanager
			//this.setLayout(new GridLayout(6,2)); //6 Zeilen, 2 Spalten
	
			//Einen Rand um das Panel ziehen
			Border borderEtched = BorderFactory.createEtchedBorder();
			this.setBorder(borderEtched);
			
			check:{
				//Kein Modul uebergeben
				if(this.getModuleChoosen()==null){
					//Statt Button eine Meldung TODO: Wie graut man einen Button aus ???
					JLabel labelModuleValue = new JLabel("Save unavailable", SwingConstants.LEFT);
					this.add(labelModuleValue);
					break main;
					//TODO: Falls kein Modul �bergeben wurde, so k�nnen sp�ter immer noch Buttons wie "create new module", etc. angezeigt werden.
				}else if(this.getModuleChoosen()!=null && this.getModuleChoosen().getModuleName().equals("")){
					//Statt Button eine Meldung TODO: Wie graut man einen Button aus ???
					JLabel labelModuleValue = new JLabel("Save unavailable", SwingConstants.LEFT);
					this.add(labelModuleValue);
					break main;
					//TODO: Falls kein Modul �bergeben wurde, so k�nnen sp�ter immer noch Buttons wie "create new module", etc. angezeigt werden.
				}else{
					//ModuleExists ?
					String sModule = this.getModuleChoosen().getModuleName();
										
					boolean bModuleConfigured = this.objKernelChoosen.proofModuleFileIsConfigured(sModule);
					if(bModuleConfigured==false){
						//Fall: Modul nicht configuriert
						JLabel labelModuleValue = new JLabel(sModule + ", Error: This is not configured in the kernel main .ini-file.", SwingConstants.LEFT);
						this.add(labelModuleValue);
					}else{
						boolean bModuleExists = this.objKernelChoosen.proofModuleFileExists(sModule);
						if(bModuleExists==false){
							//Fall: Konfiguriertes Modul existiert nicht physikalisch als Datei am erwarteten Ort/mit dem erwarteten Namen
							JLabel labelModuleValue = new JLabel(sModule + ", Error: The .ini-file does not exist.", SwingConstants.LEFT);
							this.add(labelModuleValue);
						}else{
							//Fall: Alles o.k.
							JButton buttonSave = new JButton("Save Section Values");
							
							//Merke: Das hier verwendete KernelObjectChoosen wurde zuvor extra erstellt als eigenes Objekt für ausgewählte Modul, etc.
							ActionSaveSection objActSAVE_SECTION = new ActionSaveSection(this.getKernelObject(), this.objKernelChoosen, this);
														
							buttonSave.addActionListener(objActSAVE_SECTION);
							this.add(buttonSave);
						}
					}
				}				
			}//END check:
			
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
			}
		}//END main:
	}
	
	public IKernelZZZ getKernelChoosen() {
		return this.objKernelChoosen;
	}
	public void setKernelChoosen(IKernelZZZ objKernelChoosen) {
		this.objKernelChoosen = objKernelChoosen;
	}
	
	public IKernelModuleZZZ getModuleChoosen() {
		return this.objModuleChoosen;
	}
	public void setModuleChoosen(IKernelModuleZZZ objModuleChoosen) {
		this.objModuleChoosen = objModuleChoosen;
	}
	
	
	
	
	public static class ActionSaveSection extends KernelUseObjectZZZ  implements ActionListener{
		/**
		 * This internal class doe not extend KernelActionCascadedZZZ, because of the different constructor, which has to work with 2 KernelObjects.
		 */
		private JPanel panelParent;
		private IKernelZZZ objKernelChoosen;
		public ActionSaveSection(IKernelZZZ objKernel,IKernelZZZ objKernelChoosen,  JPanel panelParent) throws ExceptionZZZ{
			super(objKernel);
			this.panelParent = panelParent;	
			this.objKernelChoosen = objKernelChoosen;
		}
	
		//### Methoden kommen aus den Schnittstellen
		//### 
		public void actionPerformed(ActionEvent arg0) {
			try {
			//+++ Protokolll Eintrag
				this.getLogObject().WriteLineDate("Performing Action: 'Save Section'");
				
			//+++ Zugriff auf das Panel, in dem die Informationen stehen 
			//Panel_EASTZZZ objPanelSubEast = (Panel_EASTZZZ)this.panelParent;  //DAS IST MIT MOCK OBJEKT NICHT TESTBAR !!!
			KernelJPanelCascadedZZZ objPanelSubEast = (KernelJPanelCascadedZZZ)this.panelParent;
			//Panel_DLGBOXZZZ objPanelDLGBox = (Panel_DLGBOXZZZ)objPanelSubEast.getPanelParent(); //DAS IST MIT MOCK OBJEKT NICHT TESTBAR !!!
			KernelJPanelCascadedZZZ objPanelDLGBox = (KernelJPanelCascadedZZZ)objPanelSubEast.getPanelParent();
			
			//Panel_CENTERZZZ objPanelCenter = (Panel_CENTERZZZ)objPanelDLGBox.getPanelSub("CENTER"); 
			//KernelJPanelCascadedZZZ objPanelCenterTemp = objPanelDLGBox.getPanelSub("CENTER");
			Panel_CENTERZZZ objPanelCenter = (Panel_CENTERZZZ)objPanelDLGBox.getPanelSub("CENTER");
			
			//Merke: F�r jede Property=Value Zeile kommen 2 Component hinzu: JLabel + JTextField;						
			//System.out.println("Anzahl der Componenten im CENTER Panel: " + objPanelCenter.getComponentCount());
			
			//TODO GOON Zugriff auf die JLabel und JTextFields des Panels, am besten als Hashtable
			//System.out.println("Wert des Textfeldes an der Position 0: '" + objPanelCenter.getValue(0) + "'");
			
			
//			+++ Den Namen des Moduls auslesen = TabellenAlias
			//IKernelModuleZZZ objModule = objPanelCenter.getModule();
			//String sModule = objModule.getModuleName();
			
			String sModule = KernelUIZZZ.getModuleUsedName((IKernelModuleZZZ) objPanelCenter);
			//System.out.println("Name des Moduls: " + sModule);
			
			//+++ Den Namen der zu verarbeitenden Section auslesen
			String sSection = objPanelCenter.getTableAlias();
			this.getLogObject().WriteLineDate("Performing Action: ... on module '" + sModule + "' and section '" + sSection + "'");
						
			//+++ F�llen einer Tabelle mit den Werten der JLabel und JTextfield Components
			Hashtable objHt = objPanelCenter.getTable(false);
			boolean bSuccess;
			if(objHt==null){
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE + "Array lengths of Labels and Textfields does not match.", iERROR_PARAMETER_VALUE,  ReflectCodeZZZ.getMethodCurrentName(), "");
				   //doesn�t work. Only works when > JDK 1.4
				   //Exception e = new Exception();
				   //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");			  
				   throw ez;	
			}else if(objHt.size()<=0){
				//TODO GOON Sicherheitsabfrage per Dialog
				//Section l�schen				
				//TODO GOON, das ist nicht sauber das eigenen KernelObjekt daf�r zu verwenden. Es muss ein anderes herangezogen werden.
				//FileIniZZZ objFileIni = this.getKernelObject().getFileConfigIniByAlias(sModule);
				FileIniZZZ objFileIni = this.objKernelChoosen.getFileConfigIniByAlias(sModule);
				objFileIni.deleteSection(objPanelCenter.getTableAlias());
				bSuccess = objFileIni.save();
			}else{
				//TODO GOON Sicherheitsabfrage per Dialog
				//Talleneintr�ge der Section hinzuf�gen. Zudem: Section in der Datei zuerst entfernen.
				
				//TODO GOON, das ist nicht sauber das eigenen KernelObjekt daf�r zu verwenden. Es muss ein anderes herangezogen werden.
				//FileIniZZZ objFileIni = this.getKernelObject().getFileConfigIniByAlias(sModule);
				FileIniZZZ objFileIni = this.objKernelChoosen.getFileConfigIniByAlias(sModule);
				
				//TODO GOON, eine sortierte Hashtable �bergeben !!!
				objFileIni.setSection(sSection, objHt, true);	
				bSuccess = objFileIni.save();
			}
			
			//Erfolgsmeldung ausgeben
			//TODO: Das mit einer Dialogbox machen
			if(bSuccess==true){
				//System.out.println("Configuration file for module '" + sModule + "' successfully updated.");
				this.getLogObject().WriteLineDate("Performing Action: Successfully completed.");
			}else{
				//System.out.println("Configuration file for module '" + sModule + "' NOT updated.");
				this.getLogObject().WriteLineDate("Performing Action: NOT ended as expected.");
			}
			
			
			} catch (ExceptionZZZ ez) {
				// TODO Auto-generated catch block
				ez.printStackTrace();
			}
		}//END actionPerformed(ActionEvent arg0) {
		
	}//End class
	
}// END class
