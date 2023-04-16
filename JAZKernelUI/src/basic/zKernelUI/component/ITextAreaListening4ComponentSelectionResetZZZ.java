package basic.zKernelUI.component;

import basic.zBasic.ExceptionZZZ;
import basic.zKernelUI.component.IDialogExtendedZZZ.FLAGZLOCAL;

public interface ITextAreaListening4ComponentSelectionResetZZZ {
	public enum FLAGZ {
		USEEVENTRESETDEFAULT;
	}
	
	//damit muss man nicht mehr tippen hinter dem enum .name()
	public abstract boolean getFlagLocal(ITextAreaListening4ComponentSelectionResetZZZ.FLAGZ objEnumFlag);
	public abstract boolean setFlagLocal(ITextAreaListening4ComponentSelectionResetZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	public abstract boolean[] setFlagLocal(ITextAreaListening4ComponentSelectionResetZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	public abstract boolean proofFlagLocalExists(ITextAreaListening4ComponentSelectionResetZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;
	
	
	
}
