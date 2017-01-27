import java.io.*;

public class Checker {
	
	public static String CheckDirectory(String path)
	{
		File current = new File(System.getProperty("user.dir"));
	    int startIndex = 0;
	    String prefix = "";
	    String[] parts;
	    
		File dir;
		boolean wrongPath = false;
		
		
		path = path.replace('/', '\\');
		
		//get the each SubDirectroy into a string
		parts = path.split("\\\\");
		
		//Handling the special cases like '.', '..', or '\'
		if(path.length() == 0 || path.compareTo("\\") == 0 || parts[0].length() == 0)
		{
			prefix = "C:\\";
			startIndex = 1;
		}
		else if(parts[0].compareTo(".") == 0)
		{
			startIndex = 1;
			prefix = current.getAbsolutePath() + "\\";
		}
		else if(parts[0].compareTo("..") == 0)
		{
			startIndex = 1;
			prefix = current.getParent();
			try{
				for(int i = 1;i < parts.length;i++)
				{
					if(parts[i].compareTo("..") == 0)
					{
						startIndex++;
						prefix = new File(prefix).getParent();
					}
					else
						break;
				}
				prefix += "\\";
			}
			catch(Exception ex){
				wrongPath = true;
			}
		}
		else if(parts[0].contains(":") == false)
		{
			prefix = current.getAbsolutePath() + "\\";
		}
		
		
		path = prefix;
		
		for(int i = startIndex;i < parts.length;i++)
		{
			path += parts[i] + "\\";
			if(parts[i].compareTo(".") == 0 || parts[i].compareTo("..") == 0)
			{
				wrongPath = true;
				break;
			}
		}
		
		if(wrongPath || !(new File(path)).exists() || !(new File(path)).isDirectory())
			return "??";
		else	
			return path;
	}
	
	public static String CheckFile(String path)
	{
		path = path.replace("/", "\\");
		String FileName;
		if(path.contains("\\"))
		{
			FileName = path.substring(path.lastIndexOf("\\")+1, path.length());
			path = CheckDirectory(path.substring(0,path.lastIndexOf("\\")));
			
			if(path.compareTo("??") == 0 || !(new File(path + FileName).exists()) ||(new File(path + FileName).isDirectory()) )
				return "??";
			else
				return path + FileName;
		}
		else
		{
			FileName =  path;
			path = CheckDirectory(".");
			if(new File(path + FileName).exists() && !new File(path + FileName).isDirectory())
				return path + FileName;
			else
				return "??";
		}
	}
	
	
}
