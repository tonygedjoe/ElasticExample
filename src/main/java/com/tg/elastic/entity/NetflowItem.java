package com.tg.elastic.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NetflowItem {

	private NetflowNode node;
	private NetflowFlow flow;
	
	public NetflowNode getNode() {
		return node;
	}
	public void setNode(NetflowNode node) {
		this.node = node;
	}
	public NetflowFlow getFlow() {
		return flow;
	}
	public void setFlow(NetflowFlow flow) {
		this.flow = flow;
	}
	
	
	
}
