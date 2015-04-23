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

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class get {
	private static final String URL_SECURED_BY_BASIC_AUTHENTICATION = "http://localhost/alphastreet/api/public/api/1.0/user/login";
	private static final String DEFAULT_USER = "kishorekalapala@digital-nirvana.com";
	private static final String DEFAULT_PASS = "123456";

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

	private final String authorizationHeader(final String username,
			final String password) {
		final String auth = username + ":" + password;
		final byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset
				.forName("US-ASCII")));
		final String authHeader = "Basic " + new String(encodedAuth);

		return authHeader;
	}

	@Test
	public final void userLogin() throws ClientProtocolException,
			IOException

	{
		client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(URL_SECURED_BY_BASIC_AUTHENTICATION);
		request.setHeader(HttpHeaders.AUTHORIZATION,
				authorizationHeader(DEFAULT_USER, DEFAULT_PASS));
		response = client.execute(request);
		int statuscode = response.getStatusLine().getStatusCode();
		System.out.println(statuscode);

	}
}
