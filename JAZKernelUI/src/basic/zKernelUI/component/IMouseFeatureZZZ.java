/**
 * 
 */
package basic.zKernelUI.component;

import basic.zBasic.ExceptionZZZ;

/**
 * @author 0823
 *
 */
public interface IMouseFeatureZZZ {
	public enum FLAGZ{
		COMPONENT_DRAGGABLE, TERMINATE;
	}
	
	//damit muss man nicht mehr tippen hinter dem enum .name()
	public boolean getFlag(IMouseFeatureZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;
	public boolean setFlag(IMouseFeatureZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	public boolean[] setFlag(IMouseFeatureZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	public boolean proofFlagExists(IMouseFeatureZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;
	public boolean proofFlagSetBefore(IMouseFeatureZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;
	
	public abstract boolean isJComponentContentDraggable() throws ExceptionZZZ;
	public abstract void setJComponentContentDraggable(boolean bValue) throws ExceptionZZZ;
}
