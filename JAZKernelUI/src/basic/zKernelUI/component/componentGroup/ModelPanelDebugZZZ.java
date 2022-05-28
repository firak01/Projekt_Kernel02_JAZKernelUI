package basic.zKernelUI.component.componentGroup;

import java.awt.Container;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JComponent;
import javax.swing.JLabel;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernelUI.component.IPanelCascadedZZZ;
import basic.zKernelUI.component.KernelJPanelCascadedZZZ;
import basic.zKernelUI.component.filler.IComponentFillerCreatorZZZ;
import basic.zKernelUI.component.filler.ModelComponentFillerCreatorFactoryZZZ;
import basic.zKernelUI.component.model.ModelComponentHelperZZZ;
import debug.zKernelUI.component.buttonSwitchLabelGroup.ModelRow2ZZZ;

public class ModelPanelDebugZZZ extends AbstractModelComponentGroupZZZ{	
	public ModelPanelDebugZZZ() {	
		super();
	}
	
	public ModelPanelDebugZZZ(IKernelZZZ objKernel, String sTitle, IPanelCascadedZZZ panelParent) throws ExceptionZZZ {
		super(objKernel, sTitle, panelParent);
	}
	
	public ModelPanelDebugZZZ(IKernelZZZ objKernel, String sTitle, IPanelCascadedZZZ panelParent, int iIndexInCollection) throws ModelComponentGroupExceptionZZZ, ExceptionZZZ {
		super(objKernel, sTitle,panelParent,iIndexInCollection);
	}
		
	//##############################
	@Override
	public IModelComponentGroupValueZZZ createModelForGroup(String sTitle, IPanelCascadedZZZ panelParent, int iIndexInGroupCollection) throws ModelComponentGroupExceptionZZZ, ExceptionZZZ {
		 return new ModelPanelDebugZZZ(this.getKernelObject(),sTitle, panelParent, iIndexInGroupCollection); 
	}
	
	@Override
	public HashMapIndexedZZZ<Integer,ArrayList<String>>createValuesText(String sTitle, IPanelCascadedZZZ panel, int iIndexInCollection) throws ModelComponentGroupExceptionZZZ{
		/* 20210702 WICHTIG, MERKE:
		 * Hier unbeding einen Eintrag in der Liste erzeugen, auch für die "nicht gefunden" Fälle.
		 *  
		 * Grund: Auch ein Dummy-Eintrag in der Liste erzeugt immer die Komponente, bzw. die ComponentGroup.
		 *        Falls nun das Modell später doch einen "echten Wert" findet, dann ist die Komponnente/ComponentGroup schon da
		 *        und kann mit dem korrekten Wert gefüllt werden.
		 * Beispiel: 
		 * Beim Erstellen eines Panels wird ja erst das Flag ISKERNELPROGRAM im Konstruktor erst erstellt.
		 * D.h. bis dahin wird kein Program / Programalias gefunden.
		 * Die ComponentGroup wird aber ggfs. danach erstellt, es würde also ggfs. keine erstellt, wenn nicht ein Dummy-Eintrag als Platzhalter eingefügt wird.
		 * 
		 * Beim Clicken auf den "Weiterschaltbutton" wird dann  jeweils das Modell für die ComponentGroups neu errechnet.
		 * Hier ist dann ggfs. das Flag ISKERNELPROGRAM schon gesetzt. 
		 * Damit würde der korrekte Programname/Alias verwendet und der zuvor gesetzte Dummy-Eintrag aktualisiert.
		 */
		HashMapIndexedZZZ<Integer,ArrayList<String>> hmReturn = null; 
			
		String stemp;
		main:{	
			try {
			hmReturn = new HashMapIndexedZZZ<Integer, ArrayList<String>>();
			ArrayList<String>listaTitle = new ArrayList<String>();
			listaTitle.add("Title:" + sTitle);
			
			int iLengthDefault=25;
			int iLengthDefaultRightOffset=2;
			switch(iIndexInCollection) {			
			case 0:
				{									
					//+++ 1. Klassenname des Panels				{
					stemp = panel.getClass().getSimpleName(); //das ist zu lang und nicht aussagekräftig genug String sParent = this.getClass().getSuperclass().getSimpleName();
					listaTitle.add(stemp);															
					hmReturn.put(listaTitle);
					
					//Hier: Für Spaltenorientierte Layouts, wird an dieser Stelle die Anzahl der Komponenten 
					//      aufgefüllt mit Leerkomponenten. Damit ist die Gesamtzahl der Komponenten
					//      ohne Rest teilbar durch die Anzahl der Spalten
					//Das bedeutet, das fuer unterschiedliche Layout-Manager die Anzahl der "Fuellkomponenten" auch unterschiedlich sein muss.
					LayoutManager objLayoutManager = ((Container) this.getPanelParent()).getLayout();
					System.out.println(objLayoutManager.getClass().getName());
					//TODOGOON; //20121219
					//IDEE: Nun eine Factory-Klasse anbieten, die mit den Details die Füllobjekte erstellt.
					//Dazu braucht man die Anzahl der Spalten und die Gesamtzahl der Komponenten
					//                 UND den verwendeten LayoutManager-Typ					
					IComponentFillerCreatorZZZ objFillerCreator = ModelComponentFillerCreatorFactoryZZZ.getInstance().getFillerCreatorObject(objLayoutManager);
					//ArrayList<String>listaTest = objFillerCreator.getFiller(iComponentAvailable, iColumnTotal);
					
					//A) Ohne LayoutManager, z.B. KernelJPanelCascadedZZZ /JPanel pur
					//Keine Füllkomponenten
					
					//B) EntryLayout4visibleZZZ
					//MERKE: HIER MUSS DIE ANZAHL DER KOMPONENTEN IN ALLEN CASE FAELLEN DURCH DIE ANZAHL DER SPALTEN TEILBAR SEIN,
					//       DABEI ZAEHLT DAS MAXIMUM. ALSO WENN IN EINEM CASE FALL NEUE KOMPONENTEN HINZUKOMMEN, NICHT UNBEDIMGT DIE ANZAHL ANPASSEN.
//					ArrayList<String>listaTest = new ArrayList<String>();
//					listaTest.add(" ");
//					hmReturn.put(listaTest);
//					
//					ArrayList<String>listaTest2 = new ArrayList<String>();
//					listaTest2.add(" ");
//					hmReturn.put(listaTest2);
//					
//					ArrayList<String>listaTest3 = new ArrayList<String>();
//					listaTest3.add(" ");
//					hmReturn.put(listaTest3);	
//					
//					ArrayList<String>listaTest4 = new ArrayList<String>();
//					listaTest4.add(" ");
//					hmReturn.put(listaTest4);	
					
					//C) GridLayout4VisibleZZZ (schon als Lösung für das normale GridLayout)
					//Problem: GridLayout hält Platz auch für verborgene Components
					//         https://stackoverflow.com/questions/7727728/invisible-components-still-take-up-space-jpanel
					//panel.setLayout(new GridLayout(6,2)); //6 Zeilen, 2 Spalten
					//Darum diese spezielle Variante dafür, nur für sichtbare Components	
					//MERKE: HIER MUSS DIE ANZAHL DER KOMPONENTEN IN ALLEN CASE FAELLEN GLEICH SEIN,
					//       ALSO WENN IN EINEM CASE FALL NEUE KOMPONENTEN HINZUKOMMEN, HIER DIE ANZAHL ANPASSEN.
					//Bei 2 Spalten, allerdings kein Füller, denn das wäre eine ganze neue Zeile
//					ArrayList<String>listaTest = new ArrayList<String>();
//					listaTest.add("A");
//					hmReturn.put(listaTest);
//					
//					ArrayList<String>listaTest2 = new ArrayList<String>();
//					listaTest2.add("B");
//					hmReturn.put(listaTest2);
					
				}
				break;				
			case 1:
				{				
					//+++ 2. Module, das zur Verfügung steht				
					String sModule = panel.getModuleName();	
					if(!StringZZZ.isEmpty(sModule)) {								
						//sModule = StringZZZ.abbreviateDynamicLeft(sModule, iLengthDefault+iLengthDefaultRightOffset);
						//Kürzen, wg. Platzmangel
						if(StringZZZ.contains(sModule, ".")) {
							sModule = StringZZZ.right(sModule, "."); //Weil ggfs. der Packagename auch im Programnamen enthalten ist.
							sModule = "... ."+ sModule;
						}
						sModule = StringZZZ.abbreviateDynamic(sModule, iLengthDefault);
						
						
						//!!! TODOGOON Wenn sich die Textlänge ständig ändert, dann verschieben sich ggfs. Nachbarpanels nach rechts aus dem Frame/der Dialogbox heraus.
						//    Daher müsste eigentlich auch der Frame/die Dialogbox neu "gepackt" werden (frame.pack() ).
											
						
						listaTitle.add("Module:" + sModule);
						hmReturn.put(listaTitle);
						
						ArrayList<String>listaTest = new ArrayList<String>();
						listaTest.add("TEST");
						listaTest.add("2. Zeile");
						hmReturn.put(listaTest);
					}else {
						listaTitle.add("Module: Not configured");
						hmReturn.put(listaTitle);
						
						//TESTE WEITERES LABEL
						//NEIN, Nicht löschen, damit würde auch die vorherige Liste geleert. listaReturn.clear();
						ArrayList<String>listaTest = new ArrayList<String>();
						listaTest.add("TEST");
						listaTest.add("Ein Testwert");
						hmReturn.put(listaTest);
					}
					
					//Hier: Für Spaltenorientierte Layouts, wird an dieser Stelle die Anzahl der Komponenten 
					//      aufgefüllt mit Leerkomponenten. Damit ist die Gesamtzahl der Komponenten
					//      ohne Rest teilbar durch die Anzahl der Spalten  
					//Das bedeutet, das fuer unterschiedliche Layout-Manager die Anzahl der "Fuellkomponenten" auch unterschiedlich sein muss.
					//A) EntryLayout4visibleZZZ
//					ArrayList<String>listaTest = new ArrayList<String>();
//					listaTest.add(" ");
//					hmReturn.put(listaTest);
//					
//					ArrayList<String>listaTest2 = new ArrayList<String>();
//					listaTest2.add(" ");
//					hmReturn.put(listaTest2);
//					
//					ArrayList<String>listaTest3 = new ArrayList<String>();
//					listaTest3.add(" ");
//					hmReturn.put(listaTest3);	
//					
//					ArrayList<String>listaTest4 = new ArrayList<String>();
//					listaTest4.add(" ");
//					hmReturn.put(listaTest4);
					
					//C) GridLayout4VisibleZZZ (schon als Lösung für das normale GridLayout)
					//Problem: GridLayout hält Platz auch für verborgene Components
					//         https://stackoverflow.com/questions/7727728/invisible-components-still-take-up-space-jpanel
					//panel.setLayout(new GridLayout(6,2)); //6 Zeilen, 2 Spalten
					//Darum diese spezielle Variante dafür, nur für sichtbare Components	
					ArrayList<String>listaTest = new ArrayList<String>();
					listaTest.add("B");
					hmReturn.put(listaTest);
				}
				break;
			case 2:
				 //+++ 3. Program, das zur Verfügung steht 
				{
					String sProgram = panel.getProgramName();
					if(!StringZZZ.isEmpty(sProgram)) {
						//Kürzen, wg. Platzmangel
						//sProgram = StringZZZ.abbreviateDynamicLeft(sProgram, iLengthDefault+iLengthDefaultRightOffset);
						if(StringZZZ.contains(sProgram, ".")) {
							sProgram = StringZZZ.right(sProgram, "."); //Weil ggfs. der Packagename auch im Programnamen enthalten ist.
							sProgram = "... ."+ sProgram;
						}
						//sProgram = StringZZZ.abbreviateDynamic(sProgram, iLengthDefault);
						
						listaTitle.add("Program: " + sProgram);
						hmReturn.put(listaTitle);
					}else {
						listaTitle.add("Program: Not configured");
						hmReturn.put(listaTitle);
					}
					
					//Hier: Für Spaltenorientierte Layouts, wird an dieser Stelle die Anzahl der Komponenten 
					//      aufgefüllt mit Leerkomponenten. Damit ist die Gesamtzahl der Komponenten
					//      ohne Rest teilbar durch die Anzahl der Spalten  
					//Das bedeutet, das fuer unterschiedliche Layout-Manager die Anzahl der "Fuellkomponenten" auch unterschiedlich sein muss.
					//A) EntryLayout4visibleZZZ
//					ArrayList<String>listaTest = new ArrayList<String>();
//					listaTest.add(" ");
//					hmReturn.put(listaTest);
//					
//					ArrayList<String>listaTest2 = new ArrayList<String>();
//					listaTest2.add(" ");
//					hmReturn.put(listaTest2);
//					
//					ArrayList<String>listaTest3 = new ArrayList<String>();
//					listaTest3.add(" ");
//					hmReturn.put(listaTest3);	
//					
//					ArrayList<String>listaTest4 = new ArrayList<String>();
//					listaTest4.add(" ");
//					hmReturn.put(listaTest4);	
					
					//C) GridLayout4VisibleZZZ (schon als Lösung für das normale GridLayout)
					//Problem: GridLayout hält Platz auch für verborgene Components
					//         https://stackoverflow.com/questions/7727728/invisible-components-still-take-up-space-jpanel
					//panel.setLayout(new GridLayout(6,2)); //6 Zeilen, 2 Spalten
					//Darum diese spezielle Variante dafür, nur für sichtbare Components	
					//Bei 2 Spalten, allerdings kein Füller, denn das wäre eine ganze neue Zeile
//					ArrayList<String>listaTest = new ArrayList<String>();
//					listaTest.add("A");
//					hmReturn.put(listaTest);
//					
//					ArrayList<String>listaTest2 = new ArrayList<String>();
//					listaTest2.add("B");
//					hmReturn.put(listaTest2);
				}
				break;
			case 3:
				//+++ 4. ProgramAlias, der ggfs. zur Verfügung steht
				{
					try {
					String sProgram = panel.getProgramName();									
					String sProgram4alias = panel.getProgramAlias();				
					if(!StringZZZ.isEmpty(sProgram4alias)) {						
						String sProgramAlias = panel.getProgramAlias();
						if(sProgram4alias.equals(sProgram)) {
							sProgramAlias="dito";
						}else {
							sProgramAlias = StringZZZ.abbreviateDynamicLeft(sProgramAlias, iLengthDefault+iLengthDefaultRightOffset);
							sProgramAlias = StringZZZ.abbreviateDynamic(sProgramAlias, iLengthDefault);
						}
						
						listaTitle.add("ProgramAlias: " + sProgramAlias);
						hmReturn.put(listaTitle);				
					}else {
						listaTitle.add("ProgramAlias: Not configured");
						hmReturn.put(listaTitle);
					}
					}catch(ExceptionZZZ ez) {						
					}
					
					//Hier: Für Spaltenorientierte Layouts, wird an dieser Stelle die Anzahl der Komponenten 
					//      aufgefüllt mit Leerkomponenten. Damit ist die Gesamtzahl der Komponenten
					//      ohne Rest teilbar durch die Anzahl der Spalten  
					//Das bedeutet, das fuer unterschiedliche Layout-Manager die Anzahl der "Fuellkomponenten" auch unterschiedlich sein muss.
					//A) EntryLayout4visibleZZZ
//					ArrayList<String>listaTest = new ArrayList<String>();
//					listaTest.add(" ");
//					hmReturn.put(listaTest);
//					
//					ArrayList<String>listaTest2 = new ArrayList<String>();
//					listaTest2.add(" ");
//					hmReturn.put(listaTest2);
//					
//					ArrayList<String>listaTest3 = new ArrayList<String>();
//					listaTest3.add(" ");
//					hmReturn.put(listaTest3);	
//					
//					ArrayList<String>listaTest4 = new ArrayList<String>();
//					listaTest4.add(" ");
//					hmReturn.put(listaTest4);	
					
					//C) GridLayout4VisibleZZZ (schon als Lösung für das normale GridLayout)
					//Problem: GridLayout hält Platz auch für verborgene Components
					//         https://stackoverflow.com/questions/7727728/invisible-components-still-take-up-space-jpanel
					//panel.setLayout(new GridLayout(6,2)); //6 Zeilen, 2 Spalten
					//Darum diese spezielle Variante dafür, nur für sichtbare Components	
					//Bei 2 Spalten, allerdings kein Füller, denn das wäre eine ganze neue Zeile
//					ArrayList<String>listaTest = new ArrayList<String>();
//					listaTest.add("A");
//					hmReturn.put(listaTest);
//					
//					ArrayList<String>listaTest2 = new ArrayList<String>();
//					listaTest2.add("B");
//					hmReturn.put(listaTest2);
				}
				break;
			default: 
				hmReturn = null; //Wenn eine Indexposition nicht existiert, null zurückgeben.				
				break main;
			}
			}catch(ExceptionZZZ ez) {
				ModelComponentGroupExceptionZZZ cme = new ModelComponentGroupExceptionZZZ(ez);
				throw cme;
			}
		}//end main:
		return hmReturn;
	}
	
}

