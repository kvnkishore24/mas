package mas.testcases;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import mas.utils.BaseApi;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class add {

	CloseableHttpClient client;
	CloseableHttpResponse response;

	BaseApi baseapi = new BaseApi();

	@Test(priority=1)
	public void login() throws IOException {
		
		String src = baseapi.API_URL + "/user/login";
		client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(src);
		request.setHeader(HttpHeaders.AUTHORIZATION, baseapi.authorizationHeader(baseapi.USER_NAME, baseapi.PASS_WORD));

		response = client.execute(request);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

	}
	
	@Test(priority=2)
	public void nullTestXauthToken(){
		System.out.println("priority 2");
		
		
	}
	@Test(priority=3)
	public final void userLogin() throws ClientProtocolException, IOException {

		client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(baseapi.API_URL + "/user/login");
		request.setHeader(HttpHeaders.AUTHORIZATION, baseapi.authorizationHeader(baseapi.USER_NAME, baseapi.PASS_WORD));

		response = client.execute(request);
		int statuscode = response.getStatusLine().getStatusCode();
		System.out.println(statuscode);

		HttpEntity entity = response.getEntity();
		String responseString = EntityUtils.toString(entity);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jnode = mapper.readTree(responseString);

		Iterator<String> fieldnames = jnode.fieldNames();
		while (fieldnames.hasNext()) {
			ArrayList<String> fieldNameslist = new ArrayList<String>();
			fieldNameslist.add(fieldnames.next());
			System.out.print(fieldNameslist);
			for (int i = 0; i < fieldNameslist.size(); i++) {

				JsonNode nodevalues = jnode.get(fieldNameslist.get(i));
				System.out.println(nodevalues);
			}

		}
	}
}
