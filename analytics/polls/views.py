import json

from django.shortcuts import render

from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt
from networkx.readwrite import json_graph

from polls.data_parser import data_parsing
from polls.histogram_gen import histogram_data,histogram_data_mul,histogram_data_dec
from polls.to_graph import make_graph
from polls.dendro_normal import normal_dendrogram

from polls.radial_dendro import radial_dendrogram

from polls.decision import decision_tree
from polls.apriori import apriori_rules

from polls.data_parser import get_desc


@csrf_exempt
def index(request):
    print "Request received"
    return HttpResponse("Wrong url")


@csrf_exempt
def dendo(request):
    print "Request received"
    myDict = dict(request.POST.iterlists())
    data_csv  = myDict['data'][0]
    desc = myDict['desc'][0]
    data_csv = data_csv.split('\n')
    data_csv = [x.split(",") for x in data_csv]
    average, quantity = data_parsing(data_csv)
    graph_data = make_graph(desc, average, quantity)
    dendrogram = normal_dendrogram(desc,graph_data)
    return HttpResponse(dendrogram)


@csrf_exempt
def straight(request):
    print "Request received"
    myDict = dict(request.POST.iterlists())
    data_csv  = myDict['data'][0]
    desc = myDict['desc'][0]
    data_csv = data_csv.split('\n')
    data_csv = [x.split(",") for x in data_csv]
    average, quantity = data_parsing(data_csv)
    graph_data = make_graph(desc, average, quantity)
    json_data = json_graph.node_link_data(graph_data)
    return HttpResponse(json.dumps(json_data))


@csrf_exempt
def labbar(request):
    print "Request received"
    myDict = dict(request.POST.iterlists())
    data_csv  = myDict['data'][0]
    desc = get_desc(myDict['desc'][0])
    data_csv = data_csv.split('\n')
    data_csv = [x.split(",") for x in data_csv]
    average, quantity = data_parsing(data_csv)
    q = []
    for key, val in quantity.items():
        q.append(int(key))
    print quantity
    print desc
    print q
    output = histogram_data(quantity,desc,q)
    output = ''.join(map(str, output))
    return HttpResponse(json.dumps({"file":output}))


@csrf_exempt
def mulbar(request):
    print "Request received"
    myDict = dict(request.POST.iterlists())
    data_csv = myDict['data'][0]
    desc = myDict['desc'][0]
    import sys
    reload(sys)
    sys.setdefaultencoding('utf-8')
    print data_csv
    print "--------------------"
    data_csv = data_csv.split('\n')
    if data_csv[-1]=="":
        data_csv=data_csv[:-1]
    print data_csv
    print "--------------------"
    row, data_csv = data_csv[0], data_csv[1:]
    data_csv = [x.split(",") for x in data_csv]
    desc = json.loads(desc)
    description = {}
    rows = row.split(",")
    for idx,val in enumerate(rows):
        description[val] = desc['answers'][idx]
    output = histogram_data_mul(data_csv,description,rows,desc['correct'])
    output = ''.join(map(str, output))
    return HttpResponse(json.dumps({"file":output}))


@csrf_exempt
def radial(request):
    return HttpResponse(json.dumps({"file": "aa"}))
#
# myDict = dict(request.POST.iterlists())
# data_csv = myDict['data'][0]
# row, data_csv = data_csv[0], data_csv[1:]
# desc = myDict['desc'][0]
# desc = json.loads(desc)
# data_csv = data_csv.split('\n')
@csrf_exempt
def simbar(request):
    print "Request received"
    myDict = dict(request.POST.iterlists())
    data_csv = myDict['data'][0]
    desc = myDict['desc'][0]
    import sys
    reload(sys)
    sys.setdefaultencoding('utf-8')
    data_csv = data_csv.split('\n')
    if data_csv[-1]=="":
        data_csv=data_csv[:-1]
    print data_csv
    print "--------------------"
    row, data_csv = data_csv[0], data_csv[1:]
    data_csv = [x.split(",") for x in data_csv]
    desc = json.loads(desc)
    description = {}
    rows = row.split(",")
    for idx,val in enumerate(rows):
        if idx != len(desc['examination']):
            description[val] = desc['examination'][idx]
    data_csv = [x[:-1] for x in data_csv]
    output = histogram_data_dec(data_csv,description,rows,desc['correct'])
    output = ''.join(map(str, output))
    return HttpResponse(json.dumps({"file":output}))

@csrf_exempt
def graph(request):
    print "Request received"
    myDict = dict(request.POST.iterlists())
    data_csv  = myDict['data'][0]
    desc = myDict['desc'][0]
    data_csv = data_csv.split('\n')
    data_csv = [x.split(",") for x in data_csv]
    average, quantity = data_parsing(data_csv)
    graph_data = make_graph(desc, average, quantity)
    json_data = json_graph.node_link_data(graph_data)
    return HttpResponse(json.dumps(json_data))

@csrf_exempt
def radial(request):
    print "Request received"
    myDict = dict(request.POST.iterlists())
    data_csv  = myDict['data'][0]
    desc = myDict['desc'][0]
    data_csv = data_csv.split('\n')
    data_csv = [x.split(",") for x in data_csv]
    average, quantity = data_parsing(data_csv)
    graph_data = make_graph(desc, average, quantity)
    dendrogram  = radial_dendrogram(desc, graph_data)
    output = ''.join(map(str, dendrogram))
    return HttpResponse(json.dumps({"file":output}))

@csrf_exempt
def decision(request):
    print "Request received"
    myDict = dict(request.POST.iterlists())
    data  = myDict['data'][0]
    desc = myDict['desc'][0]
    output = decision_tree(data,desc)
    json_output = json.dumps({"file":output})
    print json_output
    print {"file":output}
    h = HttpResponse(json_output)
    return h



@csrf_exempt
def apriori(request):
    print "Request received"
    myDict = dict(request.POST.iterlists())
    data  = myDict['data'][0]
    desc = myDict['desc'][0]
    output = apriori_rules(data,desc)
    return HttpResponse(json.dumps({"file": output}))
#
# f = open('/home/zuchens/pmoves_sample_data/multiple_choice_description.json','rb')
# desc = f.read()
#
# f = open('/home/zuchens/pmoves_sample_data/multiple_choice_data.csv', 'rb')
# data = f.read()
#
# mulbar(data,desc)
