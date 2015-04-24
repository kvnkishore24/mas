/**
 * 
 */
package mas.testcases;

/**
 * @author kishorekalapala
 * @project mas 
 * @class post.java
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;

public class post {
	
	ITestResult result;
	final String value = "OBJECT";
	@Test
	public void signUpPost() throws ClientProtocolException, IOException {
		String baseurl = "http://localhost/alphastreet/api/public/api/1.0/user/create";

		HttpClient hclient = new DefaultHttpClient();
		ArrayList<String> fieldarraylist = new ArrayList<String>();
		try {
			// Request
			HttpPost PostReq = new HttpPost(baseurl);

			// creating headers by adding NAME-VALUE-PAIR to list
			List<NameValuePair> namevaluepairs = new ArrayList<NameValuePair>(2);
			namevaluepairs.add(new BasicNameValuePair("first_name", "kishore"));
			namevaluepairs.add(new BasicNameValuePair("last_name", "kalapala"));
			namevaluepairs.add(new BasicNameValuePair("email", "kishorekalapala@digital-nirvana.com"));
			namevaluepairs.add(new BasicNameValuePair("password", "123456"));
			// adding parameters to request
			PostReq.setEntity(new UrlEncodedFormEntity(namevaluepairs));
			System.out.println("executing request " + PostReq.getRequestLine());

			// executing Request
			HttpResponse response = hclient.execute(PostReq);
			// Entity
			HttpEntity resEntity = response.getEntity();

			if (resEntity != null) {
				// converting entity to string
				String responsebody = EntityUtils.toString(resEntity);
				System.out.println("HTTP RESPOSE:"+response.getStatusLine());
				System.out.println("RESPONSE LENGTH: " + resEntity.getContentLength());
				System.out.println("Chunked? " + resEntity.isChunked());
				System.out.println("JSON DATA:" + responsebody);
				
				//
				
				ObjectMapper mapper = new ObjectMapper();
				JsonNode actualjson = mapper.readTree(responsebody);
				
				System.out.println("JSON PAGE TYPE: " + actualjson.getNodeType()); // object
				System.out.println("---------------------------------------------------------");
				Assert.assertNotNull(actualjson);

				Iterator<String> fieldnames = actualjson.fieldNames();
				while (fieldnames.hasNext()) {
					fieldarraylist.add(fieldnames.next());
				}
				for (int j = 0; j < fieldarraylist.size(); j++) {
					String x = fieldarraylist.get(j);

					JsonNode allNodes = actualjson.get(fieldarraylist.get(j));
				
//					if(allNodes.getNodeType() == Value.)
//					{
//						System.out.println("123498712471098274uej98123u42jdoiijalisdjadjdlajzlj");;
//					}
				
						
					
					System.out.println(x + "-" + allNodes.getNodeType()+ "-"+allNodes);
					System.out.println("---------------------------------------------------------");
					
				}
				

				JsonNode apiversionNode = actualjson.get("apiVersion");
				System.out.println("apiversionNode TYPE: " + apiversionNode.getNodeType());// string
				JsonNode resposeidNode = actualjson.get("responseId");
				System.out.println("resposeidNode TYPE: " + resposeidNode.getNodeType());// string
				JsonNode dataNode = actualjson.get("data");
				System.out.println("dataNode TYPE: " + dataNode.getNodeType());// string
				
				/*
				 * String versionNode = apiversionNode.toString();
				 * Assert.assertEquals( versionNode.substring(1,
				 * versionNode.length()-1), "1.0");
				 */
				Assert.assertEquals(apiversionNode.textValue(), "1.0");
				Assert.assertNotNull(resposeidNode);
				System.out.println(result.getStatus()+result.SUCCESS+""+resposeidNode);
				
				if (result.getStatus()==ITestResult.SUCCESS){
					System.out.println("User succesfully Added");
									}
					}

			} finally {

		}

	}

}
