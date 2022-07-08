import pandas as pd
import numpy as np
from constants import MOVIES_DATA_DIR
from utils import get_integrated_schema_target_df, to_xml

# reading the streaming dataset into the dataframe
target_df = get_integrated_schema_target_df()
source_df = pd.read_csv(MOVIES_DATA_DIR.joinpath('streaming.csv'))

# lowercase the columns
source_df.columns = source_df.columns.str.lower()

# creating a unique id, currently commenting this out based on the
# discussion done in the call.
target_df.id = source_df.id.apply(lambda id: 'streaming_' + str(int(id)-1))

# direct mapping for columns
simple_move_map = {
    'title' : 'title',
    'duration' : 'runtime',
    'release year' : 'year',
    'imdb score' : 'imdb',
    'disney flag' : 'disney+',
    'hulu flag' : 'hulu',
    'netflix flag' : 'netflix',
    'prime video flag' : 'prime video'
    }
target_df[list(simple_move_map.keys())] = source_df[simple_move_map.values()]

# mapping list(string) datatype columns
list_move_map = {
    'genre' : 'genres',
    'director' : 'directors',
    'language' : 'language',
    'country' : 'country'
}
target_df[list(list_move_map.keys())] = source_df[list_move_map.values()].apply(lambda c: c.str.split(','))

# defining the source of the dataset
target_df['source'] = 'streaming'

# transforming the final column names & checking the dataframe shape
target_df.columns = target_df.columns.str.replace(' ', '_')

# getting the list of all columns for 'target_df' 
cols = target_df.columns.tolist()
cols = cols[-2:] + cols[:-2]
# print(cols)

# creating new `target_df` with rearranged columns
target_df = target_df[cols]

# Replacing the special character in the `target_df` dataframe
target_df['title'] = target_df.title.str.replace("&", "and")

# saving the 'target_df' dataset as integrated schema xml file
target_df.to_xml(MOVIES_DATA_DIR.joinpath('streaming.xml'))
