package com.tg.elastic.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NetflowFlow {

	private String server_addr;
	
	private String src_addr;
	private String dst_addr;
	
	private int src_port; 
	private int dst_port;
	
	private String ip_protocol;
	
	private String src_mac;
	private String dst_mac;
	
	private long packets;
	private String[] tcp_flags;
	public String getServer_addr() {
		return server_addr;
	}
	public void setServer_addr(String server_addr) {
		this.server_addr = server_addr;
	}
	public String getSrc_addr() {
		return src_addr;
	}
	public void setSrc_addr(String src_addr) {
		this.src_addr = src_addr;
	}
	public String getDst_addr() {
		return dst_addr;
	}
	public void setDst_addr(String dst_addr) {
		this.dst_addr = dst_addr;
	}
	public int getSrc_port() {
		return src_port;
	}
	public void setSrc_port(int src_port) {
		this.src_port = src_port;
	}
	public int getDst_port() {
		return dst_port;
	}
	public void setDst_port(int dst_port) {
		this.dst_port = dst_port;
	}
	public String getIp_protocol() {
		return ip_protocol;
	}
	public void setIp_protocol(String ip_protocol) {
		this.ip_protocol = ip_protocol;
	}
	public String getSrc_mac() {
		return src_mac;
	}
	public void setSrc_mac(String src_mac) {
		this.src_mac = src_mac;
	}
	public String getDst_mac() {
		return dst_mac;
	}
	public void setDst_mac(String dst_mac) {
		this.dst_mac = dst_mac;
	}
	public long getPackets() {
		return packets;
	}
	public void setPackets(long packets) {
		this.packets = packets;
	}
	public String[] getTcp_flags() {
		return tcp_flags;
	}
	public void setTcp_flags(String[] tcp_flags) {
		this.tcp_flags = tcp_flags;
	}
	

	
	
	
}
