package basic.zKernelUI.component;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;
import basic.zKernel.flag.IFlagZUserZZZ.FLAGZ;

public interface IDialogExtendedZZZ {
//	public enum FLAGZLOCAL {
//		SKIPDEBUGUI,HIDE_ON_OK,HIDE_ON_CLOSE,HIDE_ON_CANCEL;
//	}
	
	//20230416: SKIPDEBUGUI ist doch nur was für die Panels, oder?
	public enum FLAGZLOCAL {
		HIDE_ON_OK,HIDE_ON_CLOSE,HIDE_ON_CANCEL;
	}
	
	//damit muss man nicht mehr tippen hinter dem enum .name()
	public abstract boolean getFlagLocal(IDialogExtendedZZZ.FLAGZLOCAL objEnumFlag);
	public abstract boolean setFlagLocal(IDialogExtendedZZZ.FLAGZLOCAL objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	public abstract boolean[] setFlagLocal(IDialogExtendedZZZ.FLAGZLOCAL[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	public abstract boolean proofFlagLocalExists(IDialogExtendedZZZ.FLAGZLOCAL objEnumFlag) throws ExceptionZZZ;
	
}
