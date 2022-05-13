import matplotlib.pyplot as plt 
import pandas as pd
import seaborn as sns

# Print distribution graph of all data from "Scores" column
df = pd.read_csv('example.csv') 
scores = df["Scores"]

dev = list(scores)
print('Standard deviation ' + str(dev))
sns.displot(df["Scores"])
plt.show(block=True)
