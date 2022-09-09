package custom.zKernelUI.module.config.DLG;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Scrollbar;
import java.util.Calendar;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Hashtable;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
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
import basic.zBasic.persistence.interfaces.enums.KeyImmutable;
import basic.zBasic.util.datatype.binary.BinaryTokenizerZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.enums.EnumZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasicUI.layoutmanager.EntryLayout;
import basic.zBasicUI.layoutmanager.EntryLayout4VisibleZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelUserZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernelUI.KernelUIZZZ;
import basic.zKernelUI.component.IDebugUiZZZ;
import basic.zKernelUI.component.IPanelCascadedZZZ;
import basic.zKernelUI.component.KernelJPanelCascadedZZZ;
import basic.zKernelUI.module.config.DLG.strategy.DebugUIStrategyZZZ;
import basic.zKernelUI.module.config.DLG.strategy.EnumSetDebugUIStrategyUtilZZZ;
import basic.zKernelUI.module.config.DLG.strategy.IEnumDebugUIStrategyZZZ;
import basic.zKernelUI.module.config.DLG.strategy.IEnumSetDebugUIStrategyZZZ;
import basic.zKernelUI.module.config.DLG.strategy.DebugUIStrategyZZZ.EnumDebugUIStrategy;
import basic.zKernel.KernelZZZ;
import basic.zKernel.component.IKernelModuleUserZZZ;
import basic.zKernel.component.IKernelModuleZZZ;
import basic.zKernel.file.ini.KernelFileIniZZZ;
import custom.zKernel.LogZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public class Panel_CENTERZZZ extends KernelJPanelCascadedZZZ implements IKernelModuleZZZ, IComponentTableZZZ {
	private IKernelZZZ objKernel2configure;
	
	//Besonderes Panel. hat das Module intus... 
	protected IKernelModuleZZZ objModule2configure=null; //Das Modul, das zur Bearbeitung ausgewählt wurde.
	private String sModuleName2configure = null;
	
	private static final int iTEXTFIELD_COLUMN_DEFAULT = 10;             //Wie breit ein Textfeld sein soll
	private static final int iNR_OF_TEXTFIELD_SHOWN_DEFAULT = 10; //Wieviele Textfields angezeigt werden sollen
	private static final int iNR_OF_TEXTFIELD_SHOWN_DEBUG = 1; //Wieviele Textfields im DEBUG Fall angezeigt werden sollen.

	
	private JLabel[] labelaIndex = null;
	private JLabel[] labelaKey = null;
	private JButton[] buttonaKey = null;
	private JLabel[] labelaText = null;
	private JTextField[] textfieldaValue = null; 
	

	public Panel_CENTERZZZ(IKernelZZZ objKernel, JPanel panelParent, IKernelZZZ objKernelChoosen,  IKernelModuleZZZ objModuleChoosen, String sProgram) throws ExceptionZZZ {
		super(objKernel, panelParent);
		main:{
		try {					
			this.objKernel2configure = objKernelChoosen;//TODOGOON 20210310: Kann man kernelChoosen komplett durch ModuleChoosen ersetzen???? BZW. es sollte KernelChoosen das einzige KernelObjekt sein!!!
			this.setModuleChoosen(objModuleChoosen);//Merke 20210310: Das ist ggfs. auch ein ganz abstraktes Moduluobjekt, also nicht etwas, das konkret existiert wie z.B. ein anderes Panel.
			
			//PROBLEM: NULL!!!!!
			//String sModule = KernelUIZZZ.getModuleUsedName((IKernelModuleZZZ) this);//20210312 Hier KernelUIZZZ als Hilfsklasse verwenden, um den Modulnamen auszulesen. besser als: String sModule = this.getModule().getModuleName();
			
			//Border borderEtched = BorderFactory.createEtchedBorder();
			//this.setBorder(borderEtched);
			
			Border borderLine = BorderFactory.createLineBorder(Color.BLACK);
			this.setBorder(borderLine);
					
			//+++ Vor dem Anlegen der Components erst einmal pruefen, ob es ueberhaupt etwas zu tun gibt
			String sModuleChoosen = KernelUIZZZ.getModuleUsedName((IKernelModuleZZZ) objModuleChoosen);//20210312 Hier KernelUIZZZ als Hilfsklasse verwenden, um den Modulnamen auszulesen. besser als: String sModule = this.getModule().getModuleName();
									
			//Das KernelObject zu verwenden ist nicht sauber. Es muss ein eigenes Objekt fuer das zu konfigurierende Modul vorhanden sein.
			boolean bModuleConfigured = this.objKernel2configure.proofFileConfigModuleIsConfigured(sModuleChoosen);
			if(bModuleConfigured==false){
				break main;	//Fall: Modul nicht configuriert
			}else{
				//Das KernelObject zu verwenden ist nicht sauber. Es muss ein eigenes Objekt fuer das zu konfigurierende Modul vorhanden sein.
				boolean bModuleExists = this.objKernel2configure.proofFileConfigModuleExists(sModuleChoosen);
				if(bModuleExists==false){				
					break main;//Fall: Konfiguriertes Modul existiert nicht physikalisch als Datei am erwarteten Ort/mit dem erwarteten Namen
				}
			}
			
			
			//+++ Fonts festlegen
			//TODOGOON; //20211212
//			String sGuiButtonFontSize = null;									
//			IKernelConfigSectionEntryZZZ objEntry = objKernel.getParameterByProgramAlias(sModule, sProgramAlias, "GuiLabelFontSize_float" ); 
//			if(!objEntry.hasAnyValue()){
//				String serror = "Parameter existiert nicht in der Konfiguration: 'GuiLabelFontSize_float'";
//				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": " +serror);
//				ExceptionZZZ ez = new ExceptionZZZ(serror,ExceptionZZZ.iERROR_CONFIGURATION_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
//				throw ez;
//			}else{
//				sGuiLabelFontSize = objEntry.getValue();
//			}
//			if(StringZZZ.isEmpty(sGuiLabelFontSize)){ sGuiLabelFontSize="8.0"; }
//			Float fltGuiLabelFontSize = new Float(sGuiLabelFontSize);
//			
//			//Nun Graphics-Objekt holen, zum Font holen und Größe des Fonts einstellen.
//			//KernelJPanelCascadedZZZ objPanel = this.getPanelParent();				
//			//Graphics g = objPanel.getGraphics(); //!!! DAS GIBT IMMER NULL. Graphics Objekt steht nur in paint() Methode zur Verfügung.
//			
//			objReturn = new Font("Verdana", Font.PLAIN, fltGuiLabelFontSize.intValue());
//			
					
			//+++ Layout - Manager und Anzahl Spalten festlegen ++++++++++++
			
			//Problem: Suche nach dem passenden LayoutManager, der fest positioniert, aber unsichtbare ausblendet.
			//this.setLayout(new GridLayout(iLine2Show,2)); //1 Zeilen, 2 Spalten
			//EntryLayout layout = new EntryLayout(daProportion);
			
			double[] daProportion={0.1, 0.1, 0.3, 0.5};//Merke: Das wird zu WIDTH im Layout, die Anzahl der Spalten ist entsprechend der Proportionsparameter !!!
            EntryLayout4VisibleZZZ layout = new EntryLayout4VisibleZZZ(daProportion);				
            this.setLayout(layout);
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
			//Standardgrößen von Label und Textfeld
			Dimension dimensionLabelColumnFirst = new Dimension (20,20);
			Dimension dimensionLabelColumnSecond = new Dimension (30,20);
			Dimension dimensionButtonColumnSecond = new Dimension (30,20);
			Dimension dimensionLabel = new Dimension(150,20);			
			Dimension dimensionTextfield = new Dimension(200, 20);
								
			//Übergreifende Zählvariablen.
			int iLines2Show = 0; //Alle anzuzeigenden Label-Zeilen, ggf. mit leeren aufgefülllt.			
			int iLinesWithValue = 0; //Momentan anzuzeigende "gefüllte" Label-Zeilen
			int iLines2Fill = 0; //Die zum auffüllen verwendeten "leeren" Label-Zeilen
			int iLinesForDebugUI = 2; //Im DebugUI 2 Zeilen dazurechnen
			int iColumnsForDebugUI = 2; //Im DebugUI 2 Componenten in der Gruppe (1x Button, 1. Label)
			int iColumns2FillDebugUI = 0; //Die für die DebugUI zum Auffüllen benötigten Leer-Kompponenten.
			
			//SystemKey als Schluessel fuer die Section
			//Das KernelObject zu verwenden ist nicht sauber. Es muss ein eigenes Objekt fuer das zu konfigurierende Modul vorhanden sein.
			String sSystemKeyChoosen = this.objKernel2configure.getSystemKey();
			
			//FileIniZZZ Objekt holen
			//Das KernelObject zu verwenden ist nicht sauber. Es muss ein eigenes Objekt fuer das zu konfigurierende Modul vorhanden sein.
			FileIniZZZ objFileIni = this.objKernel2configure.getFileConfigModuleIni(sModuleChoosen);
			
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
			String[] saProperty = objFileIni.getPropertyAll(sSystemKeyChoosen);
			if(saProperty==null){
				//Hinweis: Keine Eintraege gefunden
				iLines2Show = 1; //Die Hinweiszeile
				iLines2Fill = iNR_OF_TEXTFIELD_SHOWN_DEFAULT - iLines2Show;
				
				labelaIndex = new JLabel[1];
				labelaKey = new JLabel[1];
				labelaText = new JLabel[1];
				textfieldaValue = new JTextField[1];
				buttonaKey = new JButton[1];
				
				labelaIndex[0] = new JLabel(" ");
				this.add(labelaIndex[0]);
				//labelaKey[0] = new JLabel(" ");
				//this.add(labelaKey[0]);
				buttonaKey[0] = new JButton(" ");
				this.add(buttonaKey[0]);
				
				
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
					
					//Übergreifende UI-DEBUG-LAYOUT Strategy
					//Erste Zeile anzeigen, Dummy Zeile anzeigen, Letzte Zeile anzeigen 			
					int iDebugUILayoutStrategy=EnumSetDebugUIStrategyUtilZZZ.computeEnumConstant_StrategyValueForFlagUser(this);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": DebugUIStrategyValue hat die Summme='"+iDebugUILayoutStrategy+"'");
													
					//Aus dem Strategiewert ein Array machen, damit sind sie ohne gezielte Abfrage "greifbar".
					boolean[] baStrategy = BinaryTokenizerZZZ.createBinaryTokens(iDebugUILayoutStrategy);
					if(baStrategy!=null) {						
						//Jetzt eine Schleife machen: Schleifenzähler mit der Rangzahl / ordinalzahl der Enumeration vergleichen
						
//++++++ START: KOMMENTARE AUS DOKUGRUENDEN STEHEN LASSEN.......						
						//Merke: Folgendes geht nicht, das in einer Switch Anweisung im case immer eine Konstante stehen muss
//						switch icount:
//						case Ordinalzahl der Strategy1:

//						case Ordinalzahl der Strategy2:
//							//Dummy Zeile anzeigen
//							
//						case Ordingalzahl der Strategy3:
//							//Letzte Zeile anzeigen	
						
						//Merke: So kann man alle EnumWerte durchgehen.
//						EnumSet<?> setEnum = DebugUIStrategyZZZ.getEnumSetUsedStatic();
//						Iterator<?> itEnum = setEnum.iterator();
//						while(itEnum.hasNext()) {
//							Enum<?> objEnum = (Enum<?>) itEnum.next();
//							
//							if(baStrategy.length>objEnum.ordinal()) {
//								if(baStrategy[objEnum.ordinal()]) {//Also, wenn die Strategy gesetzt/true ist:
//									//Das bringt aber nix, da für die Zuweisung der boolschen Steuerungsvariablen der Platz 1,2,3,... quasi fest ist.
//									//Erste Zeile anzeigen
//									//bUseStrategy = true;
//									//bShowLineFirst = true;
//								}
//							}
//						}
						
						//Merke: Der Entwicklungsweg zur unten verwendeten Schleife verlief u.a. über diesen statischeren Ausdruck
//						//int i1 = EnumDebugUIStrategy.S01.ordinal();
//						if(baStrategy.length>EnumDebugUIStrategy.S01.getIndex()) {
//						if(baStrategy[EnumDebugUIStrategy.S01.getIndex()]) {//Also, wenn die Strategy gesetzt/true ist:
//							//Erste Zeile anzeigen
//							bUseStrategy = true;
//							bShowLineFirst = true;
//						}
//						}
//						
//						if(baStrategy.length>EnumDebugUIStrategy.S02.getIndex()) {
							//Dummy Zeile anzeigen
//							...
//						}
//						
//						...
//									
//ENDE: KOMMENTARE AUS DOKUGRUENDEN STEHEN LASSEN.......
						
						//Das Durchgehen der EnumWerte und Casten UND keine statischen Werte verwenden, ausser den eh schon genutzten Flags!!!
						EnumSet<EnumDebugUIStrategy> setEnumCasted = (EnumSet<EnumDebugUIStrategy>) DebugUIStrategyZZZ.getEnumSetUsedStatic();
						Iterator<EnumDebugUIStrategy> itEnumCasted = setEnumCasted.iterator();
						while(itEnumCasted.hasNext()) {
							Enum<EnumDebugUIStrategy> objEnum = (Enum<EnumDebugUIStrategy>) itEnumCasted.next();
							int iIndex = ((IEnumSetDebugUIStrategyZZZ) objEnum).getIndex();
							if(baStrategy.length>iIndex) {													
								if(baStrategy[iIndex]) {//Also, wenn die Strategy gesetzt/true ist:
									//Die Zuweisung der boolschen Steuerungsvariablen der Platz 1,2,3,... am Flagnamen festmachen.								
									if(((IEnumSetDebugUIStrategyZZZ) objEnum).getStrategyFlagName().equals(IDebugUiZZZ.FLAGZ.DEBUGUI_PANELLIST_STRATEGIE_ENTRYFIRST.name())) {//Also, wenn es sich um die EntryFirst-Strategy handelt:									
										//Erste Zeile anzeigen
										bUseStrategy = true;
										bShowLineFirst = true;
									}else if(((IEnumSetDebugUIStrategyZZZ) objEnum).getStrategyFlagName().equals(IDebugUiZZZ.FLAGZ.DEBUGUI_PANELLIST_STRATEGIE_ENTRYDUMMY.name())) {//Also, wenn es sich um die EntryDummy-Strategy handelt:									
										//Dummy Zeile anzeigen
										bUseStrategy = true;
										bShowLineDummy = true;
									}else if(((IEnumSetDebugUIStrategyZZZ) objEnum).getStrategyFlagName().equals(IDebugUiZZZ.FLAGZ.DEBUGUI_PANELLIST_STRATEGIE_ENTRYLAST.name())) {//Also, wenn es sich um die EntryLast-Strategy handelt:
										//Letzte Zeile anzeigen
										bUseStrategy = true;
										bShowLineLast = true;//iLinesWithValue = saProperty.length;																		
									}
								}
							}
						}//end while																																							
					}//end if baStrategy!=null
					
					//Zwei zusätzliche Zeilen voranstellen für den Button und das Label.
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
				labelaKey = new JLabel[iLinesWithValue];
				buttonaKey = new JButton[iLinesWithValue];
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
						
						if(iCount==(iLinesWithValue-1) && bShowLineLast) {
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

						
						//Neu: Statische Methode anbieten. Merke: Die wird auch beim Speichern verwendet.
						String sSectionUsed = KernelFileIniZZZ.getSectionUsedForPropertyBySystemKey(objFileIni, sSystemKeyChoosen, saProperty[iCount]);
						
						//Key - Eintrag: //20211208 Hier den Application Key oder die SystemNr. ausgeben, ja nachdem woher der Wert stammt.
						String sKeyUsed = null;
						//TODOGOON 20211212; ActionEditProperty objActionEditProperty = null;
						if(KernelFileIniZZZ.isSectionWithSystemNrAny(sSectionUsed)){
							sKeyUsed = this.objKernel2configure.getSystemNumber();
//							objActionEditProperty = new ActionEditProperty_SYSTEM_REMOVE();//Also wenn man hierauf clickt den ApplicationWert anzeigen, den SystemWert entfernen.
							
						}else {
							sKeyUsed = this.objKernel2configure.getApplicationKey();
//							objActionEditProperty = new ActionEditProperty_SYSTEM_ADD();//Also wenn man hierauf clickt den SystemWert "default" als neuen Wert mit dem ApplicationWert-Eintrag anzeigen.
						}
						
						
						//Der Key als Button
						buttonaKey[iCount] = new JButton(sKeyUsed);
						buttonaKey[iCount].setAlignmentX(Component.RIGHT_ALIGNMENT);
						buttonaKey[iCount].setSize(dimensionButtonColumnSecond);
						buttonaKey[iCount].setPreferredSize(dimensionButtonColumnSecond);						
						//buttonaKey[iCount].addActionListener(objActionEditProperty);
						this.add(buttonaKey[iCount]);
						
						//Der Key als Label
//						labelaKey[iCount] = new JLabel(sKeyUsed,SwingConstants.RIGHT);
//						labelaKey[iCount].setAlignmentX(Component.RIGHT_ALIGNMENT);
//						labelaKey[iCount].setSize(dimensionLabelColumnSecond);
//						labelaKey[iCount].setPreferredSize(dimensionLabelColumnSecond);
//						this.add(labelaKey[iCount]);
					
						
						
						
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
							
							//Das Key - Label
							labelaKey[iCount] = new JLabel("...",SwingConstants.RIGHT);
							labelaKey[iCount].setAlignmentX(Component.RIGHT_ALIGNMENT);
							labelaKey[iCount].setSize(dimensionLabelColumnFirst);
							labelaKey[iCount].setPreferredSize(dimensionLabelColumnFirst);
							this.add(labelaKey[iCount]);
							
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
	public IKernelModuleZZZ getModuleChoosen() {
		return this.objModule2configure;
	}
	public void setModuleChoosen(IKernelModuleZZZ objModule) {
		this.objModule2configure = objModule;
	}
	
	public String getModuleChoosenName() throws ExceptionZZZ {
		String sReturn = new String("");
		main:{	
			//TODOGOON; //20210310: Jetzt gibt es ja noch ggfs. ein Abstraktes Module-Objekt.
			//                    Wenn das abstrakte Modul Objekt vorhanden ist, dann den ModulNamen daraus verwenden.
			//                    Ist das abstrakte Modul Objekt nicht vorhanden, dann den Modulnamen wie bisher anhand des Panels selbst ermitteln.				
			if(StringZZZ.isEmpty(this.sModuleName)) {
				if(this.getModuleChoosen()!=null) {
					this.sModuleName = KernelUIZZZ.getModuleUsedName((IKernelModuleZZZ)this.getModuleChoosen());
				}else {
					this.sModuleName = KernelUIZZZ.getModuleUsedName((IPanelCascadedZZZ)this);
				}
			}
			sReturn = this.sModuleName;
		}//end main
		return sReturn;
	}
		
			
			//####### EIGENE METHODEN ###########
			/* Das ist die Variante für Entities, die nicht mit der Annotation "Immutable" versehen sind.
			* Die Entities mit der Annotation "Immutable" haben nämlich keine setter-Methoden.
			*/
			//Da Java nur ein CALL_BY_VALUE machen kann, weden hier für die eingefüllten Werte Referenz-Objekte verwendet.
			//Erst die normalen Enum-Werte, dann ... sUniquetext / sCategorytext / iMoveRange / sImageUrl / iThisKeyDefaulttext / iThiskeyImmutabletext;
				//
				//Merke: objValue - Klasse ist ein Dummy Objekt, damit man auf die als Innere Klasse deklarierte Enumeration kommt.
			protected <T, IEnumDebugUIStrategyZZZ> void _fillValueImmutableByEnumAlias(IEnumDebugUIStrategyZZZ objValue,String sEnumAlias, ReferenceZZZ<Long> objlngThiskey, 
				ReferenceZZZ<String> objsName, ReferenceZZZ<String> objsUniquetext, ReferenceZZZ<String> objsCategorytext, 
				ReferenceZZZ<Integer> objintStrategyValue
				){

				//Merke: Direktes Reinschreiben geht wieder nicht wg. "bound exception"
				//EnumSetDefaulttextUtilZZZ.getEnumConstant_DescriptionValue(EnumSetDefaulttextTestTypeTHM.class, sEnumAlias);
						
				//Also: Klasse holen und danach CASTEN.
				Class<?> objClass = ((KeyImmutable) objValue).getThiskeyEnumClass();
				Long lngThiskey = EnumSetDebugUIStrategyUtilZZZ.readEnumConstant_ThiskeyValue((Class<IEnumSetDebugUIStrategyZZZ>) objClass, sEnumAlias);//Das darf nicht NULL sein, sonst Fehler. Über diesen Schlüssel wird der Wert dann gefunden.
				System.out.println("Gefundener Thiskey: " + lngThiskey.toString());
				objlngThiskey.set(lngThiskey); //Damit wird CALL_BY_VALUE quasi gemacht....
				
				String sName = EnumSetDebugUIStrategyUtilZZZ.readEnumConstant_NameValue((Class<IEnumSetDebugUIStrategyZZZ>) objClass, sEnumAlias);
				System.out.println("Gefundener Typname: " + sName);
				objsName.set(sName); //Damit wird CALL_BY_VALUE quasi gemacht....
				
				String sUniquetext = EnumSetDebugUIStrategyUtilZZZ.readEnumConstant_UniquetextValue((Class<IEnumSetDebugUIStrategyZZZ>)objClass, sEnumAlias);
				System.out.println("Gefundener Uniquewert: " + sUniquetext);
				objsUniquetext.set(sUniquetext); //Damit wird CALL_BY_VALUE quasi gemacht....
				
				String sCategorytext = EnumSetDebugUIStrategyUtilZZZ.readEnumConstant_CategorytextValue((Class<IEnumSetDebugUIStrategyZZZ>)objClass, sEnumAlias);
				System.out.println("Gefundener Categorytext: " + sCategorytext);
				objsCategorytext.set(sCategorytext); //Damit wird CALL_BY_VALUE quasi gemacht....						
			}
			
}//end class
