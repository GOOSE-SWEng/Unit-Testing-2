package media;

@SuppressWarnings("serial")
public class UnitTestException extends Exception {

	public UnitTestException(String info) {
		UnitTest2D.reportError(info);
	}
	
	public UnitTestException(String info,String data) {
		UnitTest2D.reportError(info,data);
	}
}