from sklearn import tree
from sklearn import tree
from sklearn.datasets import load_iris

import json
import csv
def decision_tree(data,desc_data):
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
    clf = clf.fit(features,classes)
    import pydotplus
    from IPython.display import Image
    dot_data = tree.export_graphviz(clf, out_file=None,
                            max_depth=6,
                             feature_names=desc[:-1],
                             class_names=class_names,
                             filled=True, rounded=False,
                             special_characters=True)
    graph = pydotplus.graph_from_dot_data(dot_data)
    img = Image(graph.create_png())
    graph.write_png("file.png")
    with open("file.png", "rb") as imageFile:
        f = imageFile.read()
    desc_data["file"] = f.encode('base64')
    # js = json.dumps({"file":f.encode('base64')})
    # print desc_data
    return desc_data


# f = open('/home/zuchens/Desktop/pm/analytic_module/input_data/decision.json','rb')
# desc = f.read()
#
# f = open('/home/zuchens/Desktop/pm/analytic_module/input_data/decision_data.csv', 'rb')
# data = f.read()
#
# decision_tree(data,desc)
