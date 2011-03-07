package com.dexels.navajo.server.listener.http.continuation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.continuation.Continuation;
import org.eclipse.jetty.continuation.ContinuationSupport;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.listeners.NavajoDoneException;
import com.dexels.navajo.server.ClientInfo;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.FastDispatcher;
import com.dexels.navajo.server.FatalException;
import com.dexels.navajo.server.listener.http.TmlScheduler;
import com.dexels.navajo.server.listener.http.standard.TmlStandardRunner;
import com.jcraft.jzlib.JZlib;
import com.jcraft.jzlib.ZOutputStream;

public class TmlContinuationRunner extends TmlStandardRunner {

	private final Continuation continuation;
	private Navajo outDoc;

	public TmlContinuationRunner(HttpServletRequest request, Navajo inputDoc,
			HttpServletResponse response, String sendEncoding,
			String recvEncoding, Object cert) {
		super(request, inputDoc, response, sendEncoding, recvEncoding, cert);
		connectedAt = System.currentTimeMillis();
		continuation = ContinuationSupport.getContinuation(request);
		System.err.println("Continuation found: "+continuation.getClass());
		

	}
	
	

	@Override
	public void endTransaction() throws IOException {
		try {
			// writeOutput moved from execute to here, as the scheduler thread shouldn't touch the response output stream
			writeOutput(getInputNavajo(), outDoc);
		} catch (NavajoException e) {
			e.printStackTrace();
		}
		super.endTransaction();
	}



	// 
	public void writeOutput(Navajo inDoc, Navajo outDoc) throws IOException, FileNotFoundException, UnsupportedEncodingException, NavajoException {
		OutputStream out = null;

		response.setContentType("text/xml; charset=UTF-8");
		response.setHeader("Connection", "close"); // THIS IS NOT SUPPORTED, I.E. IT DOES NOT WORK...EH.. PROBABLY..
		// Should be refactored to a special filter.
	if ( recvEncoding != null && recvEncoding.equals(COMPRESS_JZLIB)) {		
		response.setHeader("Content-Encoding", COMPRESS_JZLIB);
		  out = new ZOutputStream(response.getOutputStream(), JZlib.Z_BEST_SPEED);
	  } else if ( recvEncoding != null && recvEncoding.equals(COMPRESS_GZIP)) {
		  response.setHeader("Content-Encoding", COMPRESS_GZIP);
		  out = new java.util.zip.GZIPOutputStream(response.getOutputStream());
	  }
	  else {
		  System.err.println("No content encoding specified: (" + sendEncoding + "/" + recvEncoding + ")");
		  out = response.getOutputStream();
	  }
	
	
	  long finishedScriptAt = System.currentTimeMillis();
	  // postTime = 
	  long postTime = scheduledAt - connectedAt;
	  long queueTime = connectedAt - scheduledAt;
	  long serverTime = finishedScriptAt - connectedAt;

	  outDoc.getHeader().setHeaderAttribute("postTime", ""+postTime);
	  outDoc.getHeader().setHeaderAttribute("queueTime", ""+queueTime);
	  outDoc.getHeader().setHeaderAttribute("serverTime", ""+serverTime);
	  outDoc.getHeader().setHeaderAttribute("threadName", ""+Thread.currentThread().getName());
	  
	  TmlScheduler ts = getTmlScheduler();
	  String threadStatus = null;
	  if(ts!=null) {
		  threadStatus = ts.getSchedulingStatus();
	  } else {
		  threadStatus = "Schedule status unknown, no scheduler found.";
	  }
//	  int threadsActive = getActiveCount();

	  if ( inDoc != null && inDoc.getHeader() != null && outDoc != null && outDoc.getHeader() != null && !Dispatcher.isSpecialwebservice(inDoc.getHeader().getRPCName())) {
		  System.err.println("(" + DispatcherFactory.getInstance().getApplicationId() + "): " + 
				          new java.util.Date(connectedAt) + ": " +
				          outDoc.getHeader().getHeaderAttribute("accessId") + ":" + 
				          inDoc.getHeader().getRPCName() + "(" + inDoc.getHeader().getRPCUser() + "):" + 
				          ( System.currentTimeMillis() - connectedAt ) + " ms. " + 
				          "(st=" + outDoc.getHeader().getHeaderAttribute("serverTime") + 
				          ",rpt=" + outDoc.getHeader().getHeaderAttribute("requestParseTime") + 
				          ",at=" + outDoc.getHeader().getHeaderAttribute("authorisationTime") + 
				          ",pt=" + outDoc.getHeader().getHeaderAttribute("processingTime") + 
				          ",tc=" + outDoc.getHeader().getHeaderAttribute("threadCount") + 
						  ",cpu=" + outDoc.getHeader().getHeaderAttribute("cpuload") + 
						  ",cpt="+postTime+
						  ",cqt="+queueTime+
						  ",qst="+serverTime+
						  ",cta="+threadStatus+
						  ",cwt=N/A)" + " (" + sendEncoding + "/" + recvEncoding +
						  
				  ")" );
	  }

	  outDoc.write(out);

	
}

	  /**
	   * Handle a request.
	   *
	   * @param request
	   * @param response
	   * @throws IOException
	   * @throws ServletException
	   */
	  private final void execute() throws IOException, ServletException {
		  
//		  BufferedReader r = null;
		  try {
			  Navajo in = getInputNavajo();
			  in.getHeader().setHeaderAttribute("useComet", "true");
			  

				  boolean continuationFound = false;
				  try {
					  
					  String sendEncoding = request.getHeader("Accept-Encoding");
				      String recvEncoding = request.getHeader("Content-Encoding");
						
					  ClientInfo clientInfo = 	new ClientInfo(request.getRemoteAddr(), "unknown",
								recvEncoding, (int) (scheduledAt - connectedAt), (int) (connectedAt - scheduledAt), ( recvEncoding != null && ( recvEncoding.equals(COMPRESS_GZIP) || recvEncoding.equals(COMPRESS_JZLIB))), 
								( sendEncoding != null && ( sendEncoding.equals(COMPRESS_GZIP) || sendEncoding.equals(COMPRESS_JZLIB))), 
								request.getContentLength(), new java.util.Date( connectedAt ) );
					  
					  outDoc = DispatcherFactory.getInstance().removeInternalMessages(DispatcherFactory.getInstance().handle(in, this,cert, clientInfo));
					  // Do do: Support async services in a more elegant way.
				  } catch (NavajoDoneException e) {
					  // temp catch, to be able to pre
					  continuationFound = true;
					  System.err.println("Navajo done in service runner. Thread disconnected...");
					  throw(e);
				  }
				  finally {
					  getTmlScheduler().removeTmlRunnable(request);
					  getTmlScheduler().runFinished(this);
					  if(!continuationFound) {
						  resumeContinuation();
					  }
				  }
//			  }
		  }
		  catch (NavajoDoneException e) {
			  throw(e);
		  }
		  catch (Throwable e) {
			  //e.printStackTrace(System.err);
			  if ( e instanceof  FatalException ) {
				  FatalException fe = (FatalException) e;
				  if ( fe.getMessage().equals("500.13")) {
					  // Server too busy.
					  continuation.undispatch();
					  throw new ServletException("500.13");
				  }
			  }
			  throw new ServletException(e);
		  } 
	  }



	private void resumeContinuation() {
		continuation.resume();
		
		
	}



	public void suspendContinuation() {
		continuation.suspend();
	}
	


	@Override
	public void run() {
		try {
			execute();
		} catch(NavajoDoneException e) {
			System.err.println("NavajoDoneException caught. This thread fired a continuation. Another thread will finish it in the future.");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				response.sendError(500, e.getMessage());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

}
