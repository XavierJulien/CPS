package loderunner.errors;



public class PreconditionError extends Error {

	private static final long serialVersionUID = 1884957290885506753L;

	public PreconditionError(String message) {
		super("Precondition failed: "+message);
	}
}
