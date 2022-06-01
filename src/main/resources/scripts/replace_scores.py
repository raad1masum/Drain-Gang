import pandas as pd
import json
import sys

data = json.loads(sys.argv[1])
data = json.loads(data)

dataPath = sys.argv[2] + "/" + sys.argv[3]

df = pd.DataFrame(columns=['Student ID', 'Scores'])

for key, value in data.items():
    df = df.append({'Student ID': key, 'Scores': value}, ignore_index=True)

df.to_csv(dataPath, index=False, sep=",", mode="w+")