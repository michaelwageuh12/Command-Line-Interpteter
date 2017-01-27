import java.io.*;
import java.util.*;

public class CLI {
	
	public static void main(String[] arg) throws IOException, InterruptedException
	{
		String input = new String();
		Scanner scanner = new Scanner(System.in);
		String cmd = "",options = "",param1 = "",param2 = "";
		boolean append = false;
		String filePath = "";
		char outputType = 'c';
		
		while(cmd.compareTo("exit") != 0)
		{
			System.out.print("Please enter the command(enter \"exit\" to stop) >> ");
			input = scanner.nextLine();
			
			String[] commands = input.split(";");
			
			for(int i =0 ; i < commands.length;i++)
			{
				cmd = param1 = param2 = options = "";
				outputType = 'c';
				if(commands[i].length() == 0)
					continue;
				if(commands[i].contains("\""))
					commands[i] = SpaceReplacor(commands[i]);

				commands[i] = commands[i].trim();	
				
				if(commands[i].contains(">"))
				{
					outputType = 'f';
					int firstIndex = commands[i].indexOf(">");
					int lastIndex = commands[i].lastIndexOf(">");
					
					if(firstIndex != lastIndex)
						append = true;
					else 
						append = false;

					filePath = commands[i].substring(lastIndex+1, commands[i].length());
					commands[i] = commands[i].substring(0, firstIndex);
					
					filePath = filePath.trim();
					filePath = filePath.replace('|', ' ');
					commands[i] = commands[i].trim();
				}

				
				String[] cmdLine = commands[i].split(" ");
				boolean opt;
				
				cmd = cmdLine[0];
				if(cmdLine.length == 1)
				{
					opt = false;
				}
				else if(cmdLine.length == 2)
				{
					if(cmdLine[1].charAt(0) != '-')
					{
						opt = false;
						param1 = cmdLine[1];
					}
					else
					{
						opt = true;
						options = cmdLine[1];
					}
				}
				else if(cmdLine.length == 3)
				{
					if(cmd.compareTo("cp") == 0 || cmd.compareTo("mv") == 0)
					{
						param1 = cmdLine[1];
						param2 = cmdLine[2];
						opt = false;
					}
					else{
						options = cmdLine[1];
						param1 = cmdLine[2];
						opt = true;
					}
					
				}
				else if(cmdLine.length == 4 && (cmd.compareTo("cp") == 0 || cmd.compareTo("mv") == 0))
				{
					opt = true;
					options = cmdLine[1];
					param1 = cmdLine[2];
					param2 = cmdLine[3];
				}
				else
					continue;
			
			
				param1 = param1.replace('|', ' ');
				param2 = param2.replace('|', ' ');
				if(cmd.compareTo("pwd") == 0)
				{
					Commands.pwd(outputType,append,filePath);
				}
				else if(cmd.compareTo("cd") == 0)
					Commands.cd(param1);
				else if(cmd.compareTo("cp") == 0)
					Commands.copy(options, param1, param2);
				else if(cmd.compareTo("mv") == 0)
					Commands.move(options, param1, param2);
				else if(cmd.compareTo("cat") == 0)
					Commands.cat(param1,outputType,append,filePath);
				else if(cmd.compareTo("more") == 0)
					Commands.more(param1,outputType,append,filePath);
				else if(cmd.compareTo("rm") == 0)
					Commands.rm(param1);
				else if(cmd.compareTo("rmdir") == 0)
					Commands.rmdir(param1);
				else if(cmd.compareTo("mkdir") == 0)
					Commands.mkdir(param1);
				else if(cmd.compareTo("ls") == 0)
					Commands.ls(param1,outputType,append,filePath);
				else if(cmd.compareTo("args") == 0)
					Commands.args(param1,outputType,append,filePath);
				else if(cmd.compareTo("help") == 0)
					Commands.help(outputType,append,filePath);
				else if(cmd.compareTo("clear") == 0)
					Commands.clear();
				else if(cmd.compareTo("?") == 0)
					Commands.questionMark(param1,outputType,append,filePath);
				else if(cmd.compareTo("date") == 0)
					Commands.date(param1,outputType,append,filePath);
				else if(cmd.compareTo("ls") == 0)
					Commands.ls(param1,outputType,append,filePath);
				else if(cmd.compareTo("exit") != 0)
					System.out.println("\'"+cmd+"\' command is not recognized");
				
				
			}
		}
		
	}
	
	public static String SpaceReplacor(String cmdLine)
	{
		while(cmdLine.contains("\""))
		{
			int startIndex = cmdLine.indexOf("\"");
			cmdLine = cmdLine.substring(0,startIndex) + cmdLine.substring(startIndex+1,cmdLine.length());
			int endIndex = cmdLine.indexOf("\"");
			cmdLine = cmdLine.substring(0,endIndex) + cmdLine.substring(endIndex+1,cmdLine.length());
			
			for(int i = startIndex;i < endIndex;i++)
			{
				if(cmdLine.charAt(i) == ' ')
					cmdLine = cmdLine.substring(0,i) + "|" + cmdLine.substring(i+1,cmdLine.length());
			}
			
		}
		return cmdLine;
	}
}


