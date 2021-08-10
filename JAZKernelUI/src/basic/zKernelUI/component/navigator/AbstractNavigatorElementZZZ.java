package basic.zKernelUI.component.navigator;

import javax.swing.JLabel;
import javax.swing.JPanel;

import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;

public abstract class AbstractNavigatorElementZZZ implements INavigatorElementZZZ {
	//Merke: Das darf z.B. nicht von JPanel erben, da man es dann nicht einfach einem anderen Panel zuordnen kann.
	//       Statt dessen die Komponenten hierin definieren.
	
	protected IModelNavigatorValueZZZ objModelNavigator=null;
	protected String sAlias = null;
	protected int iPositionInModel=0;
	
	protected JLabel label = null;
	
	public AbstractNavigatorElementZZZ() {
		super();
	}
	
	public AbstractNavigatorElementZZZ(IModelNavigatorValueZZZ objModelNavigator, String sAlias, int iPositionInModel, String sValue) {
		this();
		NavigatorElementNew_(objModelNavigator, null, sAlias, iPositionInModel, sValue);
	}
	
	public AbstractNavigatorElementZZZ(IModelNavigatorValueZZZ objModelNavigator, IKernelConfigSectionEntryZZZ objEntry) {
		this();
		NavigatorElementNew_(objModelNavigator, objEntry, null, -1, null);
	}
	
	private void NavigatorElementNew_(IModelNavigatorValueZZZ objModelNavigator, IKernelConfigSectionEntryZZZ objEntry, String sAlias, int iPositionInModel,String sValue) {		
		this.objModelNavigator = objModelNavigator;
		
		String sValueUsed=null;
		if(objEntry==null) {
			
			this.iPositionInModel = iPositionInModel;
			if(!StringZZZ.isEmpty(sAlias)) {
				this.sAlias = sAlias;				
			}else {
				this.sAlias = Integer.toString(this.iPositionInModel);
			}
			sValueUsed=sValue;
		}else {
			this.iPositionInModel = objEntry.getIndex();
			this.sAlias = Integer.toString(this.iPositionInModel);
			sValueUsed=objEntry.getValue();
		}
		
		JLabel label = new JLabel(sValueUsed);
		this.setLabel(label);	
	}
	
	@Override
	public JLabel getLabel() {
		return this.label;
	}
	
	@Override
	public void setLabel(JLabel label) {
		this.label = label;
	}
	
	@Override
	public String getAlias() {
		return this.sAlias;
	}

	@Override
	public void setAlias(String sAlias) {
		this.sAlias = sAlias;
	}

	@Override
	public int getPosition() {
		return this.iPositionInModel;
	}

	@Override
	public void setPosition(int iPosition) {
		this.iPositionInModel = iPosition;
	}
	
	@Override
	public void setVisible(boolean bVisible) {
		// TODO Auto-generated method stub
		
	}
}
