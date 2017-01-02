from django.conf.urls import url

from . import views

urlpatterns = [
    # ex: /polls/
    url(r'^$', views.index, name='index'),
    url(r'^dendo/$', views.dendo, name='dendo'),
    url(r'^straight/$', views.straight, name='straight'),
    url(r'^bar/$', views.bar, name='bar'),
    url(r'^graph/$', views.graph, name='graph'),
    url(r'^radial/$', views.radial, name='radial'),
    url(r'^apriori/$', views.apriori, name='apriori'),
    url(r'^decision/$', views.decision, name='decision'),
    # ex: /polls/5/
    url(r'^(?P<question_id>[0-9]+)/$', views.detail, name='detail'),
    # ex: /polls/5/results/
    url(r'^(?P<question_id>[0-9]+)/results/$', views.results, name='results'),
    # ex: /polls/5/vote/
    url(r'^(?P<question_id>[0-9]+)/vote/$', views.vote, name='vote'),
]
