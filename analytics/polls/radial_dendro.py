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

def radial_dendrogram(xml_input,graph):
	tree = ET.fromstring(xml_input)

	patient_text = {}
	for child in tree:
		# print child.attrib["id"]
		for grandchild in child:
			import re

			ids = child.attrib["id"]
			m = re.search('v(\d+)', ids)
			if m:
				patient_text[m.group(1)] = grandchild.text

	numpy_G = nx.to_numpy_matrix(graph)
	Z = linkage(numpy_G, method='ward', metric='euclidean')
	T2 = scipy.cluster.hierarchy.to_tree( Z , rd=False )

	# scipy.cluster.hierarchy.dendrogram(clusters, labels=labels, orientation='right')
	# plt.savefig("scipy-dendrogram.png")

	# Create a nested dictionary from the ClusterNode's returned by SciPy
	def add_node(node, parent ):
		# First create the new node and append it to its parent's children
		newNode = dict( node_id=node.id, children=[] )
		parent["children"].append( newNode )

		# Recursively add the current node's children
		if node.left: add_node( node.left, newNode )
		if node.right: add_node( node.right, newNode )
	radial_dendrogram = []
	# Initialize nested dictionary for d3, then recursively iterate through tree
	def child(tree,parent):

		if len(tree['children'])==0:
			return [parent+'.'+str(tree['node_id'])]
		# print [tree['node_id'],0], [child(x) for x in tree['children']]
		pr = [[parent + '.' + str(tree['node_id'])]]
		pr.extend([child(x, parent + '.' + str(tree['node_id'])) for x in tree['children']])
		pr = [item for sublist in pr for item in sublist]
		return pr

	d3Dendro = dict(children=[])
	add_node( T2, d3Dendro )
	tree = child(d3Dendro['children'][0],'')

	for x in range(0,len(tree)):
		tree[x] = tree[x]+',0\n'
	names = ["id,value\n"]
	names.extend(tree)
	return names
