package spotinstEx;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;
import spotinstEx.Directory;
import spotinstEx.File;

/**
 * The class FileSystem represents an ordered branched record of a filling system.
 * The class is using the File and Directory classes and methods in order to
 * allow the client manage the system, they should be used only through the 
 * FileSystem methods and not directly.
 * Every system is initialized with a root directory.
 * 
 * @author Tom Carmi
 *
 */


public class FileSystem {
	/**
	 * Initialize an instance of a SystemFile with 2 hash tables:
	 * 1. allFileNames- an empty table to keep all the File objects, in the form (key:fileName, value:File).
	 * 2. allFileNames- a table to keep all the Directory objects, in the form (key:DirName, value:Directory).
	 * 	  It is initialized with the "root:" Directory.
	 */
	private Hashtable<String, File> allFileNames = new Hashtable<>();
	private Hashtable<String, Directory> allDirNames = new Hashtable<>();
	
	public FileSystem() {
		allDirNames.put("root:", new Directory("root:", null));
	}
	public Directory getDir(String DirName) {return this.allDirNames.get(DirName);}
	public File getFile(String FileName) {return this.allFileNames.get(FileName);}
	public Hashtable<String, Directory> getAllDirs() {return this.allDirNames;}
	public Hashtable<String, File> getAllFiles() {return this.allFileNames;}
	
	public String checkName(String name) {
		/**
		 * Checks if one of the system nested files or directories possess the inserted name,
		 * if not it is going to keep asking the client to insert new string, until it will be a valid one.
		 * @param name
		 * @return String, unique name which isn't in one of the 2 hash tables.
		 */
		String[] nameArr = {name};
		while(allFileNames.containsKey(nameArr[0]) | allDirNames.containsKey(nameArr[0])) {
			Scanner in = new Scanner(System.in);
			System.out.print("The name is already exist in the system." +
					" Please enter a different name\n");
			nameArr[0] = in.next();
			in.close();
		}
		return nameArr[0];
	}
	
	public void addFile (String parentDirName, String fileName, int fileSize) {
		/**
		 * Creates a new File instance and adds it to the system.
		 * @param parentDirName, the instance directory that carry this name will add the file as a child.
		 * @param fileName
		 * @param fileSize
		 */
		String uniqueFileName = checkName(fileName);
		File file = new File(uniqueFileName, fileSize, allDirNames.get(parentDirName));
		allFileNames.put(uniqueFileName, file);
		allDirNames.get(parentDirName).addFile(file);
		}

	public void addDir (String parentDirName, String dirName) {
		/**
		 * Creates a new Directory instance and adds it to the system.
		 * @param parentDirName, the instance directory that carry this name will add the directory as a child.
		 * @param dirName
		 */
		String uniqueDirName = checkName(dirName);
		Directory dir = new Directory(uniqueDirName, allDirNames.get(parentDirName));
		allDirNames.put(uniqueDirName, dir);
		allDirNames.get(parentDirName).addDir(dir);	
	}
	
	public void delete (String name) {
		/**
		 * Deletes a file/directory out of the system.
		 * In case it is a directory it is also deletes all the nested directories and files.
		 * it is also going to get the object out of the list of the parent directory.
		 * @param name, the name of the object to be deleted.
		 */
		// the user can't delete the root directory.
		if(name == "root:") {
			System.out.println("You have tried to delete the root directory of the system, this is an eligal command");
		
		// if the directory is empty it is possible to just delete the directory
		}else if(allFileNames.containsKey(name)) {
			File file = allFileNames.get(name);
			file.getParentDir().getParentDir().getFiles().remove(file);
			allFileNames.remove(name);
		
		// if not the method will go down to all the nested directories and delete all of their files on the way;
		// when it is going to get to a 'leaf' of the directory tree it is going to delete it, and do its
		// way back up until it will delete the first directory.
		} else {
			List<Directory> DirToBeChecked = new ArrayList<>();
			DirToBeChecked.add(allDirNames.get(name));
			// until we are going to delete all the directories
			while (!DirToBeChecked.isEmpty()) {
				int ListLastIndex = DirToBeChecked.size()-1;
				Directory currentDir = DirToBeChecked.get(ListLastIndex);
				// only if the directory is empty we can delete it, unless we need to clean it first
				if(currentDir.getFiles().isEmpty() && currentDir.getDirs().isEmpty()) {
					currentDir.getParentDir().delete(currentDir);
					allDirNames.remove(currentDir.getName());
					DirToBeChecked.remove(ListLastIndex);
					continue;
				}
				// iterates through all the files of a directory and deletes them all
				for(int i=0; i < currentDir.getFiles().size(); i++) {
					File currentFile = currentDir.getFiles().get(i);
					String fileName = currentFile.getName();
					currentFile.getParentDir().delete(currentFile);
					allFileNames.remove(fileName);
				}
				// puts all the child directories inside the the list to be deleted later
				DirToBeChecked.addAll(currentDir.getDirs());		
			}	
		}
	}
	
	public void showFileSystem () {
		/**
		 * Prints a display of all the directories in the system, with their exact addresses and date of creation.
		 * For each directory it is mentioning all the nested files names, sizes and dates of creation.
		 */
		// it is starting from the root directory and printing it first.
		List<Directory> printList = new ArrayList<>();
		printList.add(allDirNames.get("root:"));
		
		while (!printList.isEmpty()) {
			if(printList.get(0).getFiles().isEmpty()) {
				System.out.println("The directory \"" + printList.get(0) + "\" was made at " + printList.get(0).getDate() +
						"\nThe directory contains no files");
			}else {
				System.out.println("The directory \"" + printList.get(0).toString() + "\" was made at " + printList.get(0).getDate() +
						"\nThe directory contains the files:");
				for(int i=0; i < printList.get(0).getFiles().size(); i++) {
					System.out.println("\"" + printList.get(0).getFiles().get(i).getName() + "\"- Size: " +
				printList.get(0).getFiles().get(i).getSize()  + ", Creation date: " + printList.get(0).getFiles().get(i).getDate());
				}
			}
			System.out.println("");
			// the nested directories of every directory that was displayed will be take into the list to be displayed later.
			// this way the method will finish to print each level of the tree before it will go to the next one.
			printList.addAll(printList.get(0).getDirs());
			printList.remove(0);
		}	
	}
}
