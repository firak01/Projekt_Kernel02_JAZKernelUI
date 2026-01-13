package basic.zKernelUI.component;

import java.util.ArrayList;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import basic.zBasic.ExceptionZZZ;

public interface IFormLayoutZZZ {
	public abstract boolean initFormLayoutDebug() throws ExceptionZZZ;
	public abstract boolean initFormLayoutContent() throws ExceptionZZZ;
	
	public abstract int getNumberOfRows() throws ExceptionZZZ;
	public abstract void setNumberOfRows(int iNumberOfRows) throws ExceptionZZZ;
	
	public abstract FormLayout buildFormLayoutUsed() throws ExceptionZZZ;
	public abstract FormLayout buildFormLayoutUsed(int iNumberOfRows) throws ExceptionZZZ;
	
	public abstract ArrayList<RowSpec> getRowSpecs() throws ExceptionZZZ;
	public abstract void setRowSpecs(ArrayList<RowSpec> listaRowSpec) throws ExceptionZZZ;
	public abstract ArrayList<RowSpec> buildRowSpecs() throws ExceptionZZZ;	
	public abstract ArrayList<RowSpec> buildRowSpecs(int iNumberOfRows) throws ExceptionZZZ;
	public abstract RowSpec buildRowSpecDebug() throws ExceptionZZZ;	
	
	public abstract ArrayList<ColumnSpec> getColumnSpecs() throws ExceptionZZZ;
	public abstract ArrayList<ColumnSpec> buildColumnSpecs() throws ExceptionZZZ;
		
	public abstract boolean fillRowDebug(CellConstraints cc) throws ExceptionZZZ;
	public abstract boolean fillRowContent(CellConstraints cc, int iRow) throws ExceptionZZZ;
	
}
