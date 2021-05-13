# General data utilities

- https://drive.google.com/file/d/1t0NB3HvpO7vHy7Q0CscbZH8n2fTesNG9/view
- https://solr.apache.org/guide/8_5/graph-traversal.html#exporting-graphml-to-support-graph-visualization
- https://stackoverflow.com/questions/55130208/using-solr-graph-to-traverse-n-relationships
- 

## Taxonomy -- genome -> feature challenge


## Queries to test

- https://solr.apache.org/guide/6_6/graph-traversal.html
- https://stackoverflow.com/questions/55130208/using-solr-graph-to-traverse-n-relationships

- http://localhost:8983/solr/anl/select?q=%7B!graph%3Dgenome_id_s%2Bto%3Did%7Dgenome_name_s%3Aone

- https://www.youtube.com/watch?v=83c6GBaE1TE
- https://stackoverflow.com/questions/55130208/using-solr-graph-to-traverse-n-relationships
- https://solr.apache.org/guide/7_3/other-parsers.html


```
curl -H 'Content-Type: application/json' 'http://localhost:8983/solr/anl/update?commit=true' --data-binary '[
  {"id":"A","foo":  7, "out_edge":["1","9"],  "in_edge":["4","2"]  },
  {"id":"B","foo": 12, "out_edge":["3","6"],  "in_edge":["1"]      },
  {"id":"C","foo": 10, "out_edge":["5","2"],  "in_edge":["9"]      },
  {"id":"D","foo": 20, "out_edge":["4","7"],  "in_edge":["3","5"]  },
  {"id":"E","foo": 17, "out_edge":[],         "in_edge":["6"]      },
  {"id":"F","foo": 11, "out_edge":[],         "in_edge":["7"]      },
  {"id":"G","foo":  7, "out_edge":["8"],      "in_edge":[]         },
  {"id":"H","foo": 10, "out_edge":[],         "in_edge":["8"]      }
]'
```

# Various links and docs
- http://localhost:8983/solr/anl/select?q=%7B!graph%20from%3Din_edge%20to%3Dout_edge%7Did%3AF


- "{!graph to=in_edge from=out_edge}id:"g:1""
- http://localhost:8983/solr/anl/select?q=%7B!graph%20to%3Din_edge%20from%3Dout_edge%7Did%3A%22g%3A1%22


- http://localhost:8983/solr/anl/select?q=%7B!graph%20to%3Dgenome_id_s%20from%3Dparent_genome_id_ss%7Did%3A%22g%3A2%22&sort=type_s%20desc


http://localhost:8983/solr/anl/select?q=genome_name_s%3Aone&sort=id%20asc

