/**
 * 
 */
package mas.testcases;

/**
 * @author kishorekalapala
 * @project mas 
 * @class get.java
 */

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;

import mas.utils.BaseApi;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class get {

	BaseApi baseapi = new BaseApi();
	private CloseableHttpClient client;
	private CloseableHttpResponse response;

	@BeforeClass
	public final void before() {
		client = HttpClientBuilder.create().build();
		System.out.println("before class");
	}

	@AfterClass
	public final void after() throws IllegalStateException, IOException {
		if (response == null) {
			return;
		}
		try {
			final HttpEntity entity = response.getEntity();
			if (entity != null) {
				final InputStream instream = entity.getContent();
				instream.close();
			}
		} finally {
			response.close();
		}
	}

	private final String authorizationHeader(final String username, final String password) {
		final String auth = username + ":" + password;
		final byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
		final String authHeader = "Basic " + new String(encodedAuth);

		return authHeader;
	}

	@Test
	public final void validation() {

		String jsonmimetype = "application/json";
		String mimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
		System.out.println(mimeType);
		Assert.assertEquals(mimeType, jsonmimetype);

	}

	@Test
	public final void userLogin() throws ClientProtocolException, IOException {

		client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(baseapi.API_URL + "/user/login");
		request.setHeader(HttpHeaders.AUTHORIZATION, authorizationHeader(baseapi.USER_NAME, baseapi.PAS_WORD));

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
