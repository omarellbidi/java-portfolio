package observer;

public class NewsSpreaderException extends Exception {
	public NewsSpreaderException(String source) {
		super("News spreader: " + source);
	}
}