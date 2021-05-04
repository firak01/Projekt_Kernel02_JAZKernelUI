package debug.zKernelUI.component.buttonSwitchLabelGroup;

import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernelUI.component.IPanelCascadedZZZ;
import basic.zKernelUI.component.KernelJPanelCascadedZZZ;

public class PanelDebugHelperZZZ {
	public static ArrayList<JLabel>createLabelArrayList(String sTitle, KernelJPanelCascadedZZZ panel) throws ExceptionZZZ {
		ArrayList<JLabel>listaReturn=new ArrayList<JLabel>();
		
		String stemp;
		main:{
			JLabel labelDebug;
						
			//+++ 1. Klassenname des Panels			
			stemp = panel.getClass().getSimpleName(); //das ist zu lang und nicht aussagekräftig genug String sParent = this.getClass().getSuperclass().getSimpleName();
			
			ArrayList<String> listaText = new ArrayList<String>();
			listaText.add(sTitle);
			listaText.add(stemp);	
			labelDebug = PanelDebugHelperZZZ.createLabel(listaText);							
			if(labelDebug!=null) listaReturn.add(labelDebug);

			//+++ 2. Module, das zur Verfügung steht
			String sModule = panel.getModuleName();	
			if(!StringZZZ.isEmpty(sModule)) {								
				sModule = StringZZZ.abbreviateDynamic(sModule, 10);//TODOGOON: StringZZZ Methode, um von rechts ausgehende abzukürzen.
				//!!! Wenn sich die Textlänge ständig ändert, dann verschieben sich ggfs. Nachbarpanels nach rechts aus dem Frame/der Dialogbox heraus.
				//    Daher müsste eigentlich auch der Frame/die Dialogbox neu "gepackt" werden (frame.pack() ).
				
				listaText.clear();
				listaText.add(sTitle);
				listaText.add("Module:" + sModule);
				labelDebug = PanelDebugHelperZZZ.createLabel(listaText);						
				if(labelDebug!=null) listaReturn.add(labelDebug);
			}
			
			
		    //+++ 3. Program, das zur Verfügung steht 
			String sProgram = panel.getProgramName();
			if(!StringZZZ.isEmpty(sProgram)) {
				sProgram = StringZZZ.abbreviateDynamic(sProgram, 10);//TODOGOON: von rechts abkürzen
				
				listaText.clear();
				listaText.add(sTitle);
				listaText.add("Program: " + sProgram);
				labelDebug = PanelDebugHelperZZZ.createLabel(listaText);						
				if(labelDebug!=null) listaReturn.add(labelDebug);
			}
			
							
			//+++ 4. ProgramAlias, der ggfs. zur Verfügung steht
			JLabel labelDebug3 = null;
			if(!StringZZZ.isEmpty(sProgram)) {
				String sProgramAlias = panel.getProgramAlias();
				if(sProgram.equals(sProgramAlias)) {
					sProgramAlias="dito";
				}else {
					sProgramAlias = StringZZZ.abbreviateDynamic(sProgramAlias, 10);//TODOGOON: von rechts abkürzen
				}
				
				listaText.clear();
				listaText.add(sTitle);
				listaText.add("ProgramAlias: " + sProgramAlias);
				labelDebug = PanelDebugHelperZZZ.createLabel(listaText);						
				if(labelDebug!=null) listaReturn.add(labelDebug);
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
