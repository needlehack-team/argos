# argos

## Tips

### PostgreSQL

> Given that the `description` field is stored as a LOB, you need to use the `lo_get()` function to retrieve the content referenced by the OID. 
However, if you want the content to be interpreted as text rather than binary, you must use PostgreSQL's `convert_from()` function to convert it 
from binary (`BYTEA`) to text (`TEXT`), using the appropriate encoding (usually `UTF8`).

```sql
SELECT convert_from(lo_get(public.incidence.description), 'UTF8') AS desc_data
FROM public.incidence;
```