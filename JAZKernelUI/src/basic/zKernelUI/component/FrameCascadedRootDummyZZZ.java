package basic.zKernelUI.component;

import javax.swing.JComponent;
import javax.swing.JFrame;

import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernelUI.KernelUIZZZ;
import basic.zKernelUI.util.JFrameHelperZZZ;
import basic.zBasic.ExceptionZZZ;

/** Weil KernelJFrameCascaded eine abstracte Klasse ist, wird das ben�tigt, um z.B. einen CascasdedFrame aus einem Normalen JFrame zu erstellen,
 *   der dann im Konstruktor von KernelJPanelCascadedZZZ verwendet werden kann.
 *   
 * @author lindhaueradmin
 *
 */
public class FrameCascadedRootDummyZZZ extends KernelJFrameCascadedZZZ{
	
	public FrameCascadedRootDummyZZZ(IKernelZZZ objKernel, JFrame frame) {
		super(objKernel, frame);

	}

	public boolean launchCustom() throws ExceptionZZZ {		
		return false;
	}

	public JComponent getPaneContent(String sAlias) throws ExceptionZZZ {
		return null;
	}

	@Override
	public boolean setSizeDefault() throws ExceptionZZZ {
		JFrameHelperZZZ.setSizeDefault(this);
		return true;
	}

	@Override
	public IPanelCascadedZZZ createPanelContent() throws ExceptionZZZ {		
		return null;
	}
}
	
