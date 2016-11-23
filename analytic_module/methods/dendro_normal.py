#!/usr/bin/python

# Load required modules
import pandas as pd 
import scipy.spatial
import scipy.cluster
import numpy as np
import json
import matplotlib.pyplot as plt
import pickle
import networkx as nx
from scipy.cluster.hierarchy import linkage
import xml.etree.ElementTree as ET

def normal_dendrogram(xml_input, graph, output):
	tree = ET.parse(xml_input)
	root = tree.getroot()
	patient_text = {}
	for child in root:
		for grandchild in child:
			import re
			ids = child.attrib["id"]
			m = re.search('v(\d+)', ids)
			if m:
				patient_text[m.group(1)] = grandchild.text
	numpy_G = nx.to_numpy_matrix(graph)
	Z = linkage(numpy_G, method='ward', metric='euclidean')
	T2 = scipy.cluster.hierarchy.to_tree( Z , rd=False )

	def add_node(node, parent ):
		newNode = dict( node_id=node.id, children=[] )
		parent["children"].append( newNode )
		if node.left: add_node( node.left, newNode )
		if node.right: add_node( node.right, newNode )

	d3Dendro = dict(children=[])
	add_node( T2, d3Dendro )

	def label_tree( n ):
		if len(n["children"]) == 0:
			if str(n["node_id"]) not in patient_text:
				text = "No text provided"
			else:
				text = patient_text[str(n["node_id"])]
			leafNames = [ n["node_id"] ]
		else:
			leafNames = reduce(lambda ls, c: ls + label_tree(c), n["children"], [])
		del n["node_id"]
		if len(leafNames) ==1:
			n["name"] = name = "-".join(sorted(map(str, leafNames)))
			n["txt"]= text
		else:
			n["txt"] = name = "-".join(sorted(map(str, leafNames)))
			n["name"] = ""
		return leafNames

	label_tree( d3Dendro["children"][0] )
	json.dump(d3Dendro, open(output, "w"), sort_keys=True, indent=4)
