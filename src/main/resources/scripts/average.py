import pandas as pd
import sys 

# Average all test scores from "Scores" column
df = pd.read_csv('example.csv') 

# if len(sys.argv) > 1:
#     print("Averaging " + sys.argv[1] + " test scores") 

print("The average of all scores are: " + str(df["Scores"].mean()))
