package spotinstEx;
public class test {
	
	public static void main (String[] args) {
		FileSystem system = new FileSystem();
		system.addDir("root:", "users");
		system.addDir("root:", "others");
		system.addDir("others", "regular");
		system.addDir("regular", "sisters");
		system.addDir("regular", "brothers");
		system.addFile("regular", "frog", 100);
		system.addFile("regular", "dog", 100);
		system.addFile("brothers", "cat", 100000000);
		system.addFile("brothers", "donkey",100);
		
//		System.out.println(system.getAllDirs().toString());
//		System.out.println(system.getAllFiles().toString());
		system.showFileSystem();
		system.delete("others");
		system.showFileSystem();
	}
}
