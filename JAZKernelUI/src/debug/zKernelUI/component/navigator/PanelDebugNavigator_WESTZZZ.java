package debug.zKernelUI.component.navigator;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
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
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedZZZ;
import basic.zBasic.util.abstractList.HashMapMultiZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.log.ReportLogZZZ;
import basic.zBasicUI.thread.SwingWorker;
import basic.zKernelUI.KernelUIZZZ;
import basic.zKernelUI.component.IPanelCascadedZZZ;
import basic.zKernelUI.component.AbstractKernelActionListenerCascadedZZZ;
import basic.zKernelUI.component.KernelButtonGroupZZZ;
import basic.zKernelUI.component.KernelJPanelCascadedZZZ;
import basic.zKernelUI.component.navigator.ActionSwitchZZZ;
import basic.zKernelUI.component.navigator.INavigatorElementZZZ;
import basic.zKernelUI.component.navigator.ISenderNavigatorElementSwitchZZZ;
import basic.zKernelUI.component.navigator.NavigatorElementCollectionZZZ;
import basic.zKernelUI.thread.KernelSwingWorkerZZZ;
import basic.zKernel.IKernelUserZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelLogZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.component.IKernelModuleZZZ;
import basic.zKernel.component.IKernelProgramZZZ;
import basic.zKernel.flag.IFlagZLocalEnabledZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;

public class PanelDebugNavigator_WESTZZZ extends KernelJPanelCascadedZZZ implements IKernelProgramZZZ {	
    //private static final String sBUTTON_SWITCH = "buttonSwitch";
   	
	public PanelDebugNavigator_WESTZZZ(IKernelZZZ objKernel, JPanel panelParent) throws ExceptionZZZ {
		super(objKernel, panelParent);
		String stemp; boolean btemp;
		main:{			
		try {		
			//Diese Panel ist Grundlage für diverse INI-Werte auf die über Buttons auf "Programname" zugegriffen wird.
			stemp = IKernelProgramZZZ.FLAGZ.ISKERNELPROGRAM.name();
			btemp = this.setFlag(stemp, true);	
			if(btemp==false){
				ExceptionZZZ ez = new ExceptionZZZ( "the flag '" + stemp + "' is not available. Maybe an interface is not implemented.", IFlagZEnabledZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 
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
			
			//Einen Rahmen um das Panel zeichnen
			Border borderEtched = BorderFactory.createEtchedBorder();
			this.setBorder(borderEtched);
			
			GridBagLayout layout = new GridBagLayout();			
			this.setLayout(layout);
			
	        GridBagConstraints gbc = new GridBagConstraints();
	        gbc.insets=new Insets(20,20,70,70);
	        gbc.fill = GridBagConstraints.VERTICAL;
	        
	        //20210804 Also: 
	        //Navigator-Objekt erstellen: Im übergeordneten Navigator-Objekt
	        //Zuerst das Modell holen
	        //Das soll eine HashMap an Einträgen liefern
	        //Für jeden Eintrag ein NavigatorElement erstellen, das ist ein Panel, das ein Label enthält mit dem Text
	        
	        
	        //TODOGOON 20210804 starte mit dem Model...
	       
	        //++++ Die GroupCollection, basierend auf dem Modell
	        ModelDebugNavigatorZZZ modelNavigator = new ModelDebugNavigatorZZZ(this.getKernelObject());
			NavigatorElementCollectionZZZ<INavigatorElementZZZ> groupc = new NavigatorElementCollectionZZZ<INavigatorElementZZZ>(objKernel, this, modelNavigator);	
			//Merke: Damit würde der erste Eintrag vorausagewählt und ein roter Rahmen käme darum
			//groupc.setVisible(0); //Initiales Setzen der Sichtbarkeit
			
			Iterator<INavigatorElementZZZ> it = groupc.iterator();
			while(it.hasNext()) {
				ArrayList<INavigatorElementZZZ> listaElementTemp = (ArrayList<INavigatorElementZZZ>) it.next();
				createRow(this, gbc, listaElementTemp);
			}
			
	        	       
	        
	        //für jeden Elementeintrag aufrufen... und damit die Navigator-Elemente als Panel erstellen.
//			ArrayListExtendedZZZ<INavigatorElementZZZ>alNavigatorElement = modelNavigator.getNavigatorElementArrayList();
//	        for(INavigatorElementZZZ objElementTemp : alNavigatorElement) {
//	        	createRow(this, gbc, objElementTemp);
//	        }
			
			
	        //+++++++++++++++++++++++++++++++++++++++
	        
//	        gbc.gridy = 0;
//	        createRow1(this, gbc, sModule, sProgram);
//	        
//	        gbc.gridy = 1;
//	        createRow2(this, gbc, sModule, sProgram);
//	        
//	        gbc.gridy = 2;
//	        createRow3(this, gbc, sModule, sProgram);
	        	        			
			
			//###########################################################################################					
			//+++ Das Layout validieren, mit dem Ziel die Komponenten passend anzuordnen.
			this.validate();
			this.repaint();
			} catch (ExceptionZZZ ez) {
				String sError = ReflectCodeZZZ.getMethodCurrentName() + ": " + ez.getDetailAllLast();
				System.out.println(sError);
				this.getLogObject().WriteLineDate(sError);
			}
		}//END main:
	}	
	
	/**############################################################################################
	   ########## DRITTE ZEILE MIT DRITTER GROUPCOLLECTION, DIE EIN ANDERES MODELL BENUTZT
		
	 * @param panel
	 * @param gbc
	 * @param sModule
	 * @param sProgram
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 15.05.2021, 11:46:13
	 */
	private void createRow(KernelJPanelCascadedZZZ panel, GridBagConstraints gbc, ArrayList<INavigatorElementZZZ> listaElement) throws ExceptionZZZ {
		gbc.gridx = 0;
		for(INavigatorElementZZZ objElement : listaElement) {
			int iPosition = objElement.getPosition();
			gbc.gridy = iPosition;
			this.add(objElement.getLabel(),gbc);
		}
	}
	
	
	
//	/**############################################################################################
//	   ########## ERSTE ZEILE MIT ERSTER GROUPCOLLECTION, OHNE MODELL
//	 * @param panel
//	 * @param gbc
//	 * @param sModule
//	 * @param sProgram
//	 * @throws ExceptionZZZ
//	 * @author Fritz Lindhauer, 15.05.2021, 11:48:32
//	 */
//	private void createRow1(KernelJPanelCascadedZZZ panel, GridBagConstraints gbc, String sModule, String sProgram) throws ExceptionZZZ {
//
//		// +++ Werte aus der KernelKonfiguration auslesen und anzeigen
//		String sLabelButtonGroup_01 = this.getKernelObject().getParameterByProgramAlias(sModule, sProgram, "LabelButtonGroupRow1").getValue();
//		JLabel labelModuleText_01 = new JLabel(sLabelButtonGroup_01, SwingConstants.LEFT);			
//		this.add(labelModuleText_01,gbc);
//		
//		//++++ Die LabelGroupZZZ				
//		//+++++++++++ GRUPPE 1 ++++++++++++++++++
//		String sLabel02_01 = "Label 1A";
//		JLabel label02_01 = new JLabel(sLabel02_01, SwingConstants.LEFT);			
//		String sLabel03_01 = "Label 2A";
//		JLabel label03_01 = new JLabel(sLabel03_01, SwingConstants.LEFT);	
//		
//		ArrayList<JComponent>listaComponent_01 = new ArrayList<JComponent>();
//		listaComponent_01.add(label02_01);
//		listaComponent_01.add(label03_01);			
//		JComponentGroupZZZ group01_01 = new JComponentGroupZZZ(objKernel, "EINS", "Title: DebugGroup01", this, listaComponent_01);
//		
//		//++++++++++++
//		String sLabel04_01 = "Label 1B";
//		JLabel label04_01 = new JLabel(sLabel04_01, SwingConstants.LEFT);			
//		String sLabel05_01 = "Label 2B";
//		JLabel label05_01 = new JLabel(sLabel05_01, SwingConstants.LEFT);
//		
//		listaComponent_01.clear();
//		listaComponent_01.add(label04_01);
//		listaComponent_01.add(label05_01);			
//		JComponentGroupZZZ group02_01 = new JComponentGroupZZZ(objKernel, "ZWEI", "Title: DebugGroup02", this, listaComponent_01);
//															
//		//### Die Gruppen in einer Collection zusammenfassen
//		JComponentGroupCollectionZZZ groupc_01 = new JComponentGroupCollectionZZZ(objKernel);
//		groupc_01.add(group01_01);
//		groupc_01.add(group02_01);
//		groupc_01.setVisible("EINS");
//		
//		
//		//######## Das UI gestalten. Die Reihenfolge der Componenten ist wichtig für die Reihenfolge im UI #################
//		//++++ Die Buttons
//		String sLabelButton_01 = this.getKernelObject().getParameterByProgramAlias(sModule, sProgram, "LabelButtonRow1").getValue();
//		JButton buttonSwitch_01 = new JButton(sLabelButton_01);			
//		ActionSwitchZZZ actionSwitch_01 = new ActionSwitchZZZ(objKernel, this, groupc_01);			
//		buttonSwitch_01.addActionListener(actionSwitch_01);
//		
//		this.setComponent(PanelDebugNavigator_WESTZZZ.sBUTTON_SWITCH, buttonSwitch_01);
//		gbc.gridx = 1;
//		this.add(buttonSwitch_01,gbc);
//		
//		
//		//+++ Nun erst die Label dem Panel hinzufügen. 
//		//Merke: Die auszutauschenden Komponenten müssen in die gleichen Zellen hinzugefügt werden. Sonst entstehen Leerzellen		
//		gbc.gridx = 2;
//		this.add(label02_01,gbc);
//		gbc.gridx = 3;
//		this.add(label03_01,gbc);
//		gbc.gridx = 2;
//		this.add(label04_01,gbc);
//		gbc.gridx = 3;
//		this.add(label05_01,gbc);
//	}
//	
//	
//	
//	/**############################################################################################
//	   ########## DRITTE ZEILE MIT DRITTER GROUPCOLLECTION, DIE EIN ANDERES MODELL BENUTZT
//		
//	 * @param panel
//	 * @param gbc
//	 * @param sModule
//	 * @param sProgram
//	 * @throws ExceptionZZZ
//	 * @author Fritz Lindhauer, 15.05.2021, 11:46:13
//	 */
//	private void createRow3(KernelJPanelCascadedZZZ panel, GridBagConstraints gbc, String sModule, String sProgram) throws ExceptionZZZ {
//		gbc.gridx = 0;
//		
//		// +++ Werte aus der KernelKonfiguration auslesen und anzeigen
//		String sLabelButtonGroup = this.getKernelObject().getParameterByProgramAlias(sModule, sProgram, "LabelButtonGroupRow3").getValue();
//		JLabel labelModuleText = new JLabel(sLabelButtonGroup, SwingConstants.LEFT);			
//		this.add(labelModuleText,gbc);
//		
//		String sTitle="row3";		
//		ModelPanelDebugZZZ modelRow3 = new ModelPanelDebugZZZ(this.getKernelObject(),sTitle, this);
//		
//		//++++ Die GroupCollection
//		//202105178 Jetzt muss es reichen das model zu übergeben
//		JComponentGroupCollectionZZZ groupc = new JComponentGroupCollectionZZZ(this.getKernelObject(), modelRow3);
//		groupc.setVisible(0); //Initiales Setzen der Sichtbarkeit
//				
//		//######## Das UI gestalten. Die Reihenfolge der Componenten ist wichtig für die Reihenfolge im UI #################
//		//++++ Die Buttons
//		String sLabelButton = this.getKernelObject().getParameterByProgramAlias(sModule, sProgram, "LabelButtonRow3").getValue();
//		JButton buttonSwitch = JComponentGroupHelperZZZ.createButtonSwitch(objKernel, panel, groupc, sLabelButton);
//		this.setComponent(PanelDebugNavigator_WESTZZZ.sBUTTON_SWITCH+"_03", buttonSwitch);
//		gbc.gridx = 1;
//		this.add(buttonSwitch,gbc);
//					
//		//+++ Nun erst die Label dem Panel hinzufuegen	
//		//Merke: Die auszutauschenden Komponenten müssen in die gleichen Zellen hinzugefügt werden. Sonst entstehen Leerzellen
//		HashMapIndexedZZZ<Integer,JComponentGroupZZZ> hmComponent = groupc.getHashMapIndexed();
//		Iterator it = hmComponent.iterator();
//		int iIndexOuter=-1;
//		while(it.hasNext()) {
//			JComponentGroupZZZ group = (JComponentGroupZZZ) it.next();
//			if(group!=null) {
//				iIndexOuter=iIndexOuter+1;
//				ArrayList<JComponent>listaComponenttemp = (ArrayList<JComponent>) group.getComponents();
//				if(listaComponenttemp!=null) {
//					
//					//Die Labels der Arraylist abarbeiten und dem panel hinzufügen
//					int iIndexInner=-1;				
//					for(JComponent componenttemp : listaComponenttemp) {
//						if(componenttemp!=null) {
//							System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Component.isVisible()='" + componenttemp.isVisible() + "'");
//							
//							iIndexInner=iIndexInner+1;
//							gbc.gridx = 2+iIndexInner;
//							this.add(componenttemp,gbc);						
//							this.setComponent("ComponentDebug"+iIndexOuter+"_"+iIndexInner, componenttemp);	
//													
//						}
//					}		
//				}
//			}	
//		}
//	}
}
