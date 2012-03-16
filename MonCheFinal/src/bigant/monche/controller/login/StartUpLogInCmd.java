package bigant.monche.controller.login;

import org.puremvc.java.patterns.command.MacroCommand;

public class StartUpLogInCmd 

	extends MacroCommand
	{
		protected void initializeMacroCommand()
		{
			addSubCommand( new PreModelLoginCmd() );
			addSubCommand( new PreViewLoginCmd() );
			
			/*
			 * Initiate loading currencies data from the webservice or reading
			 * from the local database. 
			 */
			//addSubCommand( new RefreshCurrenciesCmd() );
		    
		    System.out.println("Start Up Login Command");
		}
	}