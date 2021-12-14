/**
 * 
 */
package zKernelUI;

import javax.swing.JComponent;

import junit.framework.TestCase;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernelUI.KernelUIZZZ;
import basic.zKernelUI.component.IPanelCascadedZZZ;
import basic.zKernelUI.component.KernelJFrameCascadedZZZ;
import basic.zKernelUI.component.KernelJPanelCascadedZZZ;
import basic.zKernelUI.util.JFrameHelperZZZ;
import basic.zKernel.KernelZZZ;

/**
 * @author 0823
 *
 */
public class KernelUIZZZTest  extends TestCase{
	private FrmMain frameMainTest;
	
	
	protected void setUp(){
		try {			
			KernelZZZ objKernel = new KernelZZZ("TEST", "01", "", "ZKernelConfigKernelUI_test.ini",(String)null);			
			frameMainTest = new FrmMain(objKernel);
									
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}		
	}//END setup
	
	
	protected void tearDown(){
		//Den ggf. gestarteten Frame wieder schliessen, sonst �ffnet man permanent Fenster
		frameMainTest.dispose();
	}
	
	public void testIsModuleConfigured(){
		try{
			boolean btemp =KernelUIZZZ.isModuleConfigured(frameMainTest); 
			assertTrue(btemp);
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}				
	}
	
	public void testSearchModuleFirstConfiguredModule(){	
		try{
			String sModule = null;
			String sModuleTemp = frameMainTest.getClass().getName();
			if(StringZZZ.contains(sModuleTemp, "$")){
				sModule = StringZZZ.left(sModuleTemp, "$");
			}else{
				sModule = sModuleTemp;
			}
			String stemp = KernelUIZZZ.searchModuleFirstConfiguredClassname(frameMainTest);
			assertEquals(stemp, sModule);
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}	
	}
	
	class FrmMain extends KernelJFrameCascadedZZZ{
		public FrmMain(KernelZZZ objKernel) throws ExceptionZZZ{
			super(objKernel);
		}

		public boolean launchCustom() throws ExceptionZZZ {		
			return false;
		}

		public KernelJPanelCascadedZZZ getPaneContent() {
			// TODO Auto-generated method stub
			//TODO Hier muss nun das gew�nschte Panel mit new erstellt werden und 
			//          das panel muss der hashtable mit dem Alias "panelcontent" hinzugef�gt werden.
			return null;
		}

		public JComponent getPaneContent(String sAlias) throws ExceptionZZZ {
			// TODO Auto-generated method stub
			//Hier wird nix in einen anderen Pane als den ContentPane gestellt.
			return null;
		}

		@Override
		public boolean setSizeDefault() throws ExceptionZZZ {
			JFrameHelperZZZ.setSizeDefault(this);
			return true;
		}

		@Override
		public IPanelCascadedZZZ createPanelContent() throws ExceptionZZZ {
			// TODO Auto-generated method stub
			return null;
		} 
	}//END class fFrmMain4test
}//END class KernelUIZZZTest

	