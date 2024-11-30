package scrapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

public class Mjob_Skuld_Operation implements WebScraper {

    private List<String> getthaturl(String url) {

        List<String> urllist = new ArrayList<>();
        try{
            Document doc = Jsoup.connect(url).get();
            Elements links = doc.select("div.offer-box > div.right-area > div.date-buttons > form > div.form-group > a.btn.featured-btn");


            for (Element link : links) {
                String href = link.attr("href");
                if (!href.isEmpty()) {
                    urllist.add(href);
                }
            }
            return urllist;
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return urllist;
    }


    private String nextoneplease(String url, int previous_page) {
        StringBuilder nexturl = new StringBuilder();
        nexturl.append(url).append("?page=").append(previous_page+1);
        return nexturl.toString();
    }


    private String datascraped(String url) {


        StringBuilder data = new StringBuilder();

        try{

            Document doc = Jsoup.connect(url).get();

            Element element = doc.select("div.container > div.details-content > div.header-details > div.header-info").first();
            Element sec = doc.select("div.container > div.details-content > div.the-content").first();


            if (sec != null && element != null) {
                try{

                    Element title = element.select("h1.offer-title").first();
                    data.append("-Titre:").append(title.text()).append("\n");

                }catch(Exception e){
                    data.append("-Titre:element not found!!").append("\n");
                }



                try{

                    Element Company = element.select("ul.list-details > li:nth-child(1) > h3").first();
                    data.append("-Company:").append(Company.text()).append("\n");

                }catch(Exception e){
                    data.append("-Company:element not found!!").append("\n");
                }


                try{

                    Element location = element.select("div.location > span").first();
                    data.append("-Localisation:").append(location.text()).append("\n");

                }catch(Exception e){
                    data.append("-Localisation:element not found!!").append("\n");
                }


                try{

                    Element contract = element.select("ul.list-details > li:nth-child(2) > h3").first();
                    data.append("-Contrat:").append(contract.text()).append("\n");

                }catch(Exception e){
                    data.append("-Contrat:element not found!!").append("\n");
                }


                //try{

                    //Element experience = sec.select("div:nth-child(14) ").first();
                    //data.append("-Niveau expérience:").append(experience.text()).append("\n");

                //}catch(Exception e){
                    //data.append("-Niveau expérience:element not found!!").append("\n");
                //}




                String Exp = "(?<=\\bNiveau d'expériences requis :)(.*?)(?=\\bNiveau d'études exigé :)";
                String Std = "(?<=Niveau d'études exigé :)(.*?)(?=\\bLangue\\(s\\) exigée\\(s\\) :)";



                Pattern pattern = Pattern.compile(Exp);
                Matcher matcher = pattern.matcher(sec.text());

                Pattern patternS = Pattern.compile(Std);
                Matcher matcherS = patternS.matcher(sec.text());

                // Find and print the match
                if (matcher.find()) {
                    data.append("-Niveau d'experience :").append(matcher.group()).append("\n");
                }

                if (matcherS.find()) {
                    data.append("-Niveau d'étude :").append(matcherS.group(1).trim()).append("\n");
                }



                //try{

                    //Element studylvl = sec.select("div:nth-child(15) ").first();
                    //data.append("-Niveau d'étude:").append(studylvl.text()).append("\n");

                //}catch(Exception e){
                    //data.append("-Niveau d'étude:element not found!!").append("\n");
                //}

                data.append("-Lien:").append(url).append("\n");
            }


            return data.toString();

        } catch (Exception e) {
            return "an error occurred while trying to get that offer";
        }
    }


    @Override
    public List<String> getScrapedData(){
        List<String> data = new ArrayList<>();
        String element="";
        List<String> urllist = new ArrayList<>();
        String M_job = "https://www.m-job.ma/recherche";

        int nombreDePage =1;
        int i =0;
        while (i<nombreDePage){

            urllist = getthaturl(M_job);
            for (String url : urllist) {
                element = datascraped(url);
                data.add(element);
            }

            M_job = nextoneplease(M_job, i);

            i++;
        }

        return data;

    }


    @Override
    public void scrap() {
        // Print out the scraped job data to console
        getScrapedData().forEach(System.out::println);
    }
}
