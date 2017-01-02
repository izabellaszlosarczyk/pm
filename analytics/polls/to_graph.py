import json

import networkx as nx
import pickle
#import matplotlib.pyplot as plt
#import pylab
from networkx.readwrite import json_graph
import xml.etree.ElementTree as ET

def make_graph(input, average,quantity_nodes):
    tree = ET.fromstring(input)
    # print tree
    # root = tree.getroot()

    patient_text = {}
    for child in tree:
        for grandchild in child:
            import re
            ids = child.attrib["id"]
            m =  re.search('v(\d+)',ids)
            if m:
                patient_text[m.group(1)] = grandchild.text

    graph = nx.Graph()

    for key,val in average.items():
        if val.from_node not in patient_text:
            text = "No text provided"
        else:
            text = patient_text[str(val.from_node)]
        if not graph.has_node("g_"+val.from_node):
            graph.add_node("g_"+val.from_node,quantity = quantity_nodes[val.from_node], txt = text)

        if not graph.has_node("g_"+val.to_node):
            graph.add_node("g_"+val.to_node,quantity =  quantity_nodes[val.to_node],txt = text)
        graph.add_edge("g_"+val.from_node,"g_"+val.to_node, quantity = val.quantity, time = val.time)



    graph = nx.convert_node_labels_to_integers(graph)
    json_data =  json_graph.node_link_data(graph)

    # with open(output, 'w+') as fn:
    #     json.dump(json_data, fn)
    return graph