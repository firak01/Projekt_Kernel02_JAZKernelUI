package custom.zKernelUI.module.config.DLG;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Scrollbar;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import basic.zBasic.IObjectZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasicUI.layoutmanager.EntryLayout;
import basic.zBasicUI.layoutmanager.EntryLayout4VisibleZZZ;
import basic.zKernel.IKernelUserZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernelUI.KernelUIZZZ;
import basic.zKernelUI.component.IPanelCascadedZZZ;
import basic.zKernelUI.component.KernelJPanelCascadedZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.component.IKernelModuleUserZZZ;
import basic.zKernel.component.IKernelModuleZZZ;
import custom.zKernel.LogZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public class Panel_CENTERZZZ extends KernelJPanelCascadedZZZ implements IKernelModuleZZZ, IComponentTableZZZ {
	private IKernelZZZ objKernel2configure;
	
	//Besonderes Panel. hat das Module intus... 
	protected IKernelModuleZZZ objModule=null; //Das Modul, das zur Bearbeitung ausgewählt wurde.
	private String sModuleName = null;
	
	private static final int iTEXTFIELD_COLUMN_DEFAULT = 10;             //Wie breit ein Textfeld sein soll
	private static final int iNR_OF_TEXTFIELD_SHOWN_DEFAULT = 10; //Wieviele Textfields angezeigt werden sollen

	private JLabel[] labelaText = null;
	private JTextField[] textfieldaValue = null; 
	

	public Panel_CENTERZZZ(IKernelZZZ objKernel, JPanel panelParent, IKernelZZZ objKernelChoosen,  IKernelModuleZZZ objModuleChoosen, String sProgram) throws ExceptionZZZ {
		super(objKernel, panelParent);
		main:{
		try {			
			this.objKernel2configure = objKernelChoosen;//TODOGOON 20210310: Kann man kernelChoosen komplett durch ModuleChoosen ersetzen???? BZW. es sollte KernelChoosen das einzige KernelObjekt sein!!!
			this.setModule(objModuleChoosen);//Merke 20210310: Das ist ggfs. auch ein ganz abstraktes Moduluobjekt, also nicht etwas, das konkret existiert wie z.B. ein anderes Panel.
			
			
			Border borderEtched = BorderFactory.createEtchedBorder();
			this.setBorder(borderEtched);	
			
			//+++ Vor dem Anlegen der Components erst einmal pruefen, ob es ueberhaupt etwas zu tun gibt
			String sModule = KernelUIZZZ.getModuleUsedName((IKernelModuleZZZ) this);//20210312 Hier KernelUIZZZ als Hilfsklasse verwenden, um den Modulnamen auszulesen. besser als: String sModule = this.getModule().getModuleName();
									
			//Das KernelObject zu verwenden ist nicht sauber. Es muss ein eigenes Objekt fuer das zu konfigurierende Modul vorhanden sein.
			boolean bModuleConfigured = this.objKernel2configure.proofModuleFileIsConfigured(sModule);
			if(bModuleConfigured==false){
				break main;	//Fall: Modul nicht configuriert
			}else{
				//Das KernelObject zu verwenden ist nicht sauber. Es muss ein eigenes Objekt fuer das zu konfigurierende Modul vorhanden sein.
				boolean bModuleExists = this.objKernel2configure.proofModuleFileExists(sModule);
				if(bModuleExists==false){				
					break main;//Fall: Konfiguriertes Modul existiert nicht physikalisch als Datei am erwarteten Ort/mit dem erwarteten Namen
				}
			}
						
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++			
			int iLines2Show = 0; //Alle anzuzeigenden Label-Zeilen, ggf. mit leeren aufgefülllt.			
			int iLinesWithValue = 0; //Momentan anzuzeigende "gefüllte" Label-Zeilen
			int iLines2Fill = 0; //Die zum auffüllen verwendeten "leeren" Label-Zeilen
			
			//SystemKey als Schluessel fuer die Section
			//Das KernelObject zu verwenden ist nicht sauber. Es muss ein eigenes Objekt fuer das zu konfigurierende Modul vorhanden sein.
			String sSystemKey = this.objKernel2configure.getSystemKey();
			
			//FileIniZZZ Objekt holen
			//Das KernelObject zu verwenden ist nicht sauber. Es muss ein eigenes Objekt fuer das zu konfigurierende Modul vorhanden sein.
			FileIniZZZ objFileIni = this.objKernel2configure.getFileConfigIniByAlias(sModule);
			
			//Alle Einträge dieses Keys holen
			String[] saProperty = objFileIni.getPropertyAll(sSystemKey);
			if(saProperty==null){
				//Hinweis: Keine Eintraege gefunden
				iLines2Show = 1; //Die Hinweiszeile
				iLines2Fill = iNR_OF_TEXTFIELD_SHOWN_DEFAULT - iLines2Show;
				
				labelaText = new JLabel[1];
				textfieldaValue = new JTextField[1];
				
				labelaText[0]= new JLabel("No property found.", SwingConstants.RIGHT);
				this.add(labelaText[0]);								
				textfieldaValue[0] = new JTextField("No value found.", iTEXTFIELD_COLUMN_DEFAULT);
				this.add(textfieldaValue[0]);
				
			}else{
				//Ausrechnen mit wievielen Leerlabels das Layout aufgefüllt werden muss
				iLinesWithValue = saProperty.length;			
				if(iLinesWithValue >= iNR_OF_TEXTFIELD_SHOWN_DEFAULT){
					iLines2Show = iLinesWithValue;
					iLines2Fill = 0;  
				}else{					
					iLines2Show = iNR_OF_TEXTFIELD_SHOWN_DEFAULT;
					iLines2Fill = iNR_OF_TEXTFIELD_SHOWN_DEFAULT - iLinesWithValue;
				}
				
				//DIE ANZAHL DER ZEILEN VON DER ANZAHL DER GEFUNDENEN EINTR�GE ABHÄNGIG MACHEN.
				//this.setLayout(new GridLayout(iLine2Show,2)); //1 Zeilen, 2 Spalten
				
				double[] daProportion={0.2, 0.8};//Merke: Das wird zu WIDTH im Layout, die Anzahl der Spalten ist immer 2 !!!
	            EntryLayout4VisibleZZZ layout = new EntryLayout4VisibleZZZ(daProportion);
				//EntryLayout layout = new EntryLayout(daProportion);
	             this.setLayout(layout);
				
				
				//DIE ARRAY GRÖSSEN VON DER ANZAHL DER GEFUNDENEN EINTRÄGE ABHÄNGIG MACHEN
				String sValue = new String("");
				labelaText = new JLabel[iLinesWithValue];
				textfieldaValue = new JTextField[iLinesWithValue];
				Dimension dimensionLabel = new Dimension(10,10);
				Dimension dimensionTextfield = new Dimension(10, 10);
				for(int icount=0; icount < iLinesWithValue; icount++){
								
					//Das Property - Label
					labelaText[icount]= new JLabel(saProperty[icount] + "=", SwingConstants.RIGHT);
					labelaText[icount].setSize(dimensionLabel);
					this.add(labelaText[icount]);
					
					//Das Value - Textfeld										
					//Das KernelObject zu verwenden ist nicht sauber. Es muss ein eigenes Objekt fuer das zu konfigurierende Modul vorhanden sein.
					sValue = this.objKernel2configure.getParameterByModuleFile(objFileIni, saProperty[icount]).getValue(); //Parameter);
					
					textfieldaValue[icount] = new JTextField(sValue, iTEXTFIELD_COLUMN_DEFAULT);
					textfieldaValue[icount].setSize(dimensionTextfield);
					this.add(textfieldaValue[icount]);
				}
			}//END IF: if(saProperty==null){
			
			//### Damit die Labels hinsichtlich der Hoehe nicht so gross werden, ggf. mit leeren Werten auffüllen
			for(int icount = 0; icount < iLines2Fill; icount ++){
				JLabel labeltemp = new JLabel("", SwingConstants.RIGHT);
				this.add(labeltemp);								
				JLabel labeltemp2 = new JLabel("", SwingConstants.LEFT);
				this.add(labeltemp2);	
			}
			
			
				/* TODO: Auf das TextField zugreifen k�nnen, auch per Lotusscript
				JTextField objText2 = new JTextField(strParam, iTEXTFIELD_COLUMN_DEFAULT);
				listaText.add(objText2);
				this.add(objText2);	
				*/
				
				} catch (ExceptionZZZ ez) {
					this.getLogObject().WriteLineDate(ez.getDetailAllLast());
				}
		}
		
	}

	

	/* (non-Javadoc)
	 * @see custom.zKernelUI.module.config.DLG.IComponentTableZZZ#getValue(int)
	 */
	public String getValue(int iPosition){
		String strReturn = new String("");	
		main:{
			check:{
		if(textfieldaValue==null)break main;
		if(iPosition > textfieldaValue.length| iPosition <= -1) break main;
			}//END check:
		
		JTextField objText = textfieldaValue[iPosition];
		strReturn = objText.getText();
		}//END main:
		return strReturn;
	}
		
	/* (non-Javadoc)
	 * @see custom.zKernelUI.module.config.DLG.IComponentTableZZZ#getTableAlias()
	 */
	public String getTableAlias() throws ExceptionZZZ{
		String sReturn = new String("");
		main:{
			IKernelZZZ objKernel = this.objKernel2configure;
			sReturn = objKernel.getSystemKey();			
		}
		return sReturn;
	}
	
	/* (non-Javadoc)
	 * @see custom.zKernelUI.module.config.DLG.IComponentTableZZZ#getTable(boolean)
	 */
	public Hashtable getTable(boolean bExcludeValueEmpty) throws ExceptionZZZ{
		Hashtable objReturn = null;
		main:{
			check:{
				if(this.labelaText==null) break main;
				if(this.textfieldaValue==null) break main;	
				if(this.textfieldaValue.length != this.labelaText.length){
					ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE + "Array lengths of Labels and Textfields does not match.", iERROR_PARAMETER_VALUE,  ReflectCodeZZZ.getMethodCurrentName(), "");
					   //doesn�t work. Only works when > JDK 1.4
					   //Exception e = new Exception();
					   //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");			  
					   throw ez;	
				}
			}//end check:
		
		//###################################
		//Die Hashtable immer neu fuellen, es kann sich ja gegenueber dem Einlesen etwas geaendert haben
		objReturn = new Hashtable();
		
		int iLineTotal = textfieldaValue.length;
		for(int icount=0; icount<iLineTotal;icount++){
			if(textfieldaValue[icount]!=null){
				String sValue=this.textfieldaValue[icount].getText();
				if(sValue.startsWith("=")){
					//Ggf. wird dem Value das Gleichheitszeichen mitgegeben. Das wird hier am Anfang entfernt.
					sValue = sValue.substring(1, sValue.length());
				}
				if (!(sValue.equals("") && bExcludeValueEmpty==true)){
					//LEERWERTE SOLLEN �BERNOMMEN WERDEN
					String sProperty=this.labelaText[icount].getText();
					if(sProperty.endsWith("=")){
						//Ggf. wird dem Label das Gleicheitszeichen mitgegeben. Das wird hier am Ende entfernt.
						sProperty = sProperty.substring(0, sProperty.length()-1);
					}
					objReturn.put(sProperty,sValue);
				}
			}
		}
		
		}//end main:
		return objReturn;
	}
	
	//#######################################################
	//### GETTER / SETTER

	//### Aus IKernelModuleUserZZZ
	public IKernelModuleZZZ getModule() {
		return this.objModule;
	}
	public void setModule(IKernelModuleZZZ objModule) {
		this.objModule = objModule;
	}
	
	//#################### Interface IKernelModuleUserZZZ
			public String getModuleName() throws ExceptionZZZ {
				String sReturn = new String("");
				main:{	
					//TODOGOON; //20210310: Jetzt gibt es ja noch ggfs. ein Abstraktes Module-Objekt.
					//                    Wenn das abstrakte Modul Objekt vorhanden ist, dann den ModulNamen daraus verwenden.
					//                    Ist das abstrakte Modul Objekt nicht vorhanden, dann den Modulnamen wie bisher anhand des Panels selbst ermitteln.				
					if(StringZZZ.isEmpty(this.sModuleName)) {
						if(this.getModule()!=null) {
							this.sModuleName = KernelUIZZZ.getModuleUsedName((IKernelModuleZZZ)this.getModule());
						}else {
							this.sModuleName = KernelUIZZZ.getModuleUsedName((IPanelCascadedZZZ)this);
						}
					}
					sReturn = this.sModuleName;
				}//end main
				return sReturn;
			}
}
