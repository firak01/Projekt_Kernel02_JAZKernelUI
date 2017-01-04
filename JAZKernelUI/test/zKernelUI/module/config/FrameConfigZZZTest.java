package zKernelUI.module.config;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;

import basic.zBasic.ExceptionZZZ;
import basic.zKernelUI.util.KernelJComboBoxHelperZZZ;
import basic.zKernel.KernelZZZ;
import custom.zKernelUI.module.config.FrameConfigZZZ;
import custom.zKernelUI.module.config.PanelConfigZZZ;
import custom.zKernelUI.module.config.PanelConfig_CENTERZZZ;
import custom.zKernelUI.module.config.PanelConfig_SOUTHZZZ;
import junit.framework.TestCase;

public class FrameConfigZZZTest extends TestCase {
	
	FrameConfigZZZ frmConfigTest;
	
	protected void setUp(){
		try {			

			//### Aufbau des TestFixtures
			//Das Kernel-Objekt
			KernelZZZ objKernel = new KernelZZZ("TEST", "01", "", "ZKernelConfigKernelUI_test.ini",(String)null);
			
			//The main object used for testing: Es wird die TEST-Applikation verwendet. Angezeigt werden soll aber die Konfiguration der FGL - Applikation....
			frmConfigTest = new FrameConfigZZZ(objKernel, "FGL", "01");
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} 
	}//END setup
	
	protected void tearDown(){
		//Den gestarteten Frame wieder schliessen, sonst �ffnet man permanent Fenster
		frmConfigTest.dispose();
	}
	
	/** void, Test auf Sichtbarkeit und auf den angezeigten Titel des Frames
	* Lindhauer; 05.05.2006 07:49:38
	 */
	public void testFrameConfigCreation(){	
		try{
			//Frame starten
			frmConfigTest.launch("FGL module configuration");  // Nun muss aber f�r jeden Test das Fenster wieder geschlossen werden.
	
			//Test auf Sichtbarkeit
			assertTrue("The configuration frame is not showing.", frmConfigTest.isShowing());
			
			//Test auf Titel
			assertEquals("FGL module configuration", frmConfigTest.getTitle());
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} 
	}
	
	
	/** void, Test auf Sichtbarkeit des Hauptpanels, das in den ContentPane des Frames eingef�gt wurde
	 * - Testet auch GetPanelSub(...)
	* Lindhauer; 05.05.2006 08:44:17
	 */
	public void testPanelContentPaneCreation(){		
		try{				
			//Frame starten. Erst dadarch werden die Panels hinzugef�gt
			frmConfigTest.launch("FGL module configuration");  // Nun muss aber f�r jeden Test das Fenster wieder geschlossen werden.
						
			//Test auf das Panel, das diesem Frame hinzugef�gt wurde
			PanelConfigZZZ panelConfig = (PanelConfigZZZ) frmConfigTest.getPanelSub("ContentPane");
			assertNotNull("The panel 'ContentPane' of the frame was not found", panelConfig);	
			
			//Test auf Sichtbarkeit
			assertTrue("The 'ContentPane' panel is not showing", panelConfig.isShowing());			
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} 
	}
	
	/** void, Test auf Sichtbarkeit der Komponenten des Panels, also der "Unterpanels"
	 * - Testet auch GetPanelSub(...)
	* Lindhauer; 05.05.2006 08:44:17
	 */
	public void testPanelSubALLCreation(){		
		try{
			//Frame starten. Erst dadarch werden die Panels hinzugef�gt
			frmConfigTest.launch("FGL module configuration");  // Nun muss aber f�r jeden Test das Fenster wieder geschlossen werden.									
			PanelConfigZZZ panelConfig = (PanelConfigZZZ) frmConfigTest.getPanelSub("ContentPane");		
			
			//1. CENTER
			PanelConfig_CENTERZZZ panelCenter = (PanelConfig_CENTERZZZ) panelConfig.getPanelSub("CENTER");
			assertNotNull("The subpanel 'CENTER' is not available", panelCenter);
			assertTrue("The subpanel 'CENTER' is not showing", panelCenter.isShowing());		
			
			//Test auf das Vorhandensein der Combo-Box
			JComponent objComp = panelCenter.getComponent("ComboModule");
			assertNotNull("The combo box for module selection is not available", objComp);		
			assertTrue("The component 'ComboModule' is not showing", objComp.isShowing());	
			
			//Test auf den Inhalt der Combo-Box
			JComboBox objCombo = (JComboBox) objComp;
			
			//Dazu eine Helper-Klasse verwenden ....
			KernelZZZ objKernel = frmConfigTest.getKernelObject();
			KernelJComboBoxHelperZZZ objComboHelp = new KernelJComboBoxHelperZZZ(objKernel, objCombo);
			String[] saContent = objComboHelp.getStringAll();
			assertNotNull("The combo box for module selection seems to have no content", saContent);	
			assertFalse("There were 2 entries expected 'TestModule' and 'Kernel' as content of the combo box. But there is only the entry '" + saContent[0] +"' (using applicationkey: '" + objKernel.getApplicationKey() + "'", saContent.length<=1);
			assertFalse("There were only expected 'TestModule' and 'Kernel' as content of the combo box", saContent.length > 2 );
			assertEquals("There was  expected 'Kernel' as first entry of the combo box","Kernel", saContent[0]);
			assertEquals("There was expected 'TestModul' as second entry of the combo box", "TestModule", saContent[1]);
						
			///##########################################################
			//2. SOUTH
			PanelConfig_SOUTHZZZ panelSouth = (PanelConfig_SOUTHZZZ) panelConfig.getPanelSub("SOUTH");
			assertNotNull("The subpanel 'SOUTH' is not available", panelSouth);
			assertTrue("The subpanel 'SOUTH' is not showing", panelSouth.isShowing());
			
//			Test auf das Vorhandensein der Combo-Box
			JComponent objComp2 = panelSouth.getComponent("ButtonEdit");
			assertNotNull("The button for starting the module dialogbox is not available.", objComp2);		
			assertTrue("The button for starting the module dialogbox is not showing", objComp2.isShowing());	
			
			//Test auf den Inhalt des buttons
			JButton objButton = (JButton) objComp2;
			assertEquals("Edit module configuration", objButton.getText());
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} 
	}
}//END class
		

