// package com.darwinsys.entrylayout;
package basic.zBasicUI.layoutmanager;
import java.awt.*;
import java.util.ArrayList;

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
			System.out.println("FGLTEST A: row="+ row + " - col="+ col + "|" + c.getClass() + "--> h=" + heights[row] + " w=" + widths[col]);
		}

		// Pass two: agregate them.
		System.out.println("FGLTEST B: Rechne jeweils die Zeilenhoehe und Breiten zusammen.");
		for (i=0; i<widths.length; i++)
			preferredWidth += widths[i] + hpad;
		for (i=0; i<heights.length; i++)
			preferredHeight += heights[i] + vpad;

		// Finally, pass the sums back as the actual size.
		return new Dimension(preferredWidth, preferredHeight);
	}

	//ORIGINAL:
	/** Lays out the container in the specified panel. */
	public void layoutContainer(Container parent) {
		// System.out.println("layoutContainer:");
		if (!validWidths)
			return;
		Component[] components = parent.getComponents();
		
		//FGL: Arbeite nun nur noch mit den sichtbaren Components
		ArrayList<Component>listaComponent = new ArrayList<Component>();
		for(Component c : components) {
			if(c.isVisible()) {
				listaComponent.add(c);
			}
		}
		Component[] componentsVisible = ArrayListZZZ.toComponentArray(listaComponent);
		
		
		Dimension contSize = parent.getSize();
		for (int i=0; i<componentsVisible.length; i++) {
			int row = i / COLUMNS; //Merke: Hier ist explizit abrunden gewuenscht.
			
			//FGL 20211104: Wenn aber eine ungerade Zahl der Componenten da ist - wie z.B. in der DebugUI-Zeile - gibt es einen Fehler: heights = new int[(components.length / COLUMNS];
			//ergo, aber Merke: https://stackoverflow.com/questions/43300892/dividing-numbers
			//                  man muss also in double casten, damit 5/2 nicht 2.0 ergibt !!!		
//			double dtemp = MathZZZ.divide(i, COLUMNS);		
//			int itemp = MathZZZ.roundUp(dtemp);
//			int row = itemp;
			int col = i % COLUMNS;
			Component c = componentsVisible[i];
			Dimension d = c.getPreferredSize();
			int colWidth = (int)(contSize.width * widthPercentages[col]);
			Rectangle r = new Rectangle(
				col == 0 ? 0 :
				hpad * (col-1) + (int)(contSize.width * widthPercentages[col-1]),
				vpad * (row) + (row * heights[row]) + (heights[row]-d.height),
				colWidth, d.height);
			System.out.println(c.getClass() + "-->" + r);
			c.setBounds(r);
		}
	}
	
	/** Lays out the container in the specified panel. */
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
//		//FGL: Arbeite mit Arrays, die dann feste Werte haben und sich auf die "Row" beziehen.
//		//     Also ein fest Zeilenhöhe ermitteln für die preferedSize der Komponenten einer Zeile.		
//		double dtemp = MathZZZ.divide(componentsVisible.length, COLUMNS);		
//		int itemp = MathZZZ.roundUp(dtemp);
//		int[]dimensionHeightsUsed = new int[itemp+1];
//		
//		int rowPrevious=0;
//		for (int i=0; i<componentsVisible.length; i++) {
//			Component c = componentsVisible[i];
//			Dimension d = c.getPreferredSize();
//			
//			int row = i / COLUMNS; //Merke: Hier ist explizit Abrunden gewuenscht um die aktuelle Reihe zu bekommen.		
//			dimensionHeightsUsed[row] = 20;//Math.max(d.height, dimensionHeightsUsed[row]);						
//		}//end for
//		
//		
//		
//		int vtempprevious = 0; int vtempused = 0;int irowPrevious = 0;
//		Dimension contSize = parent.getSize();
//		for (int i=0; i<componentsVisible.length; i++) {
//			int row = i / COLUMNS; //Merke: Hier ist explizit Abrunden gewuenscht um die aktuelle Reihe zu bekommen.
//			if(irowPrevious!=row) {
//				//ZeilenweiseWerte wieder zurücksetzen
//				//vtempprevious = 0;
//			}
//			irowPrevious = row;
//			if(row==3) {
//				System.out.println("BREAK Zeile 4");
//			}
//			//FGL 20211104: Wenn aber eine ungerade Zahl der Componenten da ist - wie z.B. in der DebugUI-Zeile - gibt es einen Fehler: heights = new int[(components.length / COLUMNS];
//			//ergo, aber Merke: https://stackoverflow.com/questions/43300892/dividing-numbers
//			//                  man muss also in double casten, damit 5/2 nicht 2.0 ergibt !!!		
////			double dtemp = MathZZZ.divide(i, COLUMNS);		
////			int itemp = MathZZZ.roundUp(dtemp);
////			int row = itemp;
//			int col = i % COLUMNS;
//			Component c = componentsVisible[i];
//			//Dimension d = c.getPreferredSize();
//			
//			
//			int colWidth = (int)(contSize.width * widthPercentages[col]);
//			int htemp = 0;
//			if(col>=1) {
//				htemp = hpad * (col-1) + (int)(contSize.width * widthPercentages[col-1]);
//			}
//			//!!! DAS KANN DOCH NUR FUNKTIONIEREN, WENN ALLE ZEILEN GLEICH HOCH SIND ... int vtemp = vpad * (row) + (row * heights[row]) + (heights[row]-d.height);
//			//20210909: Man muss die Höhe aufsummieren
//			int vtemp = 0;
//			for(int iIndex=0;iIndex<=row;iIndex++) {
//				vtemp = vtemp + heights[iIndex];
//			}
//			//int vtemp = vpad * (row) + (row * heights[row]) + (heights[row]-d.height);
//			//vtempused = vpad * (row) + vtemp + (heights[row]-d.height);
//			//Variante einfach nur erhöhen, ohne "Zusammenrechnen"
//			vtempused = vpad + vtempprevious + heights[row] - dimensionHeightsUsed[row];
//			//vtempused = Math.max(vtempused,vtempprevious);
//			vtempprevious = vtempused;
//			System.out.println("FGLTEST C1: row="+ row + "- col="+ col + "| htemp="+htemp + " vtempused="+vtempused);
//			Rectangle r = new Rectangle(
//				col == 0 ? 0 :
//				htemp, vtempused, colWidth, dimensionHeightsUsed[row]);
//			System.out.println("FGLTEST C2: row="+ row + "- col="+ col + "|" + c.getClass() + "-->" + r);
//			c.setBounds(r);
//		}
//	}
	
	//############################
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
//		int vtempprevious = 0; int vtempused = 0;
//		Dimension contSize = parent.getSize();
//		for (int i=0; i<componentsVisible.length; i++) {
//			int row = i / COLUMNS; //Merke: Hier ist explizit abrunden gewuenscht.
//			if(row==3) {
//				System.out.println("BREAK Zeile 4");
//			}
//			//FGL 20211104: Wenn aber eine ungerade Zahl der Componenten da ist - wie z.B. in der DebugUI-Zeile - gibt es einen Fehler: heights = new int[(components.length / COLUMNS];
//			//ergo, aber Merke: https://stackoverflow.com/questions/43300892/dividing-numbers
//			//                  man muss also in double casten, damit 5/2 nicht 2.0 ergibt !!!		
////			double dtemp = MathZZZ.divide(i, COLUMNS);		
////			int itemp = MathZZZ.roundUp(dtemp);
////			int row = itemp;
//			int col = i % COLUMNS;
//			Component c = componentsVisible[i];
//			Dimension d = c.getPreferredSize();
//			int colWidth = (int)(contSize.width * widthPercentages[col]);
//			int htemp = 0;
//			if(col>=1) {
//				htemp = hpad * (col-1) + (int)(contSize.width * widthPercentages[col-1]);
//			}
//			//!!! DAS KANN DOCH NUR FUNKTIONIEREN, WENN ALLE ZEILEN GLEICH HOCH SIND ... int vtemp = vpad * (row) + (row * heights[row]) + (heights[row]-d.height);
//			//20210909: Man muss die Höhe aufsummieren
//			int vtemp = 0;
//			for(int iIndex=0;iIndex<=row;iIndex++) {
//				vtemp = vtemp + heights[iIndex];
//			}
//			//int vtemp = vpad * (row) + (row * heights[row]) + (heights[row]-d.height);
//			//vtempused = vpad * (row) + vtemp + (heights[row]-d.height);
//			//Variante einfach nur erhöhen, ohne "Zusammenrechnen"
//			vtempused = vpad + vtempprevious + heights[row] -d.height;
//			vtempprevious = vtempused;
//			System.out.println("FGLTEST C1: row="+ row + "- col="+ col + "| htemp="+htemp + " vtempused="+vtempused);
//			Rectangle r = new Rectangle(
//				col == 0 ? 0 :
//				htemp, vtempused, colWidth, d.height);
//			System.out.println("FGLTEST C2: row="+ row + "- col="+ col + "|" + c.getClass() + "-->" + r);
//			c.setBounds(r);
//		}
//	}

}
