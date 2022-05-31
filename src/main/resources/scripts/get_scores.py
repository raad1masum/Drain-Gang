import pandas as pd
import sys 

df = pd.read_csv(sys.argv[1])
print(df.set_index('Student ID').to_dict()['Scores'])
