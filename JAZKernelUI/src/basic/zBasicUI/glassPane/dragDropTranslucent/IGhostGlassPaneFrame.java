package basic.zBasicUI.glassPane.dragDropTranslucent;

public interface IGhostGlassPaneFrame {
	public abstract GhostGlassPane getGhostGlassPane(); //etwas, das per Drag/Drop bewegt wird, wird dorthin als Bild kopiert.
	public abstract void setGhostGlassPane(GhostGlassPane glassPane);
}
