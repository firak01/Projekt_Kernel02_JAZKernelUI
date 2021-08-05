package basic.zKernelUI.component.navigator;

import javax.swing.JPanel;

public abstract class AbstractNavigatorElementZZZ extends JPanel implements INavigatorElementZZZ {
	protected IModelNavigatorValueZZZ objModelNavigator=null;
	protected String sAlias = null;
	protected int iPositionInModel=0;//das soll kein Indexwert sein.
	
	public AbstractNavigatorElementZZZ() {
		super();
	}
	
	public AbstractNavigatorElementZZZ(IModelNavigatorValueZZZ objModelNavigator, String sAlias, int iPositionInModel) {
		this();
		NavigatorElementNew_(objModelNavigator, sAlias, iPositionInModel);
	}
	
	private void NavigatorElementNew_(IModelNavigatorValueZZZ objModelNavigator, String sAlias, int iPositionInModel) {
		this.objModelNavigator = objModelNavigator;
		this.sAlias = sAlias;
		this.iPositionInModel = iPositionInModel;
	}
}
