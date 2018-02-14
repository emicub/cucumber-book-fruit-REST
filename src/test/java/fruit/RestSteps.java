package fruit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.UniformInterface;
import com.sun.jersey.api.client.WebResource;
import cucumber.api.java.en.*;
import hooks.ServerHooks;
import cucumber.api.PendingException;
import java.net.HttpURLConnection;
import java.util.List;

import org.junit.*; 

public class RestSteps {
    private ClientResponse response;
    List<Fruit> fruitListResponse;
    
    @When("^the client requests GET /fruits$")
    public void theClientRequestsGETFruits() throws Throwable 
    {
        try {

            Client client = Client.create();

            WebResource webResource = client.resource("http://localhost:" + ServerHooks.PORT + "/fruits");
   
            response = webResource.type("application/json").get(ClientResponse.class);
                        
            fruitListResponse = response.getEntity(new GenericType<List<Fruit>>(){});
            
            String s = "dummy";
        } 
        catch (RuntimeException r) 
        {
            throw r;
        } 
        catch (Exception e) 
        {
            System.out.println("Exception caught");
            e.printStackTrace();
        }

        Assert.assertEquals("Did not receive OK response: ", HttpURLConnection.HTTP_OK, response.getStatus());
    }

    @Then("^the response should be JSON:$")
    public void theResponseShouldBeJSON(String jsonExpected) throws Throwable 
    {
        JsonParser parser = new JsonParser();
        
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx jsonExpected: " + jsonExpected);
        
        //response.bufferEntity(); // hack que he encontrado en stack oveflow 
        
        Gson objGson = new GsonBuilder().setPrettyPrinting().create();
        String json = objGson.toJson(fruitListResponse);
        
        
        
    	System.out.println(json);
        
        System.out.println(" *********************************************** response:     " + json);


        
        Assert.assertEquals("Unexpected JSON.", parser.parse(jsonExpected), parser.parse(objGson.toJson(fruitListResponse)));

    }    
}