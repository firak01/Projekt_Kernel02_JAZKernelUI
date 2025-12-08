package debug.zKernelUI.component.buttonSwitchLabelGroup;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JComponent;
import javax.swing.JLabel;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ArrayListUtilZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedObjectZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernelUI.component.IPanelCascadedZZZ;
import basic.zKernelUI.component.KernelJPanelCascadedZZZ;
import basic.zKernelUI.component.componentGroup.AbstractModelComponentGroupZZZ;
import basic.zKernelUI.component.componentGroup.ModelComponentGroupExceptionZZZ;
import basic.zKernelUI.component.componentGroup.IModelComponentGroupValueZZZ;
import basic.zKernelUI.component.model.ModelComponentHelperZZZ;

public class ModelRow2ZZZ extends AbstractModelComponentGroupZZZ{	
	public ModelRow2ZZZ() {	
		super();
	}
	
	public ModelRow2ZZZ(IKernelZZZ objKernel, String sTitle, IPanelCascadedZZZ panelParent) throws ExceptionZZZ {
		super(objKernel, sTitle, panelParent);
	}
	
	public ModelRow2ZZZ(IKernelZZZ objKernel, String sTitle, IPanelCascadedZZZ panelParent, int iIndexInCollection) throws ExceptionZZZ {
		super(objKernel, sTitle,panelParent,iIndexInCollection);
	}
		
	//##############################
	@Override
	public IModelComponentGroupValueZZZ createModelForGroup(String sTitle, IPanelCascadedZZZ panelParent, int iIndexInGroupCollection) throws ExceptionZZZ, ModelComponentGroupExceptionZZZ {
		 return new ModelRow2ZZZ(this.getKernelObject(),sTitle, panelParent, iIndexInGroupCollection); 
	}
	
	@Override
	public HashMapIndexedObjectZZZ<Integer,ArrayList<String>>createValuesText(String sTitle, IPanelCascadedZZZ panel, int iIndexInCollection) throws ModelComponentGroupExceptionZZZ{
		HashMapIndexedObjectZZZ<Integer,ArrayList<String>> hmReturn = null; 
				
		String stemp;
		main:{	
			try {
			hmReturn = new HashMapIndexedObjectZZZ<Integer, ArrayList<String>>();
			
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
				//+++ 3. Werte aus der Konfigurationsdatei auslesen, das zur Verfügung steht								
				{
					String sModule = panel.getModuleName();	
					String sProgram = panel.getProgramName();
					String sValue = null;
					try {
						sValue = this.getKernelObject().getParameterByProgramAlias(sModule, sProgram, "labelValue01a").getValue();
					}catch(ExceptionZZZ ez) {
						sValue = null;
					}
					
					if(!StringZZZ.isEmpty(sValue)) {
						sValue = StringZZZ.abbreviateDynamicLeft(sValue, iLengthDefault+iLengthDefaultRightOffset);
						sValue = StringZZZ.abbreviateDynamic(sValue, iLengthDefault);						
						listaTitle.add("Wert01a " + sValue);						
					}else {
						listaTitle.add("Wert01a: Not configured");						
					}
					//+++++++++++++++++++++++++++++
					hmReturn.put(listaTitle);	
					
					//+++++++					
					ArrayList<String>listaTest01 = new ArrayList<String>();
					try {
						sValue = this.getKernelObject().getParameterByProgramAlias(sModule, sProgram, "labelValue01b").getValue();
					}catch(ExceptionZZZ ez) {
						sValue = null;
					}
					
					if(!StringZZZ.isEmpty(sValue)) {
						sValue = StringZZZ.abbreviateDynamicLeft(sValue, iLengthDefault+iLengthDefaultRightOffset);
						sValue = StringZZZ.abbreviateDynamic(sValue, iLengthDefault);						
						listaTest01.add("Wert01b " + sValue);						
					}else {
						listaTest01.add("Wert01b: Not configured");
					}
					//+++++++++++++++++++++++++++++
					hmReturn.put(listaTest01);	

					//++++++++
					ArrayList<String>listaTest02 = new ArrayList<String>();
					try {
						sValue = this.getKernelObject().getParameterByProgramAlias(sModule, sProgram, "labelValue01c").getValue();
					}catch(ExceptionZZZ ez) {
						sValue = null;
					}
					
					if(!StringZZZ.isEmpty(sValue)) {
						sValue = StringZZZ.abbreviateDynamicLeft(sValue, iLengthDefault+iLengthDefaultRightOffset);
						sValue = StringZZZ.abbreviateDynamic(sValue, iLengthDefault);						
						listaTest02.add("Wert01c " + sValue);						
					}else {
						listaTest02.add("Wert01c: Not configured");
					}
					
					//+++++++++++++++++++++++++++++
					hmReturn.put(listaTest02);					
				}
				break;
			case 3:		
				//+++ 4. Werte aus der Konfigurationsdatei auslesen, das zur Verfügung steht
				{
					String sModule = panel.getModuleName();	
					String sProgram = panel.getProgramName();
					String sValue = null;
					try {
						sValue = this.getKernelObject().getParameterByProgramAlias(sModule, sProgram, "labelValue02a").getValue();
					}catch(ExceptionZZZ ez) {
						sValue = null;
					}
					
					if(!StringZZZ.isEmpty(sValue)) {
						sValue = StringZZZ.abbreviateDynamicLeft(sValue, iLengthDefault+iLengthDefaultRightOffset);
						sValue = StringZZZ.abbreviateDynamic(sValue, iLengthDefault);
						
						listaTitle.add("Wert02a " + sValue);						
					}else {
						listaTitle.add("Wert02a: Not configured");						
					}
					
					//+++++++++++++++++++++++++++++++++++++++++
					hmReturn.put(listaTitle);
				}
				break;
			case 4:		
				//+++ 5. Werte aus der Konfigurationsdatei auslesen, das zur Verfügung steht
				{
					String sModule = panel.getModuleName();	
					String sProgram = panel.getProgramName();
					String sValue = null;
					try {
						sValue = this.getKernelObject().getParameterByProgramAlias(sModule, sProgram, "labelValue03a").getValue();
					}catch(ExceptionZZZ ez) {
						sValue = null;
					}
					
					if(!StringZZZ.isEmpty(sValue)) {
						sValue = StringZZZ.abbreviateDynamicLeft(sValue, iLengthDefault+iLengthDefaultRightOffset);
						sValue = StringZZZ.abbreviateDynamic(sValue, iLengthDefault);						
						listaTitle.add("Wert03a " + sValue);						
					}else {
						listaTitle.add("Wert03a: Not configured");						
					}
					
					//+++++++++++++++++++++++++++++++++++++++++++++
					hmReturn.put(listaTitle);
				}
				break;
			case 5:		
				//+++ 5. Werte aus der Konfigurationsdatei auslesen, das zur Verfügung steht
				{
					String sModule = panel.getModuleName();	
					String sProgram = panel.getProgramName();
					String sValue = null;
					try {
						sValue = this.getKernelObject().getParameterByProgramAlias(sModule, sProgram, "NICHTDA-FALL").getValue();
					}catch(ExceptionZZZ ez) {
						sValue = null;
					}
					
					if(!StringZZZ.isEmpty(sValue)) {
						sValue = StringZZZ.abbreviateDynamicLeft(sValue, iLengthDefault+iLengthDefaultRightOffset);
						sValue = StringZZZ.abbreviateDynamic(sValue, iLengthDefault);						
						listaTitle.add("WertNICHTDA " + sValue);						
					}else {
						listaTitle.add("WertNICHTDA: Not configured");						
					}
					
					
					//+++++++++++++++++++++++++++++++++++++++++++++++
					hmReturn.put(listaTitle);
				}				
				break;
			default: 
				hmReturn = null; //Wenn eine Indexposition nicht existiert, null zurückgeben.				
				break;
			}
			}catch(ExceptionZZZ ez) {
				ModelComponentGroupExceptionZZZ cme = new ModelComponentGroupExceptionZZZ(ez);
				throw cme;
			}
		}//end main:
		return hmReturn;
	}	
}

