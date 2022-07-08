import pandas as pd

from constants import MOVIES_DATA_DIR

# declaring the target_df dataframe structure definer function
def get_integrated_schema_target_df():
    """
    Generates empty DataFrame with all columns that are supposed to be filled with values from the Movie datasets
    """
    integrated_schema = pd.read_csv(MOVIES_DATA_DIR.joinpath('integrated_schema.csv'), sep=';').drop(
        columns='Class name')
    integrated_schema.columns = ['name', 'type', 'dataset']
    integrated_schema = integrated_schema.apply(lambda column: column.str.split(', '))
    integrated_schema = integrated_schema.explode(column=['type', 'name'])
    return pd.DataFrame(columns=list(integrated_schema['name']) + ['source'])
	

# declaring and overriding the 'to_xml' in pandas
def to_xml(df, filename=None, mode='w+'):
    """
    Generates the integrated schema xml file corresponding to each dataframe that is parsed as input
    """
    plural_dict = {'country': 'countries','genre':'genres','actor_names':'actors',
               'director':'directors','language': 'languages','production_company':'production_companies',
               'writer':'writers','tags':'tags', 'country_availability':'countries_availability'}
    def row_to_xml(row):
        xml = ['    <movie>']
        for i, col_name in enumerate(row.index):
            # case for list columns
            if isinstance(row.iloc[i], list):
                parent_tag = plural_dict[col_name]
                xml.append(f'        <{parent_tag}>')
                for item in row.iloc[i]:
                    xml.append(f'            <{col_name}>{item}</{col_name}>')
                xml.append(f'        </{parent_tag}>')
            # case for regular columns
            elif not pd.isna(row.iloc[i]): # skip nan
                xml.append(f'        <{col_name}>{row.iloc[i]}</{col_name}>')
        xml.append('    </movie>')
        return '\n'.join(xml)
    res = '\n'.join(df.apply(row_to_xml, axis=1))
    res = f"<?xml version='1.0' encoding='utf-8'?>\n<movies>\n{res}\n</movies>"

    if filename is None:
        return res
    with open(filename, mode) as f:
        f.write(res)

pd.DataFrame.to_xml = to_xml
