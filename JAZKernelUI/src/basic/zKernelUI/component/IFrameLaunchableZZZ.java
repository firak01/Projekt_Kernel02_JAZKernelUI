package basic.zKernelUI.component;

import javax.swing.JComponent;
import javax.swing.JMenuBar;

import basic.zBasic.ExceptionZZZ;


public interface IFrameLaunchableZZZ {
	
	/** Startet einen Frame. Dabei wird der erste Frame mit einem extra Thread UND Swingutilities.invokeLater() gestartet.
	 *   Damit wird also die Erzeugung des Frames nicht in den EventDispatchThread gestellt. Merke: Das macht nur Sinn, wenn in launchCustom() weiterer zeitaufwendiger Code ausgef�hrt wird.
	* @return
	* @throws ExceptionZZZ
	* 
	* lindhaueradmin; 15.03.2007 10:04:17
	 */
	public abstract boolean launch() throws ExceptionZZZ;
	public abstract boolean launch(String sTitle) throws ExceptionZZZ;
	
	
	/** Hier ist die Stelle in der noch zus�tzlicher Code stattfinden kann. Z.B: initialisieren von Datenbank oder Http-Connections.
	* @return true= es wird kein standardm��iges packen der Komponenten durchgef�hrt, so dass die gesamte Frame Gr��e von der gew�hlten Gr��e abh�ngt.
	*               false=es wird frame.pack() ausgef�hrt. Damit h�ngt die Gr��e des Frames von der Gr��e der einzelnen Komponenten ab.
	* @throws ExceptionZZZ
	* 
	* lindhaueradmin; 15.03.2007 10:09:12
	 */
	public abstract boolean launchCustom() throws ExceptionZZZ;
	
	
	/**Hier wird das erste Panel des JFrames festgelegt. Es wird automatisch im .launch() herangezogen.
	* @return
	* @throws ExceptionZZZ
	* 
	* lindhaueradmin; 15.03.2007 10:07:46
	 */
	public abstract IPanelCascadedZZZ getPaneContent() throws ExceptionZZZ;
	
	/** Hier wird ein anderes Panel des JFrame festgelegt, das auf einer anderen Eben liegen kann. Es wird automatisch im .launch() herangezogen.
	* @param sAlias, - LayeredPane  -ContentPane
	* @return
	* @throws ExceptionZZZ
	* 
	* lindhaueradmin; 12.09.2008 13:28:10
	 */
	public abstract JComponent getPaneContent(String sAlias) throws ExceptionZZZ;
	
	
	
	/** Hier wird das Men� definiert. Es wird automatisch in .launch() herangezogen.
	* @return
	* 
	* lindhaueradmin; 15.03.2007 10:08:35
	 */
	public abstract JMenuBar getMenuContent()  throws ExceptionZZZ;
}
