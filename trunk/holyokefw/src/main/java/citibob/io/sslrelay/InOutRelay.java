/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.io.sslrelay;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/** Specialized version of RelayIntoOut, hacked to serve
 *  the needs of SSLRelayClient.
 */
class InOutRelay extends Thread {

interface Listener {
	/** Called when the connection was closed, for a reason
	 * other than normal EOF termination.
	 * @param e Exception that caused the connection to close; or null if none.
	 */
	void onConnectionClosed(Exception e);
}


	private InputStream in ;
	private OutputStream out ;
	private String name;
	private Listener listener;

	// ---------------------------------------------------------------
	private void init (InputStream in,
	OutputStream out , Listener listener)
	{
		this.listener = listener;
		this.name = name;
		this.in = in;
		this.out = out ;
		setDaemon(true);
		this.start();
	}
	public InOutRelay ( InputStream in,
	OutputStream out , String name, Listener listener)
	{
		super(name);
		init(in, out, listener);
	}

	public InOutRelay ( InputStream in, OutputStream out, Listener listener)
	{
		super();
		init(in, out, listener);
	}
	// ---------------------------------------------------------------


	public void run(){

		int size ;
		byte[] buffer = new byte[8192];

		try {
			while(true) {
				size = in.read(buffer);
				if(size > 0 ) {
//					System.out.println(name + " receive from in connection" + size);
					out.write(buffer,0, size);
					out.flush();
//					System.out.println(name  + " finish forwarding to out connection");
				} else if (size == -1) { //end of stream
//					System.out.println(name + " EOF detected!");
					out.close();
					return ;
				}
			}
		}
		catch(Exception e){
			e.printStackTrace(System.err);
			//	       System.err.println(name + e);
			closeAll(e);
		}


	}


//	public void closeAll() { closeAll(null); }

	/** Causes the thread to stop! */
	public void closeAll(Exception e){
		try{
			if(in != null ) in.close();
			if(out != null) out.close();
			if (listener != null) listener.onConnectionClosed(e);
		} catch(IOException e2){
			e2.printStackTrace(System.err);
	//		    System.err.println(e);
		}
	}
}
