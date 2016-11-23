import json

import networkx as nx
import pickle
import matplotlib.pyplot as plt
import pylab
from networkx.readwrite import json_graph
from networkx.readwrite.json_graph import node_link_data, node_link_graph
from scipy.cluster.hierarchy import dendrogram, fcluster
from scipy.cluster.hierarchy import linkage

from data_parser import AverageMove


def make_graph():
    with open('average_tree.pickle','rb') as fn:
        average = pickle.load(fn)
    with open('qunatities.pickle','rb') as fn:
        quantity_nodes = pickle.load(fn)
    graph = nx.Graph()


    for key,val in average.items():
        if not graph.has_node("g_"+val.from_node):
            graph.add_node("g_"+val.from_node,quantity = quantity_nodes[val.from_node])
        if not graph.has_node("g_"+val.to_node):
            graph.add_node("g_"+val.to_node,quantity =  quantity_nodes[val.to_node])
        graph.add_edge("g_"+val.from_node,"g_"+val.to_node, quantity = val.quantity, time = val.time)
    return graph,quantity_nodes


def create_hierarchical(graph):
    numpy_G = nx.to_numpy_matrix(graph)
    Z = linkage(numpy_G, method='ward', metric='euclidean')
    plt.figure()
    plt.title('Hierarchical Clustering Dendrogram')
    plt.xlabel('sample index')
    plt.ylabel('distance')
    d = dendrogram(
        Z,
        # p=60,
        show_contracted=True,
        count_sort='descending',
        distance_sort='ascending',
        # truncate_mode= 'lastp',
        show_leaf_counts = True,
        leaf_rotation=90.,  # rotates the x axis labels
        leaf_font_size=8.,  # font size for the x axis labels
    )
    i = 0.5
    # while  max(fcluster(Z, i, 'distance'))
    clusters = fcluster(Z,2, 'distance')
    print clusters
    plt.show()

    nx.draw_networkx(graph, node_color=clusters)
    plt.draw()
    plt.show()

    print("END")

graph, nodes = make_graph()
#print graph.edges(data = True)
# nx.draw_networkx(graph)
# pylab.show()
graph = nx.convert_node_labels_to_integers(graph)
f =open('graph.pickle','wb')
pickle.dump(graph,f,protocol=2)
create_hierarchical(graph)
json_data =  json_graph.node_link_data(graph)

quantities = []
for key,val in nodes.items():
    quantities.append([int(key),val])
quantities.sort(key=lambda p : p[0])


import csv
with open('node_quantities.csv', 'wb') as csvfile:
    spamwriter = csv.writer(csvfile, delimiter=',',quotechar='|', quoting=csv.QUOTE_MINIMAL)
    for item in quantities:
        spamwriter.writerow(item)

print json_data
with open('graph.json', 'w+') as fn:
    json.dump(json_data, fn)
