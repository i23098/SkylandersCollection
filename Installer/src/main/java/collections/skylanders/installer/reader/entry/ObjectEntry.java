package collections.skylanders.installer.reader.entry;

public abstract class ObjectEntry {
	protected final String name;
	
	protected ObjectEntry(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
