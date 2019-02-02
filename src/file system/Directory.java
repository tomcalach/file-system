package spotinstEx;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import spotinstEx.File;

/**
 * The Directory class represents a directory,
 * an object that can contain other directories and files.
 * @author Tom Carmi
 *
 */
public class Directory{
	
	/**
	 * Initialize an instance of a Directory
	 * with the date of the initialize time, and 2 empty lists:
	 * 1. To contain all the file children of the directory.
	 * 2. To contain all the directory children of the directory.
	 * The name and parent directory of it are
	 * determined by the parameters which were supplied:
	 * @param dirName
	 * @param parentDir, which is another instance of the type Directory
	 */
	private String name;
	private Date date = new Date();
	private List<Directory> dirList = new ArrayList<>();
	private List<File> fileList = new ArrayList<>();
	private Directory parentDir = null;
	
	public Directory(String dirName, Directory parentDir){
		this.name = dirName;
		this.parentDir = parentDir;
	}
	
	public String getName() {return this.name;}
	public Date getDate() {return this.date;}
	public Directory getParentDir() {return this.parentDir;}
	public List<File> getFiles() {return this.fileList;}
	public List<Directory> getDirs() {return this.dirList;}
	
	public String toString() {
		/**
		 * it takes no params. 
		 * It goes up through the parent directories, 
		 * in order to find the full path\name of the directory.
		 * @return String, represents the full path to a directory in the form of:
		 * "root:\...\parentDirOfParentDirName\parentDirName\DirName"
		 */
		List<String> adressList = new ArrayList<>();
		String[] currentName = {this.name};
		adressList.add(currentName[0]);
		Directory[] currentDir = {this.parentDir};
		while(currentName[0] != "root:") {
			currentName[0] = currentDir[0].name;
			adressList.add(0, currentName[0]);
			currentDir[0] = currentDir[0].parentDir;
		}
		
		return String.join("\\", adressList);
	}

	
	public void addFile(File file) {
		/**
		 * Adds a new child file into the FileList
		 *@param File object
		 */
		this.fileList.add(file);
	}
	public void addDir(Directory dir) {
		/**
		 * Adds a new child directory into the dirList
		 *@param File object
		 */
		this.dirList.add(dir);
	}
	
	public void delete(Object fileOrDir) {
		/**
		 * Deletes a child File or Directory instance from the related list.
		 * @param File or Directory object, which exist in dirList or in fileList
		 */
		if(dirList.contains(fileOrDir)) {
			dirList.remove(fileOrDir);
		}else {
			fileList.remove(fileOrDir);
		}
	}
	
}
