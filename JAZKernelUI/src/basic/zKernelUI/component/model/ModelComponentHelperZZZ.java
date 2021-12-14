package basic.zKernelUI.component.model;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JComponent;
import javax.swing.JLabel;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zKernelUI.component.KernelJPanelCascadedZZZ;
import basic.zKernelUI.component.componentGroup.ModelPanelDebugZZZ;

public class ModelComponentHelperZZZ {
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
			objReturn.setToolTipText(sHtml); //Workaround... falls das Feld sehr klein wird, soll das helfen den Text zu erkennen.
			
		}//end main:
		return objReturn;
	}	
	
	public static ArrayList<JLabel>createLabelArrayList(HashMapIndexedZZZ<Integer,ArrayList<String>> hmTextValues) throws ExceptionZZZ {
		ArrayList<JLabel>listaReturn=null;//Auch wenn eine Indexposition nix füllen sollte, leere Liste zurückgeben.
		main:{
			JLabel labelDebug;									
			if(hmTextValues==null)break main;
						
			if(hmTextValues!=null) {
				listaReturn = new ArrayList<JLabel>();
				Iterator<ArrayList<String>> itValues = hmTextValues.iterator();
				while(itValues.hasNext()) {						
					ArrayList<String> listaText = (ArrayList<String>) itValues.next();
					labelDebug = ModelComponentHelperZZZ.createLabel(listaText);
					if(labelDebug!=null) listaReturn.add(labelDebug);
				}
			}			
		}//end main:
		return listaReturn;
	}	
	
	public static boolean fillComponentHashMap(HashMapIndexedZZZ<Integer,ArrayList<JComponent>> hmComponentsIndexed, HashMapIndexedZZZ<Integer,ArrayList<String>> hmValuesText, int iIndexInCollection) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(hmComponentsIndexed==null) break main;
			if(hmValuesText==null)break main;
			
			ArrayList<JLabel>listaLabel = ModelComponentHelperZZZ.createLabelArrayList(hmValuesText);
			if(listaLabel!=null) {				
				Integer intIndexInCollection = new Integer(iIndexInCollection);
				hmComponentsIndexed.put(intIndexInCollection, listaLabel);
				bReturn = true;
			}			
			
		}//end main:
		return bReturn;
	}
}
