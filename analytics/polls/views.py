import json

from django.shortcuts import render

from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt
from networkx.readwrite import json_graph

from polls.data_parser import data_parsing
from polls.histogram_gen import histogram_data
from polls.to_graph import make_graph
from polls.dendro_normal import normal_dendrogram

from polls.radial_dendro import radial_dendrogram

from polls.decision import decision_tree
from polls.apriori import apriori_rules

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
    return HttpResponse(json_data)


@csrf_exempt
def bar(request):
    print "Request received"
    myDict = dict(request.POST.iterlists())
    data_csv  = myDict['data'][0]
    desc = myDict['desc'][0]
    data_csv = data_csv.split('\n')
    data_csv = [x.split(",") for x in data_csv]
    average, quantity = data_parsing(data_csv)
    output = histogram_data(quantity)
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
    return HttpResponse(json.dumps({"file":output}))

@csrf_exempt
def apriori(request):
    print "Request received"
    myDict = dict(request.POST.iterlists())
    data  = myDict['data'][0]
    desc = myDict['desc'][0]
    output = apriori_rules(data,desc)
    return HttpResponse(json.dumps({"file":output}))

def detail(request, question_id):
    return HttpResponse("You're looking at question %s." % question_id)

def results(request, question_id):
    response = "You're looking at the results of question %s."
    return HttpResponse(response % question_id)

def vote(request, question_id):
    return HttpResponse("You're voting on question %s." % question_id)
