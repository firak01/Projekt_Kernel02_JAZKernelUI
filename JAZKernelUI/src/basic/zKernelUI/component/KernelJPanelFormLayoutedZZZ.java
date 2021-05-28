package basic.zKernelUI.component;

import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JTextField;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.Sizes;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zKernel.IKernelZZZ;

public abstract class KernelJPanelFormLayoutedZZZ extends KernelJPanelCascadedZZZ implements IFormLayoutZZZ, IFormLayoutUserZZZ{
	
	protected FormLayout formLayoutUsed = null;
	ArrayList<ColumnSpec> listFormLayoutColumnSpec = null;
	ArrayList<RowSpec> listFormLayoutRowSpec = null;
	
	public KernelJPanelFormLayoutedZZZ() {
		super();
	}
	
	public KernelJPanelFormLayoutedZZZ(IKernelZZZ objKernel, KernelJDialogExtendedZZZ dialogExtended) throws ExceptionZZZ{
		super(objKernel, dialogExtended);
		KernelJPanelFormLayoutedNew_(this);
	}
	
	public KernelJPanelFormLayoutedZZZ(IKernelZZZ objKernel, KernelJDialogExtendedZZZ dialogExtended, KernelJPanelCascadedZZZ panelRoot) throws ExceptionZZZ{
		super(objKernel, dialogExtended);
		KernelJPanelFormLayoutedNew_(panelRoot);
	}
	
	private boolean KernelJPanelFormLayoutedNew_(KernelJPanelCascadedZZZ panelRoot) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			//##################################################################
			//### Definition des Masken UIs
			//###
			//Diese einfache Maske besteht nur aus 1 Zeile und 4 Spalten. 
			//Es gibt außen einen Rand von jeweils einer Spalte/Zeile
			//Merke: gibt man pref an, so bewirkt dies, das die Spalte beim ver�ndern der Fenstergröße nicht angepasst wird, auch wenn grow dahinter steht.
			
			//erster Parameter sind die Spalten/Columns (hier: vier 5dlu), als Komma getrennte Eintraege. .
			//zweiter Parameter sind die Zeilen/Rows (hier:  drei), Merke: Wenn eine feste L�nge k�rzer ist als der Inhalt, dann wird der Inhalt als "..." dargestellt
			//FormLayout layout = new FormLayout(
			//	"5dlu, right:pref:grow(0.5), 5dlu:grow(0.5), left:50dlu:grow(0.5), 5dlu, center:pref:grow(0.5),5dlu",  
			//	"5dlu, center:10dlu, 5dlu"); 		
			//
			////TESTTEST TODOGOON
			//	RowSpec rs = new RowSpec(Sizes.dluX(14));
			////	 new RowSpec(RowSpec.CENTER, Sizes.dluX(14), 0.0);
			////	 new RowSpec(RowSpec.CENTER, Sizes.dluX(14), RowSpec.NO_GROW);
			////	 RowSpec.parse("14dlu");
			////	 RowSpec.parse("14dlu:0");
			////	 RowSpec.parse("center:14dlu:0");
			//layout.insertRow(1, rs);//RowIndex beginnt mit 1						
		}//end main:
		return bReturn;
	}
	
	public int computeContentRowNumberUsed(int iRow) {
		int iReturn=0;
		main:{
			int iRowOffset=0;
			if(this.getFlag(IDebugUiZZZ.FLAGZ.DEBUGUI_PANELLABEL_ON.name())) {
				iRowOffset=1;
			}
			iReturn = iRow + iRowOffset;
		}//end main:
		return iReturn;
	}
	
	//##### IFormLayoutZZZ #########
	//weitere Methoden müssen dann von dem konkreten FormLayouted nutzenden Panel implementiert werden.
		@Override 
		public boolean initFormLayoutDebug() throws ExceptionZZZ {
			boolean bReturn = false;
			main:{					
				FormLayout layout = this.getFormLayoutUsed();
				if(layout!=null) {
					this.setLayout(layout);              //!!! wichtig: Das layout muss dem Panel zugewiesen werden BEVOR mit constraints die Componenten positioniert werden.
					CellConstraints cc = new CellConstraints();
					this.fillRowDebug(cc);
					
					//PROBLEM: Problem, wenn auf Daten über einen Programmnamen zugegriffen werden soll, der erst später über das FlagSetzen definiert wird., der aber erst später als FlagGesetzt wird.
					//         Also hier nie den Content füllen. Sondern das immer der konkreten PanelKlasse überlassen.
					//this.fillRowContent(cc, 1);
					
					bReturn = true;
				}
			}//end main:
			return bReturn;
		}
		
		@Override
		public boolean initFormLayoutContent() throws ExceptionZZZ {
			boolean bReturn = false;
			main:{					
				FormLayout layout = this.getFormLayoutUsed();
				if(layout!=null) {
					this.setLayout(layout);              //!!! wichtig: Das layout muss dem Panel zugewiesen werden BEVOR mit constraints die Componenten positioniert werden.
					CellConstraints cc = new CellConstraints();
					//Das wird in der Elternklasse schon gemacht:     this.fillRowDebug(cc);
					
					//PROBLEM: Problem, wenn auf Daten über einen Programmnamen zugegriffen werden soll, der erst später über das FlagSetzen definiert wird., der aber erst später als FlagGesetzt wird.
					//         Also hier nie den Content füllen. Sondern das immer der konkreten PanelKlasse überlassen.
					this.fillRowContent(cc, 1);					
					bReturn = true;
				}
			}//end main:
			return bReturn;
		}
		
		@Override
		public FormLayout getFormLayoutUsed() {
			//Wenn man das rein im Konstruktor erstellt, z.B.:
			//Erste Zeile sind die Spalten
			//Zweite Zeile sind die Zeilen (hier immer mit einer "Zwischenzeile",zum Abstand halten)
			//FormLayout layout = new FormLayout(
			//"5dlu, right:pref:grow(0.5), 5dlu:grow(0.5), left:50dlu:grow(0.5), 5dlu, center:pref:grow(0.5),5dlu",  
			//"5dlu, center:10dlu, 5dlu"); 
			
			if(this.formLayoutUsed==null) {
				this.formLayoutUsed = this.buildFormLayoutUsed();
			}
			return this.formLayoutUsed;
		}
		@Override
		public void setFormLayoutUsed(FormLayout formLayout) {
			this.formLayoutUsed = formLayout;
		}
		@Override
		public FormLayout buildFormLayoutUsed() {
			FormLayout objReturn = new FormLayout();
			main:{		
				ArrayList<RowSpec> listRow = this.getRowSpecs();
				if(listRow!=null) {
					for(RowSpec row:listRow) {
						objReturn.appendRow(row);
					}
				}
				
				RowSpec rowDebug = this.buildRowSpecDebug();
				if(rowDebug!=null && listRow!=null) {				
					objReturn.insertRow(1, rowDebug);//RowIndex beginnt mit 1
				}
				
				ArrayList<ColumnSpec>listColumn = this.getColumnSpecs();
				if(listColumn!=null) {
					for(ColumnSpec column:listColumn) {
						objReturn.appendColumn(column);
					}
				}
			}//end main;
			return objReturn;
		}
		
		@Override
		public ArrayList<ColumnSpec> getColumnSpecs(){
			if(this.listFormLayoutColumnSpec==null) {
				this.listFormLayoutColumnSpec = this.buildColumnSpecs();
			}
			return this.listFormLayoutColumnSpec;
		}
		
		@Override
		public ArrayList<RowSpec> getRowSpecs(){
			if(this.listFormLayoutRowSpec==null) {
				this.listFormLayoutRowSpec = this.buildRowSpecs();
			}
			return this.listFormLayoutRowSpec;
		}
			
		@Override
		public boolean fillRowDebug(CellConstraints cc) throws ExceptionZZZ{
			boolean bReturn = false;
			main:{			
				int iStartingRow = 1; //Die Debugzeile ist immer oben
				int iStartingColumn = 1; //Beginne immer in Spalte 1. Die Gesamtanzahl der Spalten wird dann als "Breite" genommen.
				
				ArrayList<ColumnSpec>listCs=this.getColumnSpecs();
				if(listCs==null)break main;
				
				int iColumns = listCs.size(); 

				String stemp = this.getClass().getSimpleName();
				//das ist zu lange und nicht aussagekräftig genug String sParent = this.getClass().getSuperclass().getSimpleName();
				ArrayList<String> listaParent = new ArrayList<String>();
				listaParent.add("FormLayouted");
				listaParent.add(stemp);
				
				String[]saParent=ArrayListZZZ.toStringArray(listaParent);				
				String sHtml = StringArrayZZZ.asHtml(saParent);	
				
				JLabel labelDebug = new JLabel(sHtml);
				labelDebug.setHorizontalAlignment(JTextField.LEFT);
				//this.add(labelDebug, cc.xyw(iStartingColumn,iStartingRow, iColumns));
				this.add(labelDebug, cc.xy(iStartingColumn,0)); 
				
				bReturn = true;
			}//end main;
			return bReturn;
		}
		
		public RowSpec buildRowSpecDebug() {
			RowSpec rs = new RowSpec(Sizes.dluX(14));
			return rs;
		}
		
		//+++ Von KernelJPanelCascadedZZZ überschrieben. Jetzt müssen die Debug-Komponenten auf ein Startzeile verteilt werden.
		@Override
		public boolean createDebugUi(String sTitle) throws ExceptionZZZ {
			boolean bReturn = false;
			main:{
				String stemp;
						
				if(this.getFlagZ(IDebugUiZZZ.FLAGZ.DEBUGUI_PANELLABEL_ON.name())) {
					//this.initFormLayoutDebug();
					
		/*			
									
					//20210419 Bei vielen Zeilen im Label "verwischt" dann das UI
					//Idee: Führe eine "Label-Gruppe" ein und einen Button, der diese Labels dann der Reihe nach durchschalten kann.

					//Die Anzahl der Texteinträge bestimmt die Anzahl der JLabel Objekte, bestimmt die Anzahl der Gruppen.
					//Mit einer einfachen ArrayList kann aber immer nur 1 Label pro Button definiert werden. Es muss eine Indizierte HashMap sein.
					//Teste mit mehreren Labels pro Gruppe.
					
					//Die Action als eigene Klasse ausgliedern und alle beteiligten Klassen in ein passendes Package verschieben.
			        //Den Debug/Testpanel für die Gruppenumschaltung soll dann auch diese nutzen.

					//Die Verwaltung der HashMap für die Componenten einer ComponentGroupCollection übertragen.	
					//Darin auch den Eventhandler/Eventbroker, etc. hinzufügen.
					
					//20210507 Vereinheitlichung die Definition der ComponentGroup im Debug-Test-Fall und im KernelJPanelCascadedZZZ.createDebugUI();

					//20210514 Modell hinzugefügt.
					          //Wg. Problematik der Reihenfolge der Panels hinzuzufügen 
					          //und daraufhin Probleme beim korrekten/gleichen ermitteln des Programnamens
							  //==> Bei jedem Umschalten die Werte der Componenten/Labels neu errechnen
					          //    und neu füllen.
							
					//TODOGOON; //Ein Button zum Umschalten ist auch erst im Panel notwendig, wenn es mehr als 1 Gruppenobjekt gibt.
																	
					HashMapIndexedZZZ<Integer,ArrayList<JComponent>>hmComponent;
					PanelDebugModelZZZ modelDebug = new PanelDebugModelZZZ();
					hmComponent = modelDebug.createComponentHashMap(sTitle, this);
										
													
					//+++ Die Labels auf die Gruppen verteilen
					ArrayList<JComponentGroupZZZ>listaGroup = new ArrayList<JComponentGroupZZZ>();				
					int iIndex=-1;
					
					Iterator itListaComponent = hmComponent.iterator();
					while(itListaComponent.hasNext()) {
						ArrayList<JComponent>listaComponenttemp = (ArrayList<JComponent>) itListaComponent.next();
						
						iIndex=iIndex+1;						
						String sIndexAsAlias = Integer.toString(iIndex);
						IComponentGroupValueModelZZZ objValueProvider = new PanelDebugModelZZZ(objKernel, "Cascaded", this, iIndex); //Diese Modell wird bei jedem "Click" in dem refresh() aufgerufen.
						JComponentGroupZZZ grouptemp = new JComponentGroupZZZ(objKernel, sIndexAsAlias, objValueProvider,listaComponenttemp);
						if(grouptemp.hasAnyComponentAdded()) {
							listaGroup.add(grouptemp);
						}								
					}
					
					//++++ Die GroupCollection
					JComponentGroupCollectionZZZ groupc = new JComponentGroupCollectionZZZ(objKernel, listaGroup);
					groupc.setVisible(0); //Initiales Setzen der Sichtbarkeit
					
							
					//######## Das UI gestalten. Die Reihenfolge der Componenten ist wichtig für die Reihenfolge im UI #################
					//++++ Der Umschaltebutton
					String sLabelButton = ">";//this.getKernelObject().getParameterByProgramAlias(sModule, sProgram, "LabelButton").getValue();
					JButton buttonSwitch = new JButton(sLabelButton);	
								
					ActionSwitchZZZ actionSwitch = new ActionSwitchZZZ(objKernel, this, groupc);
					buttonSwitch.addActionListener(actionSwitch);								
					this.setComponent(KernelJPanelCascadedZZZ.sBUTTON_SWITCH, buttonSwitch);				
					this.add(buttonSwitch);
					
					int iIndexOuterMax = hmComponent.size() -1;
					for(int iIndexOuter=0; iIndexOuter <= iIndexOuterMax; iIndexOuter++) {
						ArrayList<JComponent>listaComponenttemp = (ArrayList<JComponent>) hmComponent.getValue(iIndexOuter);
						if(listaComponenttemp!=null) {
							
							//Die Labels der Arraylist abarbeiten und dem panel hinzufügen
							int iIndexInner=-1;				
							for(JComponent componenttemp : listaComponenttemp) {
								if(componenttemp!=null) {
									iIndexInner=iIndexInner+1;
									this.add(componenttemp);
									this.setComponent("ComponentDebug"+iIndexOuter+"_"+iIndexInner, componenttemp);
								}
							}		
						}
					}	
					*/															
				}						
			}//end main:
			return bReturn;		
		}		
		
		//+++ von den eingentlichen Klassen zu implementieren
		public abstract ArrayList<RowSpec> buildRowSpecs();
		public abstract ArrayList<ColumnSpec> buildColumnSpecs();
		public abstract boolean fillRowContent(CellConstraints cc, int iRow) throws ExceptionZZZ;
		
		//###### 
}
