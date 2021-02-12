package basic.zKernelUI.component;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.component.AbstractKernelProgramZZZ;
import basic.zKernel.component.IKernelProgramZZZ;

public class AbstractKernelProgramUIZZZ extends AbstractKernelProgramZZZ{

	/**Z.B. Wg. Reflection immer den Standardkonstruktor zur Verfügung stellen.
	 * 
	 * 31.01.2021, 12:15:10, Fritz Lindhauer
	 */
	public AbstractKernelProgramUIZZZ() {
		super();
	}
	
	public AbstractKernelProgramUIZZZ(IKernelZZZ objKernel) {
		super(objKernel); 		
	}
	
	public AbstractKernelProgramUIZZZ(IKernelZZZ objKernel, KernelJPanelCascadedZZZ panel, String[] saFlagControl) throws ExceptionZZZ {
		super(objKernel);
		AbstractKernelProgramUINew_(objKernel, panel, saFlagControl);
	}
	
	private void AbstractKernelProgramUINew_(IKernelZZZ objKernel, KernelJPanelCascadedZZZ panel, String[] saFlagControl) throws ExceptionZZZ {
		main:{
			check:{	 		
				if(saFlagControl != null){
					String stemp; boolean btemp;
					for(int iCount = 0;iCount<=saFlagControl.length-1;iCount++){
						stemp = saFlagControl[iCount];
						btemp = setFlag(stemp, true);
						if(btemp==false){ 								   
							   ExceptionZZZ ez = new ExceptionZZZ(stemp, iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 							
							   throw ez;		 
						}
					}
					if(this.getFlag("init")) break main;										
				}							
			}//End check
		
		//WICHTIG, DAS IST DER UI UNTERSCHIED ZUM BACKEND PROGRAM
		this.setModule(panel);
//				
//		String sModuleName = this.getModuleName();
//		if(StringZZZ.isEmpty(sModuleName)){
//			ExceptionZZZ ez = new ExceptionZZZ("ModuleName", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
//			throw ez;
//		}
//				
//		String sProgramName = this.getProgramName();
//		if(StringZZZ.isEmpty(sProgramName)){
//			ExceptionZZZ ez = new ExceptionZZZ("ProgramName", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
//			throw ez;
//		}
//		
//		
		//### Prüfen, ob das Modul konfiguriert ist
//		boolean bIsConfigured = objKernel.proofModuleFileIsConfigured(sModuleAlias);
//		if(bIsConfigured==false){
//			ExceptionZZZ ez = new ExceptionZZZ("ModuleAlias='" + sModuleAlias + "' seems not to be configured for the Application '" + objKernel.getApplicationKey(), iERROR_CONFIGURATION_MISSING, ReflectCodeZZZ.getMethodCurrentName());
//			throw ez;
//		}		
//		boolean bExists = objKernel.proofModuleFileExists(sModuleAlias);
//		if(bExists==false){
//			ExceptionZZZ ez = new ExceptionZZZ("ModuleAlias='" + sModuleAlias + "' is configured, but the file does not exist for the Application '" + objKernel.getApplicationKey(), iERROR_CONFIGURATION_MISSING, ReflectCodeZZZ.getMethodCurrentName());
//			throw ez;
//		}	
		
		
	}//END main
		
	}
	
}
