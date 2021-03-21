package basic.zKernelUI.thread;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectZZZ;
import basic.zBasicUI.thread.SwingWorker;
import basic.zKernel.IKernelUserZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelLogZZZ;
import custom.zKernel.LogZZZ;

public abstract class KernelSwingWorkerZZZ extends SwingWorker  implements IObjectZZZ, IKernelUserZZZ{
	protected IKernelZZZ objKernel = null;
	protected LogZZZ objLog = null;
	protected ExceptionZZZ objException = null;    // diese Exception hat jedes Objekt
		
	public KernelSwingWorkerZZZ() {
		super();
	}
	
	public KernelSwingWorkerZZZ(IKernelZZZ objKernel) {
		super();
		KernelSwingWorkerNew_(objKernel);
	}
	
	private boolean KernelSwingWorkerNew_(IKernelZZZ objKernel) {
		boolean bReturn = false;
		main:{
			this.setKernelObject(objKernel);
			this.setLogObject(this.getKernelObject().getLogObject());
			
			bReturn = true;
		}//end main:
		return bReturn;		
	}
	
	@Override
	public LogZZZ getLogObject() {		
		return this.objLog;
	}

	@Override
	public void setLogObject(LogZZZ objLog) {
		this.objLog = objLog;		
	}

	@Override
	public IKernelZZZ getKernelObject() {
		return this.objKernel;
	}

	@Override
	public void setKernelObject(IKernelZZZ objKernel) {
		this.objKernel = objKernel;
	}

	@Override
	public ExceptionZZZ getExceptionObject() {
		return this.objException;
	}

	@Override
	public void setExceptionObject(ExceptionZZZ objException) {
		this.objException = objException;
	}
	
	//aus IKernelLogObjectUserZZZ, analog zu KernelKernelZZZ
	@Override
	public void logLineDate(String sLog) {
		LogZZZ objLog = this.getLogObject();
		if(objLog==null) {
			String sTemp = KernelLogZZZ.computeLineDate(sLog);
			System.out.println(sTemp);
		}else {
			objLog.WriteLineDate(sLog);
		}		
	}	
}
