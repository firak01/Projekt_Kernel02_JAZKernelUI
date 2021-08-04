package debug.zKernelUI.component.navigator;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.KernelZZZ;
import custom.zKernel.LogZZZ;
import custom.zKernelUI.module.config.FrameConfigZZZ;

public class DebugNavigatorZZZ {

	public static void main(String[] args) {
		try {
			//1. Kernel Objekt initialisieren. Dies ist fuer das Logging grundlegend.
			KernelZZZ objKernel = new KernelZZZ("FGL", "01", "", "ZKernelConfigConfig_default.ini","useFormula");
			LogZZZ objLog = objKernel.getLogObject();
			
			
			//2. Frame initialisieren und �ffnen. Die Applikation "DebugButtonGroup soll verarbeitet werden		
			//KernelZZZ objKernelChoosen = new KernelZZZ("DebugButtonGroup",  "01", objKernel, null);
			//FrameDebugButtonGroupZZZ objFrame = new FrameDebugButtonGroupZZZ(objKernelChoosen);
			FrameDebugNavigatorZZZ objFrame = new FrameDebugNavigatorZZZ(objKernel);
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
