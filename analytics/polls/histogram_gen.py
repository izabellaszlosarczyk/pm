

def histogram_data(quantity_nodes,desc,items):
    quantities = []
    items = sorted(items)
    for key in sorted(items):
        if str(key) in desc:
            quantities.append(str(int(key)) + ',' +  str(quantity_nodes[str(key)]) + ','+ str(desc[str(key)])+"\n")
        else:
            quantities.append(str(int(key)) + ',' + str(quantity_nodes[str(key)]) + ',' +"No text provided\n")
    spamwriter = ["node,quantity,text\n"]
    for item in quantities:
        spamwriter.append(item)
    return spamwriter

def histogram_data_dec(quantity_nodes,desc,items,correct):
    q_nodes = {}
    print quantity_nodes
    for x in range(0, len(items)-1):
        columns = [ 1  if int(y[x])==1 else 0 for y in quantity_nodes]
        columns = sum(columns)
        q_nodes[items[x]] = columns
    quantities = []
    # items = sorted(items)
    for key in range(len(items)-1):
        if str(items[key]) in desc:
            quantities.append(str(items[key]) + ',' +  str(q_nodes[str(items[key])]) + ','+ str(desc[str(items[key])])+"\n")
        else:
            quantities.append(str(items[key]) + ',' + str(q_nodes[str(items[key])]) + ',' +"No text provided," +'\n')
    spamwriter = ["node,quantity,text\n"]
    for item in quantities:
        spamwriter.append(item)
    return spamwriter


def histogram_data_mul(quantity_nodes,desc,items,correct):
    q_nodes = {}
    print "nodes"
    print quantity_nodes
    for x in range(0, len(items)):
        columns = [int(y[x]) for y in quantity_nodes]
        columns = sum(columns)
        q_nodes[items[x]] = columns
    quantities = []
    items = sorted(items)
    for key in sorted(items):
        if desc[key] in correct:
            color = 'green'
        else:
            color = 'red'
        if str(key) in desc:
            quantities.append(str(key) + ',' +  str(q_nodes[str(key)]) + ','+ str(desc[str(key)])+',' + color +"\n")
        else:
            quantities.append(str(key) + ',' + str(q_nodes[str(key)]) + ',' +"No text provided," + color + '\n')
    spamwriter = ["node,quantity,text,color\n"]
    for item in quantities:
        spamwriter.append(item)
    return spamwriter
