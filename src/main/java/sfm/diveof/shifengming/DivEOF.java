import java.lang.Process;
import java.util.Scanner;
import java.io.*;

public class DivEOF{
	public static final int MAX_LINE = 100000;
	public static String[] ipt = new String[MAX_LINE];
	public static String[] opt = new String[MAX_LINE];
	public static String[] tmp = new String[MAX_LINE];
	public static String tmpFileName = "tempFile.in";
	public static String Split = "split";
	public static int IFLen,OFLen,curInLen,curOutLen,prvInLen,prvOutLen,FileIndex;
	public static List<String> DonePaths;
	
	public static List<String> divInput(String IFName,String OFName,String ExName) throws IOException, InterruptedException{
		IFLen = OFLen = curInLen = curOutLen = prvInLen = prvOutLen = FileIndex = 0;
		FileReader ifst = new FileReader(IFName);
		FileReader ofst = new FileReader(OFName);
		BufferedReader IFin = new BufferedReader(ifst);
		BufferedReader OFin = new BufferedReader(ofst);
		while((ipt[IFLen]=IFin.readLine()) != null){
			IFLen++;
		}
		while((opt[OFLen]=OFin.readLine()) != null){
			OFLen++;
		}
		String arg = new String("");
		
		ExecuteWindowsCommand cmd = new ExecuteWindowsCommand();
		arg = ipt[0];
		curInLen = 1;
		for(int i=1;i<=IFLen;i++){
			curOutLen = 0;
			for(;curInLen<i;curInLen++){
				arg = arg + "\n" + ipt[curInLen];
			}
			
			BufferedWriter fout = new BufferedWriter(new FileWriter(tmpFileName));
			fout.write(arg);
			fout.flush();
			fout.close();
			fout = new BufferedWriter(new FileWriter("tempFile.out"));
			fout.close();
			
			cmd.execute(ExName+" < tempFile.in > tempFile.out" + "\n");
			Thread.sleep(1000);
			Character terminate;
			terminate = 3;
			cmd.execute(terminate.toString());
			BufferedReader getOut = new BufferedReader(new FileReader("tempFile.out"));
			while( (tmp[curOutLen]=getOut.readLine()) != null ){
				System.out.println(tmp[curOutLen]);
				curOutLen++;
			}
			System.out.println(curOutLen);
			if(curOutLen > prvOutLen && isprefix()){
				fout = new BufferedWriter(new FileWriter(Split+FileIndex+".out"));
				DonePaths.add(String(Split+FileIndex+".out"));
				for(;prvInLen < curInLen;prvInLen++){
					fout.write(ipt[prvInLen]);
					fout.write("\n");
					fout.flush();
				}
				FileIndex++;
				prvOutLen = curOutLen;
				System.out.println("A file is created");
			}
			getOut.close();
		}
		IFin.close();
		OFin.close();
		return DonePaths;
	}
	
	public static boolean isprefix(){
		for(int i=0;i<curOutLen;i++){
			if(!opt[i].equals(tmp[i])) return false;
		}
		return true;
	}
}
