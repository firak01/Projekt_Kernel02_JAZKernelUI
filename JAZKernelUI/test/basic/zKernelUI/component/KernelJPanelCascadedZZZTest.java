/**
 * 
 */
package basic.zKernelUI.component;

import javax.swing.JComponent;

import com.jgoodies.forms.layout.RowSpec;

import junit.framework.TestCase;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernelUI.KernelUIZZZ;
import basic.zKernelUI.component.KernelJFrameCascadedZZZ;
import basic.zKernelUI.component.KernelJPanelCascadedZZZ;
import basic.zKernelUI.util.JFrameHelperZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.component.IKernelProgramZZZ;

/**
 * @author 0823
 *
 */
public class KernelJPanelCascadedZZZTest  extends TestCase{
	private FrmMain frameMainTest;
	private PanelMain panelMainTest;
	
	
	protected void setUp(){
		try {			
			KernelZZZ objKernel = new KernelZZZ("TEST", "01", "", "ZKernelConfigKernelUI_test.ini",(String)null);			
			frameMainTest = new FrmMain(objKernel);			
			panelMainTest = new PanelMain(objKernel);
									
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}		
	}//END setup
	
	
	protected void tearDown(){
		//Den ggf. gestarteten Frame wieder schliessen, sonst öffnet man permanent Fenster
		frameMainTest.dispose();
	}
	
	public void testFlagZExistsProof() throws ExceptionZZZ{
//		try{
		
			PanelMain objPanelMainTest = new PanelMain();
			//PanelMain objPanelMainInstanceTest = new objPanelMainTest.
					
			ObjectZZZ objObjectTest = new ObjectZZZ();
			Class objClass=null;
			try {
				objClass = Class.forName(objObjectTest.getClass().getName());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				ObjectZZZ objObjectInstanceTest = (ObjectZZZ) objClass.newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			//Test auf Flags aus dem basic-Bereich
			boolean btemp = panelMainTest.proofFlagZExists(ObjectZZZ.FLAGZ.DEBUG.name());			
			assertTrue(btemp);
			
			btemp = panelMainTest.proofFlagZExists("nixda");			
			assertFalse(btemp);
			
			btemp = panelMainTest.proofFlagZExists(KernelJPanelCascadedZZZ.FLAGZ.COMPONENT_DRAGGABLE.name());
			assertTrue(btemp);
			
			//Test auf Flags aus dem component-Bereich
			btemp = panelMainTest.proofFlagZExists(IKernelProgramZZZ.FLAGZ.ISKERNELPROGRAM.name());
			assertTrue(btemp);
//		} catch (ExceptionZZZ ez) {
//			fail("Method throws an exception." + ez.getMessageLast());
//		}				
	}
	
	
	//public deklaration notwendig , um per ObjectZZZ auf diese innere Klasse zugreifen zu k�nnen ?
	public class FrmMain extends KernelJFrameCascadedZZZ{
		//Default Konstruktor, notwendig, um newInstance() machen zu k�nnen.
		public FrmMain(){
			super();
		}
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
	}//END class fFrmMain4test
	
	//public deklaration notwendig , um per ObjectZZZ auf diese innere Klasse zugreifen zu k�nnen ?
	public class PanelMain extends KernelJPanelCascadedZZZ{
		//Default Konstruktor, notwendig, um newInstance() machen zu k�nnen.
		public PanelMain(){
			super();
		}
		public PanelMain(KernelZZZ objKernel) throws ExceptionZZZ{
			super(objKernel);
		}			
	}
}//END class KernelUIZZZTest

	