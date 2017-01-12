from apyori import apriori
import json
# min_support
# min_confidence
# min_lift
# max_length

def apriori_rules(data,desc_json):
    desc_json = json.loads(desc_json)
    import sys
    reload(sys)
    sys.setdefaultencoding('utf-8')
    rows = data.split('\n')
    if rows[-1]=="":
        rows=rows[:-1]

    rows = [x.split(",") for x in rows ]
    desc = rows[0]
    data = rows[1:]
    dataset = []
    for row in data:
        set  = []
        for item in range (0,len(desc)):
            if int(row[item])==0:
                set.append(desc_json['answers'][item]+"=0")
            else:
                set.append(desc_json['answers'][item]+"=1")
        dataset.append(set)
    results = list(apriori(dataset,min_support=0.5,min_confidence=0.85))
    ordered = [r.ordered_statistics for r in results]
    order  = [item for sublist in ordered for item in sublist]
    rules = []
    for item in order:
        if len(item.items_base)>0:
            rules.append({'items_base':[x for x in item.items_base], 'items_add':[x for x in item.items_add],'confidence':item.confidence,'lift':item.lift})
    desc_json['rules']=rules
    return desc_json

#
# f = open("/home/zuchens/pmoves_sample_data/multiple_choice_description.json",'rb')
# desc = f.read()
#
# f = open("/home/zuchens/pmoves_sample_data/multiple_choice_data.csv", 'rb')
# data = f.read()
#
# print apriori_rules(data,desc)
