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
	
	public static boolean moveCursorToStart(JTextField textfield) {
		boolean bReturn = false;
		main:{
			if(textfield==null)break main;
			if(!textfield.isEditable())break main;
			
			textfield.setCaretPosition(0);   //Das soll bewirken, dass der Anfang jedes neu eingegebenen Textes sichtbar ist.
			textfield.requestFocus();
			
			bReturn = true;
		}//end main
		return bReturn;

		
		
	}
}
