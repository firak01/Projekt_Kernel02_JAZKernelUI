package basic.zKernelUI.util;

import javax.swing.JTextField;

public class JTextFieldHelperZZZ {
	public static boolean markAndFocus(JTextField textfield){
		boolean bReturn = false;
		main:{
			if(textfield==null)break main;
			if(!textfield.isEditable())break main;
			
			textfield.selectAll();
			textfield.requestFocus();
			
			bReturn = true;
		}//end main
		return bReturn;
	}
}
