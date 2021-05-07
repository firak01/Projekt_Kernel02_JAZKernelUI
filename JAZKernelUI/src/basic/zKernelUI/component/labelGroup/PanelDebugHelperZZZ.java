package basic.zKernelUI.component.labelGroup;

import java.util.ArrayList;

import javax.swing.JLabel;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernelUI.component.KernelJPanelCascadedZZZ;

public class PanelDebugHelperZZZ {
	public static HashMapIndexedZZZ<Integer,ArrayList<JLabel>>createLabelHashMap(String sTitle, KernelJPanelCascadedZZZ panel) throws ExceptionZZZ {
		HashMapIndexedZZZ<Integer,ArrayList<JLabel>> hmReturn = new HashMapIndexedZZZ<Integer,ArrayList<JLabel>>();
		
		String stemp;
		main:{			
			//Labels hinzufuegen, in dem der Panel-Klassennamen steht (zu Debug- und Analysezwecken)
			ArrayList<JLabel>listaLabel;
			int iIndex=0;			
			listaLabel = PanelDebugHelperZZZ.createLabelArrayList(sTitle, panel, iIndex);
			if(listaLabel!=null) {				
				Integer intIndex = new Integer(iIndex);
				hmReturn.put(intIndex, listaLabel);
			
				while(listaLabel!=null) {
					iIndex++;
					listaLabel = PanelDebugHelperZZZ.createLabelArrayList(sTitle, panel, iIndex);
					if(listaLabel!=null) {
						intIndex = new Integer(iIndex);
						hmReturn.put(intIndex, listaLabel);
					}
					
				}
			}							
		}//end main
		return hmReturn;
	}
	public static ArrayList<JLabel>createLabelArrayList(String sTitle, KernelJPanelCascadedZZZ panel, int iIndex) throws ExceptionZZZ {
		ArrayList<JLabel>listaReturn=new ArrayList<JLabel>();//Auch wenn eine Indexposition nix füllen sollte, leere Liste zurückgeben.
		
		String stemp;
		main:{
			JLabel labelDebug;									
			ArrayList<String> listaText = new ArrayList<String>();
			listaText.add(sTitle);
			
			switch(iIndex) {			
			case 0:
				//+++ 1. Klassenname des Panels			
				stemp = panel.getClass().getSimpleName(); //das ist zu lang und nicht aussagekräftig genug String sParent = this.getClass().getSuperclass().getSimpleName();

				listaText.add(stemp);	
				labelDebug = PanelDebugHelperZZZ.createLabel(listaText);							
				if(labelDebug!=null) listaReturn.add(labelDebug);	
				break;
			case 1:
				//+++ 2. Module, das zur Verfügung steht
				String sModule = panel.getModuleName();	
				if(!StringZZZ.isEmpty(sModule)) {								
					sModule = StringZZZ.abbreviateDynamic(sModule, 10);//TODOGOON: StringZZZ Methode, um von rechts ausgehende abzukürzen.
					//!!! Wenn sich die Textlänge ständig ändert, dann verschieben sich ggfs. Nachbarpanels nach rechts aus dem Frame/der Dialogbox heraus.
					//    Daher müsste eigentlich auch der Frame/die Dialogbox neu "gepackt" werden (frame.pack() ).
										
					
					listaText.add("Module:" + sModule);
					labelDebug = PanelDebugHelperZZZ.createLabel(listaText);						
					if(labelDebug!=null) listaReturn.add(labelDebug);	
					
					
					//TESTE WEITERES LABEL
					listaText.clear();
					listaText.add("TEST");
					listaText.add("Ein Testwert");
					labelDebug = PanelDebugHelperZZZ.createLabel(listaText);						
					if(labelDebug!=null) listaReturn.add(labelDebug);	
					
				}
				break;
			case 2:
				 //+++ 3. Program, das zur Verfügung steht 
				String sProgram = panel.getProgramName();
				if(!StringZZZ.isEmpty(sProgram)) {
					sProgram = StringZZZ.abbreviateDynamic(sProgram, 10);//TODOGOON: von rechts abkürzen

					listaText.add("Program: " + sProgram);
					labelDebug = PanelDebugHelperZZZ.createLabel(listaText);						
					if(labelDebug!=null) listaReturn.add(labelDebug);					
				}else {
					listaText.add("Program: Not configured");
					labelDebug = PanelDebugHelperZZZ.createLabel(listaText);						
					if(labelDebug!=null) listaReturn.add(labelDebug);
				}
				break;
			case 3:
				//+++ 4. ProgramAlias, der ggfs. zur Verfügung steht
				JLabel labelDebug3 = null;
				String sProgram4alias = panel.getProgramName();
				if(!StringZZZ.isEmpty(sProgram4alias)) {
					String sProgramAlias = panel.getProgramAlias();
					if(sProgram4alias.equals(sProgramAlias)) {
						sProgramAlias="dito";
					}else {
						sProgramAlias = StringZZZ.abbreviateDynamic(sProgramAlias, 10);//TODOGOON: von rechts abkürzen
					}
					
					listaText.add("ProgramAlias: " + sProgramAlias);
					labelDebug = PanelDebugHelperZZZ.createLabel(listaText);						
					if(labelDebug!=null) listaReturn.add(labelDebug);					
				}
				break;
			default: 
				listaReturn = null; //Wenn eine Indexposition nicht existiert, null zurückgeben.
				break;
			}
		}//end main:
		return listaReturn;
	}
	
	public static JLabel createLabel(ArrayList<String>listaText) throws ExceptionZZZ {
		JLabel objReturn = null;
		main:{
			if(listaText==null) break main;
			if(listaText.isEmpty()) {
				objReturn = new JLabel("");
				break main;
			}
			
			String[]saParent=ArrayListZZZ.toStringArray(listaText);				
			String sHtml = StringArrayZZZ.asHtml(saParent);
											
			objReturn = new JLabel(sHtml);					
			
		}//end main:
		return objReturn;
	}
}
