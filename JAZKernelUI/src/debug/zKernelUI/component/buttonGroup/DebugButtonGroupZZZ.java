package debug.zKernelUI.component.buttonGroup;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.KernelZZZ;
import custom.zKernel.LogZZZ;
import custom.zKernelUI.module.config.FrameConfigZZZ;

public class DebugButtonGroupZZZ {

	public static void main(String[] args) {
		try {
			//1. Kernel Objekt initialisieren. Dies ist f�r das Logging grundlegend.
			KernelZZZ objKernel = new KernelZZZ("FGL", "01", "", "ZKernelConfigConfig_default.ini",(String)null);
			LogZZZ objLog = objKernel.getLogObject();
			
			
			//2. Frame initialisieren und �ffnen. Die Applikation "DebugButtonGroup soll verarbeitet werden		
			KernelZZZ objKernelChoosen = new KernelZZZ("DebugButtonGroup",  "01", objKernel, null);
			FrameDebugButtonGroupZZZ objFrame = new FrameDebugButtonGroupZZZ(objKernelChoosen);
			boolean btemp = objFrame.launch();
			
			//3. Erfolgreichen Start protokollieren
			if (btemp==true){
				objLog.WriteLineDate("Main frame of configuration module successfully launched.");
			}else{
				objLog.WriteLineDate("Main frame of configuration module can not  launch successfully.");
			}
			
}catch (ExceptionZZZ ez){
	System.out.println(ez.getDetailAllLast());
		}
}//end main -function
}
