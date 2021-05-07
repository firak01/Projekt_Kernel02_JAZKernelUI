package debug.zKernelUI.component.buttonSwitchLabelGroup;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import custom.zKernel.LogZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedZZZ;
import basic.zBasic.util.abstractList.HashMapMultiZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.log.ReportLogZZZ;
import basic.zBasicUI.thread.SwingWorker;
import basic.zKernelUI.KernelUIZZZ;
import basic.zKernelUI.component.KernelActionCascadedZZZ;
import basic.zKernelUI.component.KernelButtonGroupZZZ;
import basic.zKernelUI.component.KernelJPanelCascadedZZZ;
import basic.zKernelUI.component.labelGroup.ActionSwitchZZZ;
import basic.zKernelUI.component.labelGroup.EventComponentGroupSwitchZZZ;
import basic.zKernelUI.component.labelGroup.IListenerComponentGroupSwitchZZZ;
import basic.zKernelUI.component.labelGroup.ISenderComponentGroupSwitchZZZ;
import basic.zKernelUI.component.labelGroup.JComponentGroupZZZ;
import basic.zKernelUI.component.labelGroup.KernelSenderComponentGroupSwitchZZZ;
import basic.zKernelUI.thread.KernelSwingWorkerZZZ;
import basic.zKernel.IKernelUserZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelLogZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.component.IKernelModuleZZZ;
import basic.zKernel.component.IKernelProgramZZZ;

public class PanelDebugButtonSwitchLabelGroup_NORTHZZZ extends KernelJPanelCascadedZZZ implements IKernelProgramZZZ {	
    private static final String sBUTTON_SWITCH = "buttonSwitch";
   	
	public PanelDebugButtonSwitchLabelGroup_NORTHZZZ(IKernelZZZ objKernel, JPanel panelParent) throws ExceptionZZZ {
		super(objKernel, panelParent);
		String stemp; boolean btemp;
		main:{			
		try {		
			//Diese Panel ist Grundlage für diverse INI-Werte auf die über Buttons auf "Programname" zugegriffen wird.
			stemp = IKernelProgramZZZ.FLAGZ.ISKERNELPROGRAM.name();
			btemp = this.setFlagZ(stemp, true);	
			if(btemp==false){
				ExceptionZZZ ez = new ExceptionZZZ( "the flag '" + stemp + "' is not available. Maybe an interface is not implemented.", iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 
				throw ez;		 
			}
			
			
			//Wichtige Informationen, zum Auslesen von Parametern aus der KernelConfiguration
			String sProgram; String sModule;
			sModule = KernelUIZZZ.getModuleUsedName((IKernelModuleZZZ) this);
			if(StringZZZ.isEmpty(sModule)){
				ExceptionZZZ ez = new ExceptionZZZ("No module configured for this component '" + this.getClass().getName() + "'", iERROR_CONFIGURATION_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			sProgram = this.getProgramName();
			if(StringZZZ.isEmpty(sProgram)){
				ExceptionZZZ ez = new ExceptionZZZ("No program '" + sProgram + "' configured for the module: '" +  sModule + "'", iERROR_CONFIGURATION_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			
			//Komplizierteres aber sch�neres Layout durch einen anderen Layoutmanager
			//Merke: Wenn man das GridLayout verwendet, dann funktioniert das PACKEN und REVALIDATE nicht.
			//       Also jede "verborgene" Componente behält ihren Platz.
			//       Besser ist es also für diese Minimalistische Darstellung kein Layout zu verwenden.
			//this.setLayout(new GridLayout(0,3)); //6 Zeilen, 3 Spalten. Merke new GridLayout(6,3)); funktioniert nicht, wohl wenn die Anzahl der Zellen nicht ganz passt. 
	
			//Einen Rahmen um das Panel zeichnen
			Border borderEtched = BorderFactory.createEtchedBorder();
			this.setBorder(borderEtched);
					
			// +++ Werte aus der KernelKonfiguration auslesen und anzeigen
			String sLabelButtonGroup01 = this.getKernelObject().getParameterByProgramAlias(sModule, sProgram, "LabelButtonGroup").getValue();
			JLabel labelModuleText = new JLabel(sLabelButtonGroup01, SwingConstants.LEFT);
			this.add(labelModuleText);
			
			//++++ Die LabelGroupZZZ				
			//+++++++++++ GRUPPE 1 ++++++++++++++++++
			String sLabel02 = "Label 1A";
			JLabel label02 = new JLabel(sLabel02, SwingConstants.LEFT);
			
			String sLabel03 = "Label 2A";
			JLabel label03 = new JLabel(sLabel03, SwingConstants.LEFT);	
			
			//++++++++++++
			String sLabel04 = "Label 1B";
			JLabel label04 = new JLabel(sLabel04, SwingConstants.LEFT);
			
			String sLabel05 = "Label 2B";
			JLabel label05 = new JLabel(sLabel05, SwingConstants.LEFT);
			
			//+++++++++++++
			JComponentGroupZZZ group1 = new JComponentGroupZZZ(objKernel, "EINS");
			group1.addComponent(label02);
			group1.addComponent(label04);
			group1.setVisible(true);
			
			JComponentGroupZZZ group2 = new JComponentGroupZZZ(objKernel, "ZWEI");
			group2.addComponent(label03);
			group2.addComponent(label05);
			group2.setVisible(false);
				
			//Den EventBroker DER GRUPPE hinzufügen, damit darueber der Event abgefeuert werden kann
			//Merke: Dem EventBroker ist eine Reihefolge (über den Index) egal
			KernelSenderComponentGroupSwitchZZZ objEventBroker = new KernelSenderComponentGroupSwitchZZZ(objKernel);
			objEventBroker.addListenerComponentGroupSwitch(group1);
			objEventBroker.addListenerComponentGroupSwitch(group2);
			
			
			//++++++++++ Ggfs. mehrerer Gruppen zu der HashMap zusammenfassen.
			//Merke: Der Button steuert über den Index die Reihenfolge
			HashMapIndexedZZZ<Integer,JComponentGroupZZZ> hmIndexed = new HashMapIndexedZZZ<Integer,JComponentGroupZZZ>();
			hmIndexed.put(group1);
			hmIndexed.put(group2);
			
			
			//######## Das UI gestalten. Die Reihenfolge der Componenten ist wichtig für die Reihenfolge im UI #################
			//++++ Die Buttons
			String sLabelButton = this.getKernelObject().getParameterByProgramAlias(sModule, sProgram, "LabelButton").getValue();
			JButton buttonSwitch = new JButton(sLabelButton);			
			ActionSwitchZZZ actionSwitch = new ActionSwitchZZZ(objKernel, this, hmIndexed);
			actionSwitch.setSenderUsed(objEventBroker);
			buttonSwitch.addActionListener(actionSwitch);
			
			this.setComponent(PanelDebugButtonSwitchLabelGroup_NORTHZZZ.sBUTTON_SWITCH, buttonSwitch);
			this.add(buttonSwitch);
			
			
			//+++ Nun erst die Label dem Panel hinzufügen			
			this.add(label02);
			this.add(label03);
			this.add(label04);
			this.add(label05);
			
								
			//+++ Das Layout validieren, mit dem Ziel die Komponenten passend anzuordnen.
			this.validate();
			} catch (ExceptionZZZ ez) {
				String sError = ReflectCodeZZZ.getMethodCurrentName() + ": " + ez.getDetailAllLast();
				System.out.println(sError);
				this.getLogObject().WriteLineDate(sError);
			}
		}//END main:
	}		
}
