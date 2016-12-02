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

def normal_dendrogram():
    for i in range(0,10):
        print i
    root = ET.fromstring("<?xml version=\"1.0\"?>\
                          <data>\
                              <country name=\"Liechtenstein\">\
                                  <rank>1</rank>\
                                  <year>2008</year>\
                                  <gdppc>141100</gdppc>\
                                  <neighbor name=\"Austria\" direction=\"E\"/>\
                                  <neighbor name=\"Switzerland\" direction=\"W\"/>\
                              </country>\
                              <country name=\"Singapore\">\
                                  <rank>4</rank>\
                                  <year>2011</year>\
                                  <gdppc>59900</gdppc>\
                                  <neighbor name=\"Malaysia\" direction=\"N\"/>\
                              </country>\
                              <country name=\"Panama\">\
                                  <rank>68</rank>\
                                  <year>2011</year>\
                                  <gdppc>13600</gdppc>\
                                  <neighbor name=\"Costa Rica\" direction=\"W\"/>\
                                  <neighbor name=\"Colombia\" direction=\"E\"/>\
                              </country>\
                          </data>")
    print root

normal_dendogram()