package bigant.monche.app;

import org.puremvc.java.patterns.facade.Facade;



public class MonCheAppFacade extends Facade
{

    /**
	 * Construct the <code>ApplicationFacade</code> singleton needed by the
	 * application.
	 * 
	 * @param multitonKey
	 * 		Multiton key for this facade instance.
	 */
	public MonCheAppFacade(  )
	{
		super();
	}

	/**
	 * <code>ApplicationFacade</code> singleton fabrication method
	 * implementation.
	 * 
	 * @param multitonKey
	 * 		Multiton key of the facade instance to get.
	 */
	public static MonCheAppFacade getInstance( )
	{
		if( instance == null )
			instance = new MonCheAppFacade();

		return (MonCheAppFacade) instance;
	}

	protected void initializeController()
	{
		super.initializeController();
	}

	public void startup( MonCheApp application )
	{
	}

}
