package Google;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;

public class DriveInterface {
	private static final FileDataStoreFactory DATA_STORE_FACTORY; static {
		FileDataStoreFactory fileFactory = null;
		
		try { fileFactory = new FileDataStoreFactory(new java.io.File("C:/Users/Sam/Desktop")); }
		catch(IOException e) { e.printStackTrace(); }
		
		DATA_STORE_FACTORY = fileFactory;
	}
	
	private static final String CLIENT_ID = "978272378680-vo1a15hudah5cobjme83h2167fp783u4.apps.googleusercontent.com";
	private static final String CLIENT_SECRET = "3naUm55kGn5ui5e9gdkvoWIG";

	private static final List<String> SCOPES = Arrays.asList(DriveScopes.DRIVE_APPDATA);
	
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	
	private static DriveInterface instance;
	public  static DriveInterface getInstance() {
		if(instance == null) 
			instance = new DriveInterface();
		return instance;
	} 
	
//	---
	
	private Credential credential;
	private Drive drive;
	
	private DriveInterface() {
		try { 
			loadCredentails();
			drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).build();
//			findFile();
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	private void loadCredentails() throws IOException {
		AuthorizationCodeFlow authorization = new GoogleAuthorizationCodeFlow.Builder(
				HTTP_TRANSPORT, JSON_FACTORY, CLIENT_ID, CLIENT_SECRET, SCOPES)
			.setDataStoreFactory(DATA_STORE_FACTORY)
			.setAccessType("offline")
		.build();
		
		credential = new AuthorizationCodeInstalledApp(authorization, new LocalServerReceiver()).authorize(CLIENT_ID);
	}

	public List<String> query(String q) throws IOException {
		List<File> files = drive.files().list().setQ(q).setSpaces("appDataFolder").execute().getItems();
		return files.stream().map(file -> file.getTitle()).collect(Collectors.toList());
	}
	
	protected File updateMetaData(File file) throws IOException {
		if(file.getId() == null)
			return drive.files().insert(file).setFields("id").execute();
		List<File> files = drive.files().list().setQ("title='" + file.getTitle() + "'").setSpaces("appDataFolder").execute().getItems();
		
		if(files.isEmpty()) {
			file.setId(null);
			return updateMetaData(file);
		}
		
		return files.get(0);
	}
	
	protected File updateMetaDataByTitle(File file) throws IOException {
		List<File> files = drive.files().list().setQ("title='" + file.getTitle() + "'").setSpaces("appDataFolder").execute().getItems();
		
		if(files.isEmpty()) 
			return file;
		return files.get(0);
	}
	
	protected void download(GoogleFile file, java.io.File saveTo) throws IOException {
		file.updateMetaData();
		
		try(FileOutputStream out = new FileOutputStream(saveTo)) { 
			HttpResponse resp = drive.getRequestFactory().buildGetRequest(new GenericUrl(file.getFile().getDownloadUrl())).execute();
			out.getChannel().transferFrom(Channels.newChannel(resp.getContent()), 0, Long.MAX_VALUE);
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	protected boolean upload(GoogleFile file, java.io.File sourceFile) {
		try {
			String head = drive.files().list().setQ("title='" + file.getFile().getTitle() + "'").execute().getItems().get(0).getHeadRevisionId();
			
			if(!file.getRevisionID().equals(head))
				return false;
			
			FileContent mediaContent = new FileContent(file.getFile().getMimeType(), sourceFile);
			drive.files().update(file.getFile().getId(), file.getFile(), mediaContent).execute();
			file.updateMetaData();
			
			return true;
		} catch (IOException e) { e.printStackTrace(); }
		
		return false;
	}
	
	public static void main(String[] args) throws IOException {
		GoogleFile file = new GoogleFile("Test.tst") {};
		DriveInterface.getInstance().upload(file, new java.io.File("C:/Users/Sam/Desktop/lst.txt"));
		DriveInterface.getInstance().download(file, new java.io.File("C:/Users/Sam/Desktop/lst11.txt"));
	}
}
