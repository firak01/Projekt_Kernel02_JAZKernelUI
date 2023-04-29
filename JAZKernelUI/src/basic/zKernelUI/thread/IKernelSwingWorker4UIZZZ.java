package basic.zKernelUI.thread;

import basic.zKernelUI.component.IProgramUIZZZ;

public interface IKernelSwingWorker4UIZZZ{
	//Wie IProgramUI, nur hier dieses Program als Argument mitgeben
	public void updateLabel(IProgramUIZZZ objProgram, String sValue);
	public void updateValue(IProgramUIZZZ objProgram, String sValue);
	public void updateMessage(IProgramUIZZZ objProgram, String sValue);
}
