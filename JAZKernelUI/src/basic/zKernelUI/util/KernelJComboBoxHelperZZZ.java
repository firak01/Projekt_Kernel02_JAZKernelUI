package basic.zKernelUI.util;

import javax.swing.JComboBox;

import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.AbstractKernelUseObjectZZZ;

/**Class helps to find out more about a JComboBox.
 * - e.g. returns the items as an array
 * @author Lindhauer
 *
 */
public class KernelJComboBoxHelperZZZ extends AbstractKernelUseObjectZZZ{
private JComboBox objCombo;

	public KernelJComboBoxHelperZZZ(IKernelZZZ objKernel, JComboBox objCombo) throws ExceptionZZZ{
		super(objKernel);
		this.setJComboBox(objCombo);
	}
	
	/** String[], on an existing Combo-Box: Iterates through all items. Get the strings and return them as an array.
	* Lindhauer; 06.05.2006 08:34:25
	 * @return
	 */
	public String[] getStringAll(){
		String[] saReturn = null;
		main:{
			JComboBox objCombo = this.getJComboBox();
			check:{
			if(objCombo==null) break main;
			if(objCombo.getItemCount()==0) break main;
			}//END check:
			
			saReturn = new String[objCombo.getItemCount()];
			for(int icount=0; icount < objCombo.getItemCount(); icount++){
				saReturn[icount] = (String) objCombo.getItemAt(icount);				
			}
		}//END main:
		return saReturn;
	}
	
	public void insertSorted(String sValue) throws ExceptionZZZ{
		main:{
			if(StringZZZ.isEmpty(sValue)==true) break main;
			JComboBox objCombo = this.getJComboBox();
			if(objCombo==null) break main;
			
			//zuerst auf das StringArray zur�ckkommen
			String[] saAll = this.getStringAll();
			if(saAll==null){
				objCombo.addItem(sValue);
				break main;
			}else{
//				In das sortierte StringArray nun den Wert einf�gen				
				String[] saNew = StringArrayZZZ.insertSorted(saAll, sValue, "IGNORECASE");
				
				//Nun alle Items entferen und neu aufbauen.
				objCombo.removeAllItems();
				for(int icount = 0; icount <= saNew.length-1;icount++){
					String stemp = saNew[icount];
					objCombo.addItem(stemp);
				}
			}
		}//end main:
	}
	
	//##############################################################
	//### Getter / Setter
	public JComboBox getJComboBox(){
		return this.objCombo;
	}
	public void setJComboBox(JComboBox objCombo){
		this.objCombo = objCombo;
	}
}
