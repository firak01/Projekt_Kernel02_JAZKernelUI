package basic.zKernelUI.component;

import basic.zBasic.ExceptionZZZ;
import basic.zKernelUI.component.IDialogExtendedZZZ.FLAGZLOCAL;

public interface ITextFieldListening4ComponentSelectionResetZZZ {
	public enum FLAGZ {
		USEEVENTRESETDEFAULT;
	}
	
	//damit muss man nicht mehr tippen hinter dem enum .name()
	public abstract boolean getFlagLocal(ITextFieldListening4ComponentSelectionResetZZZ.FLAGZ objEnumFlag);
	public abstract boolean setFlagLocal(ITextFieldListening4ComponentSelectionResetZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	public abstract boolean[] setFlagLocal(ITextFieldListening4ComponentSelectionResetZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	public abstract boolean proofFlagLocalExists(ITextFieldListening4ComponentSelectionResetZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;
	
	
	
}
