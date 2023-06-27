package basic.zKernelUI.thread;

import javax.swing.SwingUtilities;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernelUI.component.IProgramUIZZZ;


public abstract class KernelSwingWorker4UIZZZ extends KernelSwingWorkerZZZ implements IKernelSwingWorker4UIZZZ{

	public KernelSwingWorker4UIZZZ() {
		super();
	}
	
	public KernelSwingWorker4UIZZZ(IKernelZZZ objKernel) {
		super(objKernel);		
	}
	
	/**Aus dem Worker-Thread heraus wird ein Thread gestartet (der sich in die EventQueue von Swing einreiht.)
	 *  Entspricht auch ProgramIPContext.updateLabel(..)
	* @param stext
	* 
	* lindhaueradmin; 17.01.2007 12:09:17
	 */
	@Override
	public void updateLabel(final IProgramUIZZZ objProgram, final String sValue) {	
//		Das Schreiben des Ergebnisses wieder an den EventDispatcher thread uebergeben
		Runnable runnerUpdateLabel= new Runnable(){

			public void run(){
				logLineDate("Program for label update started with '" + sValue + "'");								
				try {
					objProgram.updateLabel(sValue);
				} catch (ExceptionZZZ e) {
					e.printStackTrace();
				}				 
			}
		};
		
		SwingUtilities.invokeLater(runnerUpdateLabel);			
	}

	@Override
	public void updateValue(final IProgramUIZZZ objProgram, final String sValue) {		
//		Das Schreiben des Ergebnisses wieder an den EventDispatcher thread uebergeben
		Runnable runnerUpdateValue= new Runnable(){

			public void run(){
				logLineDate("Program for label update started with '" + sValue + "'");								
				try {
					objProgram.updateValue(sValue);
				} catch (ExceptionZZZ e) {
					e.printStackTrace();
				}
			}
		};
		
		SwingUtilities.invokeLater(runnerUpdateValue);
	}

	@Override
	public void updateMessage(final IProgramUIZZZ objProgram, final String sValue) {
//		Das Schreiben des Ergebnisses wieder an den EventDispatcher thread uebergeben
		Runnable runnerUpdateMessage= new Runnable(){

			public void run(){
				logLineDate("Program for message update started with '" + sValue + "'");								
				try {
					objProgram.updateMessage(sValue);
				} catch (ExceptionZZZ e) {
					e.printStackTrace();
				}
			}
		};
		
		SwingUtilities.invokeLater(runnerUpdateMessage);
	}
}
