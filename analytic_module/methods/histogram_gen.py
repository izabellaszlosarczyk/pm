def histogram_data(quantity_nodes, output):
    quantities = []
    for key, val in quantity_nodes.items():
        quantities.append([int(key), val, "No text provided"])
    quantities.sort(key=lambda p: p[0])

    import csv

    with open(output, 'wb') as csvfile:
        spamwriter = csv.writer(csvfile, delimiter=',', quotechar='|', quoting=csv.QUOTE_MINIMAL)
        spamwriter.writerow(["node","quantity","text"])
        for item in quantities:
            spamwriter.writerow(item)