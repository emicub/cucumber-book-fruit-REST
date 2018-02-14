package hooks;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import fruit.FruitServer;

public class ServerHooks 
{
	public static final int PORT = 9988;
	private FruitServer server;
    
    @Before
    public void startServer() throws Exception {
    	System.out.println("This will run before the Scenario");
    	
        server = new FruitServer(PORT);
        server.start();
    }

    @After
    public void stopServer() throws Exception {
    	System.out.println("This will run after the Scenario");
        server.stop();
    }
    
}