import pandas as pd

df = pd.read_csv('breed_names.csv')

print('{')
for key, value in df.iterrows():
    a = value['eng']
    b = value['cht']
    print(f'"{a}": "{b}",')

print('}')
