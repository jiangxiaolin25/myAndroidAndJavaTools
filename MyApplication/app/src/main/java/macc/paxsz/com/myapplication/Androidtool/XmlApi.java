package macc.paxsz.com.myapplication.Androidtool;

import android.os.Environment;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

;import static android.R.id.list;

public class XmlApi {

    public String produceXml(){

        StringWriter stringWriter = new StringWriter();
//        ArrayList<Beauty> beautyList = getData();   其实就是需要传进来的数据
        try {
            // 获取XmlSerializer对象
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlSerializer xmlSerializer = factory.newSerializer();
            // 设置输出流对象
            xmlSerializer.setOutput(stringWriter);
            /*
             * startDocument(String encoding, Boolean standalone)encoding代表编码方式
             * standalone  用来表示该文件是否呼叫其它外部的文件。
             * 若值是 ”yes” 表示没有呼叫外部规则文件，若值是 ”no” 则表示有呼叫外部规则文件。默认值是 “yes”。
             */
            xmlSerializer.startDocument("utf-8", true);
            xmlSerializer.startTag(null, "beauties");
//            for(Beauty beauty:beautyList){
                    /*
                     * startTag (String namespace, String name)这里的namespace用于唯一标识xml标签
                     *XML 命名空间属性被放置于某个元素的开始标签之中，并使用以下的语法：
                        xmlns:namespace-prefix="namespaceURI"
                        当一个命名空间被定义在某个元素的开始标签中时，所有带有相同前缀的子元素都会与同一个命名空间相关联。
                        注释：用于标示命名空间的地址不会被解析器用于查找信息。其惟一的作用是赋予命名空间一个惟一的名称。不过，很多公司常常会作为指针来使用命名空间指向某个实存的网页，这个网页包含着有关命名空间的信息。
                     */
                xmlSerializer.startTag(null, "beauty");

                xmlSerializer.startTag(null, "name");
                xmlSerializer.text("JXL");
                xmlSerializer.endTag(null, "name");

                xmlSerializer.startTag(null, "age");
                xmlSerializer.text("27");
                xmlSerializer.endTag(null, "age");

                xmlSerializer.endTag(null, "beauty");
//            }
            xmlSerializer.endTag(null, "beauties");
            xmlSerializer.endDocument();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringWriter.toString();

    }

    /**
     * 在安卓的跟目录创建一个XML文件
     * XML文件的数据可以是一个集合，然后通过foreach语句循环覆盖
     * 需要添加读取内存权限
     */
    public   void XmlFileCreator(){
        File newxmlfile = new File(Environment.getExternalStorageDirectory()+"/new.xml");
        try{
            if(!newxmlfile.exists())
                newxmlfile.createNewFile();
        }catch(IOException e){
            Log.e("IOException", "exception in createNewFile() method");
        }
        //we have to bind the new file with a FileOutputStream
        FileOutputStream fileos = null;
        try{
            fileos = new FileOutputStream(newxmlfile);
        }catch(FileNotFoundException e){
            Log.e("FileNotFoundException", "can't create FileOutputStream");
        }
        //we create a XmlSerializer in order to write xml data
        XmlSerializer serializer = Xml.newSerializer();
        try {
            //we set the FileOutputStream as output for the serializer, using UTF-8 encoding
            serializer.setOutput(fileos, "UTF-8");
            //Write <?xml declaration with encoding (if encoding not null) and standalone flag (if standalone not null)
            serializer.startDocument(null, Boolean.valueOf(true));
            //set indentation option
            serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            //start a tag called "root"
            serializer.startTag(null, "jokes");
//            for(JokeBean joke:data) {
                serializer.startTag(null, "joke");
                //i indent code just to have a view similar to xml-tree
                serializer.startTag(null, "id");
                serializer.text("ID123456");
                serializer.endTag(null, "id");

                serializer.startTag(null, "title");
                serializer.text("title:456789");
                //set an attribute called "attribute" with a "value" for <child2>
                //serializer.attribute(null, "attribute", "value");
                serializer.endTag(null, "title");
                serializer.startTag(null, "text");
                //write some text inside <text>
                serializer.text("text:789789");
                serializer.endTag(null, "text");

                serializer.endTag(null, "joke");
//            }


            serializer.endTag(null, "jokes");
            serializer.endDocument();
            //write xml data into the FileOutputStream
            serializer.flush();
            //finally we close the file stream
            fileos.close();
        } catch (Exception e) {
            Log.e("Exception","error occurred while creating xml file");
        }
    }

    public List ParseXml() {
        List<String> myper=new ArrayList<String>();

          return myper;
        }





}
