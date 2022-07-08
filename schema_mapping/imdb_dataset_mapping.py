import pandas as pd
import numpy as np
from constants import MOVIES_DATA_DIR
from utils import get_integrated_schema_target_df, to_xml

target_df = get_integrated_schema_target_df()

imdb_df = pd.read_csv(MOVIES_DATA_DIR.joinpath('imdb.csv'))

#Remove duplicates
imdb_df = imdb_df.drop_duplicates(subset=['title','year'])

#Replace None by nan in lanugage attribute
imdb_df = imdb_df['language'].replace('None', np.nan, inplace=True)

#Changing datatype of year to integer
imdb_df=imdb_df[pd.to_numeric(imdb_df['year'], errors='coerce').notnull()]
imdb_df['year']=imdb_df['year'].astype(int)

# rename columns name and remove underscore(_) from columns
imdb_df.columns = imdb_df.columns.str.replace('_', ' ')
imdb_df = imdb_df.rename(columns={'imdb title id': 'title id',
                                  'actors': 'actor names',
                                  'year': 'release year',
                                  'votes': 'imdb votes'}) # , 
                                  # 'date published': 'release date'})
# 'date published' field data needs to be transformed into 'release date' attribute.
# Or we can directly map the 'year' attribute in the dataframe to integrated schema.

# mapping 1-to-1 non-list attributes.
lower_copy_columns = ['title id','title', 'country', 'release year','duration','avg vote', 'imdb votes']
target_df[[col.lower() for col in lower_copy_columns]] = imdb_df[lower_copy_columns]
target_df['imdb score'] = imdb_df['avg vote']

# Attributes that have to be split into list[string]
imdb_df_map = ({'genre': 'genre',
                'language': 'language',
                'actor names': 'actor names',
                'production company': 'production company',
                'writer': 'writer',
                'director': 'director',
                'country': 'country'})
# Replacing the special character "&" in the dataframe
imdb_df[list(imdb_df_map.keys())] = imdb_df[imdb_df_map.values()].apply(
    lambda column: column.str.replace("&", "and"))
target_df[list(imdb_df_map.keys())] = imdb_df[imdb_df_map.values()].apply(
    lambda column: column.str.split(', '))

# make all budget into dollar and delete other currency and change datatype of budget
imdb_df['budget'] = imdb_df['budget'].astype(str).replace(r"[\a-zA-Z|]", np.nan)
target_df['budget'] = imdb_df['budget'].str.extract('(\d+)', expand=False)

target_df['source'] = 'imdb'

# copying dataframe index into the 
target_df['id'] = target_df.index

# creating a unique id, currently commenting this out based on the
# discussion done in the call.
target_df.id = target_df.id.apply(lambda id: 'imdb_' + str(id))

# getting the list of all columns for 'target_df' 
cols = target_df.columns.tolist()
cols = cols[-2:] + cols[:-2]
# print(cols)

# rearranging column order in the dataframe 
target_df = target_df[cols]

# replacing the spaces in columns to create xml compliant tags
target_df.columns = target_df.columns.str.replace(' ', '_')

# saving the 'target_df' dataset as integrated schema xml file
target_df.to_xml(MOVIES_DATA_DIR.joinpath('imdb.xml'))
