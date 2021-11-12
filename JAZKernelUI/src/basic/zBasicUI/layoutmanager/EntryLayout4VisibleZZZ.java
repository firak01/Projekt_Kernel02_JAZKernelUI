// package com.darwinsys.entrylayout;
package basic.zBasicUI.layoutmanager;
import java.awt.*;
import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.math.MathZZZ;


/**Einfacher Layout-Manager. Es sollen nur sichtbare Komponenten Einfluss darauf haben.
 * Ziel: Wenn über eine "Gruppierungskomponente" mehrere Komponenten eingebunden sind, davon aber nur immer 1 sichtbar ist,
 *       dann darf es zu keiner Verschiebung kommen.
 * 
 *  Basiert auf EntryLayout:
 *  A simple layout manager, for Entry areas like:
 * @author Ian F. Darwin, ian@darwinsys.com
 * @version $Id: EntryLayout.java,v 1.1 2006/06/20 18:24:27 lindhauer Exp $
 */
public class EntryLayout4VisibleZZZ implements LayoutManager {
	/** The array of widths, as decimal fractions (0.4 == 40%, etc.). */
	protected final double[] widthPercentages;

	/** The number of columns. */
	protected final int COLUMNS;

	/** The default padding */
	protected final static int HPAD = 5, VPAD = 5;
	/** The actual padding */
	protected final int hpad, vpad;

	/** True if the list of widths was valid. */
	protected boolean validWidths = false;

	/** Construct an EntryLayout with widths and padding specified.
	 * @param widths	Array of doubles specifying column widths.
	 * @param h			Horizontal padding between items
	 * @param v			Vertical padding between items
	 */
	public EntryLayout4VisibleZZZ(double[] widths, int h, int v) {
		COLUMNS = widths.length;
		widthPercentages = new double[COLUMNS];
		for (int i=0; i<widths.length; i++) {
			if (widths[i] >= 1.0)
				throw new IllegalArgumentException(
					"EntryLayout: widths must be fractions < 1");
			widthPercentages[i] = widths[i];
		}
		validWidths = true;
		hpad = h;
		vpad = v;
	}
	/** Construct an EntryLayout with widths and with default padding amounts.
	 * @param widths	Array of doubles specifying column widths.
	 */
	public EntryLayout4VisibleZZZ(double[] widths) {
		this(widths, HPAD, VPAD);
	}

	/** Adds the specified component with the specified constraint 
	 * to the layout; required by LayoutManager but not used.
	 */
	public void addLayoutComponent(String name, Component comp) {
		// nothing to do
	}

	/** Removes the specified component from the layout;
	 * required by LayoutManager, but does nothing.
	 */
	public void removeLayoutComponent(Component comp)  {
		
		// nothing to do
	}

	/** Calculates the preferred size dimensions for the specified panel 
	 * given the components in the specified parent container. */
	public Dimension preferredLayoutSize(Container parent)  {
		// System.out.println("preferredLayoutSize");
		return computeLayoutSize(parent, hpad, vpad);
	}

	/** Find the minimum Dimension for the 
	 * specified container given the components therein.
	 */
	public Dimension minimumLayoutSize(Container parent)  {
		// System.out.println("minimumLayoutSize");
		return computeLayoutSize(parent, 0, 0);
	}

	/** The width of each column, as found by computLayoutSize(). */
	int[] widths;
	/** The height of each row, as found by computLayoutSize(). */
	int[] heights;

	/** Compute the size of the whole mess. Serves as the guts of 
	 * preferredLayoutSize() and minimumLayoutSize().
	 */
	protected Dimension computeLayoutSize(Container parent, int hpad, int vpad) {
		if (!validWidths)
			return null;
		Component[] components = parent.getComponents();
		int preferredWidth = 0, preferredHeight = 0;
		widths = new int[COLUMNS];
		
		//FGL: Arbeite nun nur noch mit den sichtbaren Components
		ArrayList<Component>listaComponent = new ArrayList<Component>();
		for(Component c : components) {
			if(c.isVisible()) {
				listaComponent.add(c);
			}
		}
		Component[] componentsVisible = ArrayListZZZ.toComponentArray(listaComponent);
		
		
		//FGL 20211104: Wenn aber eine ungerade Zahl der Componenten da ist - wie z.B. in der DebugUI-Zeile - gibt es einen Fehler: heights = new int[(components.length / COLUMNS];
		//ergo, aber Merke: https://stackoverflow.com/questions/43300892/dividing-numbers
		//                  man muss also in double casten, damit 5/2 nicht 2.0 ergibt !!!		
		double dtemp = MathZZZ.divide(componentsVisible.length, COLUMNS);		
		int itemp = MathZZZ.roundUp(dtemp);
		heights = new int[itemp];
		// System.out.println("Grid: " + widths.length + ", " + heights.length);

		int i;
		// Pass One: Compute largest widths and heights.
		for (i=0; i<componentsVisible.length; i++) {
			int row = i / widthPercentages.length;
			int col = i % widthPercentages.length;
			Component c = components[i];
			Dimension d = c.getPreferredSize();
			widths[col] = Math.max(widths[col], d.width);
			heights[row] = Math.max(heights[row], d.height);
			//System.out.println("FGLTEST A: row="+ row + " - col="+ col + "|" + c.getClass() + "--> h=" + heights[row] + " w=" + widths[col]);
		}

		// Pass two: agregate them.
		//System.out.println("FGLTEST B: Rechne jeweils die Zeilenhoehe und Breiten zusammen.");
		for (i=0; i<widths.length; i++)
			preferredWidth += widths[i];
		for (i=0; i<heights.length; i++)
			preferredHeight += heights[i];

		//nicht für jede Spalte das Padding, sondern nur für die "Zwischenräume"
		preferredWidth += preferredWidth + (hpad*widths.length-1);
		
		//dito nicht für jede Zeile, sondern nur für die "Zwischenräume"
		preferredHeight += preferredHeight + (vpad*heights.length-1);
		
		// Finally, pass the sums back as the actual size.
		return new Dimension(preferredWidth, preferredHeight);
	}

	//ORIGINAL, Variante beschränkt auf die nur sichtbaren:
//	/** Lays out the container in the specified panel. */
//	public void layoutContainer(Container parent) {
//		// System.out.println("layoutContainer:");
//		if (!validWidths)
//			return;
//		Component[] components = parent.getComponents();
//		
//		//FGL: Arbeite nun nur noch mit den sichtbaren Components
//		ArrayList<Component>listaComponent = new ArrayList<Component>();
//		for(Component c : components) {
//			if(c.isVisible()) {
//				listaComponent.add(c);
//			}
//		}
//		Component[] componentsVisible = ArrayListZZZ.toComponentArray(listaComponent);
//		
//		
//		Dimension contSize = parent.getSize();
//		for (int i=0; i<componentsVisible.length; i++) {
//			int row = i / COLUMNS; //Merke: Hier ist explizit abrunden gewuenscht.
//			int col = i % COLUMNS;
//			Component c = componentsVisible[i];
//			Dimension d = c.getPreferredSize();
//			int colWidth = (int)(contSize.width * widthPercentages[col]);
//			Rectangle r = new Rectangle(
//				col == 0 ? 0 :
//				hpad * (col-1) + (int)(contSize.width * widthPercentages[col-1]),
//				vpad * (row) + (row * heights[row]) + (heights[row]-d.height),
//				colWidth, d.height);
//			System.out.println(c.getClass() + "-->" + r);
//			c.setBounds(r);
//		}
//	}
	
	/** FGL 20211109
	 * Lays out the container in the specified panel. 
	 * - Restricted to visible Components
	 * - Restricted to 2 Columns
	 * - Alows different row heights*/
	public void layoutContainer(Container parent) {
		// System.out.println("layoutContainer:");
		if (!validWidths) return;
		Component[] components = parent.getComponents();
		
		//FGL: Arbeite nun nur noch mit den sichtbaren Components
		ArrayList<Component>listaComponent = new ArrayList<Component>();
		for(Component c : components) {
			if(c.isVisible()) {
				listaComponent.add(c);
			}
		}
		
		//WICHTIG: DIE ANZAHL DER KOMPONENTEN MUSS IMMER DURCH DIE ANZAHL DER SPALTEN OHNE REST TEILBAR SEIN
		if(!MathZZZ.isDivisibleWithoutRemainder(listaComponent.size(),COLUMNS)){
			String sLog = "Anzahl der Components '" + listaComponent.size() + "' passt nicht zur Anzahl der Spalten '"+ COLUMNS + "'. Es muss eine voll durch die Spalten teilbare Anzahl der Komponenten sein.";
		
//		if(!MathZZZ.isEven(listaComponent.size())) {
//			String sLog = "Ungerade Anzahl der Components. Bei 2 Spalten nicht vorgesehen.";
			try {
				System.out.println(ReflectCodeZZZ.getPositionCurrent()+": " + sLog);
			} catch (ExceptionZZZ e) {				
				e.printStackTrace();
			}
			return;
		}
		
		Component[] componentsVisible = ArrayListZZZ.toComponentArray(listaComponent);
		
		//####### HOEHE ##########################################
		//FGL: Arbeite mit Arrays, die dann feste Werte haben und sich auf die "Row" beziehen.
		//     Also ein fest Zeilenhöhe ermitteln für die preferedSize der Komponenten einer Zeile.		
		double dtemp = MathZZZ.divide(componentsVisible.length, COLUMNS);		
		int rowTotal = MathZZZ.roundUp(dtemp);
		int[]dimensionHeightsUsed = new int[rowTotal+1];
		int[]vtempused = new int[rowTotal+1];
		
		//1. Schritt: Höhe der Komponenten
		int heightsum=0;
		for (int i=0; i<componentsVisible.length; i++) {
			Component c = componentsVisible[i];
			Dimension d = c.getPreferredSize();
			
			int row = i / COLUMNS; //Merke: Hier ist explizit Abrunden gewuenscht um die aktuelle Reihe zu bekommen.		
			dimensionHeightsUsed[row] = Math.max(d.height, dimensionHeightsUsed[row]); //Wähle die groesste Hoehe.
		}//end for
		
		//2. Schritt: Die so ausgewählte Höhe der jeweiligen Zeile zuweisen.
		for(int row=0;row<rowTotal;row++) {
			
			//FGL: In der Ausgangsversion wird einfach die Höhe mit der Anzahl der Zeilen multipliziert.
			//int vtempused = vpad * (row) + (row * heights[row]) + (heights[row]-d.height); 	
			//Wenn man einfach die Höhe mit der Anzahl der Zeilen multipliziert, geht das nur, wenn die Zeilen gleich hoch sind.
			//Das ist nicht gegeben, wenn z.B. die Komponenten des DebugUI zusätzlich angezeigt werden sollen.
			//Also hier nur für die Zwischenräume aufsummieren
			if(row>=1) {	
				//bisherige Spaltenbreiten Zusammenrechnen PLUS Padding
				for(int rowtemp=0;rowtemp<=row-1;rowtemp++) {
					vtempused[row]= vtempused[row] + dimensionHeightsUsed[rowtemp] + vpad;					
				}				
			}else {
				vtempused[row]= 0; //dimensionHeightsUsed[row];
			}
		}
		
		
		//++++++++++++++++++
		
		//###### BREITE ############
		//FGL: In der Ausgangsversion wird einfach die mögliche Gesamtgröße auf die Proportionen aufgeteilt.
		//     Bei mehr als 2 Spalten kommt es bei der Strategie zu "Überlappungen".
		//     Daher das Spaltenmaximum der Größe ermitteln	
		//1. Schritt: Breite der Componenten
		int[]widthOfComponentUsed = new int[COLUMNS];		
		Dimension contSize = parent.getSize();
		for (int i=0; i<componentsVisible.length; i++) {
			int row = i / COLUMNS; //Merke: Hier ist explizit abrunden gewuenscht.
			int col = i % COLUMNS;
			Component c = componentsVisible[i];
			int widthtemp = c.getWidth();
			widthOfComponentUsed[col] = Math.max(widthOfComponentUsed[col], widthtemp);
		}
			
		//2. Schritt: Breite nach Bildschirmaufteilung oder Breite der vorherigen Komponente (plus hpadding)
		int[]htempused = new int[COLUMNS];
		int[]colwidthused = new int[COLUMNS];
		for (int col=0; col < COLUMNS; col++) {
			int htemp = (int)(contSize.width * widthPercentages[col]);			
			if(col>=1) {		
				//bisherige Spaltenbreiten Zusammenrechnen PLUS Padding
				for(int coltemp=0;coltemp<=col-1;coltemp++) {
					htempused[col] = htempused[col] + colwidthused[coltemp] + hpad;
				}
			}else {
				//bisherige Spaltenbreite ist 0
				htempused[col] = 0;
			}						
			
			//Die nächste Spaltebreite vermerken
			if(widthOfComponentUsed[col]>htemp) {
				colwidthused[col]=widthOfComponentUsed[col];
			}else {
				colwidthused[col]=htemp;
			}
		}
			
		//+++ Nun die Positionierungs-Werte den Komponenten zuweisen
		for (int i=0; i<componentsVisible.length; i++) {
			int row = i / COLUMNS; //Merke: Hier ist explizit abrunden gewuenscht.
			int col = i % COLUMNS;
			
			Component c = componentsVisible[i];
			
			//Merke: Rectangle wird hier nicht zum Zeichnen verwendet, sondern hier nur zur Positionierung.
			//       Rectangle hat folgenden Konstruktor:
			//       Rectangle(int x, int y, int width, int height)
			//       Constructs a new Rectangle whose upper-left corner is specified as (x,y) and whose width and height are specified by the arguments of the same name.
			
			//Original
//			Rectangle r = new Rectangle(
//				col == 0 ? 0 :
//				hpad * (col-1) + (int)(contSize.width * widthPercentages[col-1]),
//				vpad * (row) + (row * heights[row]) + (heights[row]-d.height),
//				colWidth, d.height);
//			System.out.println(c.getClass() + "-->" + r);
			Rectangle r = new Rectangle(col == 0 ? 0 : htempused[col], vtempused[row], colwidthused[col],	dimensionHeightsUsed[row]);
			//System.out.println("FGLTEST C2: row="+ row + "- col="+ col + "|" + c.getClass() + "-->" + r);
			
			//Merke: 
			c.setBounds(r);
			
		}
	}
}
