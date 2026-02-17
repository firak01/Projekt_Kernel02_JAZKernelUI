package debug.zKernelUI.component.navigator;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectWithExpressionZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.file.ini.IKernelExpressionIniSolverZZZ;
import basic.zKernel.file.ini.IKernelJsonArrayIniSolverZZZ;
import basic.zKernel.file.ini.IKernelJsonIniSolverZZZ;
import basic.zKernel.file.ini.IKernelJsonMapIniSolverZZZ;
import basic.zKernel.file.ini.IKernelZFormulaIni_PathZZZ;
import basic.zKernel.file.ini.IKernelZFormulaIni_VariableZZZ;
import custom.zKernel.LogZZZ;
import custom.zKernelUI.module.config.FrameConfigZZZ;

public class DebugNavigatorZZZ {

	public static void main(String[] args) {
		try {
			//1. Kernel Objekt initialisieren. Dies ist fuer das Logging grundlegend.
			String[] saFlag= {"DEBUGUI_PANELLABEL_ON","useFormula", IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH.name(), IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE.name(), IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION.name(), IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER.name(), IKernelJsonIniSolverZZZ.FLAGZ.USEJSON.name(), IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY.name(), IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP.name()};
			KernelZZZ objKernel = new KernelZZZ("FGL", "01", "", "ZKernelConfigConfig_default.ini", saFlag);
			LogZZZ objLog = objKernel.getLogObject();
			
			
			//2. Frame initialisieren und oeffnen. Die Applikation "DebugButtonGroup soll verarbeitet werden		
			//KernelZZZ objKernelChoosen = new KernelZZZ("DebugButtonGroup",  "01", objKernel, null);
			//FrameDebugButtonGroupZZZ objFrame = new FrameDebugButtonGroupZZZ(objKernelChoosen);
			FrameDebugNavigatorZZZ objFrame = new FrameDebugNavigatorZZZ(objKernel);
			boolean btemp = objFrame.launch();
			
			//3. Erfolgreichen Start protokollieren
			if (btemp==true){
				objLog.writeLineDate("Main frame of configuration module successfully launched.");
			}else{
				objLog.writeLineDate("Main frame of configuration module can not  launch successfully.");
			}
			
}catch (ExceptionZZZ ez){
	System.out.println(ez.getDetailAllLast());
		}
}//end main -function
}
