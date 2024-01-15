package personnel;

public class DateInvalide extends Exception 
{
	
	Exception exception;
	
	public DateInvalide(Exception exception)
	{
		this.exception = exception;
	}
	
	@Override
	public void printStackTrace() 
	{
			super.printStackTrace();
			System.err.println("Causé par : ");
			exception.printStackTrace();			
	}

}
