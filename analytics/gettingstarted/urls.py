from django.conf.urls import include, url

from django.contrib import admin
admin.autodiscover()

import hello.views
import polls.views

# Examples:
# url(r'^$', 'gettingstarted.views.home', name='home'),
# url(r'^blog/', include('blog.urls')),

urlpatterns = [
    url(r'^$', polls.views.index, name='index'),
    url(r'^dendo/$', polls.views.dendo, name='dendo'),
    url(r'^straight/$', polls.views.straight, name='straight'),
    url(r'^labbar/$', polls.views.labbar, name='labbar'),
    url(r'^simbar/$', polls.views.simbar, name='simbar'),
    url(r'^mulbar/$', polls.views.mulbar, name='mulbar'),
    url(r'^graph/$', polls.views.graph, name='graph'),
    url(r'^radial/$', polls.views.radial, name='radial'),
    url(r'^apriori/$', polls.views.apriori, name='apriori'),
    url(r'^decision/$', polls.views.decision, name='decision')
]
