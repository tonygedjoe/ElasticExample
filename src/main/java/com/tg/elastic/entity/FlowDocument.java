package com.tg.elastic.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "netflow")
public class FlowDocument  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private NetworkCollector collector;
	private List<NetworkFlow> flowList = new ArrayList<>();
	
	
	public NetworkCollector getCollector() {
		return collector;
	}
	public void setCollector(NetworkCollector collector) {
		this.collector = collector;
	}
	public List<NetworkFlow> getFlowList() {
		return flowList;
	}
	public void setFlowList(List<NetworkFlow> flowList) {
		this.flowList = flowList;
	}
	
	
	public void addFlow(NetworkFlow flow) {
		this.flowList.add(flow);
	}
	
}
