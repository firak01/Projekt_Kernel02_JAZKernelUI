package custom.zKernelUI.module.config.DLG;

import java.util.Hashtable;

import basic.zBasic.ExceptionZZZ;

public interface IComponentTableZZZ {

	/**Gibt den Inhalt des xten Textfeldes zurück
	 * @param iPosition
	 * @return
	 */
	public abstract String getValue(int iPosition);
	
	/**Gibt den Alias der Tabelle zurück. Dies entspricht dem Namen der Section in der Ini-Datei.
	 * Merke: Der aktuelle Section-Name entspricht dem objKernel.getSystemKey()
	 * @return String
	 * @throws ExceptionZZZ 
	 */
	public abstract String getTableAlias() throws ExceptionZZZ;

	/**Gibt den Inhalt der JLabel = JTextField Paare als Tabelle zurück. 
	 * (bzw. den Inhalt der Section als Wertpaare Property = Value)
	 * @return Hashtable
	 * @param  bExcludeValueEmpty, Leerwerte (d.h. der Inhalt von JTextField ist leer) werden dann nicht in die Tabelle übernommen. 
	 * @throws ExceptionZZZ 
	 */
	public abstract Hashtable getTable(boolean bExcludeValueEmpty)
			throws ExceptionZZZ;

}