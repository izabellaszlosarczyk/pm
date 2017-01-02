import json
import xml.etree.ElementTree as ET

def xml_to_json(input, output):
    tree = ET.parse(input)
    root = tree.getroot()

    patient_text = {}
    for child in root:
        for grandchild in child:
            patient_text[child.attrib["id"]] = grandchild.text

    f = open(output,"w+")
    json.dump(patient_text,f)

