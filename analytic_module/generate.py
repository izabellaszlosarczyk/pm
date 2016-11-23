from methods.data_parser import data_parsing
from methods.dendro_normal import normal_dendrogram
from methods.histogram_gen import histogram_data
from methods.radial_dendro import radial_dendrogram
from methods.to_graph import make_graph
from methods.xml_parser import xml_to_json

xml_to_json("input_data/patient_data.xml", "generated_data/patient_data.json")
average, quantities = data_parsing("input_data/data.csv")

histogram_data(quantities,"generated_data/bar_chart.csv")

graph_data = make_graph("input_data/patient_data.xml","generated_data/graph.json",average,quantities)

radial_dendrogram("input_data/patient_data.xml",graph_data,"generated_data/radial_dendrogram.csv")

normal_dendrogram("input_data/patient_data.xml",graph_data,"generated_data/dendrogram.json")