package basic.zKernelUI.component.componentGroup;

import java.util.ArrayList;

import javax.swing.JLabel;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedZZZ;
import basic.zKernelUI.component.KernelJPanelCascadedZZZ;

public class JComponentGroupHelperZZZ {

	public static boolean setVisible(ArrayList<JComponentGroupZZZ>listaGroup, int iIndexInitial) throws ExceptionZZZ {
		boolean bReturn = false;
		
		String stemp;
		main:{	
			if(listaGroup==null) break main;
			if(iIndexInitial<0) break main;
			
			int iIndex=-1;
			boolean bAnySet=false;
			for(JComponentGroupZZZ grouptemp : listaGroup) {
				if(grouptemp!=null) {
					iIndex++;
					
					if(iIndex==iIndexInitial) {
						grouptemp.setVisible(true);
						bAnySet=true;
						bReturn=true;
					}else {
						grouptemp.setVisible(false);
					}
				}				
			}
			
			//Falls keiner gesetzt wurde, setze den letzten sichtbar
			if(!bAnySet) {
				iIndexInitial = listaGroup.size()-1;
				if(iIndexInitial<0) break main;
				
				JComponentGroupZZZ grouptemp = listaGroup.get(iIndexInitial);
				grouptemp.setVisible(true);
				bReturn = true;
			}							
		}//end main
		return bReturn;
	}
}
