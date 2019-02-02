package spotinstEx;
import java.util.Date;
import spotinstEx.Directory;

/**
 * The class File represents a file.
 * 
 * @author Tom Carmi
 * @since 2018-01-09
 */

public class File {
	/**
	 * Initialize an instance of a File
	 * with the date of the initialize time.
	 * The name, size and parent directory of it are
	 * determined by the parameters which were supplied:
	 * @param fileName
	 * @param fileSize
	 * @param parentDir, which is an instance of the type Directory
	 */
	private String name;
	private long size;
	private Date date = new Date();
	private Directory parentDir = null;

	public File(String fileName, int fileSize, Directory parentDir) {
		this.name = fileName;
		this.size =  fileSize;
		this.parentDir = parentDir;
	}
	public String getName() {return this.name;}
	public long getSize() {return this.size;}
	public Date getDate() {return this.date;}
	public Directory getParentDir() {return this.parentDir;}
	
}
