from datetime import datetime
import pandas as pd
import sys 
import csv

# Path to CSV file is encapsulated in the argument to the script
df = pd.read_csv(sys.argv[1])
l = pd.read_csv(sys.argv[2])

print("replacing")
print(l.set_index('Student ID').to_dict()['Scores'])
print("with")
print(df.set_index('Student ID').to_dict()['Scores'])

a = df.set_index('Student ID').to_dict()['Scores']

final = []
with open(sys.argv[2], 'r') as csvfile:
    datareader = csv.reader(csvfile)
    for row in datareader:
        if row[0] != 'Email':      
            for key, value in a.items():
                if row[1] == key:
                    row[4] = value;
        final.append(row)

new = sys.argv[2] + datetime.now().strftime("%Y_%m_%d_%H_%M_%S")

temp = new

myfile = csv.writer(open(new, "w", newline='')) 
for row in final:
    myfile.writerow(row)


dfnew = pd.read_csv(temp)
print(dfnew.set_index('Student ID').to_dict()['Scores'])


