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
import basic.zKernelUI.component.KernelJPanelCascadedZZZ;

public class PanelDebugHelperZZZ {
	public static HashMapIndexedZZZ<Integer,ArrayList<JComponent>>createComponentHashMap(String sTitle, KernelJPanelCascadedZZZ panel) throws ExceptionZZZ {
		HashMapIndexedZZZ<Integer,ArrayList<JComponent>> hmReturn = new HashMapIndexedZZZ<Integer,ArrayList<JComponent>>();
		
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
	public static ArrayList<JLabel>createLabelArrayList(String sTitle, KernelJPanelCascadedZZZ panel, int iIndexInCollection) throws ExceptionZZZ {
		ArrayList<JLabel>listaReturn=null;//Auch wenn eine Indexposition nix füllen sollte, leere Liste zurückgeben.
		main:{
			JLabel labelDebug;									
			
			HashMapIndexedZZZ<Integer,ArrayList<String>> hmValues = PanelDebugHelperZZZ.createValueText(sTitle, panel, iIndexInCollection);
			if(hmValues!=null) {
				listaReturn = new ArrayList<JLabel>();
				Iterator<ArrayList<String>> itValues = hmValues.iterator();
				while(itValues.hasNext()) {						
					ArrayList<String> listaText = (ArrayList<String>) itValues.next();
					labelDebug = PanelDebugHelperZZZ.createLabel(listaText);
					if(labelDebug!=null) listaReturn.add(labelDebug);
				}
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
	
	public static HashMapIndexedZZZ<Integer,ArrayList<String>>createValueText(String sTitle, KernelJPanelCascadedZZZ panel, int iIndexInCollection) throws ExceptionZZZ{
		HashMapIndexedZZZ<Integer,ArrayList<String>> hmReturn = new HashMapIndexedZZZ<Integer, ArrayList<String>>(); 
				
		String stemp;
		main:{	
			ArrayList<String>listaTitle = new ArrayList<String>();
			listaTitle.add(sTitle);
			
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
					if(!StringZZZ.isEmpty(sProgram)) {
					if(sProgram.equals("use.openvpn.serverui.component.IPExternalUpload.PanelDlgIPExternalContentOVPN")) {
						System.out.println("DEBUGSTELLE");
						System.out.println("...TODOGOON...");
						//TODOGOON; //20210510hole den Alias für den Wert "use.openvpn.serverui.component.IPExternalUpload.PanelDlgIPExternalContentOVPN"
					}
					}
										
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
