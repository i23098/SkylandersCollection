package collections.skylanders.installer.reader.entry;

import collections.skylanders.Figure;
import collections.skylanders.Variant;

public class FigureEntry extends ObjectEntry {
	protected final Figure figure;
	protected final Variant variant;
	protected final int series;
	
//	public FigureEntry(Figure figure, String name) {
//		this(figure, name, null, 1);
//	}
	
	public FigureEntry(Figure figure, String name, Variant variant, int series) {
		super(name);
		
		this.figure = figure;
		this.variant = variant;
		this.series = series;
	}
	
	public Figure getFigure() {
		return figure;
	}
	
	public Variant getVariant() {
		return variant;
	}
	
	public int getSeries() {
		return series;
	}
}
