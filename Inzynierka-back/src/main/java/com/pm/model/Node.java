package com.pm.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Node")
public class Node extends AbstractModel{

	private String labelOfNode;

	public Node(){}

	public String getLabelOfNode() {
		return labelOfNode;
	}
	public void setLabelOfNode(String labelOfNode) {
		this.labelOfNode = labelOfNode;
	}

}
