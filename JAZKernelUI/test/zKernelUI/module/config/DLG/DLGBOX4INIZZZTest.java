package zKernelUI.module.config.DLG;

import junit.framework.TestCase;
import basic.zBasic.ExceptionZZZ;
import basic.zKernel.KernelZZZ;
import custom.zKernelUI.module.config.DLG.DLGBOX4INIZZZ;
import custom.zKernelUI.module.config.DLG.Panel_DLGBOXZZZ;

public class DLGBOX4INIZZZTest extends TestCase {
	private DLGBOX4INIZZZ frmDLGBoxTest;
	
	protected void setUp(){
		try {			

			//### Aufbau des TestFixtures
			//Das Kernel-Objekt
			//KernelZZZ objKernel = new KernelZZZ("FGL", "01", "c:\\fglKernel\\KernelTest", "ZKernelConfigKernel_test.ini",(String)null);
			KernelZZZ objKernel = new KernelZZZ("FGL", "01", "", "ZKernelConfigKernelUI_test.ini",(String)null);
			
			//The main object used for testing.
			frmDLGBoxTest = new DLGBOX4INIZZZ(objKernel, null);  //Die Dialogbox wird ohne einen ParentFrame getestet
			
			//The module used for testing: SystemKey , Modul
			String[] saModule = {"TEST#01", "TestModule"};
			
			//Dadurch wird f�r jeden Test das Frame geladen !!!
			frmDLGBoxTest.launch(saModule);  // Nun muss aber für jeden Test das Fenster wieder geschlossen werden.
	
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} 
	}//END setup
	
	protected void tearDown(){
		//Den gestarteten Frame wieder schliessen, sonst hat man f�r jeden Test ein ge�ffnetes Fenster
		//frmDLGBox.dispose();
	}
	
	public void testFrameDLGBoxCreation(){
//		Test auf Sichtbarkeit
		assertTrue("The DLGBox  frame is not showing.", frmDLGBoxTest.isShowing());
		
		//Test auf Titel
		assertEquals("FGL non modale dialogbox: Started by using kernel-construktor and method .launch(...)", frmDLGBoxTest.getTitle());
				
		//Test auf das Panel, das diesem Frame hinzugef�gt wurde
		Panel_DLGBOXZZZ panelConfig = (Panel_DLGBOXZZZ) frmDLGBoxTest.getPanelSub("ContentPane");
		assertNotNull(panelConfig);
	}
	

	
}
