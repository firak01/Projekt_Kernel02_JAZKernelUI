package debug.zKernelUI.component.buttonGroup;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zKernelUI.component.KernelJPanelCascadedZZZ;


import basic.zKernel.KernelZZZ;

public class PanelDebugButtonGroup_NORTHZZZ extends KernelJPanelCascadedZZZ {
	private KernelZZZ objKernelChoosen;
	private static final int iLABEL_COLUMN_DEFAULT = 10;

	
	public PanelDebugButtonGroup_NORTHZZZ(KernelZZZ objKernel, JPanel panelParent) throws ExceptionZZZ {
		super(objKernel, panelParent);
		main:{
		try {		
			//TODO Komplizierteres aber sch�neres Layout durch einen anderen Layoutmanager
			this.setLayout(new GridLayout(2,6)); //2 Zeilen, 6 Spalten
	
			//Einen Rahmen um das Panel zeichnen
			Border borderEtched = BorderFactory.createEtchedBorder();
			this.setBorder(borderEtched);
					
			// +++ Werte aus der KoernelKonfiguration auslesen und anzeigen
			String sLabelButtonGroup01 = this.getKernelObject().getParameterByProgramAlias("PanelNorth", "LabelButtonGroup01");
			JLabel labelModuleText = new JLabel(sLabelButtonGroup01, SwingConstants.LEFT);
			this.add(labelModuleText);
			
			
			
			} catch (ExceptionZZZ ez) {
				String sError = ReflectCodeZZZ.getMethodCurrentName() + ": " + ez.getDetailAllLast();
				System.out.println(sError);
				this.getLogObject().WriteLineDate(sError);
			}
		}//END main:
	}
    
}
