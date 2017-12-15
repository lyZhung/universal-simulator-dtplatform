import com.dtyunxi.dtplatform.model.Config;
import com.dtyunxi.dtplatform.model.Model;
import com.dtyunxi.dtplatform.utils.*;
import com.mifmif.common.regex.Generex;
import kafka.javaapi.producer.Producer;
import org.apache.hadoop.fs.FileSystem;
import org.bson.*;

import java.io.BufferedWriter;
import java.util.*;

public class Test {

    @org.junit.Test
    public void testJosnByDocument(){
        String json = JsonUtils.getJson("F:\\source\\universal-simulator-dtplatform\\src\\main\\resources\\simconfig.json");
        Document document = Document.parse(json);
        Document postgres_internal=null;
       //判断是否继续为Document对象，如果是，表示该key下又是一个document对象
       if (document.get("POSTGRES_INTERNAL") instanceof Document){
           postgres_internal = (Document)document.get("POSTGRES_INTERNAL");
           System.out.println("yes");
       }else {
           System.out.println("no");
       }
        if (postgres_internal.get("HOST") instanceof Document){
            System.out.println("yes");
        }else {
            System.out.println("no");
        }
        System.out.println(postgres_internal.get("HOST"));
        System.out.println(postgres_internal.get("PASSWORD"));
    }

    @org.junit.Test
    public void testJsonByDocument(){
        String json = JsonUtils.getJson("F:\\source\\universal-simulator-dtplatform\\src\\main\\resources\\test1.json");
        Document.parse(json);
        Document document = Document.parse(json);
        Document result = JsonUtils.getJsonByRegex(document);
        System.out.println(result.toJson());
    }

    @org.junit.Test
    public void testJsonArrayByDocument1(){
        String json = JsonUtils.getJson("F:\\source\\universal-simulator-dtplatform\\src\\main\\resources\\test2.json");
        BsonArray bsonArray = BsonArray.parse(json);
        for (BsonValue bsonValue : bsonArray) {
            BsonDocument bsonDocument = bsonValue.asDocument();
            System.out.println(bsonDocument.toJson());
        }
    }

    @org.junit.Test
    public void testJsonArrayByDocument2(){
        String json = JsonUtils.getJson("F:\\source\\universal-simulator-dtplatform\\src\\main\\resources\\test3.json");

        BsonArray bsonArray = BsonArray.parse(json);


        for (BsonValue bsonValue : bsonArray) {
            BsonDocument bsonDocument = bsonValue.asDocument();
            System.out.println(bsonDocument.toJson());
        }
    }

    @org.junit.Test
    public void testJsonArrayByDocument3(){
        String json = JsonUtils.getJson("F:\\source\\universal-simulator-dtplatform\\src\\main\\resources\\test1.json");
        Document.parse(json);
        Document document = Document.parse(json);
        Document result = JsonUtils.getJsonByRegex(document);
        System.out.println(result.toJson());
    }

    @org.junit.Test
    public void testJsonArrayByDocument4(){
        String data = JsonUtils.getJson("F:\\source\\universal-simulator-dtplatform\\src\\main\\resources\\test1.json");
        try{
            Document document = Document.parse(data);
            Document result = JsonUtils.getJsonByRegex(document);
            System.out.println(result.toJson());
        }catch (BsonInvalidOperationException e){
            BsonArray result = new BsonArray();
            BsonArray bsonArray= BsonArray.parse(data);
            for (BsonValue bsonValue : bsonArray) {
                String json = bsonValue.asDocument().toJson();
                Document documentByRegex = JsonUtils.getJsonByRegex(Document.parse(json));
                result.add(BsonDocument.parse(documentByRegex.toJson()));
            }
            System.out.println(StringUtils.getSubstring(result.toString()));
        }
    }

    @org.junit.Test
    public void testAppenByDocument(){
        Document document1 = new Document();
        document1.append("name","zgh");
        document1.append("age","12");
        Document document2 = new Document();
        document2.append("student",document1);

        Document document3 = new Document();
        document3.append("name","zgh");
        document3.append("age","12");
        Document document4 = new Document();
        document4.append("student",document1);

        ArrayList<Document> documents = new ArrayList<Document>();
        documents.add(document2);
        documents.add(document4);

        Document document5 = new Document();
        document5.append("document",documents);
        String s = document5.toJson();
        System.out.println(s);

        if (document5.get("document") instanceof List){

            System.out.println("yes");
        }


    }

    @org.junit.Test
    public void testListByDocument(){
        List<Object> list = new ArrayList<Object>();
        list.add("zhangsan");
        list.add("lisi");
        list.add(100L);
        Document document = new Document();
        document.append("student",list);

        if (document.get("student") instanceof  List){
            System.out.println("yes");

        }

        System.out.println(document.toJson());

    }

    @org.junit.Test
    public void testGenerex(){

       // String regex="[a-zA-Z0-9]{5}\\.[0-9]{5}\\.[0-9]{3}";
        String regex="true|false";
        Generex generex = new Generex(regex);

        String random = generex.random();

        System.out.println(random);
    }

    @org.junit.Test
    public void testSplit(){
        String str="zhang";
        String s = str.split(",")[0];
        System.out.println(s);
    }

    @org.junit.Test
    public void testKafkaUtils(){
       /* String configFile="F:\\source\\universal-simulator-dtplatform\\src\\main\\java\\com\\dtyunxi\\dtplatform\\config\\config.json";
        Config config = ConfigUtils.getConfig(configFile);
        System.out.println(config.getTopics()[0]);
        System.out.println(config.getMetadata_broker_list());
        Producer<String, String> producer = KafkaUtils.getProducer(config);
        String log="{ \"version\" : [\"1\", \"2\", \"3\"], \"disable_existing_loggers\" : \"true\", \"formatters\" : { \"standard\" : { \"format\" : \"m6XdN.55080.410\" }, \"logstash\" : { \"()\" : \"logstash_formatter.LogstashFormatter\" }, \"elogging_format\" : { \"format\" : \"9IC.695.480\" } }, \"handlers\" : { \"console\" : { \"class\" : \"logging.StreamHandler\", \"level\" : \"DEBUG\", \"formatter\" : \"elogging_format\" }, \"info_file_handler\" : { \"class\" : \"logging.handlers.RotatingFileHandler\", \"level\" : \"ERROR\", \"formatter\" : \"elogging_format\", \"filename\" : \"/var/log/applog/info.log\" }, \"debug_file_handler\" : { \"class\" : \"logging.handlers.RotatingFileHandler\", \"level\" : \"INFO\", \"formatter\" : \"elogging_format\", \"filename\" : \"/var/log/applog/debug.log\" }, \"error_file_handler\" : { \"class\" : \"logging.handlers.RotatingFileHandler\", \"level\" : \"WARN\", \"formatter\" : \"elogging_format\", \"filename\" : \"/var/log/applog/errors\" }, \"kafka_handler\" : { \"class\" : \"python_kafka_logging.KafkaHandler.KafkaLoggingHandler\", \"level\" : \"DEBUG\", \"formatter\" : \"logstash\", \"hosts_list\" : \"192.168.33.21:6667\", \"topic\" : \"dtyunxi_elog\" } }, \"loggers\" : { \"elog\" : { \"level\" : \"WARN\", \"handlers\" : [\"console\", \"debug_file_handler\", \"kafka_handler\"], \"propagate\" : \"no\" }, \"root\" : { \"level\" : \"WARN\", \"handlers\" : [\"console\", \"debug_file_handler\", \"kafka_handler\"] } }, \"path\" : [{ \"event\" : \"bookedTrip\" }, { \"event\" : \"bookedTripStart\" }, { \"event\" : \"bookedTripEnd\" }] }";
        for (int i = 0; i <10 ; i++) {
            KafkaUtils.sendMess("test",log,producer);
        }*/
    }

    @org.junit.Test
    public void testLocalFile(){
        /*String configFile="F:\\source\\universal-simulator-dtplatform\\src\\main\\java\\com\\dtyunxi\\dtplatform\\config\\config.json";
        Config config = ConfigUtils.getConfig(configFile);
        String[] localPaths = config.getLocalPaths();
        String log="{ \"version\" : [\"1\", \"2\", \"3\"], \"disable_existing_loggers\" : \"true\", \"formatters\" : { \"standard\" : { \"format\" : \"m6XdN.55080.410\" }, \"logstash\" : { \"()\" : \"logstash_formatter.LogstashFormatter\" }, \"elogging_format\" : { \"format\" : \"9IC.695.480\" } }, \"handlers\" : { \"console\" : { \"class\" : \"logging.StreamHandler\", \"level\" : \"DEBUG\", \"formatter\" : \"elogging_format\" }, \"info_file_handler\" : { \"class\" : \"logging.handlers.RotatingFileHandler\", \"level\" : \"ERROR\", \"formatter\" : \"elogging_format\", \"filename\" : \"/var/log/applog/info.log\" }, \"debug_file_handler\" : { \"class\" : \"logging.handlers.RotatingFileHandler\", \"level\" : \"INFO\", \"formatter\" : \"elogging_format\", \"filename\" : \"/var/log/applog/debug.log\" }, \"error_file_handler\" : { \"class\" : \"logging.handlers.RotatingFileHandler\", \"level\" : \"WARN\", \"formatter\" : \"elogging_format\", \"filename\" : \"/var/log/applog/errors\" }, \"kafka_handler\" : { \"class\" : \"python_kafka_logging.KafkaHandler.KafkaLoggingHandler\", \"level\" : \"DEBUG\", \"formatter\" : \"logstash\", \"hosts_list\" : \"192.168.33.21:6667\", \"topic\" : \"dtyunxi_elog\" } }, \"loggers\" : { \"elog\" : { \"level\" : \"WARN\", \"handlers\" : [\"console\", \"debug_file_handler\", \"kafka_handler\"], \"propagate\" : \"no\" }, \"root\" : { \"level\" : \"WARN\", \"handlers\" : [\"console\", \"debug_file_handler\", \"kafka_handler\"] } }, \"path\" : [{ \"event\" : \"bookedTrip\" }, { \"event\" : \"bookedTripStart\" }, { \"event\" : \"bookedTripEnd\" }] }";
        for (int i = 0; i <10 ; i++) {
            FileUtils.writeMess(log,localPaths[0]);

        }*/
    }

    @org.junit.Test
    public void testDocumentArrayValue(){
        String json="{\"plannedRoute\" : [[5.067700, 55.133644],[25.034109, 55.105835],[24.995898, 55.094938]]}";
        Document document = Document.parse(json);
        System.out.println(document.get("plannedRoute"));
        Object plannedRoute = document.get("plannedRoute");
        for (Object o : ((List) plannedRoute)) {
            System.out.println(o);
            for (Object o1 : ((List) o)) {
                System.out.println(o1);
            }
        }
    }

    @org.junit.Test
    public void testJsonArrayByDocument5(){
        String data = JsonUtils.getJson("F:\\source\\universal-simulator-dtplatform\\src\\main\\resources\\test5.json");
        try{
            Document document = Document.parse(data);
            Document result = JsonUtils.getJsonArrayByRegex(document);
            System.out.println(result.toJson());
        }catch (BsonInvalidOperationException e){
            BsonArray result = new BsonArray();
            BsonArray bsonArray= BsonArray.parse(data);
            for (BsonValue bsonValue : bsonArray) {
                String json = bsonValue.asDocument().toJson();
                Document documentByRegex = JsonUtils.getJsonArrayByRegex(Document.parse(json));
                result.add(BsonDocument.parse(documentByRegex.toJson()));
            }
            System.out.println(StringUtils.getSubstring(result.toString()));
        }
    }

    @org.junit.Test
    public void testType(){
        double a=1.00;
        String b="1.2";
        System.out.println(StringUtils.getType(a));
        String type = StringUtils.getType(a);
        if (type.equals("Double")){

        }
    }

    @org.junit.Test
    public void testSub(){
        String str="BOOKED|PAY|END_s";
        System.out.println(str.substring(0,str.lastIndexOf("_")));
    }

    @org.junit.Test
    public void testFileSystem(){
     /*   String configFile="F:\\source\\universal-simulator-dtplatform\\src\\main\\java\\com\\dtyunxi\\dtplatform\\config\\config.json";
        Config config = ConfigUtils.getConfig(configFile);
        FileSystem fileSystem = HdfsUtils.getFileSystem(config);
        String log="{ \"version\" : [\"1\", \"2\", \"3\"], \"disable_existing_loggers\" : \"true\", \"formatters\" : { \"standard\" : { \"format\" : \"m6XdN.55080.410\" }, \"logstash\" : { \"()\" : \"logstash_formatter.LogstashFormatter\" }, \"elogging_format\" : { \"format\" : \"9IC.695.480\" } }, \"handlers\" : { \"console\" : { \"class\" : \"logging.StreamHandler\", \"level\" : \"DEBUG\", \"formatter\" : \"elogging_format\" }, \"info_file_handler\" : { \"class\" : \"logging.handlers.RotatingFileHandler\", \"level\" : \"ERROR\", \"formatter\" : \"elogging_format\", \"filename\" : \"/var/log/applog/info.log\" }, \"debug_file_handler\" : { \"class\" : \"logging.handlers.RotatingFileHandler\", \"level\" : \"INFO\", \"formatter\" : \"elogging_format\", \"filename\" : \"/var/log/applog/debug.log\" }, \"error_file_handler\" : { \"class\" : \"logging.handlers.RotatingFileHandler\", \"level\" : \"WARN\", \"formatter\" : \"elogging_format\", \"filename\" : \"/var/log/applog/errors\" }, \"kafka_handler\" : { \"class\" : \"python_kafka_logging.KafkaHandler.KafkaLoggingHandler\", \"level\" : \"DEBUG\", \"formatter\" : \"logstash\", \"hosts_list\" : \"192.168.33.21:6667\", \"topic\" : \"dtyunxi_elog\" } }, \"loggers\" : { \"elog\" : { \"level\" : \"WARN\", \"handlers\" : [\"console\", \"debug_file_handler\", \"kafka_handler\"], \"propagate\" : \"no\" }, \"root\" : { \"level\" : \"WARN\", \"handlers\" : [\"console\", \"debug_file_handler\", \"kafka_handler\"] } }, \"path\" : [{ \"event\" : \"bookedTrip\" }, { \"event\" : \"bookedTripStart\" }, { \"event\" : \"bookedTripEnd\" }] }";
        for (int i = 0; i < 10; i++) {
           HdfsUtils.writeMess(fileSystem,log,"/user/admin/test/log.txt");
        }*/
    }

    @org.junit.Test
    public void testDocument(){
        String line="{\"regex\":\"(-[0-9]{2}|[0-9]{2})\\\\.([0-9]{5})\",\"type\":\"\"}";
        System.out.println(line);
        Document parse = Document.parse(line);
        System.out.println(parse.get("regex"));
        Generex regex = new Generex(parse.getString("454"));
        System.out.println(regex.random());

        System.out.println(parse.getString("type").equals("")?true:false);
    }

    @org.junit.Test
    public void testSwitch(){
        String value="";
    }

    @org.junit.Test
    public void testThread(){

        for (int i = 0; i < 3; i++) {
            new Thread(new Runnable() {
                public void run() {
                    System.out.println(Thread.currentThread());
                    for (int j = 0; j <5 ; j++) {
                        new Thread(new Runnable() {
                            public void run() {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                System.out.println(Thread.currentThread());
                            }
                        }).start();
                    }

                }
            }).start();
        }
    }

    @org.junit.Test
    public void testConfig(){
        String configFile="F:\\source\\universal-simulator-dtplatform\\src\\main\\java\\com\\dtyunxi\\dtplatform\\config\\configBak.json";
        Config config = ConfigUtils.getConfig(configFile);
        System.out.println(config.getStartTime());
        System.out.println(config.getEndTime());
        List<Model> models = config.getModels();
        for (Model model : models) {
            System.out.println(model.getModel());
            System.out.println(model.getThreads());
            System.out.println(model.getTotal());
            List<Map<String, String>> exports = model.getExports();
            for (Map<String, String> export : exports) {
                Set<Map.Entry<String, String>> entries = export.entrySet();
                for (Map.Entry<String, String> entry : entries) {
                    System.out.println(entry.getKey()+":"+entry.getValue());
                }
            }
        }
    }

    @org.junit.Test
    public void testKafka() throws Exception {
        String configFile="F:\\source\\universal-simulator-dtplatform\\src\\main\\java\\com\\dtyunxi\\dtplatform\\config\\configBak.json";
        Config config = ConfigUtils.getConfig(configFile);
        Producer<String, String> producer = KafkaUtils.getProducer(config);
        KafkaUtils.sendMess("simulator1","1313",producer);

    }
}
