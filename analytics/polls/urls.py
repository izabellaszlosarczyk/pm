from django.conf.urls import url

from . import views

urlpatterns = [
    # ex: /polls/
    url(r'^$', views.index, name='index'),
    url(r'^dendo/$', views.dendo, name='dendo'),
    url(r'^straight/$', views.straight, name='straight'),
    url(r'^labbar/$', views.labbar, name='labbar'),
    url(r'^simbar/$', views.simbar, name='simbar'),
    url(r'^mulbar/$', views.mulbar, name='mulbar'),
    url(r'^graph/$', views.graph, name='graph'),
    url(r'^radial/$', views.radial, name='radial'),
    url(r'^apriori/$', views.apriori, name='apriori'),
    url(r'^decision/$', views.decision, name='decision')
]
