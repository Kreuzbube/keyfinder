import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

public class Crawler {

	
	
	public void crawl(int id) throws IOException {
		
	int starterId=id;	
	// Durchläuft die ersten hundert Posts
	for(int b=0;b<100;b++){
    URL url;


			url = new URL("http://pr0gramm.com/api/items/info?itemId="+(id-b));
		 String bla;
		
   
	
		bla = getRequest(url);
	
// isoliert die Kommentare
JSONObject obj = new JSONObject(bla);
JSONArray arr = obj.getJSONArray("comments");
//Pattern für Steam und 
Pattern p= Pattern.compile("\\w{3,6}-\\w{3,6}-\\w{3,6}-\\w{3,6}-?\\w{0,6}s|\\w{3,6}-\\w{3,6}-\\w{3,6}");
int x=0;
int a= arr.length();
for(int i=0; i <arr.length();i++){
	String comment = arr.getJSONObject(i).getString("content");
	  Matcher m = p.matcher(comment);

	     while (m.find()) {
	       System.out.println(m.group() +" in post number: " +(id-b));
	       x++;
	     }
	
   

} 
if(x==0)
	System.out.println("No Key found in post: " +(id-b));
		
	}}
	
	int mainPage(URL url) throws IOException{
		String mainPage = getRequest(url);
		JSONObject obj = new JSONObject(mainPage);
		JSONArray array = obj.getJSONArray("items");
		int id = array.getJSONObject(0).getInt("id");
		System.out.println("Neuester post ist: "+id);
		return id;
	}
	
	String getRequest(URL url) throws IOException{
		StringBuilder result = new StringBuilder();
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setRequestMethod("GET");
	    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	    String line;
	    try {
			while ((line = rd.readLine()) != null) {
			   result.append(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    try {
			rd.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return result.toString();
	}
}
