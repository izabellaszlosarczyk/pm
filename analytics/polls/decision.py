# -*- coding: utf-8 -*-
from sklearn import tree
import sys
import base64

import json
import csv
def decision_tree(data,desc_data):
    if isinstance(data, unicode):
        data = data.encode("utf-8")
        desc_data = desc_data.encode("utf-8")
    reload(sys)
    sys.setdefaultencoding('utf8')
    desc_data = json.loads(desc_data)
    rows = data.split('\n')
    if rows[-1] == '' :
        rows = rows[:-1]
    rows = [x.split(",") for x in rows ]
    desc = rows[0]
    data = rows[1:]
    clf = tree.DecisionTreeClassifier()
    features = [x[:-1] for x in data ]
    classes = [x[-1] for x in data]
    class_names = []
    for x in range(0,len(classes)):
        if classes[x] not in class_names:
            class_names.append(classes[x])
        classes[x] = class_names.index(classes[x])
    print features
    print classes
    print desc[:-1]
    clf = clf.fit(features,classes)
    import pydotplus
    dot_data = tree.export_graphviz(clf, out_file=None,
                            max_depth=6,
                             feature_names=desc[:-1],
                             class_names=class_names,
                             filled=True, rounded=False,
                             special_characters=True)
    graph = pydotplus.graph_from_dot_data(dot_data)
    img = graph.create_png()
    graph.write_png("file_3.png")
    with open("file_3.png", "r+",) as imageFile:
        f = imageFile.read()
    desc_data["file"] = f.encode('base64')
    desc_data2 = json.dumps(desc_data)
    fh = open("imageToSave.png", "wb")
    fh.write(desc_data["file"].decode('base64'))
    fh.close()
    return desc_data

#desc = {"a":"b"}
#data = "1,1,1\n2,2,2"
# f = open('/home/zuchens/Desktop/pm/analytic_module/input_data/student_decision.json','r+')
# desc = f.read()
#
# f = open('/home/zuchens/Desktop/pm/analytic_module/input_data/decision_student.csv', 'r+')
# data = f.read()
#
#decision_tree(data,desc)