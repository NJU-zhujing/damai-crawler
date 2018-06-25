
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class crawler {


    private List<Item> itemList;

    public crawler() {
        this.itemList = new ArrayList<Item>();
    }

    private String [] urls = {
            "https://search.damai.cn/search.html?ctl=演唱会&sctl=流行&order=1",
            "https://search.damai.cn/search.html?ctl=演唱会&sctl=民族&order=1",
            "https://search.damai.cn/search.html?ctl=演唱会&sctl=摇滚&order=1",
            "https://search.damai.cn/search.html?ctl=演唱会&sctl=音乐节&order=1",

            "https://search.damai.cn/search.html?ctl=音乐会&sctl=声乐及合唱&order=1",
            "https://search.damai.cn/search.html?ctl=音乐会&sctl=室内乐及古乐&order=1",
            "https://search.damai.cn/search.html?ctl=音乐会&sctl=独奏&order=1",
            "https://search.damai.cn/search.html?ctl=音乐会&sctl=管弦乐&order=1",

            "https://search.damai.cn/search.html?ctl=话剧歌剧&sctl=儿童剧&order=1",
            "https://search.damai.cn/search.html?ctl=话剧歌剧&sctl=歌剧&order=1",
            "https://search.damai.cn/search.html?ctl=话剧歌剧&sctl=话剧&order=1",
            "https://search.damai.cn/search.html?ctl=话剧歌剧&sctl=音乐剧&order=1",

            "https://search.damai.cn/search.html?ctl=曲苑杂坛&sctl=戏曲&order=1",
            "https://search.damai.cn/search.html?ctl=曲苑杂坛&sctl=杂技&order=1",
            "https://search.damai.cn/search.html?ctl=曲苑杂坛&sctl=相声&order=1",
            "https://search.damai.cn/search.html?ctl=曲苑杂坛&sctl=马戏&order=1",
            "https://search.damai.cn/search.html?ctl=曲苑杂坛&sctl=魔术&order=1",

            "https://search.damai.cn/search.html?ctl=舞蹈芭蕾&sctl=舞剧&order=1",
            "https://search.damai.cn/search.html?ctl=舞蹈芭蕾&sctl=舞蹈&order=1",
            "https://search.damai.cn/search.html?ctl=舞蹈芭蕾&sctl=芭蕾&order=1",

            "https://search.damai.cn/search.html?ctl=体育比赛&sctl=搏击运动&order=1",
            "https://search.damai.cn/search.html?ctl=体育比赛&sctl=格斗&order=1",
            "https://search.damai.cn/search.html?ctl=体育比赛&sctl=球类运动&order=1",
            "https://search.damai.cn/search.html?ctl=体育比赛&sctl=篮球&order=1",
            "https://search.damai.cn/search.html?ctl=体育比赛&sctl=赛车&order=1",
            "https://search.damai.cn/search.html?ctl=体育比赛&sctl=足球&order=1",

            "https://search.damai.cn/search.html?keyword=周杰伦",
            "https://search.damai.cn/search.html?keyword=陈奕迅",
            "https://search.damai.cn/search.html?keyword=林俊杰",
            "https://search.damai.cn/search.html?keyword=陈佩斯",
            "https://search.damai.cn/search.html?keyword=德云社"


    };

    private String [] types = {
            "演唱会 流行",
            "演唱会 民族",
            "演唱会 摇滚",
            "演唱会 音乐节",

            "音乐会 声乐",
            "音乐会 古乐",
            "音乐会 独奏",
            "音乐会 管弦乐",

            "歌舞剧 儿童剧",
            "歌舞剧 歌剧",
            "歌舞剧 话剧",
            "歌舞剧 音乐剧",

            "曲艺类 戏曲",
            "曲艺类 杂技",
            "曲艺类 相声",
            "曲艺类 马戏",
            "曲艺类 魔术",

            "舞蹈 舞剧",
            "舞蹈 舞蹈",
            "舞蹈 芭蕾",

            "体育比赛 搏击运动",
            "体育比赛 格斗",
            "体育比赛 球类运动",
            "体育比赛 篮球",
            "体育比赛 赛车",
            "体育比赛 足球",

            "演唱会 流行",
            "演唱会 流行",
            "演唱会 流行",
            "歌舞剧 话剧",
            "曲艺类 相声"

    };

    public void start() throws InterruptedException {
        WebDriver webDriver = new ChromeDriver();
        for (int i = 0; i < urls.length; i++) {
            webDriver.get(urls[i]);
            Thread.sleep(3000);
            Document document = Jsoup.parse(webDriver.getPageSource());
            Elements elements = document.select("ul#content_list").get(0).select("li");

            //最多一个子类10个
            for (int j = 0; j < elements.size() && j<10; j++) {
                try {
                    Element element = elements.get(j);
                    Element e1 = element.select("div").get(0).select("a").get(0);
                    Element e2 = element.select("div").get(1).select("p").get(0);
                    Item item = new Item(itemList.size(),e1.attr("title"),types[i],e2.text(),e1.select("img").get(0).attr("src"));
                    itemList.add(item);

                }catch (Exception e){
                    continue;
                }

            }

        }


    }

    public void output(String path) throws IOException {

        BufferedWriter br = new BufferedWriter(new FileWriter(new File(path+"activity.txt")));
        Iterator iterator = itemList.iterator();
        while (iterator.hasNext()){
            Item item = (Item) iterator.next();
            br.write(item.toString());
            br.newLine();
            item.download(path);
        }
        br.flush();
        br.close();


    }


    public static void main(String[] args) throws InterruptedException, IOException {

        crawler c = new crawler();
        c.start();
        c.output("/Users/zhujin/Desktop/damai/src/main/resources/img/");



    }
}

class Item{

    int id;
    String title;
    String type;
    String description;
    String imgSrc;

    public Item(int id, String title, String type, String description, String imgSrc) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.description = description;
        this.imgSrc = "https:"+imgSrc;
    }

    public void download(String path) throws IOException {
        DownloadUtil.imgDownload(id+".png",path,imgSrc);

    }

    @Override
    public String toString() {
        return id+";"+title+";"+type+";"+description;
    }

}

class DownloadUtil{

    public static void imgDownload(String name, String path, String url) throws IOException {

        URL u = new URL(url);
        URLConnection con = u.openConnection();
        InputStream is = con.getInputStream();

        // 1K的数据缓冲
        byte[] bs = new byte[1024];
        // 读取到的数据长度
        int len;
        // 输出的文件流
        File file=new File(path+name);

        OutputStream os = new FileOutputStream(file);
        // 开始读取
        while ((len = is.read(bs)) != -1) {
            os.write(bs, 0, len);
        }
        // 完毕，关闭所有链接
        os.close();
        is.close();

    }

}
