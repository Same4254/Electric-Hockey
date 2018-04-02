package Google;

import java.io.IOException;
import java.util.Collections;

import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.ParentReference;

public abstract class GoogleFile {
	private File file;
	
	public GoogleFile(String name) throws IOException {
		file = new File();
		file.setMimeType("text/plain");
		file.setParents(Collections.singletonList(new ParentReference().setId("appDataFolder")));
		
		file.setTitle(name);
		
		file = DriveInterface.getInstance().updateMetaDataByTitle(file);
		updateMetaData();
	}

	protected String getRevisionID() { return file.getHeadRevisionId(); }
	protected File getFile() { return file; }
	
	public String getName() { return file.getTitle(); }
	public void updateMetaData() throws IOException { file = DriveInterface.getInstance().updateMetaData(file); }
}
