import com.dtyunxi.dtplatform.utils.JsonUtils;
import com.mifmif.common.regex.Generex;
import com.mongodb.util.JSON;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.bson.*;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;


import javax.print.Doc;
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
        String json = JsonUtils.getJson("F:\\source\\universal-simulator-dtplatform\\src\\main\\resources\\test4.json");
        Document.parse(json);
        Document document = Document.parse(json);
        Document result = JsonUtils.getJsonArrayByRegex(document);
        System.out.println(result.toJson());
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
        List<String> list = new ArrayList<String>();
        list.add("zhangsan");
        list.add("lisi");
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

}
