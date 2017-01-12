import pickle

__author__ = 'Zuch'
import csv
from anytree import Node, RenderTree, NodeMixin
import networkx as nx
import xml.etree.ElementTree as ET
class Move(object):
    pass

class TreeMove(Move,NodeMixin):
    def __init__(self,session,number,time,parent= None):
        self.session = session
        self.number = number
        self.time =  time
        self.parent = parent

class AverageMove(object):
    def __init__(self, from_node, to_node,time):
        self.quantity = 1
        self.from_node = from_node
        self.to_node = to_node
        self.time = time

    def add(self, time):
        self.quantity+=1
        self.time+=time

    def eval(self):
        self.time = self.time/self.quantity

quantity_nodes = {}
def data_parsing(rows):
    parent = None
    rows = rows[1:]
    users_movements = []
    time_stamp = int(rows[0][5])
    movement = TreeMove(rows[0][2], rows[0][4], 0)
    for idx in range(1,len(rows)-1):
        if rows[idx][4] not in quantity_nodes:
            quantity_nodes[rows[idx][4]] = 0
        else:
            quantity_nodes[rows[idx][4]]+=1
        if rows[idx][3] == "1":
            if rows[idx][2] == rows[idx - 1][2]:
                parent = movement
                movement = TreeMove(rows[idx][2], rows[idx][4], int(rows[idx][5]) - parent.time - time_stamp,
                                parent=parent)
            else:
                time_stamp = int(rows[idx][5])
                movement = TreeMove(rows[idx][2], rows[idx][4], 0)
                root = movement if movement.root is None else movement.root
                users_movements.append(root)



    average = {}
    for user in users_movements:
        for pre, fill, node in RenderTree(user):
            if node.children:
                movement = str(node.number + "-" +node.children[0].number)
                if movement not in average:
                    average[movement] = AverageMove(node.number,node.children[0].number,node.time)
                else:
                    average[movement].add(node.time)
    return average, quantity_nodes


def get_desc(desc):
    tree = ET.fromstring(desc)
    # root = tree.getroot()
    patient_text = {}
    for child in tree:
        for grandchild in child:
            import re
            ids = child.attrib["id"]
            m = re.search('v(\d+)', ids)
            if m:
                patient_text[m.group(1)] = grandchild.text
    return patient_text