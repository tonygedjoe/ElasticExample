package com.tg.elastic.entity;


public class NetworkFlow {

	private String sourceIPAddr;
	private String destIPAddr;
	private int sourcePort;
	private int destPort;
	private String protocol;
	private int pktCount;
	
	public String getSourceIPAddr() {
		return sourceIPAddr;
	}
	public void setSourceIPAddr(String sourceIPAddr) {
		this.sourceIPAddr = sourceIPAddr;
	}
	public String getDestIPAddr() {
		return destIPAddr;
	}
	public void setDestIPAddr(String destIPAddr) {
		this.destIPAddr = destIPAddr;
	}
	public int getSourcePort() {
		return sourcePort;
	}
	public void setSourcePort(int sourcePort) {
		this.sourcePort = sourcePort;
	}
	public int getDestPort() {
		return destPort;
	}
	public void setDestPort(int destPort) {
		this.destPort = destPort;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public int getPktCount() {
		return pktCount;
	}
	public void setPktCount(int pktCount) {
		this.pktCount = pktCount;
	}
	
	
}
