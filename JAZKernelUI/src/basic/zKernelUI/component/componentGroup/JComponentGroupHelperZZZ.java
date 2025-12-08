package basic.zKernelUI.component.componentGroup;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedObjectZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasicUI.component.UIHelper;
import basic.zKernel.IKernelZZZ;
import basic.zKernelUI.component.IPanelCascadedZZZ;
import basic.zKernelUI.component.KernelJPanelCascadedZZZ;

public class JComponentGroupHelperZZZ {

	public static JButton createButtonSwitch(IKernelZZZ objKernel, IPanelCascadedZZZ panel, JComponentGroupCollectionZZZ groupc) throws ExceptionZZZ {
		JButton objReturn = null;
		main:{
			String sLabelButton = ">";//this.getKernelObject().getParameterByProgramAlias(sModule, sProgram, "LabelButton").getValue();
			objReturn = JComponentGroupHelperZZZ.createButtonSwitch(objKernel, panel, groupc, sLabelButton);	
		}				
		return objReturn;		
	}
	
	public static JButton createButtonSwitch(IKernelZZZ objKernel, IPanelCascadedZZZ panel, JComponentGroupCollectionZZZ groupc, String sLabelButtonIn) throws ExceptionZZZ {
		JButton objReturn = null;
		main:{
			String sLabelButton;
			if(StringZZZ.isEmpty(sLabelButtonIn)) {
				sLabelButton = ">";//this.getKernelObject().getParameterByProgramAlias(sModule, sProgram, "LabelButton").getValue();
			}else {
				sLabelButton=sLabelButtonIn;
			}
			Font font = new Font("TAHOMA",Font.BOLD,9);				
			objReturn = UIHelper.createButton(sLabelButton, font);
			objReturn.setPreferredSize(new Dimension(20, 20));
			objReturn.setBackground(Color.GREEN);
			objReturn.setBorder(BorderFactory.createCompoundBorder(
			               BorderFactory.createLineBorder(Color.CYAN, 1),
			               BorderFactory.createLineBorder(Color.BLACK, 1)));
			
			ActionSwitchZZZ actionSwitch = new ActionSwitchZZZ(objKernel, panel, groupc);
			objReturn.addActionListener(actionSwitch);
		}				
		return objReturn;		
	}
	
	
	
//	public static boolean setVisible(ArrayList<JComponentGroupZZZ>listaGroup, int iIndexInitial) throws ExceptionZZZ {
//		boolean bReturn = false;
//		
//		String stemp;
//		main:{	
//			if(listaGroup==null) break main;
//			if(iIndexInitial<0) break main;
//			
//			int iIndex=-1;
//			boolean bAnySet=false;
//			for(JComponentGroupZZZ grouptemp : listaGroup) {
//				if(grouptemp!=null) {
//					iIndex++;
//					
//					if(iIndex==iIndexInitial) {
//						grouptemp.setVisible(true);
//						bAnySet=true;
//						bReturn=true;
//					}else {
//						grouptemp.setVisible(false);
//					}
//				}				
//			}
//			
//			//Falls keiner gesetzt wurde, setze den letzten sichtbar
//			if(!bAnySet) {
//				iIndexInitial = listaGroup.size()-1;
//				if(iIndexInitial<0) break main;
//				
//				JComponentGroupZZZ grouptemp = listaGroup.get(iIndexInitial);
//				grouptemp.setVisible(true);
//				bReturn = true;
//			}							
//		}//end main
//		return bReturn;
//	}
}
