import org.apache.log4j.Logger
import org.apache.solr.client.solrj.SolrClient
import org.apache.solr.client.solrj.SolrQuery
import org.apache.solr.client.solrj.impl.BaseHttpSolrClient
import org.apache.solr.client.solrj.impl.HttpSolrClient
import org.apache.solr.client.solrj.response.QueryResponse
import org.apache.solr.client.solrj.response.UpdateResponse
import org.apache.solr.common.SolrDocumentList
import org.apache.solr.common.SolrInputDocument

final Logger log = Logger.getLogger(this.class.name);
log.info "Starting ${this.class.name}..."

String host = 'localhost'
int port = 8983
String collection = 'anl'
String baseUrl = "http://$host:$port/solr/$collection"
boolean deleteAllDocs = true
int featureCount = 30

SolrClient solrClient
SolrQuery allDocsQuery = new SolrQuery("*:*")
try {
    log.info "\t\tUrl with collection: $baseUrl"
    solrClient = new HttpSolrClient.Builder(baseUrl).build();
    log.info "Client: $solrClient"
    if (deleteAllDocs) {
        QueryResponse rsp = solrClient.query(allDocsQuery)
        SolrDocumentList sdocs = rsp.getResults()
        int docCount = sdocs.getNumFound()
        log.warn "Deleting all ($docCount) docs in the test collection!!"
        UpdateResponse updateResponse = solrClient.deleteByQuery("*:*", 100)

        QueryResponse rsp2 = solrClient.query(allDocsQuery)
        SolrDocumentList sdocs2 = rsp2.getResults()
        int docCount2 = sdocs2.getNumFound()
        log.info "Deleting all ($docCount2) docs in the test collection!!"
        log.info "delete response: $updateResponse"
    }

} catch (BaseHttpSolrClient.RemoteSolrException rse) {
    String fubarMsg = rse.message
    if (fubarMsg.containsIgnoreCase('problem accessing')) {
        log.warn "Problem accessing ping for url given: '$baseUrl' -- check solr url and collection (if included) " +
                "-- Example:[http://localhost:8983/solr/mycollection]  "
    } else {
        log.warn "Unknown RemoteSolr exception: $rse"
    }
}

// FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en-US"), new RandomService());

List genomes = ['one', 'two', 'three', 'four', 'five'
                , 'six', 'seven', 'eight', 'nine', 'ten'
                , 'eleven', 'twelve', 'thirteen', 'fourteen', 'fifteen',
                'sixteen', 'seventeen', 'eighteen', 'nineteen', 'twenty'
]
int g = 0
genomes.each { String genome ->
    g++
    Date now = new Date()
    SolrDocumentList sdl = []
    String gid = "genome-$g"
    SolrInputDocument gdoc = new SolrInputDocument([id: "g:$g", genome_id_s: gid, genome_name_s: genome, common_name_t: "common genome name $g", genome_id_i:g,
                                                    type_s: 'genome', created_dtd: now, in_edge_i: [g], out_edge: []])
    sdl << gdoc
    log.debug "Genome: $genome"
//    for (f in 1..(g * 3000)) {
    for (f in 1..(g*g*g)) {
        SolrInputDocument fdoc = new SolrInputDocument([id: "g:$g f:$f", genome_id_s: gid, parent_genome_id_ss: gid, genome_name_s: genome, genome_id_i:g,
                                                        annotation_t: "feature test text here $f", feature_id_s: "feature-$f", created_dtd: now,
                                                        type_s: 'feature', in_edge: [], out_edge_i: [g]])
        sdl << fdoc
        log.debug "\t\tfeature: $f"
    }
    UpdateResponse updateResponse = solrClient.add(sdl, 500)
    log.info "$g) [$genome] added docs... (${sdl.size()}}"
}
solrClient.commit()

QueryResponse rsp = solrClient.query(allDocsQuery)
SolrDocumentList sdocs = rsp.getResults()
int docCount = sdocs.getNumFound()
log.warn "Doc count after generation of test data: ($docCount) docs in the test collection!!"


//UpdateResponse updateResponse = solrClient.commit()
//log.info "Update response: $updateResponse"

log.info "Done...?"
