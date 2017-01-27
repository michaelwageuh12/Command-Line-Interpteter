import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.file.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class Commands {
	
	public static String pwd(char outputType,boolean app,String filePath)
	{
		String path = System.getProperty("user.dir");
		Output.printOneLine(path, outputType, app, filePath);
		return path;
	}
	
	public static void cd(String path)
	{
		path = Checker.CheckDirectory(path);
		if(path.compareTo("??") != 0)
			System.setProperty("user.dir", path);
		else
			System.out.println("Wrong Path");
	}
	
	public static boolean copy(String options,String source,String destination)
	{
		if(source.length() == 0 || destination.length() == 0)
		{
			System.out.println("Wrong Parameters");
			return false;
		}
		
		source = Checker.CheckFile(source);
		
		if(source.compareTo("??") == 0)
		{
			System.out.println("Wrong File");
			return false;
		}
		
		destination = destination.replace('/', '\\');
		
		if(destination.contains("\\"))
		{
			String dirPart = Checker.CheckDirectory(destination);
			int index = destination.lastIndexOf("\\");
			
			if(dirPart.compareTo("??") != 0)
			{
				int beginIndex = source.lastIndexOf("\\")+1;
				destination = dirPart + source.substring(beginIndex,source.length());
			}
			else
			{
				dirPart = Checker.CheckDirectory(destination.substring(0,index));
				if(dirPart.compareTo("??") == 0)
				{
					System.out.println("WrongPath");
					return false;
				}
				else
				{
					destination = dirPart + destination.substring(index+1,destination.length());
				}
			}
			
		}
		else if(destination.compareTo(".") == 0 || destination.compareTo("..") == 0 || destination.contains(":"))
		{
			int index = source.lastIndexOf("\\")+1;
			destination = Checker.CheckDirectory(destination) + source.substring(index, source.length());
		}
		else
			destination = Checker.CheckDirectory(".")+destination;
		
		File srcFile = new File(source);
		File desFile = new File(destination);
		
		try
		{
			if(Checker.CheckFile(destination).compareTo("??") != 0)
				Files.delete(desFile.toPath());
			
			Files.copy(srcFile.toPath(), desFile.toPath());
		}
		catch(AccessDeniedException ex)
		{
			System.out.println("Permission denied");
			return false;
		}
		catch(IOException ex)
		{
			System.out.println("Wrong Path");
			return false;
		}
		return true;
	}
	
	public static boolean move(String options,String source,String destination)
	{
		if(source.length() == 0 || destination.length() == 0)
		{
			System.out.println("Wrong Parameters");
			return false;
		}
		source = Checker.CheckFile(source);
		
		if(source.compareTo("??") == 0)
		{
			System.out.println("Wrong File");
			return false;
		}
		
		destination = destination.replace('/', '\\');
		if(destination.contains("\\"))
		{
			String dirPart = Checker.CheckDirectory(destination);
			int index = destination.lastIndexOf("\\");
			
			if(dirPart.compareTo("??") != 0)
			{
				int beginIndex = source.lastIndexOf("\\")+1;
				destination = dirPart + source.substring(beginIndex,source.length());
			}
			else
			{
				dirPart = Checker.CheckDirectory(destination.substring(0,index));
				if(dirPart.compareTo("??") == 0)
				{
					System.out.println("WrongPath");
					return false;
				}
				else
				{
					destination = dirPart + destination.substring(index+1,destination.length());
				}
			}
			
		}
		else if(destination.compareTo(".") == 0 || destination.compareTo("..") == 0 || destination.contains(":"))
		{
			int index = source.lastIndexOf("\\")+1;
			destination = Checker.CheckDirectory(destination) + source.substring(index, source.length());
		}
		else
			destination = Checker.CheckDirectory(".")+destination;
		
		File srcFile = new File(source);
		File desFile = new File(destination);
		
		try
		{
			
			Files.move(srcFile.toPath(), desFile.toPath(),StandardCopyOption.REPLACE_EXISTING);
		}
		catch(AccessDeniedException ex)
		{
			System.out.println("Permission denied");
			return false;
		}
		catch(IOException ex)
		{
			System.out.println("Wrong Path");
		}
		
		return true;
	    
	}
	
	public static void rm(String targetFile)
	{
		if(targetFile.length() == 0)
		{
			System.out.println("You should enter file path");
			return;
		}
		targetFile = Checker.CheckFile(targetFile);
		if(targetFile.compareTo("??") == 0)
		{
			System.out.println("File disn't exist in this DIR");
			return;
		}
		
		try{
		// It has Full Path
		 File file = new  File(targetFile);
		 file.delete();
		}
		 catch(Exception e){
			 e.printStackTrace();
		 }
	}
	
	public static void mkdir(String targetFile ){
		String PathFile;
		if(targetFile.length() == 0)
		{
			System.out.println("You should enter directory");
			return;
		}
		targetFile = targetFile.replace('/', '\\');
		if((targetFile.contains("\\")))
		{
			// exclude the file name to check if the directory is exist or no
		      PathFile = targetFile.substring(0,targetFile.lastIndexOf( "\\" ));
		      PathFile = Checker.CheckDirectory(PathFile);
		}
		else
			PathFile = Checker.CheckDirectory(".") + targetFile;
		
		if(PathFile.compareTo("??" + targetFile) == 0)
		{
	    	  System.out.println("Sorry Wrong path");  
		}
		else
		{
			File f = new File(PathFile);
		 	f.mkdir();
		}     
	}
	
	public static void rmdir(String targetFile){
		
		if(targetFile.length() == 0)
		{
			System.out.println("You should enter directory");
			return;
		}
		targetFile = Checker.CheckDirectory(targetFile);
		if(targetFile.compareTo("??") == 0)
		{
			System.out.println("Wrong Dir");
			return;
		}
		
		File myFile = new File(targetFile);
		// The file isn't empty
  		if(myFile.list().length > 0){
  			System.out.println("Sorry This directory is not empty");
  		}
  		else{
  			myFile.delete();
  		}
	}
	
	public static void cat(String targetFile,char outputType,boolean app,String filePath) throws IOException{		
		
		if(targetFile.length() == 0)
		{
			System.out.println("You should enter file path");
			return;
		}
		
		targetFile = Checker.CheckFile(targetFile);
		if(targetFile.compareTo("??") == 0)
		{
			System.out.println("Wrong File Path");
			return;
		}
		File f = new File(targetFile);
		      
		FileReader reader = new FileReader(targetFile);
		BufferedReader br = new BufferedReader(reader);
		 
		String line;
		ArrayList<String> data = new ArrayList<String>();
		while((line = br.readLine()) != null)
		{
			data.add(line);
		}
		br.close();
		
		Output.printListOfData(data, outputType, app, filePath);
	}
	
	public static void more(String targetFile,char outputType,boolean app,String filePath) throws IOException
	{
		if(targetFile.length() == 0)
		{
			System.out.println("You should enter file path");
			return;
		}
		if(outputType == 'f')
		{
			cat(targetFile, outputType, app, filePath);
			return;
		}
		
		if(targetFile.length() == 0)
		{
			System.out.println("missing file parameter");
			return;
		}
		ArrayList<String> lines = new ArrayList<String>();
		targetFile = Checker.CheckFile(targetFile);
		if(targetFile.compareTo("??") == 0)
		{
			System.out.println("Wrong File Path");
			return;
		}
		File f = new File(targetFile);
		      
		FileReader reader = new FileReader(targetFile);
		BufferedReader br = new BufferedReader(reader);
		 
		String line;
		int i = 0;
		while((line = br.readLine()) != null)
		{
	        lines.add(line);
		}
		System.out.println("");
		br.close();
		
		for(i = 1;i < lines.size();i++)
		{
			if(i % 5 == 0)
			{
				Scanner scanner = new Scanner(System.in);
				scanner.nextLine();
			}
			
			if(i%5 == 4)
				System.out.print(lines.get(i));
			else
				System.out.println(lines.get(i));
		}
		
	}
	
	public static void ls(String path,char outputType,boolean app,String filePath)
	{
		if(path.length() == 0)
			path = Checker.CheckDirectory(".");
		else
			path = Checker.CheckDirectory(path);
		
		if(path.compareTo("??") == 0)
		{
			System.out.println("Wrong path");
			return;
		}
		
		File myDir = new File(path);
		ArrayList<String> data = new ArrayList<String>();
		
		for(String line : myDir.list())
		{
			data.add(line);
		}
		Output.printListOfData(data, outputType, app, filePath);
	}
	
		public static void date(String param1,char outputType,boolean app,String filePath)
		{
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			Output.printOneLine(dateFormat.format(cal.getTime()), outputType, app, filePath);

		}
		
		public static void clear()
		{	
			for (int i=0; i<=15; i++)
			    System.out.println("\n");	
		}
		
		public static void help(char outputType,boolean app,String filePath)
		{
			ArrayList<String> data = new ArrayList<String>();
			data.add("ls :     " + "List directory contents");
			data.add("clear :  " + "Cleans up the visible area of the console");
			data.add("args :   " + "List all command arguments");
			data.add("help :   " + "List all user commands and the description");
			data.add("date :   " + "Current date/time");
			data.add("? :      " + "Display help for Specif command");
			data.add("cd:      " + "Change the working directory");
			data.add("cp:      " + "Copies sourcefile to targetfile");
			data.add("mv:      " + "Copies sourcefile to targetfile then deletes the original sourcefile");
			data.add("rm:      " + "Removes the specified files from the file system");
			data.add("rmdir:   " + "Deletes the specified directory, provided it is already empty");
			data.add("mkdir:   " + "Creates a new directory");
			data.add("cat:     " + "Display the contents of a file, printing the entire contents to the screen without interruption");
			data.add("more:    " + "Display text, one screen at a time");
			data.add("pwd:     " + "Print the full pathname of the current working directory");
			data.add("exit:    " + "Stop all");
			
			Output.printListOfData(data, outputType, app, filePath);
		}
		
		public static void args(String param1,char outputType,boolean app,String filePath)
		{
			if(param1.length() == 0)
			{
				System.out.println("You should enter command name");
				return;
			}
			if(param1.compareTo("ls") == 0)
			{
				Output.printOneLine("ls [OPTION(s)]...[file(s)]", outputType, app, filePath);
			}
			else if(param1.compareTo("clear") == 0)
			{
				Output.printOneLine("NO OPTIONS", outputType, app, filePath);
			}
			else if(param1.compareTo("args") == 0)
			{
				Output.printOneLine("args [PARAMETER]", outputType, app, filePath);
			}
			else if(param1.compareTo("help") == 0)
			{
				Output.printOneLine("NO OPTIONS", outputType, app, filePath);
			}
			else if(param1.compareTo("date") == 0)
			{
				Output.printOneLine("date [OPTION]... [+FORMAT]", outputType, app, filePath);
			}
			else if(param1.compareTo("?") == 0)
			{
				Output.printOneLine("? [PARAMETER]", outputType, app, filePath);
			}
			else if(param1.compareTo("cd") == 0)
			{
				Output.printOneLine("cd [OPTION(s)]...[directory]", outputType, app, filePath);
			}
			else if(param1.compareTo("cp") == 0)
			{
				Output.printOneLine("cp [OPTION(s)] sourcefile...targetfile", outputType, app, filePath);
			}
			else if(param1.compareTo("mv") == 0)
			{
				Output.printOneLine("mv [OPTION(s)] sourcefile...targetfile", outputType, app, filePath);
			}
			else if(param1.compareTo("rm") == 0)
			{
				Output.printOneLine("rm [OPTION(s)]...file(s)", outputType, app, filePath);
			}
			else if(param1.compareTo("mkdir") == 0)
			{
				Output.printOneLine("mkdir [OPTION(s)]...directoryname", outputType, app, filePath);
			}
			else if(param1.compareTo("rmdir") == 0)
			{
				Output.printOneLine("rmdir [OPTION(s)]...directoryname", outputType, app, filePath);
			}
			else if(param1.compareTo("cat") == 0)
			{
				Output.printOneLine("cat [OPTION(s)]...file(s)", outputType, app, filePath);
			}
			else if(param1.compareTo("more") == 0)
			{
				Output.printOneLine("more [OPTIONS]...[file_name]", outputType, app, filePath);
			}
			else if(param1.compareTo("pwd") == 0)
			{
				Output.printOneLine("pwd [OPTION]...", outputType, app, filePath);
			}
			else if(param1.compareTo("exit") == 0)
			{
				Output.printOneLine("NO OPTIONS", outputType, app, filePath);
			}
		}
		
		public static void questionMark(String param1,char outputType,boolean app,String filePath)
		{
			if(param1.length() == 0)
			{
				System.out.println("You should enter command name");
				return;
			}
			if(param1.compareTo("ls") == 0)
			{
				Output.printOneLine("List directory contents", outputType, app, filePath);
			}
			else if(param1.compareTo("clear") == 0)
			{
				Output.printOneLine("Cleans up the visible area of the console", outputType, app, filePath);
			}
			else if(param1.compareTo("args") == 0)
			{
				Output.printOneLine("List all command arguments", outputType, app, filePath);
			}
			else if(param1.compareTo("help") == 0)
			{
				Output.printOneLine("List all user commands and the description", outputType, app, filePath);
			}
			else if(param1.compareTo("date") == 0)
			{
				Output.printOneLine("Current date/time", outputType, app, filePath);
			}
			else if(param1.compareTo("?") == 0)
			{
				Output.printOneLine("Display help for Specif command", outputType, app, filePath);
			}
			else if(param1.compareTo("cd") == 0)
			{
				Output.printOneLine("Change the working directory", outputType, app, filePath);
			}
			else if(param1.compareTo("cp") == 0)
			{
				Output.printOneLine("Copies sourcefile to targetfile", outputType, app, filePath);
			}
			else if(param1.compareTo("mv") == 0)
			{
				Output.printOneLine("Copies sourcefile to targetfile then deletes the original sourcefile", outputType, app, filePath);
			}
			else if(param1.compareTo("rm") == 0)
			{
				Output.printOneLine("Removes the specified files from the file system", outputType, app, filePath);
			}
			else if(param1.compareTo("mkdir") == 0)
			{
				Output.printOneLine("Creates a new directory", outputType, app, filePath);
			}
			else if(param1.compareTo("rmdir") == 0)
			{
				Output.printOneLine("Deletes the specified directory, provided it is already empty", outputType, app, filePath);
			}
			else if(param1.compareTo("cat") == 0)
			{
				Output.printOneLine("Display the contents of a file, printing the entire contents to the screen without interruption", outputType, app, filePath);
			}
			else if(param1.compareTo("more") == 0)
			{
				Output.printOneLine("Display text, one screen at a time", outputType, app, filePath);
			}
			else if(param1.compareTo("pwd") == 0)
			{
				Output.printOneLine("Print the full pathname of the current working directory", outputType, app, filePath);
			}
			else if(param1.compareTo("exit") == 0)
			{
				Output.printOneLine("Stop all", outputType, app, filePath);
			}
		}
}

class Output
{
	public static void printOneLine(String data, char outputType,boolean app,String filePath)
	{
		if(outputType == 'c')
		{
			System.out.println(data);
		}
		else
		{
			try
			{
				filePath.replace('/', '\\');
				int index = filePath.lastIndexOf("\\");
				if(index == -1)
					filePath = Checker.CheckDirectory(".") + filePath;
				else
				{
					String dirPart = filePath.substring(0,index);
					dirPart = Checker.CheckDirectory(dirPart);
					
					String fileName = filePath.substring(index+1,filePath.length());
					
					filePath = dirPart + fileName;
				}
				FileWriter fw = new FileWriter(filePath,app);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter pw = new PrintWriter(bw);
				
				pw.println(data);
				pw.close();
			}
			catch(Exception ex)
			{
				System.out.println("Wrong Path");
			}
		}
	}
	public static void printListOfData(ArrayList<String> data, char outputType,boolean app,String filePath)
	{
		if(outputType == 'c')
		{
			for(int i = 0 ;i < data.size();i++)
				System.out.println(data.get(i));
		}
		else
		{
			try
			{
				filePath.replace('/', '\\');
				int index = filePath.lastIndexOf("\\");
				if(index == -1)
					filePath = Checker.CheckDirectory(".") + filePath;
				else
				{
					String dirPart = filePath.substring(0,index);
					dirPart = Checker.CheckDirectory(dirPart);
					
					String fileName = filePath.substring(index+1,filePath.length());
					
					filePath = dirPart + fileName;
				}
				
				FileWriter fw = new FileWriter(filePath,app);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter pw = new PrintWriter(bw);
				
				for(int i = 0;i < data.size();i++)
					pw.println(data.get(i));
				pw.close();
			}
			catch(Exception ex)
			{
				System.out.println("Wrong Path");
			}
		}
	}
}