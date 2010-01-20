package com.meidusa.amoeba.net;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.meidusa.amoeba.util.Initialisable;
import com.meidusa.amoeba.util.InitialisationException;

public class MultiConnectionManagerWrapper extends ConnectionManager{
	private static Logger log      = Logger.getLogger(MultiConnectionManagerWrapper.class);
	private AtomicLong counter = new AtomicLong();
	public ConnectionManager[] connMgrs;
	private String subManagerClassName;
	private int processors = Runtime.getRuntime().availableProcessors();
	
	{
		int maxsubManager = Integer.valueOf(System.getProperty("maxSubManager","32"));
        int processors = Runtime.getRuntime().availableProcessors();
		processors = processors>=maxsubManager?maxsubManager:processors+1;
	}
	
	public void setSubManagerClassName(String className) {
		this.subManagerClassName = className;
	}

	public void setProcessors(int processors) {
		this.processors = processors;
	}

	
	
	public MultiConnectionManagerWrapper(ConnectionManager... connMgrs) throws IOException {
		super();
		this.connMgrs = connMgrs;
	}
	
	public MultiConnectionManagerWrapper()throws IOException{
		
	}
	
    public void postRegisterNetEventHandler(NetEventHandler handler, int key) {
    	if(connMgrs != null){
    		long count = counter.incrementAndGet();
    		connMgrs[(int)count% connMgrs.length].postRegisterNetEventHandler(handler, key);
    	}else{
    		super.postRegisterNetEventHandler(handler, key);
    	}
    }
    
    public void run(){
        if(connMgrs == null){
        	super.run();
        }
    }
    
    public void init() throws InitialisationException {
    	if(connMgrs == null || connMgrs.length<1 || connMgrs[0] == null){
    		connMgrs = null;
    		if(subManagerClassName != null){
	    		connMgrs = new ConnectionManager[processors];
	    		for(int i=0;i<processors;i++){
	    		try {
	    			connMgrs[i] = (ConnectionManager)Class.forName(subManagerClassName).newInstance();
	    			connMgrs[i].setName(this.getName()+"-"+i);
					} catch (Exception e) {
						log.error("create sub manager error",e);
						e.printStackTrace();
						System.exit(-1);
					}
	    		}
    		}
    	}
    	
    	Level level = log.getLevel();
        log.setLevel(Level.INFO);
        log.info(this.getName() + " LoopingThread willStart....");
        log.setLevel(level);
        if(connMgrs != null){
	        for(int i=0;i<connMgrs.length;i++){
	        	if(connMgrs[i] instanceof AuthingableConnectionManager){
	        		AuthingableConnectionManager aconnMgr = (AuthingableConnectionManager)connMgrs[i];
	        		//aconnMgr.setAuthenticator(ProxyRuntimeContext.getInstance().get)
	        	}
	        	if(!connMgrs[i].isAlive()){
	        		connMgrs[i].start();
	        	}
	        	
	        }
        }
    }
}