package basic.zKernelUI.util;

import javax.swing.JPanel;
import javax.swing.JTextField;

import basic.zBasic.ExceptionZZZ;
import basic.zKernelUI.component.IPanelCascadedZZZ;
import basic.zKernelUI.component.KernelJDialogExtendedZZZ;

public class JTextFieldHelperZZZ {
	/** Merke: Nach dem Markiern und den Fokus setzen muss das Elternpanel neu gezeichnet werden.
	 *         panelParent.revalidate(); //Das neuzeichnen ist wicht!!!
			   panelParent.repaint();
	 * 
	 * @param textfield
	 * @return
	 * @author Fritz Lindhauer, 13.04.2023, 19:53:33
	 */
	public static boolean markAndFocus(JTextField textfield){
		boolean bReturn = false;
		main:{
			if(textfield==null)break main;
			if(!textfield.isEditable())break main;
						
			textfield.requestFocus();
			textfield.selectAll();
			
			bReturn = true;
		}//end main
		return bReturn;
	}
	
	/** Merke: Nach dem Markiern und den Fokus setzen muss das Elternpanel neu gezeichnet werden.
	 *         panelParent.revalidate(); //Das neuzeichnen ist wicht!!!
			   panelParent.repaint();
	 * @param textfield
	 * @param iStartCharacterPosition
	 * @param iEndCharacterPositions
	 * @return
	 * @author Fritz Lindhauer, 13.04.2023, 19:53:29
	 */
	public static boolean markAndFocus(JTextField textfield, int iStartCharacterPosition, int iEndCharacterPositions){
		boolean bReturn = false;
		main:{
			if(textfield==null)break main;
			if(!textfield.isEditable())break main;
			
			textfield.requestFocus();
			textfield.setSelectionStart(iStartCharacterPosition);
			textfield.setSelectionEnd(iEndCharacterPositions);
			
			
			bReturn = true;
		}//end main
		return bReturn;
	}
	
	/** Merke: Nach dem Markiern und den Fokus setzen muss das Elternpanel neu gezeichnet werden.
	 *         panelParent.revalidate(); //Das neuzeichnen ist wicht!!!
			   panelParent.repaint();
	 * 
	 * @param textfield
	 * @return
	 * @author Fritz Lindhauer, 13.04.2023, 19:53:33
	 * @throws ExceptionZZZ 
	 */
	public static boolean markAndFocus(IPanelCascadedZZZ panelOfTextfield, JTextField textfield) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			bReturn = JTextFieldHelperZZZ.markAndFocus(textfield);
			if(!bReturn)break main;
			
			if(panelOfTextfield==null) break main;
			
			JPanel panel = (JPanel) panelOfTextfield;
			panel.revalidate();
			panel.repaint();
			
			JPanel panelParent = (JPanel) panelOfTextfield.getPanelParent();
			if(panelParent==null) {
				KernelJDialogExtendedZZZ dialog = panelOfTextfield.getDialogParent();
				panelParent = (JPanel) dialog.getPanelContent();
			}
			if(panelParent!=null) {
				panelParent.revalidate(); //Das neuzeichnen ist wichtig!!!
				panelParent.repaint();	
			}
			
			bReturn = true;
		}//end main
		return bReturn;
	}
	
	/** Merke: Nach dem Markiern und den Fokus setzen muss das Elternpanel neu gezeichnet werden.
	 *         panelParent.revalidate(); //Das neuzeichnen ist wicht!!!
			   panelParent.repaint();
	 * @param textfield
	 * @param iStartCharacterPosition
	 * @param iEndCharacterPositions
	 * @return
	 * @author Fritz Lindhauer, 13.04.2023, 19:53:29
	 * @throws ExceptionZZZ 
	 */
	public static boolean markAndFocus(IPanelCascadedZZZ panelOfTextfield, JTextField textfield, int iStartCharacterPosition, int iEndCharacterPositions) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			bReturn = JTextFieldHelperZZZ.markAndFocus(textfield, iStartCharacterPosition, iEndCharacterPositions);
			if(!bReturn)break main;
			
			if(panelOfTextfield==null) break main;
			JPanel panel = (JPanel) panelOfTextfield;
			panel.revalidate();
			panel.repaint();
			
			
			JPanel panelParent = (JPanel) panelOfTextfield.getPanelParent();
			if(panelParent==null) {
				KernelJDialogExtendedZZZ dialog = panelOfTextfield.getDialogParent();
				panelParent = (JPanel) dialog.getPanelContent();
			}
			if(panelParent!=null) {
				panelParent.revalidate(); //Das neuzeichnen ist wichtig!!!
				panelParent.repaint();	
			}
			
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
