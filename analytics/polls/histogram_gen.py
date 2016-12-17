

def histogram_data(quantity_nodes):
    quantities = []
    for key, val in quantity_nodes.items():
        quantities.append(str(int(key)) + ',' +  str(val) + ','+ "No text provided\n")
    quantities.sort(key=lambda p: p[0])

    # import csv
    #
    # with open(output, 'wb') as csvfile:
    #     spamwriter = csv.writer(csvfile, delimiter=',', quotechar='|', quoting=csv.QUOTE_MINIMAL)

        # spamwriter.writerow(["node","quantity","text"])
        # for item in quantities:
        #     spamwriter.writerow(item)
    spamwriter = ["node,quantity,text\n"]
    for item in quantities:
        spamwriter.append(item)
    return spamwriter