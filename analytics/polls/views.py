import json

from django.shortcuts import render

from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt

from polls.data_parser import data_parsing
from polls.histogram_gen import histogram_data


@csrf_exempt
def index(request):
    print "Request received"
    # body_unicode = request.body
    #  print request.GET['tekst']

    # body_unicode = request.body.decode('utf-8')
    # body_data = json.loads(request.POST)
    #myDict = request.POST['param1']
    #fileData = request.POST['data']
    #fileDesc = request.POST['desc']
    #print myDict
    #print fileDesc
    #print fileData
    myDict = dict(request.POST.iterlists())
    #fileDesc = dict(request.POST.iterlists())
    print myDict
    print "------------\n\n\n\n\n\n"
    print request.POST['data']
    # data_csv  = body_unicode['txt']
    # data_csv = data_csv.split('\n')
    # data_csv = [x.split(",") for x in data_csv]
    # average, quantity = data_parsing(data_csv)
    # output = histogram_data(quantity)
    # print output
    print "ustawiam responsa"
    a = HttpResponse(json.dumps({"c": 0, "b": 0, "a": 0}, sort_keys=True), content_type="application/json")
    return a

def detail(request, question_id):
    return HttpResponse("You're looking at question %s." % question_id)

def results(request, question_id):
    response = "You're looking at the results of question %s."
    return HttpResponse(response % question_id)

def vote(request, question_id):
    return HttpResponse("You're voting on question %s." % question_id)
