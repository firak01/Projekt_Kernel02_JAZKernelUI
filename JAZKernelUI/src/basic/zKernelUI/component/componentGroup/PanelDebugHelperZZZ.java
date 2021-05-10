package basic.zKernelUI.component.componentGroup;

import java.util.ArrayList;

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
	public static ArrayList<JLabel>createLabelArrayList(String sTitle, KernelJPanelCascadedZZZ panel, int iIndex) throws ExceptionZZZ {
		ArrayList<JLabel>listaReturn=new ArrayList<JLabel>();//Auch wenn eine Indexposition nix füllen sollte, leere Liste zurückgeben.
		
		String stemp;
		main:{
			JLabel labelDebug;									
			ArrayList<String> listaText = new ArrayList<String>();
			listaText.add(sTitle);
			
			int iLengthDefault=25;
			int iLengthDefaultRightOffset=2;
			switch(iIndex) {			
			case 0:
				//+++ 1. Klassenname des Panels	
				{
					stemp = panel.getClass().getSimpleName(); //das ist zu lang und nicht aussagekräftig genug String sParent = this.getClass().getSuperclass().getSimpleName();
	
					listaText.add(stemp);	
					labelDebug = PanelDebugHelperZZZ.createLabel(listaText);							
					if(labelDebug!=null) listaReturn.add(labelDebug);
				}
				break;
			case 1:
				//+++ 2. Module, das zur Verfügung steht
				{
					String sModule = panel.getModuleName();	
					if(!StringZZZ.isEmpty(sModule)) {								
						sModule = StringZZZ.abbreviateDynamicLeft(sModule, iLengthDefault+iLengthDefaultRightOffset);
						sModule = StringZZZ.abbreviateDynamic(sModule, iLengthDefault);
						
						
						//!!! TODOGOON Wenn sich die Textlänge ständig ändert, dann verschieben sich ggfs. Nachbarpanels nach rechts aus dem Frame/der Dialogbox heraus.
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
				}
				break;
			case 2:
				 //+++ 3. Program, das zur Verfügung steht 
				{
					String sProgram = panel.getProgramName();
					if(!StringZZZ.isEmpty(sProgram)) {
						sProgram = StringZZZ.abbreviateDynamicLeft(sProgram, iLengthDefault+iLengthDefaultRightOffset);
						sProgram = StringZZZ.abbreviateDynamic(sProgram, iLengthDefault);
						
						listaText.add("Program: " + sProgram);
						labelDebug = PanelDebugHelperZZZ.createLabel(listaText);						
						if(labelDebug!=null) listaReturn.add(labelDebug);					
					}else {
						listaText.add("Program: Not configured");
						labelDebug = PanelDebugHelperZZZ.createLabel(listaText);						
						if(labelDebug!=null) listaReturn.add(labelDebug);
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
					
					JLabel labelDebug3 = null;
					String sProgram4alias = panel.getProgramAlias();				
					if(!StringZZZ.isEmpty(sProgram4alias)) {						
						String sProgramAlias = panel.getProgramAlias();
						if(sProgram4alias.equals(sProgram)) {
							sProgramAlias="dito";
						}else {
							sProgramAlias = StringZZZ.abbreviateDynamicLeft(sProgramAlias, iLengthDefault+iLengthDefaultRightOffset);
							sProgramAlias = StringZZZ.abbreviateDynamic(sProgramAlias, iLengthDefault);
						}
						
						listaText.add("ProgramAlias: " + sProgramAlias);
						labelDebug = PanelDebugHelperZZZ.createLabel(listaText);						
						if(labelDebug!=null) listaReturn.add(labelDebug);					
					}
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
