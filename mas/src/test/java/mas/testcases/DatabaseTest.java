/**
 * 
 */
package mas.testcases;

import mas.utils.Database;

import org.omg.CosNaming.NamingContextPackage.NotEmpty;
import org.testng.annotations.Test;

/**
 * @author kishorekalapala
 * @project mas
 * @class Jget.java
 */
public class DatabaseTest {
	Database database = new Database();

	@Test
	public final void jsonpayloadtesting() throws Exception {

		String output = database.run("jdbc:mysql://localhost:3306/", "root", "123456", "as", "users", "id", 13);
		if (output != null) {
			System.out.println(output);
		} else {
			System.out.println("User not avaiable Users-Table");
		}

	}

}
