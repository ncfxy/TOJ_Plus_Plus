import java.io.InputStream;
import java.io.OutputStream;

public class SyncPipe implements Runnable {
	private final OutputStream outStream;  
	private final InputStream inStream;  
	    
    public SyncPipe(InputStream istrm, OutputStream ostrm) {  
        this.inStream = istrm;  
        this.outStream = ostrm;  
    }
    
    public void run() {  
        try{  
            final byte[] buffer = new byte[1024];  
            for (int length = 0; (length = inStream.read(buffer)) != -1;){  
                outStream.write(buffer, 0, length);  
            }  
        }  
        catch (Exception e) {  
            throw new RuntimeException("haha" + e.getMessage());  
        }  
    }  
   
}  