package custom.zKernelUI.module.config.DLG;

import java.awt.Color;
import java.awt.Component;
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
import basic.zKernelUI.component.IDebugUiZZZ;
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
	private static final int iNR_OF_TEXTFIELD_SHOWN_DEBUG = 1; //Wieviele Textfields im DEBUG Fall angezeigt werden sollen.

	
	private JLabel[] labelaIndex = null;
	private JLabel[] labelaText = null;
	private JTextField[] textfieldaValue = null; 
	

	public Panel_CENTERZZZ(IKernelZZZ objKernel, JPanel panelParent, IKernelZZZ objKernelChoosen,  IKernelModuleZZZ objModuleChoosen, String sProgram) throws ExceptionZZZ {
		super(objKernel, panelParent);
		main:{
		try {					
			this.objKernel2configure = objKernelChoosen;//TODOGOON 20210310: Kann man kernelChoosen komplett durch ModuleChoosen ersetzen???? BZW. es sollte KernelChoosen das einzige KernelObjekt sein!!!
			this.setModule(objModuleChoosen);//Merke 20210310: Das ist ggfs. auch ein ganz abstraktes Moduluobjekt, also nicht etwas, das konkret existiert wie z.B. ein anderes Panel.
			
			
			//Border borderEtched = BorderFactory.createEtchedBorder();
			//this.setBorder(borderEtched);
			
			Border borderLine = BorderFactory.createLineBorder(Color.BLACK);
			this.setBorder(borderLine);
					
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
					
			//+++ Layout - Manager und Anzahl Spalten festlegen ++++++++++++
			
			//Problem: Suche nach dem passenden LayoutManager, der fest positioniert, aber unsichtbare ausblendet.
			//this.setLayout(new GridLayout(iLine2Show,2)); //1 Zeilen, 2 Spalten
			//EntryLayout layout = new EntryLayout(daProportion);
			
			double[] daProportion={0.1, 0.3, 0.6};//Merke: Das wird zu WIDTH im Layout, die Anzahl der Spalten ist entsprechend der Proportionsparameter !!!
            EntryLayout4VisibleZZZ layout = new EntryLayout4VisibleZZZ(daProportion);				
            this.setLayout(layout);
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
			//Standardgrößen von Label und Textfeld
			Dimension dimensionLabelColumnFirst = new Dimension (20,20);
			Dimension dimensionLabel = new Dimension(150,20);			
			Dimension dimensionTextfield = new Dimension(200, 20);
			
			//Übergreifende UI-DEBUG-LAYOUT Strategy
			//1: Nur 1 Zeile anzeigen
			//2: Die erste und die letzte Zeile anzeigen
			//sonst, alles anzeigen...
			int iDebugUILayoutStrategy=0;
			if(this.getFlagZ(IDebugUiZZZ.FLAGZ.DEBUGUI_PANELLIST_STRATEGIE_ENTRYFIRST.name())){
				iDebugUILayoutStrategy=1;
			}
			if(this.getFlagZ(IDebugUiZZZ.FLAGZ.DEBUGUI_PANELLIST_STRATEGIE_ENTRYDUMMY.name())) {
				iDebugUILayoutStrategy=iDebugUILayoutStrategy+2;
			}
			if(this.getFlagZ(IDebugUiZZZ.FLAGZ.DEBUGUI_PANELLIST_STRATEGIE_ENTRYLAST.name())) {
				iDebugUILayoutStrategy=iDebugUILayoutStrategy+4;
			}
			
			//Übergreifende Zählvariablen.
			int iLines2Show = 0; //Alle anzuzeigenden Label-Zeilen, ggf. mit leeren aufgefülllt.			
			int iLinesWithValue = 0; //Momentan anzuzeigende "gefüllte" Label-Zeilen
			int iLines2Fill = 0; //Die zum auffüllen verwendeten "leeren" Label-Zeilen
			int iLinesForDebugUI = 2; //Im DebugUI 2 Zeilen dazurechnen
			int iColumnsForDebugUI = 2; //Im DebugUI 2 Componenten in der Gruppe (1x Button, 1. Label)
			int iColumns2FillDebugUI = 0; //Die für die DebugUI zum Auffüllen benötigten Leer-Kompponenten.
			
			//SystemKey als Schluessel fuer die Section
			//Das KernelObject zu verwenden ist nicht sauber. Es muss ein eigenes Objekt fuer das zu konfigurierende Modul vorhanden sein.
			String sSystemKey = this.objKernel2configure.getSystemKey();
			
			//FileIniZZZ Objekt holen
			//Das KernelObject zu verwenden ist nicht sauber. Es muss ein eigenes Objekt fuer das zu konfigurierende Modul vorhanden sein.
			FileIniZZZ objFileIni = this.objKernel2configure.getFileConfigIniByAlias(sModule);
			
			//Im DebugUI Fall: Ausgehend von 2 Komponenten in der DebugUI Gruppe, für die fehlende Spalten mit Leerkomponente auffüllen.
			if(this.getFlagZ(IDebugUiZZZ.FLAGZ.DEBUGUI_PANELLABEL_ON.name())){ 
				iLinesWithValue = iLinesWithValue+iLinesForDebugUI; 
				iColumns2FillDebugUI = daProportion.length - iColumnsForDebugUI;
				
				for(int iCount=0; iCount < iColumns2FillDebugUI;iCount++) {
					JLabel labelEmpty = new JLabel(" ");
					this.add(labelEmpty);
				}
				
				
			}
			
			
			
			//Alle Einträge dieses Keys holen
			String[] saProperty = objFileIni.getPropertyAll(sSystemKey);
			if(saProperty==null){
				//Hinweis: Keine Eintraege gefunden
				iLines2Show = 1; //Die Hinweiszeile
				iLines2Fill = iNR_OF_TEXTFIELD_SHOWN_DEFAULT - iLines2Show;
				
				labelaIndex = new JLabel[1];				
				labelaText = new JLabel[1];
				textfieldaValue = new JTextField[1];
				
				labelaIndex[0] = new JLabel(" ");
				this.add(labelaIndex[0]);
				labelaText[0]= new JLabel("No property found.", SwingConstants.RIGHT);
				this.add(labelaText[0]);								
				textfieldaValue[0] = new JTextField("No value found.", iTEXTFIELD_COLUMN_DEFAULT);
				this.add(textfieldaValue[0]);
				
			}else{
				iLinesWithValue = saProperty.length;
				//Ausrechnen mit wievielen Leerlabelspalten das Layout ggfs aufgefüllt werden muss
				//Setzte Flags zum Anzeigen bestimmter Zeilen
				boolean bShowLineFirst=false;
				boolean bShowLineLast=false;
				boolean bShowLineDummy=false;
				boolean bUseStrategy=false;
				if(this.getFlagZ(IDebugUiZZZ.FLAGZ.DEBUGUI_PANELLABEL_ON.name())){
					int iStrategyChooserRest=iDebugUILayoutStrategy;
					
					while(iStrategyChooserRest != 0){	
						//Zerlege schrittweise die IntegerZahl-binär				
						int iStrategyChooser=iStrategyChooserRest/2;//Ergebnis wird durch 2 dividiert
						iStrategyChooserRest = iStrategyChooserRest % 2; //Den neuen Rest bestimmen.
						
						//Der gefundene Strategieteil
						iStrategyChooser=iStrategyChooser*2;						
						if(iStrategyChooser==0) {
							iStrategyChooser=iStrategyChooserRest;
							iStrategyChooserRest=0;//zum Abbrechen der Schleife
						}
						
						TODOGOON; //Die Strategiebehandlung auf die ArrayLösung umstellen
						//wandle den Strategieteil in Handlungsanweisungen um
						if(iStrategyChooser==1) {
							//Erste Zeile anzeigen
							bUseStrategy = true;
							bShowLineFirst = true;//iLinesWithValue = 1; //Damit es ggfs. besser aussieht, AUF 1 ZEILE BESCHNEIDEN
						}else if(iStrategyChooser==2) {
							//Dummy Zeile anzeigen
							bUseStrategy = true;
							bShowLineDummy = true;//iLinesWithValue = saProperty.length;
						}else if(iStrategyChooser==3){
							//Letzte Zeile anzeigen
							bUseStrategy = true;
							bShowLineLast = true;//iLinesWithValue = saProperty.length;
						}
												
						
					}
					
					/*
					 static void wandleDezInBin(){
		Scanner eingabe = new Scanner(System.in);//Scanner zur Speicherung der Eingabe
		System.out.println("Bitte gib eine Dezimalzahl ein!");//Aufforderung zur Eingabe
		
		int dezZahl = eingabe.nextInt();//Eingabe wird gespeichert
		int anzahlStellen=0;//Anzahl der Stellen der Dualzahl
		int dezZahlZwei=dezZahl;//Kopie der Zahl, da am Ende der while-Schleife die Zahl Null ist

		 //* While Schleife soll die Anzahl der Stellen bestimmen

		while (dezZahlZwei != 0){
			dezZahlZwei=dezZahlZwei / 2;//Zahl wird solange durch 2 dividiert bis 0 herauskommt
			anzahlStellen++;//Erhöhung der Zählvariablen
		}
		
		int zahlen[] = new int [anzahlStellen];//Array mit Länge der Zählvariablen
		

		 //* For Schleife füllt das Array mit den Restwerten
		
		for (int i = 0; i < anzahlStellen; i++){ 
                        zahlen[i]=dezZahl % 2; //Speichern der Restwerte im Array
                        dezZahl = dezZahl / 2; //Die Zahl wird immer wieder durch 2 dividiert
                    } 


               //* Die zweite for-Schleife liest das Array von hinten nach vorne 
              
                for (int i = anzahlStellen - 1; i >= 0; i--){
			System.out.print(zahlen[i]);
		}
	}
					 */
					
					iLines2Show = iLinesWithValue + iNR_OF_TEXTFIELD_SHOWN_DEBUG;
					iLines2Fill = 0;					
				}else {
					iLinesWithValue = saProperty.length;						
					if(iLinesWithValue >= iNR_OF_TEXTFIELD_SHOWN_DEFAULT){
						iLines2Show = iLinesWithValue;
						iLines2Fill = 0;  
					}else{					
						iLines2Show = iNR_OF_TEXTFIELD_SHOWN_DEFAULT;
						iLines2Fill = iNR_OF_TEXTFIELD_SHOWN_DEFAULT - iLinesWithValue;
					}
				}
				
				//DIE ARRAY GRÖSSEN VON DER ANZAHL DER GEFUNDENEN EINTRÄGE ABHÄNGIG MACHEN
				String sValue = new String("");
				labelaIndex = new JLabel[iLinesWithValue];
				labelaText = new JLabel[iLinesWithValue];
				textfieldaValue = new JTextField[iLinesWithValue];
				
				boolean bDummyLineShown=false;
				for(int iCount=0; iCount < iLinesWithValue; iCount++){
					boolean bShowLine=true;
					if(bUseStrategy){ 
						bShowLine=false;
						if(iCount==0 && bShowLineFirst) {
							bShowLine=true;
						}
						
						if(iCount==iLinesWithValue && bShowLineLast) {
							bShowLine=true;
						}						
					}					
					if(bShowLine) {
						//Das Index - Label
						labelaIndex[iCount] = new JLabel((Integer.toString(iCount+1)),SwingConstants.RIGHT);
						labelaIndex[iCount].setAlignmentX(Component.RIGHT_ALIGNMENT);
						labelaIndex[iCount].setSize(dimensionLabelColumnFirst);
						labelaIndex[iCount].setPreferredSize(dimensionLabelColumnFirst);
						this.add(labelaIndex[iCount]);
						
						//Das Property - Label
						labelaText[iCount]= new JLabel(saProperty[iCount] + "=", SwingConstants.RIGHT);
						labelaText[iCount].setAlignmentX(Component.RIGHT_ALIGNMENT);
						labelaText[iCount].setSize(dimensionLabel);
						labelaText[iCount].setPreferredSize(dimensionLabel);
						
						 // create a line border with the specified color and width
				        Border border = BorderFactory.createLineBorder(Color.BLUE, 2);
				        labelaText[iCount].setBorder(border);
						
						this.add(labelaText[iCount]);
						
						//Das Value - Textfeld										
						//Das KernelObject zu verwenden ist nicht sauber. Es muss ein eigenes Objekt fuer das zu konfigurierende Modul vorhanden sein.
						sValue = this.objKernel2configure.getParameterByModuleFile(objFileIni, saProperty[iCount]).getValue(); //Parameter);
						
						textfieldaValue[iCount] = new JTextField(sValue, iTEXTFIELD_COLUMN_DEFAULT);
						textfieldaValue[iCount].setAlignmentX(Component.LEFT_ALIGNMENT);
						textfieldaValue[iCount].setSize(dimensionTextfield);
						textfieldaValue[iCount].setPreferredSize(dimensionTextfield);
						this.add(textfieldaValue[iCount]);							
					}else{
						if(bShowLineDummy && !bDummyLineShown) {
							//Eine Dummy-Zeile mit Dummy-Werten ... anzeigen
							//Das Index - Label
							labelaIndex[iCount] = new JLabel("...",SwingConstants.RIGHT);
							labelaIndex[iCount].setAlignmentX(Component.RIGHT_ALIGNMENT);
							labelaIndex[iCount].setSize(dimensionLabelColumnFirst);
							labelaIndex[iCount].setPreferredSize(dimensionLabelColumnFirst);
							this.add(labelaIndex[iCount]);
							
							//Das Property - Label
							labelaText[iCount]= new JLabel("... =", SwingConstants.RIGHT);
							labelaText[iCount].setAlignmentX(Component.RIGHT_ALIGNMENT);
							labelaText[iCount].setSize(dimensionLabel);
							labelaText[iCount].setPreferredSize(dimensionLabel);
							
							 // create a line border with the specified color and width
					        Border border = BorderFactory.createLineBorder(Color.BLUE, 2);
					        labelaText[iCount].setBorder(border);
							
							this.add(labelaText[iCount]);
							
							//Das Value - Textfeld										
							//Das KernelObject zu verwenden ist nicht sauber. Es muss ein eigenes Objekt fuer das zu konfigurierende Modul vorhanden sein.
							sValue = "...";							
							textfieldaValue[iCount] = new JTextField(sValue, iTEXTFIELD_COLUMN_DEFAULT);
							textfieldaValue[iCount].setAlignmentX(Component.LEFT_ALIGNMENT);
							textfieldaValue[iCount].setSize(dimensionTextfield);
							textfieldaValue[iCount].setPreferredSize(dimensionTextfield);
							this.add(textfieldaValue[iCount]);
														
							bDummyLineShown=true;
						}						
					}//end if bShowLine
				}
			}//END IF: if(saProperty==null){
												
			Dimension dimensionTotal = new Dimension();
			dimensionTotal.width = (dimensionLabelColumnFirst.width + dimensionLabel.width + dimensionTextfield.width);
						
			dimensionTotal.height = 2*dimensionTextfield.height * iLinesWithValue;			
			this.setPreferredSize(dimensionTotal);
						
			//### Damit die Labels hinsichtlich der Hoehe nicht so gross werden, ggf. mit leeren Werten auffüllen
//			for(int icount = 0; icount < iLines2Fill; icount ++){
//				JLabel labeltemp = new JLabel("a", SwingConstants.RIGHT);
//				labeltemp.setSize(dimensionLabel);
//				labeltemp.setPreferredSize(dimensionLabel);
//				this.add(labeltemp);								
//				JLabel labeltemp2 = new JLabel("x", SwingConstants.LEFT);
//				labeltemp2.setSize(dimensionLabel);
//				labeltemp.setPreferredSize(dimensionLabel);
//				this.add(labeltemp2);	
//			}
			
			
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
