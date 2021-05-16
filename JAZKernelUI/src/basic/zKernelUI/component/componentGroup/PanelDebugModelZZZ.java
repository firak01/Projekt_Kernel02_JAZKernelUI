package basic.zKernelUI.component.componentGroup;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JComponent;
import javax.swing.JLabel;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernelUI.component.IComponentValueModelZZZ;
import basic.zKernelUI.component.KernelJPanelCascadedZZZ;
import basic.zKernelUI.component.model.ComponentModelHelperZZZ;

public class PanelDebugModelZZZ extends AbstractComponentSwitchModelZZZ{	
	public PanelDebugModelZZZ() {	
		super();
	}
	
	public PanelDebugModelZZZ(String sTitle, KernelJPanelCascadedZZZ panelParent, int iIndexInCollection) {
		super(sTitle,panelParent,iIndexInCollection);
	}
		
	//##############################		
	public HashMapIndexedZZZ<Integer,ArrayList<String>>createValuesText(String sTitle, KernelJPanelCascadedZZZ panel, int iIndexInCollection) throws ExceptionZZZ{
		HashMapIndexedZZZ<Integer,ArrayList<String>> hmReturn = new HashMapIndexedZZZ<Integer, ArrayList<String>>(); 
				
		String stemp;
		main:{	
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
				}
				break;				
			case 1:
				{				
				//+++ 2. Module, das zur Verfügung steht
				
					String sModule = panel.getModuleName();	
					if(!StringZZZ.isEmpty(sModule)) {								
						sModule = StringZZZ.abbreviateDynamicLeft(sModule, iLengthDefault+iLengthDefaultRightOffset);
						sModule = StringZZZ.abbreviateDynamic(sModule, iLengthDefault);
						
						
						//!!! TODOGOON Wenn sich die Textlänge ständig ändert, dann verschieben sich ggfs. Nachbarpanels nach rechts aus dem Frame/der Dialogbox heraus.
						//    Daher müsste eigentlich auch der Frame/die Dialogbox neu "gepackt" werden (frame.pack() ).
											
						
						listaTitle.add("Module:" + sModule);
						hmReturn.put(listaTitle);
						
												
						//TESTE WEITERES LABEL
						//NEIN, Nicht löschen, damit würde auch die vorherige Liste geleert. listaReturn.clear();
						ArrayList<String>listaTest = new ArrayList<String>();
						listaTest.add("TEST");
						listaTest.add("Ein Testwert");
						hmReturn.put(listaTest);						
					}
				}
				break;
			case 2:
				 //+++ 3. Program, das zur Verfügung steht 
				{
					String sProgram = panel.getProgramName();
					if(!StringZZZ.isEmpty(sProgram)) {
						sProgram = StringZZZ.abbreviateDynamicLeft(sProgram, iLengthDefault+iLengthDefaultRightOffset);
						sProgram = StringZZZ.abbreviateDynamic(sProgram, iLengthDefault);
						
						listaTitle.add("Program: " + sProgram);
						hmReturn.put(listaTitle);
					}else {
						listaTitle.add("Program: Not configured");
						hmReturn.put(listaTitle);
					}
				}
				break;
			case 3:
				//+++ 4. ProgramAlias, der ggfs. zur Verfügung steht
				{
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
					}
				}
				break;
			default: 
				hmReturn = null; //Wenn eine Indexposition nicht existiert, null zurückgeben.				
				break;
			}
			
		}//end main:
		return hmReturn;
	}
	
}

